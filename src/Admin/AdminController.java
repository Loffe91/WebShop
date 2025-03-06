package Admin;

import Customers.Customer;
import Customers.CustomerService;
import Products.ProductRepository;

import java.sql.SQLException;
import java.util.Scanner;

public class AdminController {
    Admin loggedIn;
    AdminService adminService;
    CustomerService customerService;
    Scanner scanner;


    public AdminController(Admin admin){
        this.scanner = new Scanner(System.in);
        this.loggedIn = admin;
        this.customerService = new CustomerService();
        this.adminService = new AdminService();

    }

    public void run() throws SQLException {

        while (true){
            // Skriv ut adminmeny
            System.out.println("\n=== Adminmeny ===");
            System.out.println("1. Kundhantering"); //ändra till kundhantering och gör en inre loop?
            System.out.println("2. Lagerhantering");
            System.out.println("0. Logga ut");
            System.out.println("Välj ett alternativ: ");

            String select = scanner.nextLine();

            switch (select){
                case "1":
                    System.out.println("\n=== Kundhantering ===");
                    showCustomers();
                    break;
                case "2":
                    System.out.println("\n=== Lagerhantering ===");
                    showProducts();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Felaktigt val. Försök igen");

            }
        }
    }

    // Metod för att visa kunder

    public void showCustomers() throws SQLException {
        System.out.println("1. Visa alla kunder. ");
        System.out.println("2. Hämta kund baserat på email. ");
        System.out.println("3. Hämta kund baserat på ID ");
        System.out.println("4. Ta bort kund");
        System.out.println("0. Tillbaka. ");
        System.out.println("Välj ett alternativ: ");

        String select = scanner.nextLine();

        switch (select){
            case "1":
                // Anropa service-lagret för att visa alla kunder
                adminService.showAllUsers();
                break;
            case "2": // Hämta kund baserat på mail
                System.out.println("Ange mail: ");
                String mail = scanner.nextLine();
                Customer customerByEmail = adminService.getCustomerByEmail(mail); // Spara den returnerade kunden
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
                Customer customerById = adminService.getCustomerById(id); // Spara customerById till Customer
                if(customerById != null){ // Om en kund med angivet ID finns, dvs customerById är ej null
                    System.out.println("ID: "+customerById.getUserId()); // Skriv ut info
                    System.out.println("Namn: "+customerById.getName());
                    System.out.println("Email: "+customerById.getEmail());
                }
                break;
            case "4": //Ta bort kund
                System.out.println("Ange ID på kunden du vill ta bort: ");
                try {
                    int deleteId = Integer.parseInt(scanner.nextLine()); // Läs och konvertera ID från användaren
                    adminService.deleteCustomer(deleteId); // Anropa service-lagret för att ta bort kunden
                } catch (NumberFormatException e) {
                    System.out.println("Ogiltigt ID. Vänligen ange ett numeriskt värde.");
                }
                break;
            case "0":
                return;
            default:
                System.out.println("Felaktigt val. Försök igen ");
        }
    }
    public void showProducts() throws SQLException {
        System.out.println("1. Visa varor och lagersaldo");
        System.out.println("2. Uppdatera Pris");
        System.out.println("3. Uppdatera Lagersaldo");
        System.out.println("0. Tillbaka. ");

        String select = scanner.nextLine();

        switch (select){
            case "1": //visar alla produkter
                adminService.showAllProducts();
            break;
            case "2"://uppdatera pris
                System.out.println("Ange ID: ");
                String idString = scanner.nextLine();
                int productId = Integer.parseInt(idString);

                System.out.println("Ange nytt pris: ");
                String prisString = scanner.nextLine();
                double newPrice = Double.parseDouble(prisString);
                adminService.updateProductPrice(productId, newPrice);
            break;
            //case "3": adminService.updateProductStock();
        }
    }
}