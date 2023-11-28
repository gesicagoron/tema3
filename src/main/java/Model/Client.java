package Model;

/**
 * Represents a client in the system.
 */
public class Client {
    private int id;

    /**
     * Gets the email of the client.
     *
     * @return The email of the client.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the age of the client.
     *
     * @return The age of the client.
     */
    public int getAge() {
        return age;
    }

    private String name;
    private String address;

    /**
     * Sets the email of the client.
     *
     * @param email The email of the client.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the age of the client.
     *
     * @param age The age of the client.
     */
    public void setAge(int age) {
        this.age = age;
    }

    private String email;
    private int age;

    /**
     * Creates a new instance of the Client class.
     *
     * @param id      The ID of the client.
     * @param name    The name of the client.
     * @param address The address of the client.
     * @param email   The email of the client.
     * @param age     The age of the client.
     */
    public Client(int id, String name, String address,String email,int age) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email=email;
        this.age=age;
    }


    /**
     * Gets the ID of the client.
     *
     * @return The ID of the client.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the client.
     *
     * @return The name of the client.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the address of the client.
     *
     * @return The address of the client.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the ID of the client.
     *
     * @param id The ID of the client.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the name of the client.
     *
     * @param name The name of the client.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the address of the client.
     *
     * @param address The address of the client.
     */
    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + ", age=" + age
                + "]";
    }


}