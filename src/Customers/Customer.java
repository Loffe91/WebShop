package Customers;

import User.User;
import java.util.Map;

/**
 * Klass som representerar en kund i webbshopen
 * Används för att hantera kunddata mellan databasen och applikationen
 */
public class Customer extends User {

    // Privata fält för att uppnå inkapsling

    private String name;
    private Cart cart;

    /**
     * Konstruktor för att skapa en ny Customers.Customer
     * Tar emot all nödvändig information för en kund
     */
    public Customer(int userId, String name, String email, String password) {
        super(email, password);
        setUserId(userId);
        this.name = name;
        this.cart = new Cart();
    }

    // Getters och setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cart getCart() {
        return cart;
    }

    public void addToCart(String productName, int quantity) {
        cart.addProduct(productName, quantity);
    }

    public void removeFromCart(String productName) {
        cart.removeProduct(productName);
    }

    public void clearCart() {
        cart.clearCart();
    }

    public Map<String, Integer> getCartProducts() {
        return cart.getProducts();
    }

    /**
     * toString-metod för att få en läsbar representation av kunden
     * Användbar vid utskrift eller debugging
     */
    @Override
    public String toString() {
        return "Customers.Customer{" +
                "id=" + getUserId() +
                ", Name='" + name + '\'' +
                ", email='" + getEmail() + '\'' +
                ", cart size=" + cart.getProducts().size() +
                '}';
    }
}