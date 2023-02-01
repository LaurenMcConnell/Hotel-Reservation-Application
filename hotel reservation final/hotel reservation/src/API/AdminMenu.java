package API;

import Service.CustomerService;
import Service.ReservationService;
import model.IRoom;
import model.Room;
import model.RoomType;
import model.Customer;
import model.Reservation;
import java.util.*;
import java.util.Scanner;
import static API.MainMenu.displayMainMenu;

public class AdminMenu {
    private static AdminResource ar = AdminResource.getInstance();
        public static void displayAdminMenu() {
        System.out.println("Admin Menu");
        System.out.println("----------------------------------");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Add Test Data");
        System.out.println("6. Back to Main Menu");
        System.out.println("----------------------------------");
        System.out.println("Please select a number for the menu option");

        Scanner scanner = new Scanner(System.in);
        try {
            while (scanner.hasNext()) {
                int selection = Integer.parseInt(scanner.next());
                switch (selection) {
                    case 1 -> showAllCustomers();
                    case 2 -> showAllRooms();
                    case 3 -> showReservations();
                    case 4 -> addRoom();
                    case 5, 6 -> showMainMenu();
                    default -> {
                        System.out.println("Please enter only numbers 1-6");
                        displayAdminMenu();
                    }
                }
            }
        } catch (Exception exception) {
            System.out.println("Please only enter numbers from 1-6");
            displayAdminMenu();
        } finally {
            scanner.close();
        }
    }
    private static void showMainMenu(){
        displayMainMenu();
    }
    private static void showAllCustomers() {
        Set<Customer> customerSet = (Set<Customer>) ar.getAllCustomers();
        for (Customer c : customerSet) {
            System.out.println(c);
        }
        displayAdminMenu();
    }
    private static void showAllRooms(){
        List<IRoom> rooms = (List<IRoom>) ar.getAllRooms();
        for (IRoom r : rooms){
            System.out.println(r.toString());
        }
        displayAdminMenu();
    }
    private static void showReservations(){
        ar.displayAllReservations();
        displayAdminMenu();
    }
    private static void addRoom () {
        Scanner scanner = new Scanner(System.in);
        List<IRoom> rooms = new ArrayList<IRoom>();
        System.out.println("Enter room number");
        int roomNum = 0;
        try {
            roomNum = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid room number");
            addRoom();
        }
        String rm = Integer.toString(roomNum);
        List<IRoom> allRooms = (List<IRoom>) ar.getAllRooms();


        for (IRoom ir : allRooms) {
            if (ir.getRoomNumber().equals(rm)) {
                System.out.print("Room already created\n");
                addRoom();
            }
        }
        System.out.println("Enter price per night");
        double price = 0;
        try {
            price = Double.parseDouble(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid price");
            addRoom();
        }
        System.out.println("Enter room type: 1 for single bed, 2 for double bed");
        int bedType = 0;
        try {
            bedType = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid RoomType");
            addRoom();
        }
        RoomType rt = null;
        if (bedType == 1) {
            rt = RoomType.SINGLE;
        }
        if (bedType == 2) {
            rt = RoomType.DOUBLE;
        }

        Room r = new Room(rm, price, rt);
        IRoom ir = r;
        rooms.add(ir);
        ar.addRoom(rooms);
        addAnotherRoom();
   }
        private static void addAnotherRoom(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Would you like to add another room y/n");
            try {
                while (scanner.hasNext()) {
                    String selection = scanner.next();
                    switch (selection) {
                        case "y" -> addRoom();
                        case "n" -> displayAdminMenu();
                        default -> {
                            System.out.println("Please enter Y (Yes) or N (No)");
                            addAnotherRoom();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.getLocalizedMessage();
            } finally {
                scanner.close();
            }
            displayAdminMenu();
        }

}



