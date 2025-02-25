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
                        orderService.createOrder();
                        break;
                    case "2":
                        orderService.showOrder();
                        break;
                    case "3":
                        orderService.editOrder();
                        break;
                    case "4":
                        orderService.deleteOrder();
                        break;
                    case "5":
                        System.out.println("Du avslutar din order...");
                        return;
                }
            } catch (SQLException e) {
                System.out.println("Ett fel uppstod vid databasanrop: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ett oväntat fel uppstod: " + e.getMessage());
            }
        }
    }
}