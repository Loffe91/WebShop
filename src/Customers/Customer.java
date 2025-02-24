package Customers;

import User.User;
import java.util.Map;

/**
 * Klass som representerar en kund i webbshopen.
 * Ärver från User och hanterar specifika kundrelaterade funktioner
 * såsom varukorgshantering.
 */
public class Customer extends User {

    // Privata fält för att uppnå inkapsling
    private String name; // Kundens namn
    private Cart cart;   // Kundens varukorg

    /**
     * Konstruktor för att skapa en ny kund.
     *
     * @param userId   Unikt ID för kunden
     * @param name     Kundens namn
     * @param email    Kundens e-postadress
     * @param password Kundens lösenord
     */
    public Customer(int userId, String name, String email, String password) {
        super(email, password); // Anropar basklassens konstruktor
        setUserId(userId); // Sätter användar-ID från basklassen
        this.name = name;
        this.cart = new Cart(); // Initierar en tom varukorg
    }

    // Getters och setters

    /**
     * Hämtar kundens namn.
     *
     * @return Namnet på kunden
     */
    public String getName() {
        return name;
    }

    /**
     * Uppdaterar kundens namn.
     *
     * @param name Nytt namn för kunden
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Hämtar kundens varukorg.
     *
     * @return Kundens varukorg som ett Cart-objekt
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Lägger till en produkt i kundens varukorg.
     *
     * @param productName Namnet på produkten som ska läggas till
     * @param quantity    Antal av produkten som ska läggas till
     */
    public void addToCart(String productName, int quantity) {
        cart.addProduct(productName, quantity);
    }

    /**
     * Tar bort en produkt från kundens varukorg.
     *
     * @param productName Namnet på produkten som ska tas bort
     */
    public void removeFromCart(String productName) {
        cart.removeProduct(productName);
    }

    /**
     * Tömmer kundens varukorg på alla produkter.
     */
    public void clearCart() {
        cart.clearCart();
    }

    /**
     * Hämtar en lista över produkterna i varukorgen.
     *
     * @return En Map med produktnamn som nycklar och antal som värden
     */
    public Map<String, Integer> getCartProducts() {
        return cart.getProducts();
    }

    /**
     * Returnerar en strängrepresentation av kunden.
     * Användbar för utskrift och debugging.
     *
     * @return En sträng som beskriver kunden och innehållet i varukorgen
     */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getUserId() +
                ", Name='" + name + '\'' +
                ", email='" + getEmail() + '\'' +
                ", cart size=" + cart.getProducts().size() +
                '}';
    }
}