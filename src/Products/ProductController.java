package Products;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductController {
    ProductRepository repository = new ProductRepository();

    public void run() {
        //Prints all the data in the db
        /*try {
            ArrayList<Product> products = repository.getProducts();
            for (Product product : products) {
                System.out.println(product);
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }*/
    }

    public void search() {
        try {
            String sql = "select * from products where name like ?";
            

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

}
