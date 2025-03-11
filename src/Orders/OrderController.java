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
    // Metod som slutför ett köp av varorna i varukorgen
    public void placeOrder() throws SQLException {
        // Kontrollerar så att korgen ej är tom
        if(loggedIn.cart.isEmpty()){
            System.out.println("Ordern kunde ej slutföras då varukorgen är tom. ");
            return;
        }
        // Kontrollerar att det finns tillräckligt i lagret
        for(OrderProduct product : loggedIn.cart){
            if(!orderService.orderQuantity(product.getProductId(), product.getQuantity())){
                System.out.println("Finns ej tillräckligt i lager av produkt-ID: "+product.getProductId());
                return;
            }
        }

        boolean successfulOrder = orderService.placeOrder(loggedIn.getUserId(), loggedIn.cart);
        // Om produkterna finns i lager skapas en order av customerId och innehållet i varukorgen
        if (successfulOrder){

            loggedIn.clearCart();
        } else {
            System.out.println("Order kunde ej skapas");

        }
    }

    public void showOrderHistory() throws SQLException{
        orderService.getOrderHistory(loggedIn.getUserId());
    }

    public void addProductToCart() throws SQLException{
        System.out.println("Ange produkt-ID på önskad vara: ");
        int productId = Integer.parseInt(scanner.nextLine());

        System.out.println("Ange antal: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        if (quantity <= 0){
            System.out.println("Kan lägga till minst 1 vara");
            return;
        }

        double unitPrice = orderService.getUnitPrice(productId);
        // Kallar på servicelagret med id & quantity för att verifiera lagerstatus
        if(!orderService.orderQuantity(productId, quantity)){
            return;
        }
        OrderProduct orderProduct = new OrderProduct(productId, quantity, unitPrice);
        loggedIn.addToCart(orderProduct);
        System.out.println("Du la till "+quantity+" stycken av vara "+productId+" i varukorgen");

    }
}
