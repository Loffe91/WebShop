package User;

import Admin.Admin;
import Customers.Customer;
import Customers.CustomerRepository;
import java.sql.*;

/**
 * Service-klass för inloggning
 * Ska hantera och separera AdminService och CustomerService
 */

public class UserService {
    // Repo för databashantering
    CustomerRepository customerRepository;

    public UserService(){
        this.customerRepository = new CustomerRepository();
    }

    // Metod för att logga in
    public User login(String email, String password) throws SQLException {
        // Kontrollera om det är en admin eller customer som försöker logga in
        if(email.equals("admin@webshop.com") && password.equals("123")){
            return new Admin(); // Om mail & lösen matchar, returneras ett nytt Adminobjekt
        }
        // Om inloggningen ej matchar admin, testa om den matchar en kund
        Customer customer = customerRepository.loginChecker(email, password);

        if(customer != null){ // Om customer ej är null, dvs matchande kund hittas
            return customer; // Returneras customer
        }
        else { // Om varken matchande admin eller kund hittas
            return null; // Returneras null
        }
    }
}