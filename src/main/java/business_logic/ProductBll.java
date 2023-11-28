package business_logic;

import Model.Product;
import DataAccess.ProductDAO;

import java.util.List;

/**
 * Represents a class for managing product operations.
 */
public class ProductBll {
    private ProductDAO productDAO; // Add an instance of the ProductDAO

    /**
     * Initializes a new instance of the ProductBll class.
     * It sets up the ProductDAO.
     */
    public ProductBll() {
        productDAO = new ProductDAO(); // Initialize the ProductDAO
    }

    /**
     * Adds a new product.
     *
     * @param product The product to be added.
     */
    public void addProduct(Product product) {
        productDAO.add(product); // Save the product in the database
    }

    /**
     * Edits an existing product.
     *
     * @param product The product to be edited.
     */
    public void editProduct(Product product) {
        productDAO.edit(product); // Update the product in the database
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to be deleted.
     */
    public void deleteProduct(int productId) {
        productDAO.delete(productId); // Delete the product from the database
    }

    /**
     * Retrieves a list of all products.
     *
     * @return A list containing all the products.
     */
    public List<Product> getAllProducts() {
        return productDAO.viewAll(); // Retrieve all products from the database
    }

    /**
     * Checks the availability of stock for a given product and quantity.
     *
     * @param product  The product to check.
     * @param quantity The desired quantity.
     * @return true if the stock is available, false otherwise.
     */
    public boolean checkStockAvailability(Product product, int quantity) {
        return product.getStock() >= quantity;
    }

    /**
     * Updates the stock of a product after an order.
     *
     * @param product  The product to update.
     * @param quantity The quantity to subtract from the stock.
     */
    public void updateProductStock(Product product, int quantity) {
        int newStock = product.getStock() - quantity;
        product.setStock(newStock);
        productDAO.edit(product); // Update the product's stock in the database
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return The product with the specified ID.
     */
    public Product getProductById(int productId) {
        return productDAO.findById(productId, "Product");
    }
}