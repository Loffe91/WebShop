package Products;

import java.sql.*;
import java.util.ArrayList;

public class ProductRepository {

    /**
     * URL till SQLite-databasen.
     * Denna används i varje metod för att ansluta till databasen.
     */
    private static final String URL = "jdbc:sqlite:webshop.db";

    /**
     * Hämtar alla produkter från databasen.
     * Skapar en ny anslutning, hämtar data och stänger anslutning automatiskt.
     *
     * @return ArrayList med alla produkter
     * @throws SQLException vid problem med databasanrop
     */
    public ArrayList<Product> getAllProducts() throws SQLException {
        ArrayList<Product> products = new ArrayList<>(); // ArrayList som håller Product-objekt

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
                products.add(product); // Lägger till product-objektet i ArrayListen
            }
        }
        return products; // Returnerar ArrayListen
    }

    /**
     * Hämtar alla produkter från databasen beroende på kategori.
     * Skapar en ny anslutning, hämtar data och stänger anslutning automatiskt.
     *
     * @return ArrayList med alla produkter i en kategori
     * @throws SQLException vid problem med databasanrop
     */
    public ArrayList<Product> selectCategories(String category) throws SQLException {
        String sql = "SELECT c.name AS category, p.product_id, p.name, p.description, p.price, p.stock_quantity " +
                "FROM products p " +
                "JOIN products_categories pc ON p.product_id = pc.product_id " +
                "JOIN categories c ON pc.category_id = c.category_id " +
                "WHERE c.name = ? " +
                "ORDER BY p.product_id;";



        ArrayList<Product> products = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setString(1, category);
             ResultSet rs = pstmt.executeQuery();

             while (rs.next()) {
                 Product product = new Product(
                         rs.getInt("product_id"),
                         rs.getString("name"),
                         rs.getString("description"),
                         rs.getDouble("price"),
                         rs.getInt("stock_quantity")
                );
                 products.add(product);
             }
         }
        return products;
    }

    public void updateProductPrice(int productId, double newPrice) throws SQLException {
        String sql = "UPDATE products SET price = ? WHERE product_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)){

           pstmt.setDouble(1, newPrice);
           pstmt.setInt(2, productId);

            int priceUpdate = pstmt.executeUpdate();
            if (priceUpdate >0) {
                System.out.println("Produktens pris har uppdaterats till " + newPrice + " SEK");
            } else {
                System.out.println("Ingen produkt hittades med ID " + productId);
            }
        }

    }

    public void updateProductStock(int productId, int newStock) throws SQLException {
        String sql = "UPDATE products SET stock_quantity = ? where product_id = ?";

        try(Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, newStock);
            pstmt.setInt(2, productId);

            if (newStock >= 0){
                pstmt.executeUpdate();
                System.out.println("Lagerstatus för produkt "+productId+" har uppdaterats. Ny lagerstatus: "+newStock);
            }
            else {
                System.out.println("Felaktig input. Lagerstatus ej ändrad.");
            }
        }

    }



    /*// Onödig metod?
    public ArrayList<Categories> getAllCategories() throws SQLException {
        ArrayList<Categories> categories = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM categories")) {

            while (rs.next()) {
                Categories category = new Categories(
                        rs.getInt("category_id"),
                        rs.getString("name")
                );
                categories.add(category);
            }
        }
        return categories;

    }

    // Onödig metod?
    public ArrayList<Product> getProductsByCategory(String category) throws SQLException {
        ArrayList<Product> products = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products WHERE category='" + category + "'")) {

            while (rs.next()) {
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
    }*/
}
