package Products;

import Customers.Customer;
import Customers.CustomerRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Service-klass för produkthantering
 * Innehåller affärslogik mellan controller och repository
 */
public class ProductService {
    // Repository som hanterar alla databasanrop
    ProductRepository productRepository;

    /**
     * Konstruktor för Products.ProductService
     * Initierar repository-lagret
     */
    public ProductService() {
        this.productRepository = new ProductRepository();
    }

    /**
     * Hämtar och visar alla produkter från databasen
     * Service-lagret kan här:
     * - Formatera utskriften
     * - Lägga till affärslogik (t.ex. filtrera bort inaktiva kunder)
     * - Hantera specialfall (t.ex. om listan är tom)
     *
     * @throws SQLException vid problem med databasanrop
     */
    public void showAllProducts() throws SQLException {
        // Hämta alla produkter från repository-lagret
        ArrayList<Product> products = productRepository.getAllProducts();

        // Kontrollera om vi har några produkter att visa
        if (products.isEmpty()) {
            System.out.println("Inga produkter hittades.");
            return;
        }

        // Skriv ut alla produkter med tydlig formatering
        System.out.println("\n=== Produktlista ===");
        for (Product product : products) {
            System.out.println("ID: " + product.getProductId());
            System.out.println("Namn: " + product.getName());
            System.out.println("Beskrivning: " + product.getDescription());
            System.out.println("Pris: " + product.getPrice());
            System.out.println("Antal i lager: " + product.getStockQuantity());
            System.out.println("-----------------");
        }
    }

}
