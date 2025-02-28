package Orders;

import Customers.Cart;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class OrderRepository {

    private static final String URL = "jdbc:sqlite:webshop.db";

    // Metod för att skapa order med customerId som argument
    public int createOrder(int customerId) throws SQLException {

        String sql = "INSERT INTO orders (customer_id) VALUES (?)";
        try (Connection conn = DriverManager.getConnection(URL)){

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
            // Hämtar värdet av order_id i databasen
            try(ResultSet rs = pstmt.getGeneratedKeys()){
                if(rs.next()){
                    return rs.getInt(1); // columnindex 1 = order_id
                }
            }
        }
        return 0;
    }

    public void orderProductInsert(int orderId, ArrayList<OrderProduct> orderProducts) throws SQLException{
        String sql = "Insert INTO orders_products (order_id, product_id, quantity, unit_price) " +
                     "VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL)){
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for(OrderProduct orderProduct : orderProducts) {
                pstmt.setInt(1, orderId);
                pstmt.setInt(2, orderProduct.getProductId());
                pstmt.setInt(3, orderProduct.getQuantity());
                pstmt.setDouble(4, orderProduct.getUnit_price());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    public double getPrice(int productId) throws SQLException {

        return 5;
    }

}
