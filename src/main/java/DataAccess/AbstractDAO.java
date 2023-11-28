package DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;

/**
 * Represents an abstract data access object providing common database operations.
 *
 * @param <T> The type of the object being accessed.
 */
public abstract class AbstractDAO<T> {


    @SuppressWarnings("unchecked")
    private static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private Class<T> type;
    private final String idColumnName;

    /**
     * Initializes a new instance of the AbstractDAO class.
     *
     * @param idColumnName The name of the ID column in the database table.
     */
    protected AbstractDAO(String idColumnName) {
        this.idColumnName = idColumnName;
    }

    /**
     * Creates an object of type T from the ResultSet.
     *
     * @param resultSet The ResultSet containing the data.
     * @return The created object.
     * @throws SQLException if an error occurs while accessing the ResultSet.
     */
    protected abstract T createObjectFromResultSet(ResultSet resultSet) throws SQLException;
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ").append(field).append(" = ?");
        return sb.toString();
    }


    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" (");

        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            sb.append(fields[i].getName());
            if (i != fields.length - 1) {
                sb.append(", ");
            }
        }

        sb.append(") VALUES (");

        for (int i = 0; i < fields.length; i++) {
            sb.append("?");
            if (i != fields.length - 1) {
                sb.append(", ");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    private String createUpdateQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");

        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            sb.append(fields[i].getName()).append(" = ?");
            if (i != fields.length - 1) {
                sb.append(", ");
            }
        }

        sb.append(" WHERE ").append(idColumnName).append(" = ?");
        return sb.toString();
    }

    /**
     * Retrieves all objects of type T from the database.
     *
     * @return A list of all objects retrieved.
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();
        List<T> objects = new ArrayList<>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            objects = createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return objects;
    }

    /**
     * Retrieves an object of type T by its ID from the database.
     *
     * @param id        The ID of the object.
     * @param tableName The name of the database table.
     * @return The retrieved object, or null if not found.
     */
    public T findById(int id, String tableName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + tableName + " WHERE " + idColumnName + " = ?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createObjectFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, this.getClass().getName() + ":findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }



    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor<?>[] ctors = type.getDeclaredConstructors();
        Constructor<?> ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                 | SQLException | IntrospectionException e) {
            LOGGER.log(Level.SEVERE, "Error creating objects from ResultSet: " + e.getMessage());
        }
        return list;
    }

    /**
     * Inserts an object into the database.
     *
     * @param t The object to be inserted.
     * @return The inserted object.
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            String query = createInsertQuery();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            setStatementParameters(statement, t, false);
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int generatedId = resultSet.getInt(1);
                setGeneratedId(t, generatedId);
            } else {
                LOGGER.log(Level.WARNING, "Failed to retrieve generated ID for insert operation.");
            }

            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Updates an object in the database.
     *
     * @param t The object to be updated.
     * @return The updated object.
     */
    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            String query = createUpdateQuery();
            statement = connection.prepareStatement(query);
            setStatementParameters(statement, t, true);
            statement.executeUpdate();
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private void setStatementParameters(PreparedStatement statement, T t, boolean includeId) throws SQLException {
        int parameterIndex = 1;
        for (Field field : type.getDeclaredFields()) {
            if (!includeId && field.getName().equals("id")) {
                continue;
            }
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(t);
            } catch (IllegalAccessException e) {
                LOGGER.log(Level.WARNING, "Failed to access field value: " + e.getMessage());
                continue;
            }
            statement.setObject(parameterIndex, value);
            parameterIndex++;
        }
        if (includeId) {
            setIdParameter(statement, parameterIndex, t);
        }
    }

    private void setIdParameter(PreparedStatement statement, int parameterIndex, T t) throws SQLException {
        for (Field field : type.getDeclaredFields()) {
            if (field.getName().equals(idColumnName)) {
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(t);
                } catch (IllegalAccessException e) {
                    LOGGER.log(Level.WARNING, "Failed to access ID field value: " + e.getMessage());
                    continue;
                }
                statement.setObject(parameterIndex, value);
                return;
            }
        }
        LOGGER.log(Level.WARNING, "Failed to find ID field for setting parameter.");
    }

    private void setGeneratedId(T t, int generatedId) {
        for (Field field : type.getDeclaredFields()) {
            if (field.getName().equals("id")) {
                field.setAccessible(true);
                try {
                    field.set(t, generatedId);
                } catch (IllegalAccessException e) {
                    LOGGER.log(Level.WARNING, "Failed to set generated ID field value: " + e.getMessage());
                }
                return;
            }
        }
        LOGGER.log(Level.WARNING, "Failed to find ID field for setting generated ID.");
    }
}