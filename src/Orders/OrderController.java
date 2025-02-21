package Orders;

import java.util.Scanner;
import Customers.Cart;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * OrderController hanterar användarinteraktion för att skapa ordrar.
 */
public class OrderController {
    private final OrderService orderService; // Serviceklass för att hantera orderlogik
    private final Scanner scanner; // Scanner för att läsa användarinput

    /**
     * Konstruktor som initialiserar OrderService och Scanner.
     */
    public OrderController() {
        this.orderService = new OrderService();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Metod för att skapa en order genom att fråga efter kundens ID.
     */
    public void placeOrder() {
        System.out.println("Ange kundens ID för att skapa en order: ");
        int customerId = scanner.nextInt(); // Läser in kund-ID från användaren
        scanner.nextLine(); // Rensar scanner-bufferten för att undvika problem med nästa input

        // Skapar order och kontrollerar om den lyckades
        Order order = orderService.createOrder(customerId);
        if (order != null) {
            System.out.println("Order skapad: " + order);
        } else {
            System.out.println("Kunde inte skapa order.");
        }
    }
}