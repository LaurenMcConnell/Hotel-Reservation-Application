package model;

import java.util.Date;

public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
    public final Customer getCustomer(){
        return this.customer;
    }
    public final Date getCheckInDate(){
        return this.checkInDate;
    }
    public final Date getCheckOutDate() {
        return this.checkOutDate;
    }
    public final IRoom getRoom() {
        return this.room;
    }
    public final boolean equals(Object obj){
        if(this == obj)
            return true;
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        Reservation reservation = (Reservation) obj;
        return (reservation.customer.equals(this.customer)  && reservation.room.equals(this.room) && reservation.checkInDate.equals(this.checkInDate) && reservation.checkOutDate.equals(this.checkOutDate));
    }
    public final int hashCode(){
        return Integer.parseInt(room.getRoomNumber());
    }

    public final String toString(){
        return "Reservation\n"+
                customer.getFirstName() + " " + customer.getLastName() + "\n" +
                "Room: " + room.getRoomNumber() + " - " +  String.valueOf(room.getRoomType()).toLowerCase() + " bed\n" +
                "Price: $" + room.getRoomPrice() + " price per night\n" +
                "Checkin Date: " + checkInDate + "\n" +
                "Checkout Date: " + checkOutDate + "\n";



    }

}
