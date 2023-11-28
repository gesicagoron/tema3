package Presentation;

import Model.Client;
import Model.Order;
import Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The View class represents the graphical user interface of the application.
 */
public class View {
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JPanel clientPanel;
    private JPanel productPanel;
    private JPanel orderPanel;

    private DefaultTableModel orderTableModel;
    private JTable orderTable;
    private JButton placeOrderButton;
    private JButton viewOrdersButton;

    // Client Operations
    private JButton addClientButton;
    private JButton editClientButton;
    private JButton deleteClientButton;
    private JButton getAllClientsButton;
    private JTable clientTable;
    private DefaultTableModel clientTableModel;

    // Product Operations
    private JButton addProductButton;
    private JButton editProductButton;
    private JButton deleteProductButton;
    private JButton getAllProductsButton;
    private JTable productTable;
    private DefaultTableModel productTableModel;


    private Controller controller;

    /**
     * Constructs a new View object.
     */
    public View() {
        initialize();
        addEventListeners();
    }

    private void initialize() {
        frame = new JFrame("Client and Product Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        // Client Operations
        clientPanel = new JPanel(new BorderLayout());
        clientTableModel = new DefaultTableModel();
        clientTableModel.addColumn("ID");
        clientTableModel.addColumn("Name");
        clientTableModel.addColumn("Address");
        clientTableModel.addColumn("Email");
        clientTableModel.addColumn("Age");
        clientTable = new JTable(clientTableModel);
        JScrollPane clientScrollPane = new JScrollPane(clientTable);
        JPanel clientButtonPanel = new JPanel(new FlowLayout());
        addClientButton = new JButton("Add Client");
        editClientButton = new JButton("Edit Client");
        deleteClientButton = new JButton("Delete Client");
        getAllClientsButton = new JButton("Get All Clients");
        clientButtonPanel.add(addClientButton);
        clientButtonPanel.add(editClientButton);
        clientButtonPanel.add(deleteClientButton);
        clientButtonPanel.add(getAllClientsButton);
        clientPanel.add(clientScrollPane, BorderLayout.CENTER);
        clientPanel.add(clientButtonPanel, BorderLayout.SOUTH);

        // Product Operations
        productPanel = new JPanel(new BorderLayout());
        productTableModel = new DefaultTableModel();
        productTableModel.addColumn("ID");
        productTableModel.addColumn("Name");
        productTableModel.addColumn("Price");
        productTableModel.addColumn("Quantity");
        productTable = new JTable(productTableModel);
        JScrollPane productScrollPane = new JScrollPane(productTable);
        JPanel productButtonPanel = new JPanel(new FlowLayout());
        addProductButton = new JButton("Add Product");
        editProductButton = new JButton("Edit Product");
        deleteProductButton = new JButton("Delete Product");
        getAllProductsButton = new JButton("Get All Products");
        productButtonPanel.add(addProductButton);
        productButtonPanel.add(editProductButton);
        productButtonPanel.add(deleteProductButton);
        productButtonPanel.add(getAllProductsButton);
        productPanel.add(productScrollPane, BorderLayout.CENTER);
        productPanel.add(productButtonPanel, BorderLayout.SOUTH);

        // Order Creation
        orderPanel = new JPanel(new BorderLayout());
        orderTableModel = new DefaultTableModel();
        orderTableModel.addColumn("Order ID");
        orderTableModel.addColumn("Client ID");
        orderTableModel.addColumn("Product ID");
        //   orderTableModel.addColumn("Quantity");
        orderTable = new JTable(orderTableModel);
        JScrollPane orderScrollPane = new JScrollPane(orderTable);
        JPanel orderButtonPanel = new JPanel(new FlowLayout());
        placeOrderButton = new JButton("Place Order");
        orderButtonPanel.add(placeOrderButton);
        orderPanel.add(orderScrollPane, BorderLayout.CENTER);
        orderPanel.add(orderButtonPanel, BorderLayout.SOUTH);
        viewOrdersButton = new JButton("View Orders");
        orderButtonPanel.add(viewOrdersButton);


        tabbedPane.addTab("Clients", clientPanel);
        tabbedPane.addTab("Products", productPanel);
        tabbedPane.addTab("Orders", orderPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void addEventListeners() {
        addClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter client id:"));
                String name = JOptionPane.showInputDialog(frame, "Enter client name:");
                String address = JOptionPane.showInputDialog(frame, "Enter client address:");
                String email = JOptionPane.showInputDialog(frame, "Enter client email:");
                int age = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter client age:"));
                controller.addClient(id, name, address, email, age);
            }
        });

        editClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idStr = JOptionPane.showInputDialog(frame, "Enter client ID:");
                int id = Integer.parseInt(idStr);
                String name = JOptionPane.showInputDialog(frame, "Enter new client name:");
                String address = JOptionPane.showInputDialog(frame, "Enter new client address:");
                String email = JOptionPane.showInputDialog(frame, "Enter new client email:");
                int age = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter new client age:"));
                controller.editClient(id, name, address, email, age);
            }
        });

        deleteClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idStr = JOptionPane.showInputDialog(frame, "Enter client ID:");
                int id = Integer.parseInt(idStr);
                controller.deleteClient(id);
            }
        });

        getAllClientsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.getAllClients();
            }
        });

        addProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter product id:"));
                String name = JOptionPane.showInputDialog(frame, "Enter product name:");
                double price = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter product price:"));
                int quantity = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter product quantity:"));
                controller.addProduct(id, name, (int) price,quantity);
            }
        });

        editProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idStr = JOptionPane.showInputDialog(frame, "Enter product ID:");
                int id = Integer.parseInt(idStr);
                String name = JOptionPane.showInputDialog(frame, "Enter new product name:");
                double price = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter new product price:"));
                int quantity = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter product quantity:"));
                controller.editProduct(id,name, (int) price);
            }
        });

        deleteProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idStr = JOptionPane.showInputDialog(frame, "Enter product ID:");
                int id = Integer.parseInt(idStr);
                controller.deleteProduct(id);
            }
        });

        getAllProductsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.getAllProducts();
            }
        });


        getAllClientsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.getAllClients();
            }
        });

        // ...

        getAllProductsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.getAllProducts();
            }
        });

        placeOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                View.OrderInputDialogResult inputResult = showOrderInputDialog();
                if (inputResult != null) {
                    int clientId = inputResult.getClientId();
                    int productId = inputResult.getProductId();
                    int quantity = inputResult.getQuantity();
                    controller.createOrder(clientId, productId, quantity);
                }
            }
        });


        viewOrdersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.getAllOrders();
            }
        });




    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void displayClients(List<Client> clients) {
        clientTableModel.setRowCount(0);
        for (Client client : clients) {
            clientTableModel.addRow(new Object[]{client.getId(), client.getName(), client.getAddress(),client.getEmail(),client.getAge()});
        }
    }

    public void displayProducts(List<Product> products) {
        productTableModel.setRowCount(0);
        for (Product product : products) {
            productTableModel.addRow(new Object[]{product.getId(), product.getName(), product.getPrice(),product.getStock()});
        }
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void displayOrderSuccessMessage() {
        JOptionPane.showMessageDialog(frame, "Order placed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }


    public void displayUnderStockMessage() {
        JOptionPane.showMessageDialog(frame, "Not enough products in stock.", "Under Stock", JOptionPane.WARNING_MESSAGE);
    }





    public OrderInputDialogResult showOrderInputDialog() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        JLabel clientLabel = new JLabel("Client ID:");
        JLabel productLabel = new JLabel("Product ID:");
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField clientTextField = new JTextField();
        JTextField productTextField = new JTextField();
        JTextField quantityTextField = new JTextField();
        panel.add(clientLabel);
        panel.add(clientTextField);
        panel.add(productLabel);
        panel.add(productTextField);
        panel.add(quantityLabel);
        panel.add(quantityTextField);

        int result = JOptionPane.showConfirmDialog(
                frame,
                panel,
                "Place Order",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                int clientId = Integer.parseInt(clientTextField.getText());
                int productId = Integer.parseInt(productTextField.getText());
                int quantity = Integer.parseInt(quantityTextField.getText());

                return new OrderInputDialogResult(clientId, productId, quantity);
            } catch (NumberFormatException e) {
                // Display an error message if the input values are not valid integers
                JOptionPane.showMessageDialog(frame, "Invalid input values.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return null;
    }

    public void displayOrders(List<Order> orders) {
        orderTableModel.setRowCount(0);

        // Add orders to the table model
        for (Order order : orders) {
            orderTableModel.addRow(new Object[]{order.getId(), order.getClient(), order.getProduct(), order.getQuantity()});
        }


    }

    public static class OrderInputDialogResult {
        private int clientId;
        private int productId;
        private int quantity;

        public OrderInputDialogResult(int clientId, int productId, int quantity) {
            this.clientId = clientId;
            this.productId = productId;
            this.quantity = quantity;
        }

        public int getClientId() {
            return clientId;
        }

        public int getProductId() {
            return productId;
        }

        public int getQuantity() {
            return quantity;
        }
    }


}