package API;
import Service.CustomerService;
import Service.ReservationService;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class HotelResource {
    public static HotelResource instance = null;
    public static final CustomerService customerServiceInstance = CustomerService.getInstance();
    public static final ReservationService reservationServiceInstance = ReservationService.getInstance();
    private HotelResource(){}
    public static HotelResource getInstance(){
        if (instance == null){
            instance = new HotelResource();
        }
        return instance;
    }
    public Customer getCustomer(String email){
        Set<Customer> customers = new HashSet<>();
        customers = customerServiceInstance.getAllCustomers();
        for (Customer c : customers){
            if (c.equals(customerServiceInstance.getCustomer(email))){
                return customerServiceInstance.getCustomer(email);
            }
        }
        return null;
        //should return null if email is invalid
    }
    public void createACustomer(String email, String firstName, String lastName){
        customerServiceInstance.addCustomer(firstName, lastName, email);
    }
    public IRoom getRoom(String roomNumber){
        return reservationServiceInstance.getARoom(roomNumber);
    }
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        return reservationServiceInstance.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }
    public Collection<Reservation> getCustomerReservations(String customerEmail){
        return reservationServiceInstance.getCustomerReservation(getCustomer(customerEmail));
    }
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return reservationServiceInstance.findRooms(checkIn, checkOut);
    }
}
