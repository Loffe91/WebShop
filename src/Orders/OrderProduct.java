package Orders;

// Klass som representerar OrderProduct i databasen
public class OrderProduct {
    private int productId;
    private int quantity;
    private double unit_price;

    public OrderProduct(int productId, int quantity, double unit_price){
        this.productId = productId;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }
}
