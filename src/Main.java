
import Customers.Customer;
import Customers.CustomerController;
import Orders.OrderRepository;
import Products.ProductController;
import User.UserController;

import java.sql.*;


public class Main {
    public static void main(String[] args) throws SQLException {
        //CustomerController customerController = new CustomerController();
        //customerController.run();
        //UserController userController = new UserController();
        //userController.run();
        ProductController productController = new ProductController();
        productController.run();

        //OrderRepository orderRepository = new OrderRepository();
        //OrderProduct orderProduct = new OrderProduct(1, 1, 999.99);

        //ArrayList<OrderProduct> orderProducts = new ArrayList<>();
        //orderProducts.add(orderProduct);

        //orderRepository.orderProductInsert(14, orderProducts);

        //double test = orderRepository.getPrice(1);
        //System.out.println(test);
    }
}