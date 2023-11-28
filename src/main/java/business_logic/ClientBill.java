package business_logic;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import DataAccess.ClientDAO;
import business_logic.validator.EmailValidator;
import business_logic.validator.Validator;
import Model.Client;

/**
 * Represents a class for managing client business logic operations.
 */
public class ClientBill {

    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    /**
     * Initializes a new instance of the ClientBill class.
     * It sets up the validators and client DAO.
     */
    public ClientBill() {
        validators = new ArrayList<>();
        validators.add(new EmailValidator());

        clientDAO = new ClientDAO();
    }

    /**
     * Finds a client by their ID.
     *
     * @param id The ID of the client.
     * @return The client object with the specified ID.
     * @throws NoSuchElementException if the client with the given ID is not found.
     */
    public Client findClientById(int id) {
        Client client = clientDAO.findById(id, "Client");
        if (client == null) {
            throw new NoSuchElementException("The client with id = " + id + " was not found!");
        }
        return client;
    }

    /**
     * Retrieves a list of all clients.
     *
     * @return A list containing all the clients.
     */
    public List<Client> getAllClients() {
        return clientDAO.findAll();
    }

    /**
     * Adds a new client.
     *
     * @param client The client to be added.
     */
    public void addClient(Client client) {
        validateClient(client);
        clientDAO.add(client);
    }

    /**
     * Updates an existing client.
     *
     * @param client The client to be updated.
     */
    public void updateClient(Client client) {
        validateClient(client);
        clientDAO.update(client);
    }

    /**
     * Deletes a client by their ID.
     *
     * @param id The ID of the client to be deleted.
     */
    public void deleteClient(int id) {
        clientDAO.delete(id);
    }

    /**
     * Validates a client using the registered validators.
     *
     * @param client The client to be validated.
     */
    private void validateClient(Client client) {
        for (Validator<Client> validator : validators) {
            validator.validate(client);
        }
    }
}