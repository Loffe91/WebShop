package Orders;

import Customers.Customer;

import java.sql.SQLException;
import java.util.Scanner;

public class OrderController {
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
                System.out.println("\n=== Order Menu ===");
                System.out.println("1. Create Order");
                System.out.println("2. Show Order");
                System.out.println("3. Edit Order");
                System.out.println("4. Delete Order");
                System.out.println("5. Exit");

                String select = scanner.nextLine();

                switch (select) {
                    case "1":
                        orderService.createOrder(); //behöver customerId som argument
                        break;
                    case "2":
                        orderService.showOrder(); //Vill ha en specifik order att visa
                        break;
                    case "3":
                        orderService.editOrder(); //inte implementerad
                        break;
                    case "4":
                        orderService.deleteOrder(); //inte implementerad
                        break;
                    case "5":
                        System.out.println("Du avslutar din order...");
                        return;
                        default:
                            System.out.println("Invalid choice, try again");
                }
            } catch (SQLException e) {
                System.out.println("Ett fel uppstod vid databasanrop: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ett oväntat fel uppstod: " + e.getMessage());
            }
        }
    }
    /**
     * Skapar en order för den inloggade kunden
     */
    private void createOrder() {
        if (loggedIn == null) {
            System.out.println("Please login to place an order");
            return;
        }
        Order newOrder = orderService.createOrder(loggedIn.getUserId());
        if (newOrder != null) {
            System.out.println("Order created!" + newOrder.getOrderId()); //behöver en metod i Order-klassen
        }
    }
}
