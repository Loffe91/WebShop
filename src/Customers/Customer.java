package Customers;

import User.User;

/**
 * Klass som representerar en kund i webbshopen
 * Används för att hantera kunddata mellan databasen och applikationen
 */
public class Customer extends User {

    // Privata fält för att uppnå inkapsling

    private String name;

    /**
     * Konstruktor för att skapa en ny Customers.Customer
     * Tar emot all nödvändig information för en kund
     *
     */
    public Customer(int customerId, String name, String email, String password) {
        super(email, password, "customer"); // Customer som roll för att skilja från Admin
        this.userId = customerId; // Ärver userId från User-klassen men ändrar variabelnamn till customerId för customers
        this.name = name;
    }

    // Getters och setters för alla fält

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = name;
    }


    /**
     * toString-metod för att få en läsbar representation av kunden
     * Användbar vid utskrift eller debugging
     */
    @Override
    public String toString() {
        return "Customers.Customer{" +
                "id=" + userId +
                ", Name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}