import Customers.CustomerController;
import Products.Product;
import Products.ProductController;
import Products.ProductRepository;

import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {

        /*CustomerController customerController = new CustomerController();

        customerController.run();*/

        ProductController productController = new ProductController();
        productController.run();




    }
}