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
            System.out.println("-----------------");
            System.out.println("Inga produkter hittades.");
            System.out.println("-----------------");
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

    
    public void selectProductByName(String productName) throws SQLException {
        // Kontrollera om input är null eller tom
        if (productName == null || productName.trim().isEmpty()) {
            System.out.println("-------------------");
            System.out.println("Ingen produkt hittad, sök på produkt.");
            return;
        }

        // Hämta alla produkter från databasen
        ArrayList<Product> products = productRepository.getAllProducts();

        // Kontrollera om listan är tom
        if (products == null || products.isEmpty()) {
            System.out.println("-------------------");
            System.out.println("Inga produkter hittades.");
            return;
        }

        // Trimma och konvertera söktermen till lowercase för att göra sökningen mer flexibel
        productName = productName.trim().toLowerCase();

        boolean found = false;

        for (Product product : products) {
            if (product.getName().toLowerCase().contains(productName)) {
                System.out.println("-------------------");
                System.out.println("ID: " + product.getProductId());
                System.out.println("Namn: " + product.getName());
                System.out.println("Beskrivning: " + product.getDescription());
                System.out.println("Pris: " + product.getPrice());
                System.out.println("Antal i Lager: " + product.getStockQuantity());
                found = true;
            }
        }

        // Om ingen matchande produkt hittades
        if (!found) {
            System.out.println("-------------------");
            System.out.println("Inga produkter hittades.");
        }
    }

    /**
     * Metod för att printa ut all produkter i en vald kategori.
     */
    public void selectCategories(String category) throws SQLException {
        ArrayList<Product> products = productRepository.selectCategories(category);

        if (products.isEmpty()) {
            System.out.println("-----------------");
            System.out.println("Inga produkter hittades.");
            System.out.println("-----------------");
            return;
        }

        System.out.println("\n=== Produktlista " + category + " ===");
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
