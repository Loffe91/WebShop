package Orders;
import java.util.Scanner;

public class OrderController {
    private final OrderService orderService;
    private final Scanner scanner;

    public OrderController() {
        this.orderService = new OrderService();
        this.scanner = new Scanner(System.in);
    }

    public void placeOrder() {
        System.out.println("Ange kundens ID för att skapa en order: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Rensa scanner-bufferten

        Order order = orderService.createOrder(customerId);
        if (order != null) {
            System.out.println("Order skapad: " + order);
        } else {
            System.out.println("Kunde inte skapa order.");
        }
    }
}