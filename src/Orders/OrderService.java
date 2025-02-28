package Orders;

import Customers.Cart;
import Customers.Customer;
import Customers.CustomerRepository;

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

}
