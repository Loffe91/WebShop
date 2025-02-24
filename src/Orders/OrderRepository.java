package Orders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * OrderRepository hanterar databaskommunikation för att spara ordrar i systemet.
 */
class OrderRepository {
    // Databasens anslutnings-URL (SQLite används här)
    private static final String URL = "jdbc:sqlite:webshop.db";

    /**
     * Sparar en order i databasen.
     *
     * @param order Order-objektet som ska sparas.
     * @throws SQLException Om något går fel med databasanropet.
     */
    public void saveOrder(Order order) throws SQLException {
        // SQL-fråga för att infoga en ny order i tabellen 'orders'
        String sql = "INSERT INTO orders (customer_id, order_date, total_price) VALUES (?, CURRENT_TIMESTAMP, ?)";

        // Försöker ansluta till databasen och exekvera SQL-frågan
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Sätter in värdena i SQL-frågan
            pstmt.setInt(1, order.getCustomerId());  // Lägger till kundens ID
            pstmt.setDouble(2, order.getTotalPrice()); // Lägger till totalbeloppet för ordern

            // Exekverar SQL-frågan och sparar ordern i databasen
            pstmt.executeUpdate();
        }
    }
}