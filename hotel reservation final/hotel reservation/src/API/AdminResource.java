package API;
import Service.CustomerService;
import Service.ReservationService;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class AdminResource {
    public static AdminResource instance = null;
    public static final CustomerService customerServiceInstance = CustomerService.getInstance();
    public static final ReservationService reservationServiceInstance = ReservationService.getInstance();
    private AdminResource(){}
    public static AdminResource getInstance(){
        if (instance == null){
            instance = new AdminResource();
        }
        return instance;
    }
    public Customer getCustomer(String email){
        return customerServiceInstance.getCustomer(email);
    }
    public void addRoom(List<IRoom> rooms){
        for (IRoom room: rooms) {
            reservationServiceInstance.addRoom(room);
        }
    }
    public Collection<IRoom> getAllRooms(){
        return reservationServiceInstance.getAllRooms();
    }
    public Collection<Customer> getAllCustomers(){
        return customerServiceInstance.getAllCustomers();
    }
    public void displayAllReservations(){
        reservationServiceInstance.printAllReservations();
    }
}
