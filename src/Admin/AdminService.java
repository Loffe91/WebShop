package Admin;

import Customers.Customer;
import Customers.CustomerRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminService {
    CustomerRepository customerRepository;

    public AdminService(){
        this.customerRepository = new CustomerRepository();
    }

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
    public Customer getCustomerByEmail(String email) throws SQLException{
        return customerRepository.getCustomerByEmail(email);
    }
    public Customer getCustomerById(int id) throws SQLException{
        return customerRepository.getCustomerById(id);
    }

    public void deleteCustomer(int customerId) throws SQLException {
        // Kontrollera om kunden finns innan borttagning
        Customer customer = customerRepository.getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Ingen kund hittades med ID: " + customerId);
            return;
        }

        // Ta bort kunden
        customerRepository.deleteCustomer(customerId);
        System.out.println("Kunden med ID " + customerId + " har raderats.");
    }
}
