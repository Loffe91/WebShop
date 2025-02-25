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
    // Metod som anropas i CustomerController


    public boolean updateCustomerInfo(int customerId, String name, String email, String password) throws SQLException {
        Customer currentCustomer = customerRepository.getCustomerById(customerId); // Hämtar den befintliga kunden
        if(currentCustomer == null){ // Om ingen kund matchar
            System.out.println("Kunden kunde inte hittas");
            return false;
        }
        if (name == null || name.isBlank()) { // Om namn är null, behåller vi det gamla värdet
            name = currentCustomer.getName();
        }
        if (email == null || email.isBlank()){
            email = currentCustomer.getEmail(); // ---------- !! ----------------
        }
        if (password == null || password.isBlank()){ // ---------- !! ---------
            password = currentCustomer.getPassword();
        }
        Customer updatedCustomer = new Customer(customerId, name, email, password);
        return customerRepository.updateCustomer(updatedCustomer); // Skickar den uppdaterade kunden till databasen
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