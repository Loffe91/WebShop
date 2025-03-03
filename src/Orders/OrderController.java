package Orders;

import Customers.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
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
                System.out.println("\n=== Ordermeny ===");
                System.out.println("1. Lägg en beställning ");
                System.out.println("2. Visa orderhistorik ");
                System.out.println("0. Avsluta ");

                String select = scanner.nextLine();

                switch (select) {
                    case "1":
                        placeOrder(); //behöver customerId som argument
                        break;
                    case "2":
                        showOrderHistory();
                        break;
                    case "3":
                        break;
                    case "4":

                        break;
                    case "5":
                        System.out.println("Avslutar...");
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
            double unitPrice = orderService.getUnitPrice(produktId);

            // Skapar en order med produktId, quantity och unitPrice
            OrderProduct orderProduct = new OrderProduct(produktId, quantity, unitPrice);
            products.add(orderProduct);
            orderService.placeOrder(loggedIn.getUserId(), products);

            double totalPrice = orderService.getTotalPrice(products);
            System.out.println("Orderns totala pris är: "+totalPrice+" kronor. ");

        }
    }
    public void showOrderHistory() throws SQLException{
        orderService.getOrderHistory(loggedIn.getUserId());
    }

}
