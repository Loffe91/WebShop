package User;

import Admin.Admin;
import Customers.Customer;
import Customers.CustomerController;
import Admin.AdminController;
import Customers.CustomerService;
import Utils.SystemUtils;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Pattern;


/**
 * Controller-klass för kundhantering
 * Hanterar användarinteraktion för kundrelaterade operationer
 */
public class UserController {
    // Service-lager för kundhantering, hanterar affärslogik
    UserService userService;
    CustomerService customerService;
    // Scanner för användarinput
    Scanner scanner;

    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    private boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    /**
     * Konstruktor för Customers.CustomerController
     * Initierar service och scanner
     */
    public UserController() {
        // Skapa instanser av nödvändiga objekt
        this.scanner = new Scanner(System.in);
        this.userService = new UserService();
        this.customerService = new CustomerService();
    }

    /**
     * Huvudloop för kundhantering
     * Visar meny och hanterar användarval
     */
    public void run() {
        while (true) {
            try {
                // Skriv ut menyalternativ direkt i run-metoden för tydlighet
                System.out.println("\n=== Meny ===");
                System.out.println("1. Logga in");
                System.out.println("2. Skapa konto");
                System.out.println("0. Avsluta");
                System.out.print("Välj ett alternativ: ");

                // Läs användarens val
                String select = scanner.nextLine().trim();

                // Hantera användarens val
                switch (select) {
                    case "1":
                        login();
                        break;
                    case "2":
                        System.out.println("Ange namn: "); String name = scanner.nextLine().trim();
                        System.out.println("Ange mailadress: "); String email = scanner.nextLine().trim();
                        System.out.println("Ange telefonnummer: "); String phone = scanner.nextLine().trim();
                        System.out.println("Ange hemadress: "); String address = scanner.nextLine().trim();
                        System.out.println("Ange lösenord: "); String password = scanner.nextLine().trim();
                        try { // Testar så att kunden kan läggas till, t.ex ej redan använd email
                            if (!this.isValidEmail(email)) {
                                System.out.println("Ogiltlig mailadress: " + email);
                                continue;
                            }

                            customerService.addCustomer(name, email, phone, address, password);
                            break; // Om inga fel uppstår, läggs kunden till och loopen avbryts
                        } catch (Exception e){
                            logger.severe(e.getMessage()); // Om ett fel uppstår, uppmanas du att testa igen.
                        }
                        break;
                    case "0":
                        scanner.close();
                        SystemUtils.avslutarAnimation();
                        return; // Avslutar programmet
                    default:
                        System.out.println("Ogiltigt val, försök igen");
                }
            } catch (SQLException e) {
                // Hantera databasfel
                logger.severe("Ett fel uppstod vid databasanrop: " + e.getMessage());
            } catch (Exception e) {
                // Hantera övriga fel (t.ex. felaktig input)
                logger.severe("Ett oväntat fel uppstod: " + e.getMessage());
                scanner.nextLine(); // Rensa scanner-bufferten vid felinmatning
            }
        }
    }


    /// Metod för att logga in
    public void login() throws SQLException {
        System.out.println("Ange email: ");
        String email = scanner.nextLine().trim();
        System.out.println("Ange lösenord: ");
        String password = scanner.nextLine().trim();

        User loggedIn = userService.login(email, password); // Sparar en user till LoggedIn

        if (loggedIn != null) { // Om en matchande user hittas
            if (loggedIn instanceof Admin) { // Om usern är admin
                System.out.println("Inloggad med adminrättigheter");
                AdminController adminController = new AdminController((Admin) loggedIn);
                adminController.run(); // Skickar vidare användaren till AdminController
            } else if (loggedIn instanceof Customer){ // om usern är customer
                System.out.println("Välkommen, "+ ((Customer) loggedIn).getName());
                CustomerController customerController = new CustomerController((Customer) loggedIn);
                customerController.run(); // Skickar vidare användaren till CustomerController
            }
        }else {
            logger.warning("Felaktiga inloggningsuppgifter. Försök igen");
        }
    }
}