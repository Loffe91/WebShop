import Customers.Customer;
import Customers.CustomerController;
import Products.Product;
import Products.ProductRepository;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws SQLException {
        /*Skapa en instans av CustomerController och kör den
        CustomerController kundKontroller = new CustomerController();
        kundKontroller.run();*/

        // Skapa en instans av ProductRepository för att hämta produkter
        ProductRepository produktRepo = new ProductRepository();
        Map<Integer, String> produkter = new HashMap<>();

        // Hämta alla produkter och lägg till dem i HashMap
        for (Product produkt : produktRepo.getAllProducts()) {
            produkter.put(produkt.getProductId(), produkt.getName());
        }

        // Kontrollera om det finns produkter innan vi lägger till dem i kundens varukorg
        if (!produkter.isEmpty()) {
            // Skapa en kund för att lägga till produkter i varukorgen
            Customer kund = new Customer(8, "Olof", "Olofsven@gmail.com", "Olof");

            if (produkter.containsKey(1)) {
                kund.addToCart(produkter.get(1), 1);
            }
            if (produkter.containsKey(3)) {
                kund.addToCart(produkter.get(3), 2);
            }

            // Skriv ut kundens varukorg
            System.out.println("Kundens varukorg: " + kund.getCart().getProducts());
        } else {
            System.out.println("Inga produkter hittades i databasen.");
        }
    }
}
