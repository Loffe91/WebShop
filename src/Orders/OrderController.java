package Orders;

import Customers.Customer;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class OrderController {

    private static final Logger logger = Logger.getLogger(OrderController.class.getName());
//meny för att hantera input användare
    OrderService orderService;
    Customer loggedIn;
    Scanner scanner;

    public OrderController(Customer customer) {
        this.orderService = new OrderService();
        this.scanner = new Scanner(System.in);
        this.loggedIn = customer;
    }

    public void run() {
        while (true) {
            try {
                System.out.println("\n=== Ordermeny ===");
                System.out.println("1. Lägg en beställning ");
                System.out.println("2. Visa orderhistorik ");
                System.out.println("0. Gå tillbaka");

                String select = scanner.nextLine().trim();

                switch (select) {
                    case "1":
                        placeOrder();
                        break;
                    case "2":
                        showOrderHistory();
                        break;
                    case "3":
                        break;
                    case "4":
                        break;
                    case "0":
                        System.out.println("Återgår till kundmenyn");
                        return;
                    default:
                        logger.warning("Kund gjorde val utanför det godkända spannet");
                        System.out.println("Felaktigt val, försök igen. ");
                }
            } catch (SQLException e) {
                logger.warning( "Ett fel uppstod vid databasanrop: " + e.getMessage());
                System.out.println("Ojdå, nåt gick snett, försök igen. ");
            } catch (Exception e) {
                logger.warning("Ett oväntat fel uppstod: " + e.getMessage());
                System.out.println("Ojdå, nåt gick snett, försök igen. ");

            }
        }
    }
    // Metod som slutför ett köp av varorna i varukorgen
    public void placeOrder() throws SQLException {
        // Kontrollerar så att korgen ej är tom
        if(loggedIn.cart.isEmpty()){
            logger.warning("Varukorgen är tom, köpet kunde ej genomföras. ");
            System.out.println("Varukorgen är tom, köpet kunde ej genomföras. ");
            return;
        }
        // Kontrollerar att det finns tillräckligt i lagret
        for(OrderProduct product : loggedIn.cart){
            if(!orderService.orderQuantity(product.getProductId(), product.getQuantity())){
                logger.warning("Lagersaldot för vald vara, " + product.getProductId() + " är för lågt, köpet genomfördes inte ");
                System.out.println("Lagersaldot för vald vara, " + product.getProductId() + " är för lågt, köpet genomfördes inte ");
                return;
            }
        }

        boolean successfulOrder = orderService.placeOrder(loggedIn.getUserId(), loggedIn.cart);
        // Om produkterna finns i lager skapas en order av customerId och innehållet i varukorgen
        if (successfulOrder){

            loggedIn.clearCart();
        } else {
            logger.warning("Oväntat fel uppstod, ordern skapades ej. ");
            System.out.println("Oväntat fel uppstod, ordern skapades ej. ");
        }
    }

    public void showOrderHistory() throws SQLException{
        orderService.getOrderHistory(loggedIn.getUserId());
    }

    public void addProductToCart() throws SQLException{
        try {
            System.out.println("Ange produkt-ID på önskad vara: ");
            int productId = Integer.parseInt(scanner.nextLine());

            System.out.println("Ange antal: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            if (quantity <= 0 || productId <= 0) {
                logger.warning("Kund angav ett värde mindre än eller lika med 0");
                System.out.println("Produkt-ID och antal kan som minst vara 1");
                return;
            }
            if(productId > 90){
                logger.warning("Kund avgav ett värde utanför det godkända spannet, Produkt-ID anges mellan 1-90. ");
                System.out.println("Kan ej hitta produkten med det värdet, Ange ett Produkt-ID mellan 1-90. ");

                return;
            }

            double unitPrice = orderService.getUnitPrice(productId);
            // Kallar på servicelagret med id & quantity för att verifiera lagerstatus
            if (!orderService.orderQuantity(productId, quantity)) {
                return;
            }
            OrderProduct orderProduct = new OrderProduct(productId, quantity, unitPrice);
            loggedIn.addToCart(orderProduct);
            System.out.println("Du la till " + quantity + " stycken av vara " + productId + " i varukorgen");
        } catch (NumberFormatException e){
            logger.warning("Vänligen ange produkt-ID och antal som heltal. ");
        }

    }
}