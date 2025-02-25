package Orders;

public class Order {
//hämtar det som finns i cart
// skapar en order.

    private int orderId;
    private int customerId;
    private int productId;
    private int quantity;
    private double pricePerUnit; //För att räkna ut totalpriset?

    public Order(int orderId, int customerId, int productId, int quantity) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    //Getters för att hämta värden
    public int getCustomerID() {
        return customerId;
    }

    public int getOrderID() {
        return orderId;
    }

    public int getProductID() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public double getTotalPrice(){
        return quantity * pricePerUnit;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId +
                ", Customer ID: "+ customerId +
                ", Product ID: " + productId +
                ", Quantity: " + quantity +
                ", Total Price: " + getTotalPrice();
    }

}