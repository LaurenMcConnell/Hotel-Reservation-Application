package API;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {
    private static HotelResource hr = HotelResource.getInstance();

    public static void displayMainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println("----------------------------------");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("----------------------------------");
        System.out.println("Please select a number for the menu option");
        try {
            while (scanner.hasNext()) {
                int selection = Integer.parseInt(scanner.nextLine());
                switch (selection) {
                    case 1 -> showFindReserveRoom();
                    case 2 -> showMyReservations();
                    case 3 -> showCreateAccount();
                    case 4 -> showAdminMenu();
                    case 5 -> scanner.close();
                    default -> {
                        System.out.println("Please enter only numbers 1-5");
                        displayMainMenu();
                    }
                }
            }
        } catch (Exception exception) {
            System.out.println("Please enter only number 1-5");
            displayMainMenu();
            exception.getLocalizedMessage();

        } finally {
            scanner.close();
        }
    }
    private static void showFindReserveRoom(){
        String customerEmail = "";
        String emailRegex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegex);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter CheckIn Date mm/dd/yyyy example 02/01/2020");
        String checkInDate = scanner.nextLine();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date inDate = new Date();
        try {
            inDate = format.parse(checkInDate);
        } catch (Exception e) {
            System.out.println("Invalid date format");
            showFindReserveRoom();
        }
        System.out.println("Enter CheckOut Date mm/dd/yyyy example 02/01/2020");
        String checkOutDate = scanner.nextLine();
        Date outDate = new Date();
        try {
            outDate = format.parse(checkOutDate);
        } catch (Exception e) {
            System.out.println("Invalid date format");
            showFindReserveRoom();
        }

        System.out.print("Available Rooms:\n");
        List<IRoom> availableRooms;
        availableRooms = (List<IRoom>) hr.findARoom(inDate, outDate);

        if (availableRooms.size() == 0){
            System.out.println("No rooms available for dates given. Suggested rooms for a week later:\n");
            availableRooms = recommendRooms(inDate, outDate);
            int week = 7;
            Calendar cal = Calendar.getInstance();
            cal.setTime(inDate);
            cal.add(Calendar.DAY_OF_YEAR, week);
            inDate = cal.getTime();
            cal.setTime(outDate);
            cal.add(Calendar.DAY_OF_YEAR, week);
            outDate = cal.getTime();
        }
       for (IRoom room : availableRooms){
            System.out.println(room.toString());
       }
        boolean repeat = true;
        while(repeat) {
            System.out.println("\nWould you like to book a room? y/n");
            String yOrN = scanner.next();
            try {
                switch (yOrN) {
                    case "y":
                        customerEmail = accountQuestion();
                        repeat = false;
                        break;
                    case "n":
                        displayMainMenu();
                        repeat = false;

                    default:
                        System.out.println("Please enter only y or n");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        String roomNumber = "";
        System.out.println("What room number would you like to reserve");
        roomNumber = scanner.next();
        IRoom roomToBook = hr.getRoom(roomNumber);
            if (availableRooms.contains(roomToBook)){
                hr.bookARoom(customerEmail, roomToBook, inDate, outDate);
                System.out.println("Reservation");
                Customer c = hr.getCustomer(customerEmail);
                System.out.println(c.getFirstName() + " " + c.getLastName());
                System.out.println("Room: " + roomToBook.getRoomNumber() + " - " + roomToBook.getRoomType());
                System.out.println("Price: " + roomToBook.getRoomPrice() + " per night");
                System.out.println("Checkin Date: " + checkInDate);
                System.out.println("Checkout Date: " + checkOutDate);
                displayMainMenu();
            }
            else {
                System.out.println("Not an available room\n");
                showFindReserveRoom();

            }

        }

    public static List<IRoom> recommendRooms(Date inDate, Date outDate){
        int week = 7;
        Calendar cal = Calendar.getInstance();
        cal.setTime(inDate);
        cal.add(Calendar.DAY_OF_YEAR, week);
        Date newInDate = cal.getTime();
        //System.out.println("Debugging: "+ inDate);

       //System.out.println("Debugging, New Check In:"+ newInDate);
        cal.setTime(outDate);
        cal.add(Calendar.DAY_OF_YEAR, week);
        Date newOutDate = cal.getTime();
        //System.out.println("Debugging: "+ outDate);

        //System.out.println("Debugging, New Check out:"+ newOutDate);

        List<IRoom> availableRooms;
        availableRooms = (List<IRoom>) hr.findARoom(newInDate, newOutDate);
        return availableRooms;
    }
    public static String accountQuestion(){
        Scanner scan = new Scanner(System.in);
        String emailRegex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegex);
        String customerEmail = "";


        boolean repeat = true;
        while(repeat) {
            System.out.println("Do you have an account with us? y/n");
            String yOrN = scan.next();
            try {
                switch (yOrN) {
                    case "y":
                        System.out.println("Enter Email format: name@domain.com");
                        customerEmail = scan.next();
                        if (hr.getCustomer(customerEmail) == null) {
                            System.out.println("Email not found, Please create an account first");
                            showCreateAccount();
                        }
                        return customerEmail;
                    case "n":
                        System.out.println("Please create an account first");
                        showCreateAccount();
                        repeat = false;
                    default:
                        System.out.println("Please only enter y/n");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Please enter Y or No");
                accountQuestion();
            }
        }

        return customerEmail;
    }
    private static void showMyReservations(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your email");
        String customerEmail = scanner.next();
        List<Reservation> reservs = new ArrayList<>(hr.getCustomerReservations(customerEmail));
        for (Reservation r : reservs){
            System.out.println(r.toString());
        }
        displayMainMenu();
    }
    private static void showCreateAccount(){
        Scanner scanner = new Scanner(System.in);
        String emailRegex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegex);
        System.out.println("Enter Email format: name@domain.com");
        String email = scanner.nextLine();
        if (!pattern.matcher(email).matches()) {
            System.out.println("Invalid email");
            showCreateAccount();
            throw new IllegalArgumentException("Error, Invalid email");
        }
        System.out.println("First Name");
        String firstName = scanner.nextLine();
        System.out.println("Last Name");
        String lastName = scanner.nextLine();
        hr.createACustomer(email, firstName, lastName);
        displayMainMenu();
    }
    private static void showAdminMenu(){
        AdminMenu ar = new AdminMenu();
        ar.displayAdminMenu();
    }
    public static void main(String[] args){
        displayMainMenu();
    }
}
