package model;
import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String emailRegex = "^(.+)@(.+).com$";
    private final Pattern pattern = Pattern.compile(emailRegex);

    public Customer(String firstName, String lastName, String email){
        if (!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Error, Invalid email");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    public final String getFirstName(){
        return this.firstName;
    }
    public final String getLastName(){
        return this.lastName;
    }
    public final String getEmail(){
        return this.email;
    }

    public final String toString(){
        return "First name: " + firstName + " Last name: " + lastName + " Email: " + email;
    }
}
