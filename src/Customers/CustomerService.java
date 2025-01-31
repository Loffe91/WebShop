package Customers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;



/**
 * Service-klass för kundhantering
 * Innehåller affärslogik mellan controller och repository
 */
public class CustomerService {

    // Repository som hanterar alla databasanrop
    CustomerRepository customerRepository;

    /**
     * Konstruktor för Customers.CustomerService
     * Initierar repository-lagret
     */
    public CustomerService() {
        this.customerRepository = new CustomerRepository();
    }

    /**
     * Hämtar och visar alla kunder från databasen
     * Service-lagret kan här:
     * - Formatera utskriften
     * - Lägga till affärslogik (t.ex. filtrera bort inaktiva kunder)
     * - Hantera specialfall (t.ex. om listan är tom)
     *
     * @throws SQLException vid problem med databasanrop
     */
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
            System.out.println("ID: " + customer.getCustomerId());
            System.out.println("Namn: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("-----------------");
        }
    }
    public void addCustomer() throws SQLException {
        customerRepository.addCustomer();
    }

    /**
     * Här kan man lägga till fler metoder som t.ex:
     * - getCustomerById
     * - addNewCustomer
     * - updateCustomer
     * - deleteCustomer
     * - findCustomerByEmail
     */
}