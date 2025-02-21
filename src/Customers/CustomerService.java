package Customers;

import Admin.Admin;

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
            System.out.println("ID: " + customer.getUserId());
            System.out.println("Namn: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("-----------------");
        }
    }

    /** Metod som anropas i CustomerController-loopen
     * Tar input av användaren och skickar vidare till customerRepository-metoden
     * Som i sin tur skapar kunden
     */
    public void addCustomer(String name, String email, String phone, String address, String password) throws SQLException {
        try { // Kollar så att kunden kan skapas, t.ex ingen dublett av email
            customerRepository.addCustomer(name, email, phone, address, password);
            System.out.println("Kunden har registrerats. "); // Om inga fel hittas skapas kunden
        } catch (SQLException e){
            System.out.println("Fel: " + e.getMessage()); // Felmeddelande ifall kund ej kan skapas
        }
    }
    /** Metod som anropas i CustomerController-loopen
     * Tar input av användaren och skickar vidare till customerRepository-metoden
     * Som i sin tur returnerar kunden
     */
    public boolean updateCustomer(int userId, String name, String email, String password) throws SQLException {
        return customerRepository.updateCustomer(userId, name, email, password);
    }

    public Customer getCustomerByEmail(String email) throws SQLException{
        return customerRepository.getCustomerByEmail(email);
    }
    public Customer getCustomerById(int id) throws SQLException{
        return customerRepository.getCustomerById(id);
    }
    // Metod som anropas i CustomerController
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
    /**
     * Här kan man lägga till fler metoder som t.ex:
     * - getCustomerById
     * - addNewCustomer
     * - updateCustomer
     * - deleteCustomer
     * - findCustomerByEmail
     */