package Customers;
import Orders.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    //Skapar en map med index "Produktnamn" → värdet "Antal"
    private Map<String, Integer> products;

        public Cart() {
        this.products = new HashMap<>();
        }

        public void addProduct(String productName, int quantity) {
            products.put(productName, products.getOrDefault(productName, 0) + quantity);
        }

        public void removeProduct(String productName) {
            products.remove(productName);
        }

        public void clearCart() {
            products.clear();
        }

        public Map<String, Integer> getProducts() {
            return products;
        }

        public boolean isEmpty() {
            return products.isEmpty();
        }

        public double calculateTotalPrice(Map<String, Double> priceList) {
            double total = 0;
            for (Map.Entry<String, Integer> entry : products.entrySet()) {
                total += priceList.getOrDefault(entry.getKey(), 0.0) * entry.getValue();
            }
            return total;
        }

        public void printCart() {
            System.out.println("Varukorg:");
            for (Map.Entry<String, Integer> entry : products.entrySet()) {
                System.out.println(entry.getKey() + " x" + entry.getValue());
            }
        }
        // skapa metod skicka cart till checkout?
    public static Order placeOrder(int customerId, List<String> products, double totalPrice) {
     return new Order(customerId, products, totalPrice);
    }
    }
