package Orders;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Map;

public class Order {
    private static int orderCounter = 0; // Unik order-ID räknare
    private int orderId;
    private int customerId;
    private List<String> products;
    private double totalPrice;
    private LocalDateTime orderDate;
    private String status;

    // Konstruktor
    public Order(int customerId, Map<String, Integer> cartProducts) {
        this.customerId = customerId;
        this.products = new ArrayList<>();
        this.totalPrice = 0;
        this.orderDate = LocalDateTime.now();
        this.status = "Pending"; // Standardstatus vid skapande
        orderCounter++;

        // Konvertera Map till List och räkna ut totalpriset
        for (Map.Entry<String, Integer> entry : cartProducts.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                products.add(entry.getKey());
            }
        }
    }

    // Metod för att visa orderdetaljer
    public void printOrderDetails() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Products: " + products);
        System.out.println("Total Price: $" + totalPrice);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Status: " + status);
    }

    // Getters
    public int getOrderId() { return orderId; }
    public int getCustomerId() { return customerId; }
    public List<String> getProducts() { return products; }
    public double getTotalPrice() { return totalPrice; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public String getStatus() { return status; }

    // Setter för att uppdatera status
    public void setStatus(String status) {
        this.status = status;
    }
}
