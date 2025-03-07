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
     * Konstruktor för CustomerService
     * Initierar repository-lagret
     */
    public CustomerService() {
        this.customerRepository = new CustomerRepository();
    }

    /**
     * Lägger till en ny kund i databasen
     *
     * @param name Kundens namn
     * @param email Kundens e-postadress
     * @param phone Kundens telefonnummer
     * @param address Kundens adress
     * @param password Kundens lösenord
     * @throws SQLException vid problem med databasanrop
     */
    public void addCustomer(String name, String email, String phone, String address, String password) throws SQLException {
        try {
            // Kollar så att kunden kan skapas, t.ex. ingen dublett av email
            customerRepository.addCustomer(name, email, phone, address, password);
            System.out.println("Kunden har registrerats."); // Om inga fel hittas skapas kunden
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage()); // Felmeddelande ifall kund ej kan skapas
        }
    }

    /**
     * Uppdaterar en kunds information i databasen
     *
     * @param customerId Kundens unika ID
     * @param name Uppdaterat namn (om null eller tomt, behålls det gamla)
     * @param email Uppdaterad e-postadress (om null eller tomt, behålls det gamla)
     * @param password Uppdaterat lösenord (om null eller tomt, behålls det gamla)
     * @return true om uppdateringen lyckades, annars false
     * @throws SQLException vid problem med databasanrop
     */
    public boolean updateCustomerInfo(int customerId, String name, String email, String password) throws SQLException {
        Customer currentCustomer = customerRepository.getCustomerById(customerId); // Hämtar den befintliga kunden
        if (currentCustomer == null) { // Om ingen kund matchar
            System.out.println("Kunden kunde inte hittas");
            return false;
        }
        // Behåller gamla värden om nya värden saknas
        if (name == null || name.isBlank()) {
            name = currentCustomer.getName();
        }
        if (email == null || email.isBlank()) {
            email = currentCustomer.getEmail();
        }
        if (password == null || password.isBlank()) {
            password = currentCustomer.getPassword();
        }
        Customer updatedCustomer = new Customer(customerId, name, email, password);
        return customerRepository.updateCustomer(updatedCustomer); // Skickar den uppdaterade kunden till databasen
    }

    /**
     * Tar bort en kund från databasen
     *
     * @param customerId Kundens unika ID
     * @throws SQLException vid problem med databasanrop
     */
    public void deleteCustomer(int customerId) throws SQLException {
        customerRepository.deleteCustomer(customerId);
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