package Admin;

import Customers.Customer;
import Customers.CustomerService;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class.getName());

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

            String select = scanner.nextLine().trim();

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
                    System.out.println("\nLoggar ut...");
                    return;
                default:
                    logger.warning("Admin gjorde inmatning utanför tillåtna alternativ. ");
                    System.out.println("Felaktigt inmatning, Välj ett alternativ i listan");


            }
        }
    }

    // Metod för att visa kunder

    public void showCustomers() throws SQLException {
        System.out.println("=== Kundhantering ===");
        System.out.println("1. Visa alla kunder. ");
        System.out.println("2. Hämta kund baserat på email. ");
        System.out.println("3. Hämta kund baserat på ID ");
        System.out.println("4. Ta bort kund");
        System.out.println("0. Tillbaka. ");
        System.out.println("Välj ett alternativ: ");

        String select = scanner.nextLine().trim();

        switch (select){
            case "1":
                // Anropa service-lagret för att visa alla kunder
                adminService.showAllUsers();
                break;
            case "2": // Hämta kund baserat på mail
                getCustomerByEmail();
                break;
            case "3": // Hämta kund baserat på ID
                getCustomerById();
                break;
            case "4": //Ta bort kund
                deleteCustomer();
                break;
            case "0":
                return;
            default:
                logger.warning("Admin gjorde inmatning utanför tillåtna alternativ. " + select);
                System.out.println("Felaktigt inmatning, Välj ett alternativ i listan");
                showCustomers();
        }
    }

    public void showProducts() throws SQLException {
        System.out.println("=== Produkthantering ===");
        System.out.println("1. Visa varor och lagersaldo");
        System.out.println("2. Uppdatera Pris");
        System.out.println("3. Uppdatera Lagersaldo");
        System.out.println("0. Tillbaka. ");

        String select = scanner.nextLine().trim();

        switch (select){
            case "1": //visar alla produkter
                adminService.showAllProducts();
                break;
            case "2"://uppdatera pris
                updatePrice();
                break;
            case "3": // Uppdatera lagerstatus
                updateStock();
                break;
            case "0":
                return;
            default:
                logger.warning("Felaktigt input" + select);
                System.out.println("Felaktigt inmatning, välj ett alternativ i listan");
                showProducts();
        }
    }

    public void updateStock() throws SQLException{
        try{
            System.out.println("Ange ID: ");
            int quantProductId = Integer.parseInt(scanner.nextLine());
            System.out.println("Ange ny lagerstatus: ");
            int newStock = Integer.parseInt(scanner.nextLine());
            adminService.updateProductStock(quantProductId, newStock);
        } catch (NumberFormatException e){
            logger.warning("Admin gjorde inmatning utanför tillåtna tecken vid lageruppdatering. ");
            System.out.println("Felaktig inmatning, Ange ID med ett heltal ");
            showProducts();
        }
    }
    public void updatePrice() throws SQLException{

        try {
            System.out.println("Ange ID: ");
            int productId = Integer.parseInt(scanner.nextLine().trim());
            System.out.println("Ange nytt pris: ");
            double newPrice = Double.parseDouble(scanner.nextLine().trim());
            adminService.updateProductPrice(productId, newPrice);
        } catch (NumberFormatException e){
            logger.warning("Admin gjorde inmatning utanför tillåtna tecken vid prisuppdatering. ");
            System.out.println("Felaktig inmatning, Ange ID med ett heltal ");
            showProducts();
        }
    }

    public void getCustomerByEmail() throws SQLException{
        System.out.println("Ange mail: ");
        String mail = scanner.nextLine().trim();
        Customer customerByEmail = adminService.getCustomerByEmail(mail); // Spara den returnerade kunden
        if(customerByEmail != null){ // Om kund hittas, skriv ut ID, namn & mail
            System.out.println("ID: " + customerByEmail.getUserId());
            System.out.println("Namn: " + customerByEmail.getName());
            System.out.println("Email: " + customerByEmail.getEmail());
        }
        else {
            showCustomers();
        }
    }

    public void getCustomerById() throws SQLException{
        try {
            System.out.println("Ange ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Customer customerById = adminService.getCustomerById(id); // Spara customerById till Customer
            if(customerById != null){ // Om en kund med angivet ID finns, dvs customerById är ej null
                System.out.println("ID: " + customerById.getUserId()); // Skriv ut info
                System.out.println("Namn: " + customerById.getName());
                System.out.println("Email: " + customerById.getEmail());
            }
            else {
                showCustomers();
            }
        } catch (NumberFormatException e){
            logger.warning("Admin gjorde inmatning utanför tillåtna tecken");
            System.out.println("Felaktig inmatning, Ange ID med ett heltal ");
        }
    }

    public void deleteCustomer() throws SQLException{
        System.out.println("Ange ID på kunden du vill ta bort: ");
        try {
            int deleteId = Integer.parseInt(scanner.nextLine()); // Läs och konvertera ID från användaren
            adminService.deleteCustomer(deleteId); // Anropa service-lagret för att ta bort kunden
        } catch (NumberFormatException e) {
            logger.warning("Admin gjorde inmatning utanför tillåtna tecken");
            System.out.println("Felaktig inmatning, Ange ID med ett heltal ");
            showCustomers();
        }
    }
}