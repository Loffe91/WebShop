package User;

import Admin.Admin;
import Customers.Customer;
import Customers.CustomerController;
import Admin.AdminController;
import Customers.CustomerService;

import java.sql.SQLException;
import java.util.Scanner;


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

    /**
     * Konstruktor för Customers.CustomerController
     * Initierar service och scanner
     */
    public UserController() {
        // Skapa instanser av nödvändiga objekt
        this.scanner = new Scanner(System.in);
        this.userService = new UserService();
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
                //System.out.println("2. Visa kunder");
                System.out.println("2. Skapa konto");
                //System.out.println("4. Ta bort en kund baserat på ID");
                //System.out.println("5. Uppdatera kund");
                System.out.println("0. Avsluta");
                System.out.print("Välj ett alternativ: ");

                // Läs användarens val
                String select = scanner.nextLine();

                // Hantera användarens val
                switch (select) {
                    case "1":
                        login();
                        break;
                    //case "2":
                    //    showCustomers();
                    //    break;
                    case "2":
                        System.out.println("Ange namn: "); String name = scanner.nextLine();
                        System.out.println("Ange mailadress: "); String email = scanner.nextLine();
                        System.out.println("Ange telefonnummer: "); String phone = scanner.nextLine();
                        System.out.println("Ange hemadress: "); String address = scanner.nextLine();
                        System.out.println("Ange lösenord: "); String password = scanner.nextLine();

                        try { // Testar så att kunden kan läggas till, t.ex ej redan använd email
                            customerService.addCustomer(name, email, phone, address, password);
                            break; // Om inga fel uppstår, läggs kunden till och loopen avbryts
                        } catch (Exception e){
                            System.out.println(e.getMessage()); // Om ett fel uppstår, uppmanas du att testa igen.
                        }
                        break;

                    case "4":
                        System.out.println("Ange ID på kunden du vill ta bort: ");
                        try {
                            int deleteId = Integer.parseInt(scanner.nextLine()); // Läs och konvertera ID från användaren
                            customerService.deleteCustomer(deleteId); // Anropa service-lagret för att ta bort kunden
                        } catch (NumberFormatException e) {
                            System.out.println("Ogiltigt ID. Vänligen ange ett numeriskt värde.");
                        }
                        break;
                    case "0":
                        System.out.println("Avslutar kundhantering...");
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


    // Metod för att logga in
    public void login() throws SQLException {
        System.out.println("Ange email: ");
        String email = scanner.nextLine();
        System.out.println("Ange lösenord: ");
        String password = scanner.nextLine();

        User loggedIn = userService.login(email, password); // Sparar en user till LoggedIn

        if (loggedIn != null) { // Om en matchande user hittas
            if (loggedIn instanceof Admin) { // Om usern är admin
                System.out.println("Inloggad med adminrättigheter");
                AdminController adminController = new AdminController();
                //adminController.run(); // Skickar vidare användaren till AdminController
            } else if (loggedIn instanceof Customer){ // om usern är customer
                System.out.println("Välkommen, "+ ((Customer) loggedIn).getName());
                CustomerController customerController = new CustomerController((Customer) loggedIn);
                customerController.run(); // Skickar vidare användaren till CustomerController
            }
        }else {
            System.out.println("Felaktiga inloggningsuppgifter. Försök igen");
        }

    }

}