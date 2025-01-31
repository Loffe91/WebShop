package Customers;

/**
 * Klass som representerar en kund i webbshopen
 * Används för att hantera kunddata mellan databasen och applikationen
 */
public class Customer {

    // Privata fält för att uppnå inkapsling
    private int customerId;
    private String name;
    private String email;

    /**
     * Konstruktor för att skapa en ny Customers.Customer
     * Tar emot all nödvändig information för en kund
     *
     */
    public Customer(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    // Getters och setters för alla fält
    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * toString-metod för att få en läsbar representation av kunden
     * Användbar vid utskrift eller debugging
     */
    @Override
    public String toString() {
        return "Customers.Customer{" +
                "id=" + customerId +
                ", Name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}