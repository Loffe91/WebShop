import Admin.Admin;
import Customers.CustomerController;
import Products.ProductController;
import User.UserController;

import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
        //CustomerController customerController = new CustomerController();
        //customerController.run();
        UserController userController = new UserController();
        userController.run();
        //ProductController productController = new ProductController();
        //productController.run();
    }
}