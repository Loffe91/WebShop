package Orders;

import java.sql.*;
import java.util.ArrayList;


public class OrderRepository {

    private static final String URL = "jdbc:sqlite:webshop.db";

    // Metod för att skapa order och hämta ett orderId med customerId som argument
    public int createOrder(int customerId) throws SQLException {

        String sql = "INSERT INTO orders (customer_id) VALUES (?)";
        try (Connection conn = DriverManager.getConnection(URL)){

            PreparedStatement pstmt = conn.prepareStatement(sql);
            // Sätter värdet av customer_id i databasen till det vi skickade in genom customerId
            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
            // Hämtar värdet av order_id i databasen
            try(ResultSet rs = pstmt.getGeneratedKeys()){
                if(rs.next()){
                    return rs.getInt(1); // columnindex 1 = order_id
                }
            }
        }
        return -1;
    }
    // Metod för att lägga in valda produkter och deras orderId i databasen
    public void orderProductInsert(int orderId, ArrayList<OrderProduct> orderProducts) throws SQLException{
        String sql = "Insert INTO orders_products (order_id, product_id, quantity, unit_price) " +
                     "VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // Loopar igenom ArrayListen med OrderProducts
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
    // Metod för att hämta priset på en vara, baserat på productId
    public double getPrice(int productId) throws SQLException {
        String sql = "SELECT price FROM products WHERE product_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setInt(1, productId);
                ResultSet rs = pstmt.executeQuery();

                if(rs.next()){
                    return rs.getDouble("price");
            }else {
                    System.out.println("Felaktig input ");
                    return -1;
                }
        }
    }
    // Metod för att uppdatera lagerstatusen efter köp
    public void updateStock(ArrayList<OrderProduct> products) throws SQLException{
        String sql = "UPDATE products SET stock_quantity = stock_quantity - ? WHERE product_id = ?";
        try(Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            for (OrderProduct product : products){
                pstmt.setInt(1, product.getQuantity());
                pstmt.setInt(2, product.getProductId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();

        }
    }
    // Metod för att kontrollera att det finns tillräckligt många varor i lagret vid köp
    public boolean stockStatus(int productId, int quantity) throws SQLException{
        String sql = "SELECT stock_quantity FROM products WHERE product_id = ?";
        try(Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                int stock = rs.getInt("stock_quantity");
                return stock >= quantity; // Returnerar true ifall lagret är minst lika stort som quantity
            } else {
                return false;
            }
        }
    }
    // Metod för att visa orderhistorik
    public ArrayList <OrderHistory> getOrderHistory(int customerId) throws SQLException{
        String sql = "SELECT o.order_id, o.order_date, op.product_id, op.quantity, op.unit_price " +
                     "FROM orders o " +
                     "JOIN orders_products op ON o.order_id = op.order_id " +
                     "WHERE o.customer_id = ? " +
                     "ORDER BY o.order_date DESC";
        // Arraylist som håller OrderHistory-objekt
        ArrayList<OrderHistory> orderHistoryList = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            // Så länge det finns fler ordrar att hämta, skapas ett OrderHistory-objekt som läggs till i arraylisten
            while (rs.next()){
                orderHistoryList.add(new OrderHistory(
                        rs.getInt("order_id"),
                        rs.getString("order_date"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("unit_price")

                ));

            }
            System.out.println("Antal ordrar hittade: "+orderHistoryList.size());
        }
        return orderHistoryList;
    }

}
