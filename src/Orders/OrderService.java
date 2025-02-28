package Orders;

import Customers.Cart;
import Customers.Customer;
import Customers.CustomerRepository;

import java.sql.SQLException;
import java.util.ArrayList;

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

    public boolean placeOrder(int customerId, ArrayList<OrderProduct> products) throws SQLException {
        try { // Loopar igenom alla produkter i ordern och kollar lagerstatus
            for(OrderProduct product : products){
                // Om ej tillräcklig lagerstatus finns
                if(!orderRepository.stockStatus(product.getProductId(), product.getQuantity())){
                    System.out.println("Produkt "+product.getProductId() +" har ej tillräckligt många i lager");
                    return false;
                }
            }
         // Om lagerstatus är tillräckligt stort, skapas ordern
        int orderId = orderRepository.createOrder(customerId);

        if(orderId == -1) {
            System.out.println("Kunde ej skapa order. ");

        }
            // Kallar på metod som gör insert i databasen med orderId och produkterna i arraylisten som argument
            orderRepository.orderProductInsert(orderId, products);
            // Kallar på metod som uppdaterar lagerstatusen med produkterna i arraylisten som argument
            orderRepository.updateStock(products);

            System.out.println("Du har lagt en order. ID: "+orderId);
            return true;

        } catch (SQLException e){
            System.out.println("Ett fel uppstod: "+e.getMessage());
            return false;
        }

    }

    public void getOrderHistory(int customerId) throws SQLException{
        ArrayList<OrderHistory> orderHistory = orderRepository.getOrderHistory(customerId);


        for (OrderHistory orders : orderHistory){
            System.out.println(orders);
        }
    }


}
