package Orders;

import Customers.Cart;
import Customers.Customer;
import Customers.CustomerRepository;

import java.sql.SQLException;
import java.util.Map;

/**
 * OrderService hanterar skapandet och lagringen av ordrar i systemet.
 */
public class OrderService {
    private final OrderRepository orderRepository; // Repository för att hantera orderdata
    private final CustomerRepository customerRepository; // Repository för att hantera kunddata

    /**
     * Konstruktor som initierar repository-objekten för order och kund.
     */
    public OrderService() {
        this.orderRepository = new OrderRepository();
        this.customerRepository = new CustomerRepository();
    }

    /**
     * Skapar en ny order från en kunds varukorg.
     *
     * @param customerId ID för kunden som gör ordern.
     * @return Order-objekt om lyckat, annars null.
     */
    public Order createOrder(int customerId) {
        try {
            // Hämtar kunden baserat på ID
            Customer customer = customerRepository.getCustomerById(customerId);
            if (customer == null) {
                System.out.println("Kund hittades inte.");
                return null; // Returnerar null om kunden inte finns
            }

            // Hämtar kundens varukorg
            Cart cart = customer.getCart();
            if (cart.isEmpty()) {
                System.out.println("Varukorgen är tom. Ingen order skapades.");
                return null; // Returnerar null om varukorgen är tom
            }

            // Hämtar produkterna från varukorgen
            Map<String, Integer> cartProducts = cart.getProducts();
            if (cartProducts.isEmpty()) {
                System.out.println("Varukorgen är tom. Ingen order skapades.");
            }

            // Skapar en ny order med kundens ID och produkter från varukorgen
            Order newOrder = new Order(customerId, cartProducts);

            // Sparar ordern i databasen
            orderRepository.saveOrder(newOrder);

            // Rensar kundens varukorg efter att ordern har skapats
            customer.clearCart();

            // Bekräftelse på att ordern skapades
            System.out.println("Order har skapats för kund: " + customer.getName());
            return newOrder; // Returnerar det skapade orderobjektet
        } catch (SQLException e) {
            // Fångar SQL-exception om något går fel med databasen
            System.out.println("Fel vid skapande av order: " + e.getMessage());
            return null; // Returnerar null om något går fel
        }
    }

    /**
     *  Visar orderinformation
     *
     * @param order Ordern att visa
     */

    public void showOrder(Order order) {
        if (order == null) {
            System.out.println("Ingen order att visa");
            return;
        }
        order.printOrder();
    }

    public void editOrder() {

    }

    public void deleteOrder() {

    }
}
