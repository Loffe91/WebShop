package Orders;

import java.util.Map;

/**
 * Representerar en beställning med kund-ID och en lista på produkter.
 */
public class Order {
    private int customerId;
    private Map<String, Integer> products; // Ändrat från productId till productName

    /**
     * Konstruktor för att skapa en order.
     *
     * @param customerId Kundens unika ID.
     * @param products En Map där nyckeln är produktens namn och värdet är antalet.
     */
    public Order(int customerId, Map<String, Integer> products) {
        this.customerId = customerId;
        this.products = products;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void printOrder() {
        System.out.println("Order för kund: " + customerId);
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            System.out.println(entry.getKey() + " x" + entry.getValue());
        }
    }
}
