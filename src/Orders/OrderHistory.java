package Orders;

// Klass som representerar en kunds orderhistorik
public class OrderHistory {
    private int orderId;
    private String orderDate;
    private int productId;
    private int quantity;
    private double unitPrice;


    public OrderHistory(int orderId, String orderDate, int productId, int quantity, double unitPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    @Override
    public String toString(){
        return "Order ID: " + orderId +
            ", Date: " + orderDate +
            ", Product ID: " + productId +
            ", Quantity: " + quantity +
            ", Price: " + unitPrice;
    }
}
