package Model;

import java.util.List;

/**
 * Represents an order in the system.
 */
public class Order {

    /**
     * Creates a new instance of the Order class.
     *
     * @param id       The ID of the order.
     * @param client   The ID of the client associated with the order.
     * @param product  The ID of the product associated with the order.
     * @param quantity The quantity of the product in the order.
     */
    public Order(int id, int client, int product, int quantity) {
        this.id = id;
        this.client = client;
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Gets the ID of the order.
     *
     * @return The ID of the order.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the ID of the client associated with the order.
     *
     * @return The ID of the client associated with the order.
     */
    public int getClient() {
        return client;
    }

    /**
     * Gets the ID of the product associated with the order.
     *
     * @return The ID of the product associated with the order.
     */
    public int getProduct() {
        return product;
    }

    /**
     * Gets the quantity of the product in the order.
     *
     * @return The quantity of the product in the order.
     */
    public int getQuantity() {
        return quantity;
    }

    private int id;

    /**
     * Sets the ID of the order.
     *
     * @param id The ID of the order.
     */
    public void setId(int id) {
        this.id = id;
    }


    private int client;
    private int product;
    private int quantity;


    private int price;

    /**
     * Gets the price of the order.
     *
     * @return The price of the order.
     */
    public int getPrice() { return price; }
}