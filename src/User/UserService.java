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
    private CustomerRepository customerRepository;
    private Admin adminUser;

    public UserService(){
        this.customerRepository = new CustomerRepository();
        this.adminUser = new Admin(); // Hårdkodad admin vid programstart. Finns ingen admin i databasen
    }

    // Metod för att logga in
    public User login(String email, String password) throws SQLException {
        // Kontrollera om det är en admin eller customer som försöker logga in
        if(email.equals(adminUser.getEmail()) && password.equals(adminUser.getPassword())){
            return adminUser; // Om en admin matchar, returneras adminUser
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