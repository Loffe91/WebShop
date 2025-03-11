package Admin;

import Customers.Customer;
import Customers.CustomerRepository;
import Products.Product;
import Products.ProductRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminService {
    CustomerRepository customerRepository;
    ProductRepository productRepository;

///Konstruktor för initialisering av repository lagren
    public AdminService() {
        this.customerRepository = new CustomerRepository();
        this.productRepository = new ProductRepository();
    }

    /// Hämta alla kunder från repository-lagret
    public void showAllUsers() throws SQLException {

        ArrayList<Customer> customers = customerRepository.getAllCustomers();

        /// Kontrollera om vi har några kunder att visa
        if (customers.isEmpty()) {
            System.out.println("Inga kunder hittades.");
            return;
        }

        /// Skriv ut alla kunder med tydlig formatering
        System.out.println("\n=== Kundlista ===");
        for (Customer customer : customers) {
            System.out.println("ID: " + customer.getUserId());
            System.out.println("Namn: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Poäng:" + customer.getPoints());
            System.out.println("-----------------");
        }
    }
    ///Metod för att hämta kund baserat på e-mail
    public Customer getCustomerByEmail(String email) throws SQLException {
        return customerRepository.getCustomerByEmail(email);
    }
    ///Metod för att hämta kund baserat på ID
    public Customer getCustomerById(int id) throws SQLException {
        return customerRepository.getCustomerById(id);
    }
    ///Metod för att ta bort kund
    public void deleteCustomer(int customerId) throws SQLException {
        //Kontrollera om kunden finns innan borttagning
        Customer customer = customerRepository.getCustomerById(customerId);
        if (customer == null) {
            return;
        }

        ///Ta bort kunden från customerRepository
        customerRepository.deleteCustomer(customerId);
        System.out.println("Kunden med ID " + customerId + " har raderats.");
    }

    ///Se alla produkter
    public void showAllProducts() throws SQLException {
        ArrayList<Product> products = productRepository.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Inga produkter hittades.");
            return;
        }
        System.out.println("\n=== Produktlista ===");
        for (Product product : products) {
            System.out.println("ID: " + product.getProductId());
            System.out.println("Varunamn: " + product.getName());
            System.out.println("Pris: " + product.getPrice());
            System.out.println("Lagersaldo: " + product.getStockQuantity());
            System.out.println("-----------------");
        }
    }

    ///Uppdatera pris på vara
    public void updateProductPrice(int productId, double newPrice) throws SQLException {
        productRepository.updateProductPrice(productId, newPrice);
    }

    ///uppdatera lagersaldo för vara
    public void updateProductStock(int productId, int quantity) throws SQLException {
        productRepository.updateProductStock(productId, quantity);
    }
}
