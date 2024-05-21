package inventory.service;

import inventory.model.Inventory;
import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class InventoryServiceIntegrationTest {
    @Mock
    private Product product;
    @Mock
    private Part part;
    @Mock
    private Inventory inventory;
    private InventoryService inventoryService;
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setup() {
        inventoryRepository = new InventoryRepository(inventory);
        inventoryService = new InventoryService(inventoryRepository);
    }
    @Test
    void testAddProduct() {
    }

    @Test
    void testDeleteProduct() {
    }
}
