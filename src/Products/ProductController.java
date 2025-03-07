package Products;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductController {
    // Service-lager för produkthantering, hanterar affärslogik
    ProductService productService;

    // Scanner för användarinput
    Scanner scanner;

    /**
     * Konstruktor för Products.ProductController
     * Initierar service och scanner
     */
    public ProductController() {
        // Skapa instanser av nödvändiga objekt
        this.productService = new ProductService();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Huvudloop för produkthantering
     * Visar meny och hanterar användarval
     */
    public void run() {
        while (true) {
            try {
                // Skriv ut menyalternativ direkt i run-metoden för tydlighet
                System.out.println("\n=== Produkthantering ===");
                System.out.println("1. Visa alla produkter");
                System.out.println("2. Visa kategorier");
                System.out.println("3. Sök produkt");
                System.out.println("0. Avsluta");
                System.out.print("Välj ett alternativ: ");

                // Läs användarens val
                String select = scanner.nextLine();

                // Hantera användarens val
                switch (select) {
                    case "1":
                        // Anropa service-lagret för att visa alla produkter
                        productService.showAllProducts();
                        break;
                    case "2":
                        selectCategory();
                        break;
                    case "3":
                        searchProduct();
                        break;
                    case "0":
                        System.out.println("Avslutar produkthantering...");
                        return;
                    default:
                        System.out.println("Ogiltigt val, försök igen");
                }
            } catch (SQLException e) {
                // Hantera databasfel
                System.out.println("Ett fel uppstod vid databasanrop: " + e.getMessage());
            } catch (Exception e) {
                // Hantera övriga fel (t.ex. felaktig input)
                System.out.println("Ett oväntat fel uppstod: " + e.getMessage());
                scanner.nextLine(); // Rensa scanner-bufferten vid felinmatning
            }
        }
    }

    public void searchProduct() {
        System.out.println("\n=== Sök produkt ===");
        System.out.print("Sök: ");

        String search = scanner.nextLine();

        try {
            productService.selectProductByName(search.trim());
        } catch (SQLException e) {
            System.out.println("Ett fel uppstod vid databasanrop: " + e.getMessage());
        }

    }

    public void selectCategory() throws SQLException {
        System.out.println("\n=== Kategorier ===");
        System.out.println("-----------------");
        String chosenCategory = productService.categoryNames();
        productService.selectCategories(chosenCategory);
    }
}
