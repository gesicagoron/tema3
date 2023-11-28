package Presentation;

import DataAccess.ClientDAO;
import DataAccess.OrderDAO;
import DataAccess.ProductDAO;
import Model.Client;
import Model.Order;
import Model.Product;
import business_logic.ClientBill;
import business_logic.OrderBll;
import business_logic.ProductBll;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

/**
 * The Controller class acts as an intermediary between the View and the business logic.
 * It handles user actions from the View and interacts with the corresponding business logic classes.
 */
public class Controller {
    private ClientBill clientBill;
    private OrderBll orderBll;
    private ProductBll productBll;

    private View view;

    /**
     * Initializes the Controller by creating instances of the business logic classes and the View.
     * Sets the Controller instance for the View.
     */
    public Controller() {
        clientBill = new ClientBill();
        productBll=new ProductBll();
        orderBll=new OrderBll();
        view = new View();
        view.setController(this);
    }

    /**
     * Adds a new client with the given details.
     *
     * @param id      The client ID
     * @param name    The client name
     * @param address The client address
     * @param email   The client email
     * @param age     The client age
     */
    public void addClient(int id, String name, String address, String email, int age) {
        Client client = new Client(id, name, address, email, age);
        clientBill.addClient(client);
    }

    /**
     * Updates the details of an existing client.
     *
     * @param id      The client ID
     * @param name    The client name
     * @param address The client address
     * @param email   The client email
     * @param age     The client age
     */
    public void editClient(int id, String name, String address, String email, int age) {
        Client client = new Client(id, name, address, email, age);
        clientBill.updateClient(client);
    }

    /**
     * Deletes a client with the specified ID.
     *
     * @param id The ID of the client to delete
     */
    public void deleteClient(int id) {
        clientBill.deleteClient(id);
    }

    /**
     * Retrieves all clients from the database and displays them in the View.
     */
    public void getAllClients() {
        List<Client> clients = clientBill.getAllClients();
        view.displayClients(clients);
    }

    /**
     * Creates a new order with the selected client, product, and quantity.
     *
     * @param selectedClient  The ID of the selected client
     * @param selectedProduct The ID of the selected product
     * @param quantity        The quantity of the product to order
     */
    public void createOrder(int selectedClient, int selectedProduct, int quantity) {
        int orderID=generateID();
        Order order = new Order(orderID, selectedClient, selectedProduct, quantity);
        orderBll.createOrder(order);
    }

    private int orderIdCounter = 1; // Initial value for the order ID counter

    /**
     * Generates a unique order ID.
     *
     * @return The generated order ID
     */
    public int generateID() {
        int orderId = orderIdCounter;
        orderIdCounter++; // Increment the counter for the next order
        return orderId;
    }

    /**
     * Retrieves all products from the database and displays them in the View.
     */
    public void getAllProducts() {
        List<Product> products = productBll.getAllProducts();
        view.displayProducts(products);
    }

    /**
     * Deletes a product with the specified ID.
     *
     * @param id The ID of the product to delete
     */
    public void deleteProduct(int id) {
        productBll.deleteProduct(id);
    }

    /**
     * Updates the details of an existing product.
     *
     * @param id    The product ID
     * @param name  The product name
     * @param price The product price
     */
    public void editProduct(int id, String name, int price) {
        Product product = new Product(id, name, price, 5);
        productBll.editProduct(product);
    }

    /**
     * Adds a new product with the given details.
     *
     * @param id       The product ID
     * @param name     The product name
     * @param price    The product price
     * @param quantity The product quantity
     */
    public void addProduct(int id, String name, int price, int quantity) {
        Product product = new Product(id, name, price, quantity);
        productBll.addProduct(product);
    }

    /**
     * Retrieves all orders from the database and displays them in the View.
     */
    public void getAllOrders() {
        List<Order> orders = orderBll.getAllOrders();
        view.displayOrders(orders);
    }
}