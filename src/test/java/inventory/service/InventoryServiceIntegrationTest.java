package inventory.service;

import inventory.model.Inventory;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InventoryServiceIntegrationTest {
    private static final String REPOSITORY_DB = "data/items.txt";
    private Product product;
    @Mock
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

        MockitoAnnotations.openMocks(this);
        inventoryRepository = new InventoryRepository(inventory);
        inventoryService = new InventoryService(inventoryRepository);
        product = new Product(0, "Test Product", 100.0, 10, 5, 50, FXCollections.observableArrayList());

        when(inventory.getProducts()).thenReturn(FXCollections.observableArrayList(product));
        when(inventory.getParts()).thenReturn(FXCollections.observableArrayList());
    }
    @Test
    void testAddProduct() {
        inventoryService.addProduct("Test Product", 100.0, 10, 5, 50, FXCollections.observableArrayList());

        ObservableList<Product> products = inventoryService.getAllProducts();
        assertEquals(1, products.size());
        assertEquals(product, products.get(0));
        verify(inventory).addProduct(eq(product));
        verify(inventory, atLeast(1)).getProducts();
    }

    @Test
    void testDeleteProduct() {
        ArgumentCaptor<Product> valueCapture = ArgumentCaptor.forClass(Product.class);
        doNothing().when(inventory).removeProduct(valueCapture.capture());

        inventoryService.addProduct("Test Product", 100.0, 10, 5, 50, FXCollections.observableArrayList());
        ObservableList<Product> products = inventoryService.getAllProducts();
        assertEquals(1, products.size());
        inventoryService.deleteProduct(products.get(0));

        verify(inventory).removeProduct(product);
    }
}
