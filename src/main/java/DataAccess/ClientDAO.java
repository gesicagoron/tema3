package DataAccess;

import Model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectionFactory;

/**
 * Data Access Object for interacting with the "Client" table in the database.
 */
public class ClientDAO extends AbstractDAO<Client> {
    public ClientDAO() {
        super("idClient");
    }


    /**
     * Adds a new client to the database.
     *
     * @param client The client object to be added.
     */
    public void add(Client client) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO Client (idClient, name, address, email, age) VALUES (?, ?, ?, ?, ?)";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, client.getId());
            statement.setString(2, client.getName());
            statement.setString(3, client.getAddress());
            statement.setString(4, client.getEmail());
            statement.setInt(5, client.getAge());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Updates an existing client in the database.
     *
     * @param client The client object to be updated.
     * @return The updated client object.
     */
    public Client update(Client client) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "UPDATE Client SET name = ?, address = ?, email = ?, age = ? WHERE idClient = ?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, client.getName());
            statement.setString(2, client.getAddress());
            statement.setString(3, client.getEmail());
            statement.setInt(4, client.getAge());
            statement.setInt(5, client.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return client;
    }

    /**
     * Deletes a client from the database.
     *
     * @param id The ID of the client to be deleted.
     */
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM Client WHERE idClient = ?";

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
     * Retrieves all clients from the database.
     *
     * @return A list of all clients.
     */
    // Find all clients
    public List<Client> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Client";
        List<Client> clients = new ArrayList<>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Client client = createClientFromResultSet(resultSet);
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return clients;
    }

    /**
     * Creates a Client object from a ResultSet row.
     *
     * @param resultSet The ResultSet containing the client data.
     * @return The created Client object.
     * @throws SQLException If a database access error occurs.
     */
    private Client createClientFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("idClient");
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String email = resultSet.getString("email");
        int age = resultSet.getInt("age");

        return new Client(id, name, address, email, age);
    }

    @Override
    protected Client createObjectFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("idClient");
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String email = resultSet.getString("email");
        int age = resultSet.getInt("age");

        return new Client(id, name, address, email, age);
    }


}