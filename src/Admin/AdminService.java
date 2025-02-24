package Admin;

import Customers.Customer;
import Customers.CustomerRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminService {
    CustomerRepository customerRepository;

    public void showAllUsers() throws SQLException {
        // Hämta alla kunder från repository-lagret
        ArrayList<Customer> customers = customerRepository.getAllCustomers();

        // Kontrollera om vi har några kunder att visa
        if (customers.isEmpty()) {
            System.out.println("Inga kunder hittades.");
            return;
        }

        // Skriv ut alla kunder med tydlig formatering
        System.out.println("\n=== Kundlista ===");
        for (Customer customer : customers) {
            System.out.println("ID: " + customer.getUserId());
            System.out.println("Namn: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("-----------------");
        }
    }
}
