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


    public AdminService() {

        this.customerRepository = new CustomerRepository();
        this.productRepository = new ProductRepository();
    }

    public void showAllUsers() throws SQLException {
        // Hämta alla kunder från repository-lagret
        ArrayList<Customer> customers = customerRepository.getAllCustomers();

        // Kontrollera om vi har några kunder att visa
        if (customers.isEmpty()) {
            System.out.println("Inga kunder hittades.");
            return;
        }

        // Skriv ut alla kunder med tydlig formatering
        System.out.println("\n=== Kundlista ===");
        for (Customer customer : customers) {
            System.out.println("ID: " + customer.getUserId());
            System.out.println("Namn: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Poäng:" + customer.getPoints());
            System.out.println("-----------------");
        }
    }

    public Customer getCustomerByEmail(String email) throws SQLException {
        return customerRepository.getCustomerByEmail(email);
    }

    public Customer getCustomerById(int id) throws SQLException {
        return customerRepository.getCustomerById(id);
    }

    public void deleteCustomer(int customerId) throws SQLException {
        // Kontrollera om kunden finns innan borttagning
        Customer customer = customerRepository.getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Ingen kund hittades med ID: " + customerId);
            return;
        }

        // Ta bort kunden
        customerRepository.deleteCustomer(customerId);
        System.out.println("Kunden med ID " + customerId + " har raderats.");
    }

    //Se produkter
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

    //uppdatera pris på vara
    public void updateProductPrice(int productId, double newPrice) throws SQLException {
        productRepository.updateProductPrice(productId, newPrice);
    }

    public void updateProductStock(int productId, int newStock) throws SQLException{
        productRepository.updateProductStock(productId, newStock);
    }
}
