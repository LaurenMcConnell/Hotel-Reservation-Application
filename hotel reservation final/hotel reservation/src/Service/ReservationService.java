package Service;
import model.*;

import java.util.*;
import java.util.stream.Collectors;

import static model.RoomType.SINGLE;

public class ReservationService {

    private List<IRoom> roomList = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    public static ReservationService instance = null;
    private ReservationService(){
        Room test = new Room("100", 135.00, SINGLE);
        addRoom((IRoom)test);
    }
    public static ReservationService getInstance() {
        if (instance == null){
            instance = new ReservationService();
        }


        return instance;
    }

    public void addRoom(IRoom room){
        roomList.add(room);

    }
    public IRoom getARoom(String roomID){
        //treating roomID as if it is the number of the room and not part of the key/value
        for (IRoom r : roomList){
            if (r.getRoomNumber().equals(roomID)){
               return r;
            }
        }
        return null;
    };
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reserv = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reserv);
        return reserv;
    }
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Date now = new Date();
        List<IRoom> availableRooms = roomList;
        Set<IRoom> toReturn = new HashSet<>();




        //STREAMS

        List <IRoom> unavailableRooms = reservations.stream().filter(r ->
                (checkOutDate.after(r.getCheckInDate()) && checkInDate.before(r.getCheckOutDate()))).map(Reservation::getRoom).collect(Collectors.toCollection(ArrayList::new));
        availableRooms = roomList.stream().filter(r -> !unavailableRooms.contains(r)).collect(Collectors.toCollection(ArrayList::new));

        /*for (Reservation reserv : reservations){
            Date takenCheckInDate = reserv.getCheckInDate();
            Date takenCheckOutDate = reserv.getCheckOutDate();
            if (takenCheckInDate.after(checkInDate) && takenCheckInDate.before(checkOutDate) //Reserved checkin date falls within new reservation
                || takenCheckInDate.before(checkInDate) && takenCheckOutDate.after(checkOutDate) //new reservation falls within old reservation
                || takenCheckInDate.before(checkInDate) && takenCheckOutDate.before(checkOutDate)) //Reserved checkout date falls within new reservation
            //    || takenCheckInDate.equals(checkInDate) || takenCheckOutDate.equals(checkOutDate))//Needed?
            {
                    availableRooms.remove((IRoom) reserv.getRoom());
            }
        }*/

        return availableRooms;
    }
    public Collection<Reservation> getCustomerReservation(Customer customer) {
        List<Reservation> customerReservation = new ArrayList<>();
        for (Reservation reserv : reservations) {
            Customer c = reserv.getCustomer();
            if (c.equals(customer)) {
                customerReservation.add(reserv);
            }
        }
        return customerReservation;

    }
    public List<IRoom> getAllRooms(){
        return roomList;
    }
    public void printAllReservations() {

            for (Reservation reserv : reservations) {
                System.out.println(reserv.toString());
            }
        }
}
