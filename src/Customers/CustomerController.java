package Customers;

import Admin.Admin;
import Admin.AdminController;
import User.UserService;

import java.sql.SQLException;
import java.util.Scanner;
import User.User;
import User.UserService;

/**
 * Controller-klass för kundhantering
 * Hanterar användarinteraktion för kundrelaterade operationer
 */
public class CustomerController {

    // Service-lager för kundhantering, hanterar affärslogik
    CustomerService customerService;
    UserService userService;

    // Scanner för användarinput
    Scanner scanner;

    /**
     * Konstruktor för Customers.CustomerController
     * Initierar service och scanner
     */
    public CustomerController() {
        // Skapa instanser av nödvändiga objekt
        this.customerService = new CustomerService();
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
                System.out.println("\n=== Kundhantering ===");
                System.out.println("1. Logga in");
                System.out.println("2. Visa kunder");
                System.out.println("3. Lägg till kund");
                System.out.println("4. Ta bort en kund baserat på ID");
                System.out.println("5. Uppdatera kund");
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
                        showCustomers();
                        break;
                    case "3":
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

                    case "5":
                        updateCustomerMenu(); // Anropar den nya metoden
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
    // Metod för att visa kunder
    public void showCustomers() throws SQLException{
        System.out.println("\n=== Visa kunder ===");
        System.out.println("1. Visa alla kunder. ");
        System.out.println("2. Hämta kund baserat på email. ");
        System.out.println("3. Hämta kund baserat på ID ");
        System.out.println("0. Tillbaka. ");
        System.out.println("Välj ett alternativ: ");

        String select = scanner.nextLine();

        switch (select){
            case "1":
                // Anropa service-lagret för att visa alla kunder
                customerService.showAllUsers();
                break;
            case "2": // Hämta kund baserat på mail
                System.out.println("Ange mail: ");
                String mail = scanner.nextLine();
                Customer customerByEmail = customerService.getCustomerByEmail(mail); // Spara den returnerade kunden
                if(customerByEmail != null){ // Om kund hittas, skriv ut ID, namn & mail
                    System.out.println("ID: "+ customerByEmail.getUserId());
                    System.out.println("Namn: "+ customerByEmail.getName());
                    System.out.println("Email: "+ customerByEmail.getEmail());
                }
                break;
            case "3": // Hämta kund baserat på ID
                System.out.println("Ange ID: ");
                String idString = scanner.nextLine(); // Hämta id och konvertera till en stril
                int id = Integer.parseInt(idString);
                Customer customerById = customerService.getCustomerById(id); // Spara customerById till Customer
                if(customerById != null){ // Om en kund med angivet ID finns, dvs customerById är ej null
                    System.out.println("ID: "+customerById.getUserId()); // Skriv ut info
                    System.out.println("Namn: "+customerById.getName());
                    System.out.println("Email: "+customerById.getEmail());
                }
                break;
            case "0":
                return;
            default:
                System.out.println("Felaktigt val. Försök igen ");
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
                new AdminController(); // Har för tillfället ingen adminmeny, men lägg till t.ex .run efter denna rad
                                       // när en sådan meny är skapad
            } else { // om usern är customer
                System.out.println("Välkommen, "+ ((Customers.Customer) loggedIn).getName());
            }
        }else {
            System.out.println("Felaktiga inloggningsuppgifter. Försök igen");
        }

    }
    // Meny för att uppdatera kund
    public void updateCustomerMenu() {
        System.out.println("Ange e-postadressen på kunden du vill uppdatera: ");
        String email = scanner.nextLine();

        System.out.println("Ange nytt namn: ");
        String name = scanner.nextLine();

        System.out.println("Ange ny e-post: ");
        String newEmail = scanner.nextLine();

        System.out.println("Ange nytt lösenord: ");
        String password = scanner.nextLine();

        Customer updatedCustomer = new Customer(email, name, newEmail, password);
        boolean success = customerService.updateCustomer(updatedCustomer);

        if (success) {
            System.out.println("Kunden uppdaterades framgångsrikt!");
        } else {
            System.out.println("Misslyckades med att uppdatera kunden.");
        }
    }

}