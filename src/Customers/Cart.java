package Customers;  // Paket som innehåller klasser relaterade till kunder
import Orders.Order; // Importerar Order-klassen från Orders-paketet

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representerar en kundvagn där produkter kan läggas till, tas bort och hanteras.
 */
public class Cart {

    /**
     * En HashMap som lagrar produkter i kundvagnen.
     * Nyckeln är produktnamnet (String), och värdet är antalet av den produkten (Integer).
     */
    private Map<String, Integer> products;

    /**
     * Konstruktor som skapar en tom kundvagn.
     */
    public Cart() {
        this.products = new HashMap<>();
    }

    /**
     * Lägger till en produkt i kundvagnen.
     * Om produkten redan finns, ökas antalet med den angivna kvantiteten.
     *
     * @param productName Namnet på produkten.
     * @param quantity Antalet som ska läggas till.
     */
    public void addProduct(String productName, int quantity) {
        products.put(productName, products.getOrDefault(productName, 0) + quantity);
    }

    /**
     * Tar bort en produkt från kundvagnen.
     *
     * @param productName Namnet på produkten som ska tas bort.
     */
    public void removeProduct(String productName) {
        products.remove(productName);
    }

    /**
     * Tömmer kundvagnen på alla produkter.
     */
    public void clearCart() {
        products.clear();
    }

    /**
     * Returnerar en karta över produkterna i kundvagnen.
     *
     * @return En Map med produktnamn och motsvarande antal.
     */
    public Map<String, Integer> getProducts() {
        return products;
    }

    /**
     * Kontrollerar om kundvagnen är tom.
     *
     * @return true om kundvagnen är tom, annars false.
     */
    public boolean isEmpty() {
        return products.isEmpty();
    }

    /**
     * Beräknar det totala priset för produkterna i kundvagnen.
     *
     * @param priceList En Map med produktnamn som nyckel och pris per enhet som värde.
     * @return Det totala priset för alla produkter i kundvagnen.
     */
    public double calculateTotalPrice(Map<String, Double> priceList) {
        double total = 0;
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            total += priceList.getOrDefault(entry.getKey(), 0.0) * entry.getValue();
        }
        return total;
    }

    /**
     * Skriver ut innehållet i kundvagnen till konsolen.
     */
    public void printCart() {
        System.out.println("Varukorg:");
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            System.out.println(entry.getKey() + " x" + entry.getValue());
        }
    }

    /**
     * Skapar en beställning baserat på innehållet i kundvagnen.
     *
     * @param customerId Kundens unika ID.
     * @param products En lista över produkternas namn.
     * @param totalPrice Det totala priset för beställningen.
     * @return Ett nytt Order-objekt som representerar beställningen.
     */
        // skapa metod skicka cart till checkout?
    //public static Order placeOrder(int customerId, List<String> products, double totalPrice) {
     //return new Order(customerId, products, totalPrice);}
    }
