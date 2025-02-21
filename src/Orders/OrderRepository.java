package Orders;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class OrderRepository {
    private static final String URL = "jdbc:sqlite:webshop.db";

    public void saveOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders (customer_id, order_date, total_price) VALUES (?, CURRENT_TIMESTAMP, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, order.getCustomerId());
            pstmt.setDouble(2, order.getTotalPrice());
            pstmt.executeUpdate();
        }
    }
}