import Customers.Customer;
import Customers.CustomerController;
import Products.Product;
import Products.ProductRepository;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws SQLException {



                CustomerController customerController = new CustomerController();
                customerController.run();



        /*Customer customer = new Customer(8, "Olof", "Olofsven@gmail.com", "Olof");
        ProductRepository productRepo = new ProductRepository();
        Map<Integer, String> products = new HashMap<>();


        for (Product product : productRepo.getAllProducts()) {
            products.put(product.getProductId(), product.getName());
        }


        //System.out.println("Produkter i systemet: " + products);

        // Kontrollera om det finns produkter innan vi lägger till dem i varukorgen
        if (!products.isEmpty()) {
            if (products.containsKey(1)) {
                customer.addToCart(products.get(1), 1);
            }
            if (products.containsKey(3)) {
                customer.addToCart(products.get(3), 2);
            }

            System.out.println("Kundens varukorg: " + customer.getCart().getProducts());
        } else {
            System.out.println("Inga produkter hittades i databasen.");*/
        }

    }


