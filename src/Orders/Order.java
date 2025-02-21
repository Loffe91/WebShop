package Orders; // Paket som innehåller orderrelaterade klasser

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Representerar en order i systemet.
 */
public class Order {
    /**
     * Statisk räknare för att generera unika order-ID:n.
     */
    private static int orderCounter = 0;

    private int orderId;         // Unikt order-ID
    private int customerId;      // Kundens ID
    private List<String> products; // Lista över produkternas namn i ordern
    private double totalPrice;   // Totalt pris för ordern
    private LocalDateTime orderDate; // Datum och tid när ordern skapades
    private String status;       // Orderstatus (ex. "Pending", "Shipped", "Completed")

    /**
     * Konstruktor för att skapa en ny order baserad på innehållet i en kundvagn.
     *
     * @param customerId Kundens ID.
     * @param cartProducts En karta där nyckeln är produktnamnet och värdet är antalet.
     */
    public Order(int customerId, Map<String, Integer> cartProducts) {
        this.customerId = customerId;
        this.products = new ArrayList<>();
        this.totalPrice = 0;
        this.orderDate = LocalDateTime.now(); // Sätter orderdatum till aktuell tidpunkt
        this.status = "Pending"; // Standardstatus vid skapande
        orderCounter++;

        // Konverterar cartProducts (Map) till en lista av produktnamn
        for (Map.Entry<String, Integer> entry : cartProducts.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                products.add(entry.getKey());
            }
        }
    }

    /**
     * Skriver ut orderdetaljer till konsolen.
     */
    public void printOrderDetails() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Products: " + products);
        System.out.println("Total Price: $" + totalPrice);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Status: " + status);
    }

    // Getters

    /**
     * Returnerar order-ID.
     * @return orderId
     */
    public int getOrderId() { return orderId; }

    /**
     * Returnerar kundens ID.
     * @return customerId
     */
    public int getCustomerId() { return customerId; }

    /**
     * Returnerar listan över produkter i ordern.
     * @return Lista med produktnamn
     */
    public List<String> getProducts() { return products; }

    /**
     * Returnerar det totala priset för ordern.
     * @return totalPrice
     */
    public double getTotalPrice() { return totalPrice; }

    /**
     * Returnerar datumet då ordern skapades.
     * @return orderDate
     */
    public LocalDateTime getOrderDate() { return orderDate; }

    /**
     * Returnerar orderstatusen.
     * @return status
     */
    public String getStatus() { return status; }

    // Setter

    /**
     * Uppdaterar orderns status.
     * @param status Ny status för ordern (exempelvis "Shipped" eller "Completed").
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
