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

    public boolean placeOrder(int customerId, ArrayList<OrderProduct> cart) throws SQLException {
        // Kontrollerar så varukorgen ej är tom
        if(cart.isEmpty()){
            System.out.println("Varukorgen är tom. Kan ej lägga en order");
            return false;
        }

        try {
            int orderId = orderRepository.createOrder(customerId);
            if(orderId == -1){
                System.out.println("Kunde ej skapa order. ");
                return false;
            }
            // Kallar på metod som gör insert i databasen med orderId och produkterna i arraylisten som argument
            orderRepository.orderProductInsert(orderId, cart);
            // Kallar på metod som uppdaterar lagerstatusen med produkterna i arraylisten som argument
            orderRepository.updateStock(cart);

            System.out.println("Du har lagt en order. ID: "+orderId);
            return true;

        } catch (SQLException e){
            System.out.println("Ett fel uppstod: "+e.getMessage());
            return false;
        }

    }
    // Metod för att hämta orderhistorik
    public void getOrderHistory(int customerId) throws SQLException{
        // Kallar på getOrderHistory-metoden och sparar resultatet i en ArrayList
        ArrayList<OrderHistory> orderHistory = orderRepository.getOrderHistory(customerId);
        // Loopar igenom listan och skriver ut alla orders
        for (OrderHistory orders : orderHistory){
            System.out.println(orders);
        }
    }

    public double getUnitPrice(int productId) throws SQLException{
        return orderRepository.getPrice(productId);
    }

    // Metod för att räkna ut totalpriset av en order
    public double getTotalPrice(ArrayList<OrderProduct> products){
        double totalPrice = 0;
        // Loopar igenom alla produkter i listan
        for (OrderProduct product : products){ // Multiplicerar antalet med styckpriset
            totalPrice += product.getQuantity() * product.getUnit_price();
        }
        return totalPrice;
    }

    // Kontroll av lagerstatus
    public boolean orderQuantity(int productId, int quantity) throws SQLException {
        // Kallar på stockStatus-metoden i repot med quantity & productId som argument
        boolean currentStock = orderRepository.stockStatus(productId, quantity);

        // Om tillräckligt lager ej finns
        if(!currentStock){
            System.out.println("Produkt-ID: "+productId+" har för låg lagerstatus för denna order");
        }
        return currentStock;
    }
}