package Products;

import java.sql.*;
import java.util.ArrayList;

public class ProductRepository {
    private static final String URL = "jdbc:sqlite:webshop.db";

    public ArrayList<Product> getProducts() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM products");
            while(rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("price"),
                        rs.getInt("stock_quantity")
                );
                products.add(product);
            }
        }
        return products;
    }
}
