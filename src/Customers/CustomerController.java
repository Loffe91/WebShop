package Customers;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Controller-klass för kundhantering
 * Hanterar användarinteraktion för kundrelaterade operationer
 */
public class CustomerController {

    // Service-lager för kundhantering, hanterar affärslogik
    CustomerService customerService;

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
                System.out.println("1. Visa alla kunder");
                System.out.println("2. Lägg till kund");
                System.out.println("3. Hämta kund baserat på email");
                System.out.println("4. Hämta kund baserat på ID");
                System.out.println("5. Ta bort en kund baserat på ID");
                System.out.println("0. Avsluta");
                System.out.print("Välj ett alternativ: ");

                // Läs användarens val
                String select = scanner.nextLine();

                // Hantera användarens val
                switch (select) {
                    case "1":
                        // Anropa service-lagret för att visa alla kunder
                        customerService.showAllUsers();
                        break;
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
                    case "3": // Hämta kund baserat på mail
                        System.out.println("Ange mail: ");
                        String mail = scanner.nextLine();
                        Customer customerByEmail = customerService.getCustomerByEmail(mail); // Spara den returnerade kunden
                        if(customerByEmail != null){ // Om kund hittas, skriv ut ID, namn & mail
                            System.out.println("ID: "+ customerByEmail.getCustomerId());
                            System.out.println("Namn: "+ customerByEmail.getName());
                            System.out.println("Email: "+ customerByEmail.getEmail());
                        } else {
                            System.out.println("Hittade ingen matchande kund. ");
                        }
                        break;
                    case "4": // Hämta kund baserat på ID
                        System.out.println("Ange ID: ");
                        String idString = scanner.nextLine(); // Hämta id och konvertera till en stril
                        int id = Integer.parseInt(idString);
                        Customer customerById = customerService.getCustomerById(id); // Spara customerById till Customer
                        if(customerById != null){ // Om en kund med angivet ID finns, dvs customerById är ej null
                            System.out.println("ID: "+customerById.getCustomerId()); // Skriv ut info
                            System.out.println("Namn: "+customerById.getName());
                            System.out.println("Email: "+customerById.getEmail());
                        } else { // Om ingen kund hittats
                            System.out.println("Hittade ingen matchande kund. ");
                        }
                        break;

                    case "5":
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
}