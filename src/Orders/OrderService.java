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


    /**
     * Metod för att lägga en order.
     * Tillämpas rabatt baserat på kundens lojalitetsnivå och poäng uppdateras.
     */
    public boolean placeOrder(int customerId, ArrayList<OrderProduct> cart) throws SQLException {
        // Kontrollera att varukorgen inte är tom
        if (cart.isEmpty()) {
            System.out.println("Varukorgen är tom. Kan ej lägga en order");
            return false;
        }

        try {
            // Hämta kund från databasen
            Customer customer = customerRepository.getCustomerById(customerId);
            if (customer == null) {
                System.out.println("Kunde ej hitta kunden.");
                return false;
            }

            // Skapa order i databasen
            int orderId = orderRepository.createOrder(customerId);
            if (orderId == -1) {
                System.out.println("Kunde ej skapa order.");
                return false;
            }

            // Lägg till orderprodukter och uppdatera lagerstatus
            orderRepository.orderProductInsert(orderId, cart);
            orderRepository.updateStock(cart);

            //  Spara rabattnivå och kundstatus innan poängen ändras
            int discount = customer.getDiscountLevel().getDiscount();  // Spara rabattvärdet
            boolean isNewCustomer = customer.isNewCustomer();  // Spara om kunden var ny

            // Beräkna totalpris och applicera rabatt
            double totalPrice = getTotalPrice(cart);
            double finalPrice = customer.applyDiscount(totalPrice);

            // Lägg till poäng för köpet och uppdatera kundens poäng i databasen
            int earnedPoints = (int) finalPrice;
            customer.addPoints(earnedPoints);
            customerRepository.updateCustomerPoints(customerId, customer.getPoints(), customer.isNewCustomer());


            // Utskrift av orderinformation
            System.out.println("Order bearbetad för " + customer.getName());
            System.out.println("Du har lagt en order. Totalt pris: " + String.format("%.2f", totalPrice));
            System.out.println("Rabatt tillämpad: " + discount + "%");
            System.out.println("Totalbelopp efter rabatt: " + String.format("%.2f", applyDiscount(customer, finalPrice)));
            System.out.println("Nya Kundpoäng: " + customer.getPoints());

            // Använd den sparade variabeln för att säkerställa att "Ny kund" skrivs ut korrekt
            String discountLevel = isNewCustomer ? "Ny kund" : customer.getDiscountLevel().name();
            System.out.println("Nuvarande Kundnivå: " + discountLevel);


            return true;
        } catch (SQLException e) {
            System.out.println("Ett fel uppstod: " + e.getMessage());
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
    public double applyDiscount(Customer customer, double totalPrice)
    {
        int discount = customer.getDiscountLevel().getDiscount();
        return Math.round(totalPrice * (1 - (discount / 100.0)) * 100.0) / 100.0;
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