package Customers;

import Orders.OrderProduct;
import User.User;
import java.util.logging.*;
import java.util.ArrayList;

/**
 * Klass som representerar en kund i webbshopen.
 * Används för att hantera kunddata mellan databasen och applikationen.
 */
public class Customer extends User {

    // Privata fält för att uppnå inkapsling
    private String name;
    private int points;
    private boolean isNewCustomer;
    public ArrayList<OrderProduct> cart = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(Customer.class.getName());

    /**
     * Konstruktor för att skapa en ny Customer.
     * Tar emot all nödvändig information för en kund.
     */
    public Customer(int userId, String name, String email, String password, boolean isNewCustomer, int points) {
        super(email, password);
        setUserId(userId);
        this.points = points;
        this.name = name;
        this.isNewCustomer = isNewCustomer;
        this.cart = new ArrayList<>();
    }

    // Getters och setters för alla fält
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public boolean isNewCustomer() {
        return isNewCustomer;
    }


    /**
     * Metod för att lägga till poäng till kunden.
     * Efter första köpet blir kunden inte längre en ny kund.
     */
    public void addPoints(int amount) {
        this.points += amount;
        if (isNewCustomer) {
            this.isNewCustomer = false;  // Uppdateras kundens status efter första köpet
        }
    }
    /**
     * Metod för att få kundens nuvarande rabattnivå.
     * Returnerar en DiscountLevel baserat på kundens poäng och om de är en ny kund.
     */
    public DiscountLevel getDiscountLevel() {
        return DiscountLevel.getLevel(this.points, this.isNewCustomer);
    }

    /**
     * Metod för att applicera rabatten baserat på kundens rabattnivå.
     * Returnerar det nya totalpriset efter att rabatten har tillämpats.
     */
    public double applyDiscount(double totalPrice) {
        int discount = getDiscountLevel().getDiscount();
        return Math.round(totalPrice * (1 - (discount / 100.0)) * 100.0) / 100.0;
    }

    /**
     * Metod för att lägga till en produkt i kundens varukorg.
     */
    public void addToCart(OrderProduct product){
        cart.add(product);
        System.out.println("Produkt "+product.getProductId() +" har lagts till i varukorgen. ");
    }

    /**
     * Metod för att ta bort en produkt från varukorgen baserat på produktens ID.
     */
    public void removeFromCart(int productId){
        cart.removeIf(product -> product.getProductId() == productId);
        System.out.println("Produkt "+productId + " har tagits bort från varukorgen. ");
    }

    /**
     * Metod för att tömma varukorgen.
     */
    public void clearCart() {
        cart.clear();
        System.out.println("Varukorgen har tömts. ");
    }

    /**
     * Metod för att visa varukorgens innehåll och det totala priset efter rabatt.
     */
    public void viewCart(){
        if(cart.isEmpty()){
            System.out.println("Varukorgen är tom. ");
            logger.info("Kundens varukorg är tom");
            return;
        }
        System.out.println("\n=== Varukorg ===");
        double totalPrice = getTotalPrice();
        for (OrderProduct product : cart){
            System.out.println("Produkt-ID: " +product.getProductId() +
                    "\nAntal: "+product.getQuantity() +
                    "\nPris per enhet: "+product.getUnit_price() +
                    "\n--------");
        }
        System.out.println("Totalt belopp efter rabatt: "+applyDiscount(totalPrice));
    }

    /**
     * Metod för att beräkna det totala priset för alla produkter i varukorgen.
     */
    public double getTotalPrice(){
        double total = 0;
        for (OrderProduct product : cart){
            total += product.getQuantity() * product.getUnit_price();
        }
        return total;
    }

    /**
     * toString-metod för att få en läsbar representation av kunden.
     * Inkluderar kundens ID, namn, e-post, poäng, om de är en ny kund och deras rabattnivå.
     */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getUserId() +
                ", name='" + name + '\'' +
                ", email='" + getEmail() + '\'' +
                ", points=" + points +
                ", isNewCustomer=" + isNewCustomer +
                ", discountLevel=" + getDiscountLevel() +
                '}';
    }
}