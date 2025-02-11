import Customers.CustomerController;
import Products.ProductController;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        CustomerController customerController = new CustomerController();
        customerController.run();

        ProductController productController = new ProductController();
        productController.run();
    }
}