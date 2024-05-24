package inventory.service;

import inventory.model.Inventory;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryServiceE2EIntegrationTest {
    private static final String REPOSITORY_DB = "data/items.txt";
    private Product product;
    private Inventory inventory;
    private InventoryService inventoryService;
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setup() {
        try (FileWriter ignored = new FileWriter(REPOSITORY_DB)) {
            inventoryRepository = new InventoryRepository();
            inventoryService = new InventoryService(inventoryRepository);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        inventory = new Inventory();
        inventoryRepository = new InventoryRepository(inventory);
        inventoryService = new InventoryService(inventoryRepository);
        product = new Product(0, "Test Product", 100.0, 10, 5, 50, FXCollections.observableArrayList());
    }

    @Test
    void testAddProduct() {
        inventoryService.addProduct("Test Product", 100.0, 10, 5, 50, FXCollections.observableArrayList());

        ObservableList<Product> products = inventoryService.getAllProducts();
        assertEquals(1, products.size());
        assertEquals(product, products.get(0));
    }

    @Test
    void testDeleteProduct() {
        inventoryService.addProduct("Test Product", 100.0, 10, 5, 50, FXCollections.observableArrayList());
        ObservableList<Product> products = inventoryService.getAllProducts();
        assertEquals(1, products.size());
        inventoryService.deleteProduct(product);

        assertEquals(0, inventoryService.getAllProducts().size());
    }
}
