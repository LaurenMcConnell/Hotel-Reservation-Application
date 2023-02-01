package model;

public class Driver {
    public static void main(String[] args){
        Customer customer = new Customer("first", "last", "j@domain.com");
        System.out.println(customer);
        Customer customer2 = new Customer("first", "last", "email");
        //Reservation reservation = new Reservation();
    }
}
