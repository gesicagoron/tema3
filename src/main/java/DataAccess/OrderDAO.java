package DataAccess;

import Model.Order;

import Connection.ConnectionFactory;

import Model.Client;
import Model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for interacting with the "Orders" table in the database.
 */
public class OrderDAO extends AbstractDAO<Order> {
    /**
     * Creates a new instance of OrderDAO.
     */
    public OrderDAO() {
        super("idorder"); // Specify the ID column name for OrderDAO
    }

    /**
     * Places an order by adding it to the database.
     *
     * @param order The order to be placed.
     */
    public void placeOrder(Order order) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String insertQuery = "INSERT INTO `Orders` (idorder, idclient, idproduct, quantity, totalprice) VALUES (?, ?, ?, ?, ?)";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, order.getId()); // Use the provided order ID instead of generating it
            statement.setInt(2, order.getClient());
            statement.setInt(3, order.getProduct());
            statement.setInt(4, order.getQuantity());
            statement.setInt(5, order.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to place the order. No rows affected.");
            }

            // Retrieve the auto-generated order ID
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                order.setId(orderId);
            } else {
                throw new SQLException("Failed to place the order. No ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }


    /**
     * Retrieves the Product object by ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return The retrieved Product object.
     */
    private Product getProductById(int productId) {
        ProductDAO productDAO = new ProductDAO();
        return productDAO.findById(productId, "Product");
    }

    /**
     * Retrieves the Client object by ID.
     *
     * @param clientId The ID of the client to retrieve.
     * @return The retrieved Client object.
     */
    private Client getClientById(int clientId) {
        ClientDAO clientDAO = new ClientDAO();
        return clientDAO.findById(clientId, "Client");
    }

    /**
     * Creates an Order object from a ResultSet row.
     *
     * @param resultSet The ResultSet containing the order data.
     * @return The created Order object.
     * @throws SQLException If a database access error occurs.
     */
    @Override

    protected Order createObjectFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("idorder");
        int clientId = resultSet.getInt("idclient");
        int productId = resultSet.getInt("idproduct");
        int quantity = resultSet.getInt("quantity");
        int totalPrice = resultSet.getInt("totalprice");

        // Retrieve the associated Client and Product objects
        Client client = getClientById(clientId);
        Product product = getProductById(productId);

        return new Order(id, clientId, productId, quantity); // Pass totalPrice to the Order constructor
    }


    /**
     * Retrieves all orders from the database.
     *
     * @return A list of all orders.
     */
    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `Orders`");

            while (resultSet.next()) {
                int id = resultSet.getInt("idorder");
                int clientId = resultSet.getInt("idclient");
                int productId = resultSet.getInt("idproduct");
                int quantity = resultSet.getInt("quantity");
                int totalPrice = resultSet.getInt("totalprice");

                // Retrieve the associated Client and Product objects
                Client client = getClientById(clientId);
                Product product = getProductById(productId);

                Order order = new Order(id, clientId, productId, quantity);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return orders;
    }



}