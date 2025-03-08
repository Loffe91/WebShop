package Customers;

import Orders.OrderProduct;
import Products.Product;
import User.User;

import java.util.ArrayList;

/**
 * Klass som representerar en kund i webbshopen.
 * Används för att hantera kunddata mellan databasen och applikationen.
 */
public class Customer extends User {

    // Privata fält för att uppnå inkapsling
    private String name;
    public ArrayList<OrderProduct> cart = new ArrayList<>();

    /**
     * Konstruktor för att skapa en ny Customer.
     * Tar emot all nödvändig information för en kund.
     */
    public Customer(int userId, String name, String email, String password) {
        super(email, password);
        setUserId(userId);
        this.name = name;
        this.cart = new ArrayList<>();
    }

    // Getters och setters för alla fält
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addToCart(OrderProduct product){
        cart.add(product);
        System.out.println("Produkt "+product.getProductId() +" har lagts till i varukorgen. ");
    }
    public void removeFromCart(int productId){
        cart.removeIf(product -> product.getProductId() == productId);
        System.out.println("Produkt "+productId + " har tagits bort från varukorgen. ");
    }

    public void clearCart() {
        cart.clear();
        System.out.println("Varukorgen har tömts. ");
    }
    public void viewCart(){
        if(cart.isEmpty()){
            System.out.println("Varukorgen är tom. ");
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
        System.out.println("Totalt belopp: "+totalPrice);
    }
    public double getTotalPrice(){
        double total = 0;
        for (OrderProduct product : cart){
            total += product.getQuantity() * product.getUnit_price();
        }
        return total;
    }

    /**
     * toString-metod för att få en läsbar representation av kunden.
     * Användbar vid utskrift eller debugging.
     */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getUserId() +
                ", name='" + name + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}