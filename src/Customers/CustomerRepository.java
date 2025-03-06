package Customers;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

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
                        rs.getString("email"),
                        rs.getString("password")// Hämta email
                );
                customers.add(customer);
            }
        }
        return customers;
    }

    /**
     * Metod för att lägga till kunder.
     * Kund-ID skapas automatsikt av databasen, resterande information tas in av användaren.
     */
    public void addCustomer(String name, String email, String phone ,String address, String password) throws SQLException {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if (!Pattern.matches(emailRegex, email)) {
            System.out.println("--------------------------");
            throw new IllegalArgumentException("Ogiltig e-postadress. Vänligen ange en korrekt email.");
        }

        String phoneRegex = "^\\+?\\d{7,15}$";
        if (!Pattern.matches(phoneRegex, phone)) {
            System.out.println("--------------------------");
            throw new IllegalArgumentException("Ogiltigt telefonnummer. Ange endast siffror, med valfritt '+' i början.");
        }

        String sql = "INSERT INTO customers(name, email, phone, address, password) VALUES(?, ?, ?, ?, ?)"; // Frågetecknen är placeholders för värdena som tas in av användaren
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name); // Sätter värdet av name till det första frågetecknet
                pstmt.setString(2, email); // Sätter värdet av email till det andra frågetecknet, osv, osv.
                pstmt.setString(3, phone);
                pstmt.setString(4, address);
                pstmt.setString(5, password);
                pstmt.executeUpdate();
        } catch (SQLException e){
            if(e.getMessage().contains("UNIQUE constraint failed")){ // Kollar så mailadressen ej används
                throw new SQLException("Denna email är redan registrerad. Välj en annan");
            } else {
                throw e;
            }
        }
    }

    public Customer getCustomerByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM customers WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            // Kontrollera om det finns data att hämta
           if (rs.next()){
               return new Customer(
                       rs.getInt("customer_id"),
                       rs.getString("name"),
                       rs.getString("email"),
                       rs.getString("password")
               );
           }
           else {
               System.out.println("Ingen kund hittades med den mailadressen");
               return null;
           }
        }
    }
    public Customer getCustomerById(int id) throws SQLException {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            // Kontrollera om det finns data att hämta
            if (rs.next()){
                return new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
            else {
                System.out.println("Ingen kund hittades med det ID:t");
                return null;
            }
        }
    }
    // Tar bort en kund från databasen baserat på kund-ID.

    public static void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("Ingen kund med det angivna ID:t hittades.");
            } else {
                System.out.println("Kunden med ID " + customerId + " har tagits bort.");
            }
        }
    }
    // Metod för att kontrollera inloggning. Kollar ifall email & password matchar någon customer i databasen
    public Customer loginChecker(String email, String password) throws SQLException {
        String sql = "SELECT * FROM customers WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) { // Om en kund med matchande mail hittas
                String savedPassword = rs.getString("password");

                if (!savedPassword.equals(password)) { // Om lösenord ej matchar
                    System.out.println("Felaktigt lösenord. ");
                    return null;
                }

                return new Customer( // Om matchande email & lösen hittas
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            } else {
                System.out.println("Felaktig mailadress");
                return null;
            }
        }
    }
        /**
    * Uppdaterar en kund i databasen.
    * @param customer Det uppdaterade Customer-objektet.
    * @return true om uppdateringen lyckas, annars false.
    */
        public boolean updateCustomer(Customer customer) {
            String sql = "UPDATE customers SET name = ?, email = ?, password = ? WHERE customer_id = ?";
            try (Connection conn = DriverManager.getConnection(URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, customer.getName());
                pstmt.setString(2, customer.getEmail());
                pstmt.setString(3, customer.getPassword());
                pstmt.setInt(4, customer.getUserId());
                return pstmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
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
