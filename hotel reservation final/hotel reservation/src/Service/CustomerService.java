package Service;
import model.Customer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomerService {
    private static final Set<Customer> customerSet = new HashSet<>();
    private static CustomerService instance = null;
    private CustomerService(){
        addCustomer("L", "M", "l@c.com");
    }
    public static CustomerService getInstance(){
        if (instance == null){
            instance = new CustomerService();
        }
        return instance;
    }
    public void addCustomer(String firstName, String lastName, String email){
            Customer customer = new Customer(firstName, lastName, email);
            customerSet.add(customer);

    }
    public Customer getCustomer(String customerEmail){
        Set<Customer> allCustomers = new HashSet<>();
        allCustomers = getAllCustomers();
        for(Customer cust : allCustomers)
            if (cust.getEmail().equals(customerEmail))
                return cust;
        return null;
    }
    public Set<Customer> getAllCustomers(){
        return customerSet;
    }
}
