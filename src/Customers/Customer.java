package Customers;

import User.User;

/**
 * Klass som representerar en kund i webbshopen.
 * Används för att hantera kunddata mellan databasen och applikationen.
 */
public class Customer extends User {

    // Privata fält för att uppnå inkapsling
    private String name;
    private String newEmail; // Lägg till detta fält för ny e-postadress

    /**
     * Konstruktor för att skapa en ny Customer.
     * Tar emot all nödvändig information för en kund.
     */
    public Customer(int userId, String name, String email, String password) {
        super(email, password);
        setUserId(userId);
        this.name = name;
    }

    // Getters och setters för alla fält
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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