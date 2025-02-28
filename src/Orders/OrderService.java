package Orders;
import Customers.Cart;
import Customers.Customer;
import Customers.CustomerRepository;
import java.sql.SQLException;
import java.util.Map;

public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService() {
        this.orderRepository = new OrderRepository();
        this.customerRepository = new CustomerRepository();
    }

    /**
     * Skapar en ny order från en kunds varukorg
     *
     * @param customerId ID för kunden som gör ordern
     * @return Order-objekt om lyckat, annars null
     */
    public Order createOrder(int customerId) {
        try {
            Customer customer = customerRepository.getCustomerById(customerId);
            if (customer == null) {
                System.out.println("Kund hittades inte.");
                return null;
            }

            Cart cart = customer.getCart();
            if (cart.isEmpty()) {
                System.out.println("Varukorgen är tom. Ingen order skapades.");
                return null;
            }

            Map<String, Integer> cartProducts = cart.getProducts();

            Order newOrder = new Order(customerId, cartProducts);

            orderRepository.saveOrder(newOrder);

            customer.clearCart(); // Rensa varukorgen efter beställning


            System.out.println("Order har skapats för kund: " + customer.getName());
            return newOrder;
        } catch (SQLException e) {
            System.out.println("Fel vid skapande av order: " + e.getMessage());
            return null;
        }
    }
}
