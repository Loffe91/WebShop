package Customers;

import java.sql.*;
import java.util.ArrayList;

/**
 * Repository-klass för kundhantering
 * Hanterar alla databasoperationer för Customers.Customer-entiteten
 * Innehåller även databasanslutning för att göra koden tydligare
 */
public class CustomerRepository {

    /**
     * URL till SQLite-databasen
     * Denna används i varje metod för att ansluta till databasen
     */
    private static final String URL = "jdbc:sqlite:webshop.db";

    /**
     * Hämtar alla kunder från databasen
     * Skapar en ny anslutning, hämtar data och stänger anslutning automatiskt
     *
     * @return ArrayList med alla kunder
     * @throws SQLException vid problem med databasanrop
     */
    public ArrayList<Customer> getAllCustomers() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();

        // try-with-resources stänger automatiskt Connection, Statement och ResultSet
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {

            // Loopa igenom alla rader från databasen
            while (rs.next()) {
                // Skapa ett nytt Customers.Customer-objekt från varje databasrad
                Customer customer = new Customer(
                        rs.getInt("customer_id"),     // Hämta ID från customer_id kolumnen
                        rs.getString("name"),   // Hämta förnamn,    // Hämta efternamn
                        rs.getString("email")         // Hämta email
                );
                customers.add(customer);
            }
        }
        return customers;
    }
    // Metod för att lägga till kunder. Kund-ID skapas automatiskt av databasen, resterande info tas in av användaren
    public void addCustomer(String name, String email, String phone ,String address, String password) throws SQLException {
        String sql = "INSERT INTO customers(name, email, phone, address, password) VALUES(?, ?, ?, ?, ?)"; // Frågetecknen är placeholders för värdena som tas in av användaren
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name); // Sätter värdet av name till det första frågetecknet
            pstmt.setString(2, email); // Sätter värdet av email till det andra frågetecknet, osv, osv.
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            pstmt.setString(5, password);
            pstmt.executeUpdate();
        }
    }
    /**
     * Här kan fler metoder läggas till som t.ex:
     * - addCustomer
     * - getCustomerById
     * - updateCustomer
     * - deleteCustomer
     * - findCustomerByEmail
     *
     * Varje metod kommer följa samma mönster:
     * 1. Skapa Connection med DriverManager.getConnection(URL)
     * 2. Skapa Statement eller PreparedStatement
     * 3. Utför databasoperationen
     * 4. Hantera resultatet
     * 5. Låt try-with-resources stänga alla resurser
     */
}