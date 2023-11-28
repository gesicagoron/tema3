package business_logic;

import Model.Order;
import DataAccess.OrderDAO;
import Model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a class for managing order operations.
 */
public class OrderBll {
    private OrderDAO orderDAO= new OrderDAO(); // Add an instance of the OrderDAO


    /**
     * Initializes a new instance of the OrderBll class.
     * It sets up the OrderDAO.
     */
    public OrderBll() {
        orderDAO = new OrderDAO(); // Initialize the OrderDAO
    }

    /**
     * Creates a new order.
     *
     * @param order The order to be created.
     */
    public void createOrder(Order order) {
        ProductBll productService = new ProductBll();
        Product product = productService.getProductById(order.getProduct());
        if (product != null && productService.checkStockAvailability(product, order.getQuantity())) {
            // Generate a unique order ID
            int orderId = generateID();
            order.setId(orderId); // Set the generated order ID
            // Place the order in the database
            orderDAO.placeOrder(order);
            productService.updateProductStock(product, order.getQuantity());
        } else {
            System.out.println("Under-stock message: Not enough products available.");
        }
    }

    /**
     * Generates a unique order ID.
     *
     * @return The generated order ID.
     */
    private int orderIdCounter = 1; // Initial value for the order ID counter
    public int generateID() {
        int orderId = orderIdCounter;
        orderIdCounter++; // Increment the counter for the next order
        return orderId;
    }

    /**
     * Retrieves a list of all orders.
     *
     * @return A list containing all the orders.
     */
    public List<Order> getAllOrders() {
        return orderDAO.findAll(); // Retrieve all orders from the database
    }
}