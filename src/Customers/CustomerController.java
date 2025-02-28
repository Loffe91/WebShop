package Customers;



import Orders.OrderProduct;
import Orders.OrderRepository;
import Orders.OrderService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Controller-klass för kundhantering
 * Hanterar användarinteraktion för kundrelaterade operationer
 */
public class CustomerController {

    // Service-lager för kundhantering, hanterar affärslogik
    CustomerService customerService;
    Customer loggedIn;
    Scanner scanner;
    OrderRepository orderRepository;
    OrderService orderService;

    /**
     * Konstruktor för Customers.CustomerController
     * Initierar service och scanner
     */
    public CustomerController(Customer customer) {
        // Skapa instanser av nödvändiga objekt
        this.customerService = new CustomerService();
        this.scanner = new Scanner(System.in);
        this.loggedIn = customer;
        this.orderRepository = new OrderRepository();
        this.orderService = new OrderService();
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
                System.out.println("3. Lägg en beställning ");
                System.out.println("4. Visa orderhistorik");
                System.out.println("9. Ta bort mitt konto ");
                System.out.println("0. Logga ut");
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
                        placeOrder();
                        break;
                    case "4":
                        showOrderHistory();
                        break;
                    case "9":
                        System.out.println("Logik för att ta bort ditt konto: ");
                        break;
                    case "0":
                        System.out.println("Loggar ut... ");
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

    public void showCustomerDetails(){
        System.out.println("\n=== Mina uppgifter ===");
        System.out.println("ID: "+loggedIn.getUserId());
        System.out.println("Namn: "+loggedIn.getName());
        System.out.println("Email: "+loggedIn.getEmail());
    }

    public void updateCustomerInfo() throws SQLException {
        System.out.println("De fält du ej vill ändra kan du lämna tomma ");

        System.out.println("Nytt namn: ");
        String name = scanner.nextLine();

        System.out.println("Ny mailadress: ");
        String email = scanner.nextLine();

        System.out.println("Nytt lösenord: ");
        String password = scanner.nextLine();

        boolean success = customerService.updateCustomerInfo(
                loggedIn.getUserId(), // Hämtar den inloggade kundens ID
                name.isEmpty() ? null : name, // Om name lämnas tomt skickas null, annars det nya namnet
                email.isEmpty() ? null : email, // ----------- !! ---------------
                password.isEmpty() ? null : password // ----------- !! -----------
        );

        if(success){
            System.out.println("Dina uppgifter har uppdaterats. ");
        } else {
            System.out.println("Uppdatering misslyckades. ");
        }

    }

    public void placeOrder() throws SQLException {
        ArrayList<OrderProduct> products = new ArrayList<>();

        while (true){
            System.out.println("Ange produkt-ID: (Tryck 0 för att avbryta beställningen)");
            int produktId = Integer.parseInt(scanner.nextLine());
            if(produktId == 0){
                break;
            }

            System.out.println("Ange antal: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            if(quantity == 0){
                System.out.println("Felaktig input");
                break;
            }
            // Hämtar priset för den valda produkten
            double unitPrice = orderRepository.getPrice(produktId);

            // Skapar en order med produktId, quantity och unitPrice
            OrderProduct orderProduct = new OrderProduct(produktId, quantity, unitPrice);
            products.add(orderProduct);
            orderService.placeOrder(loggedIn.getUserId(), products);

        }
    }
    public void showOrderHistory() throws SQLException{
        orderService.getOrderHistory(loggedIn.getUserId());
    }
}