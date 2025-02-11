package Products;

import java.sql.*;
import java.util.ArrayList;

public class ProductRepository {

    /**
     * URL till SQLite-databasen
     * Denna används i varje metod för att ansluta till databasen
     */
    private static final String URL = "jdbc:sqlite:webshop.db";

    /**
     * Hämtar alla produkter från databasen
     * Skapar en ny anslutning, hämtar data och stänger anslutning automatiskt
     *
     * @return ArrayList med alla produkter
     * @throws SQLException vid problem med databasanrop
     */
    public ArrayList<Product> getAllProducts() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();

        // try-with-resources stänger automatiskt Connection, Statement och ResultSet
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            // Loopa igenom alla rader från databasen
            while (rs.next()) {
                // Skapa ett nytt Product.Product-objekt från varje databasrad
                Product product = new Product(
                        rs.getInt("product_id"),     // Hämta ID från product_id kolumnen
                        rs.getString("name"),   // Hämta namn
                        rs.getString("description"),  // Hämta description
                        rs.getDouble("price"), // Hämta pris
                        rs.getInt("stock_quantity") // Hämta lagerstatus
                );
                products.add(product);
            }
        }
        return products;
    }
}
