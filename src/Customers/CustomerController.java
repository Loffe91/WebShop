package Customers;

import Orders.OrderController;
import Products.ProductController;
import Utils.SystemUtils;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.*;
import java.util.regex.Pattern;

import Products.ProductController;


/**
 * Controller-klass för kundhantering
 * Hanterar användarinteraktion för kundrelaterade operationer
 */
public class CustomerController {


    CustomerService customerService; // Service-lager för kundhantering, hanterar affärslogik
    Customer loggedIn;
    Scanner scanner;
    OrderController orderController;
    ProductController productController;
    private static final Logger logger = Logger.getLogger(CustomerController.class.getName());
    /**
     * Konstruktor för Customers.CustomerController
     * Initierar service och scanner
     */
    public CustomerController(Customer customer) {
        // Skapa instanser av nödvändiga objekt
        this.customerService = new CustomerService();
        this.scanner = new Scanner(System.in);
        this.loggedIn = customer;
        this.orderController = new OrderController(customer);
        this.productController = new ProductController(customer);

    }

    /**
     * Huvudloop för kundhantering
     * Visar meny och hanterar användarval
     */
    public void run() {
        while (true) {
            try {
                // Skriv ut kundmeny
                System.out.println("\n=== Kundmeny ===");
                System.out.println("1. Visa mina uppgifter ");
                System.out.println("2. Uppdatera uppgifter ");
                System.out.println("3. Ordermeny ");
                System.out.println("4. Sök produkt ");
                System.out.println("5. Varukorg ");
                System.out.println("9. Ta bort mitt konto ");
                System.out.println("0. Logga ut ");
                System.out.print("Välj ett alternativ: ");

                // Läs användarens val
                String select = scanner.nextLine();

                // Hantera användarens val
                switch (select) {
                    case "1":
                        showCustomerDetails();
                        break;
                    case "2":
                        updateCustomerInfo();
                        break;
                    case "3":
                        orderMenu();
                        break;
                    case "4":
                        productMenu(); //ny
                        break;
                    case "5":
                        cartMenu();
                        break;
                    case "9":
                        deleteAccount();
                        break;
                    case "0":
                        SystemUtils.logoutAnimation();
                        return;

                    default:
                        System.out.println("\nFelaktig inmatning. Välj ett alternativ från menyn");
                        logger.warning("Användaren gav felaktig input. ");
                }
            } catch (SQLException e) {
                // Hantera databasfel
                logger.warning("Fel uppstod vid databasanrop" +e.getMessage());
                System.out.println("Ett oväntat fel uppstod. ");
            } catch (Exception e) {
                // Hantera övriga fel (t.ex. felaktig input)
                logger.warning("Fel uppstod: " +e.getMessage());
                System.out.println("Ett oväntat fel uppstod");
                scanner.nextLine(); // Rensa scanner-bufferten vid felinmatning
            }
        }
    }

    public void deleteAccount() {
        System.out.println("När du väljer att ta bort ditt konto");
        System.out.println("försvinner all infomation om dig och dina tidigare köp.");
        System.out.println();
        System.out.println("Vill du ta bort ditt konto?");
        System.out.println();
        System.out.println("1. Ta bort mitt konto");
        System.out.println("2. Gå tillbaka till mina sidor");

        String choice = scanner.nextLine().trim(); //variabel för val, för att slippa upprepa
        // scanner nextLine och få extra rader och knapptryck efter vald siffra.

        if (choice.equalsIgnoreCase("1")) {
            System.out.println("Ditt konto tas nu bort");
            System.out.println("Du loggas ut");

            try {
                int customerId = loggedIn.getUserId();
                customerService.deleteCustomer(customerId);
                logger.info("Kontot tas bort. ");
                System.exit(0); //Stränger ner hela programmet "0" innebär att
                                      // inga felmeddelanden uppstår
            } catch (SQLException e) {
                System.out.println("Det uppstod ett fel när kontot skulle tas bort.");
                logger.warning(e.getMessage());
            }
        }
        else if(choice.equalsIgnoreCase("2")){
            SystemUtils.tillbakaAnimation("mina sidor (kundmeny)");
        }
        else {
            System.out.println("Ogiltigt val, försök igen");
        }
    }

    public void showCustomerDetails(){
        System.out.println("\n=== Mina uppgifter ===");
        System.out.println("ID: "+loggedIn.getUserId());
        System.out.println("Namn: "+loggedIn.getName());
        System.out.println("Email: "+loggedIn.getEmail());
        System.out.println("Medlemsnivå: " + loggedIn.getDiscountLevel());
        System.out.println("Poäng: "+loggedIn.getPoints());


    }

    public void updateCustomerInfo() throws SQLException {
        System.out.println("De fält du ej vill ändra kan du lämna tomma ");

        System.out.println("Nytt namn: ");
        String name = scanner.nextLine().trim();
        if(!name.isBlank()){ // För att uppdatera namnvariabeln i realtid och inte bara för databasen
            loggedIn.setName(name);
        }

        System.out.println("Ny mailadress: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()){
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
            if(Pattern.matches(emailRegex, email)){
                loggedIn.setEmail(email);
            }
            else {
                System.out.println("Ogiltig mailadress. ");
                return;
            }
        }

        System.out.println("Nytt lösenord: ");
        String password = scanner.nextLine().trim();

        boolean success = customerService.updateCustomerInfo(
                loggedIn.getUserId(), // Hämtar den inloggade kundens ID
                name.isEmpty() ? null : name, // Om name lämnas tomt skickas null, annars det nya namnet
                email.isEmpty() ? null : email, // ----------- !! ---------------
                password.isEmpty() ? null : password // ----------- !! -----------
        );

        if (name.isBlank() && email.isBlank() && password.isBlank()) {
            System.out.println("Dina uppgifter är oförändrade.");
        } else if (success) {
            System.out.println("Dina uppgifter har uppdaterats.");
        } else {
            System.out.println("Uppdatering misslyckades.");
        }

    }

    public void orderMenu() throws SQLException {
        orderController.run();
    }
    public void productMenu() throws SQLException {
        productController.run();
    }
    // Metod för att skriva ut varukorgsmenyn
    public void cartMenu() throws SQLException {
        System.out.println("\n=== Kundvagn ===");
        System.out.println("1. Visa varukorg ");
        System.out.println("2. Ta bort vara från varukorgen ");
        System.out.println("3. Töm varukorg ");
        System.out.println("4. Lägg till vara i varukorgen ");
        System.out.println("5. Ta varukorgen till kassan");
        System.out.println("0. Tillbaka ");
        System.out.print("Välj ett alternativ: ");

        String select = scanner.nextLine().trim();

        switch (select){
            case "1":
                loggedIn.viewCart();
                break;
            case "2":
                removeItemFromCart();
                break;
            case "3":
                loggedIn.clearCart();
                break;
            case "4":
                orderController.addProductToCart();
                break;
            case "5":
                placeOrder();
                break;
            case "0":
                SystemUtils.tillbakaAnimation("kundmeny");
                return;
            default:
                System.out.println("Felaktig input. Försök igen");
                logger.warning("Feelaktig input. ");
        }
    }
    public void removeItemFromCart(){
        System.out.println("Ange produkt-ID på varan du vill ta bort: ");
        int productId = Integer.parseInt(scanner.nextLine().trim());
        loggedIn.removeFromCart(productId);
    }
    // Metod för att ta varukorgen till kassan
    public void placeOrder() throws SQLException{
        if(loggedIn.cart.isEmpty()) {
            System.out.println("Varukorgen är tom. ");
            return;
        }
        orderController.placeOrder();
    }
}