package Orders;

import Customers.Cart;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class OrderService {
    private List<Order> orderList = new ArrayList<>();
    private Map<String, Double> priceList; // Prislista för produkter

    public OrderService(Map<String, Double> priceList) {
        this.priceList = priceList;
    }

    public Order createOrderFromCart(int customerId, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Kundkorgen är tom! Ingen order skapades.");
            return null;
        }

        double totalPrice = cart.calculateTotalPrice(priceList);
        List<String> productList = new ArrayList<>(cart.getProducts().keySet());

        Order newOrder = new Order(customerId, productList, totalPrice);
        orderList.add(newOrder);

        // Töm varukorgen efter order
        cart.clearCart();

        System.out.println("Order skapad! Order ID: " + newOrder.getOrderId());
        return newOrder;
    }

    public void printOrders() {
        for (Order order : orderList) {
            order.printOrderDetails();
            System.out.println("------");
        }
    }
}
