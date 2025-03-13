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
                System.out.println("3. Butiksinformation");
                System.out.println("0. Avsluta");
                System.out.print("Välj ett alternativ: ");

                // Läs användarens val
                String select = scanner.nextLine();

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
                        try {  // Kontrollerar så fält ej lämnas tomma
                            if(!name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !address.isEmpty() && !password.isEmpty()){
                                customerService.addCustomer(name, email, phone, address, password);
                                break; // Om inga fel uppstår, läggs kunden till och loopen avbryts
                            } else {
                                logger.warning("Kunden uppgav felaktig input");
                                System.out.println("Du angav fel information, testa igen. ");
                                run();
                            }
                        break;
                        } catch (Exception e){
                            logger.severe(e.getMessage());
                            System.out.println("Fel vid registrering: "+e.getMessage());
                        }
                        break;
                    case "3":
                        System.out.println("\nVälkommen till våran elektronikbutik. \nFör att bläddra runt bland produkterna" +
                                " eller handla, vänligen skapa ett konto. ");
                        System.out.println("Nya kunder får 10% rabatt på första köpet!");
                        System.out.println("Därefter finns det fyra olika nivåer av rabatt:\n");
                        System.out.println("🥉 BRONS:    5% rabatt");
                        System.out.println("🥈 SILVER:  10% rabatt");
                        System.out.println("🥇 GULD:    15% rabatt");
                        System.out.println("🏆 PLATINUM: 20% rabatt");
                        break;
                    case "0":
                        scanner.close();
                        SystemUtils.avslutarAnimation();
                        System.exit(0); // Avslutar programmet

                    default:
                        logger.warning("Användaren gav fel input. ");
                        System.out.println("\nFelaktig input, försök igen. ");

                }
            } catch (SQLException e) {
                // Hantera databasfel
                System.out.println("Oväntat fel uppstod ");
                logger.severe("Ett fel uppstod vid databasanrop: " + e.getMessage());
            } catch (Exception e) {
                // Hantera övriga fel (t.ex. felaktig input)
                System.out.println("Oväntat fel uppstod. ");
                logger.severe(e.getMessage());
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
            System.out.println("Felaktiga inloggningsuppgifter. Försök igen");
            logger.warning("Användaren uppgav fel info. ");
        }
    }
}