package Orders;

import Customers.Cart;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderController {

  public void addOrder(Cart cart) {
      ArrayList<String> bongo = new ArrayList<>();

    Cart.placeOrder(1, "bongo", 1.0);
  }
}
