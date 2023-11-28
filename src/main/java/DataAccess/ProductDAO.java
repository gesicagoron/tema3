package DataAccess;

import Model.Product;
import Connection.ConnectionFactory;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for interacting with the "Product" table in the database.
 */
public class ProductDAO extends AbstractDAO<Product> {
    /**
     * Creates a new instance of ProductDAO.
     */
    public ProductDAO() {
        super("idProduct");
    }

    /**
     * Adds a new product to the database.
     *
     * @param product The product to be added.
     */
    public void add(Product product) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO Product (idProduct, name, quantity, price) VALUES (?, ?, ?, ?)";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, product.getId());
            statement.setString(2, product.getName());
            statement.setInt(3, product.getStock());
            statement.setInt(4, (int) product.getPrice());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product added successfully.");
            } else {
                System.out.println("Failed to add product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Deletes a product from the database.
     *
     * @param id The ID of the product to be deleted.
     */
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM Product WHERE idProduct = ?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Edits an existing product in the database.
     *
     * @param product The product to be edited.
     */
    public void edit(Product product) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "UPDATE Product SET name = ?, quantity = ?, price = ? WHERE idProduct = ?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getStock());
            statement.setInt(3, (int) product.getPrice());
            statement.setInt(4, product.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Retrieves all products from the database.
     *
     * @return A list of all products.
     */
    public List<Product> viewAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Product";
        List<Product> products = new ArrayList<>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("idProduct"));
                product.setName(resultSet.getString("name"));
                product.setStock(resultSet.getInt("quantity"));
                product.setPrice(resultSet.getInt("price"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return products;
    }

    protected Product createObjectFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("idProduct");
        String name = resultSet.getString("name");
        int quantity = resultSet.getInt("quantity");
        int price = resultSet.getInt("price");

        return new Product(id, name, quantity, price);
    }



}