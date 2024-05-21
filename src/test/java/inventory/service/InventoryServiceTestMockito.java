package inventory.service;

import inventory.model.InhousePart;
import inventory.model.Inventory;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTestMockito {

    @Test
    void testAddProduct() {
        // ARRANGE
        var inventoryRepository = mock(InventoryRepository.class);
        var inventoryService = new InventoryService(inventoryRepository);
        var product = new Product(1, "Test Product", 100.0, 10, 5, 50, FXCollections.observableArrayList());
      //  var part = new InhousePart(1, "Part 1", 50.0, 5, 1, 10, 1);
        //product.addAssociatedPart(part);

        Mockito.when(inventoryRepository.getAutoProductId()).thenReturn(1); // Returnează ID-ul valabil
        Mockito.doNothing().when(inventoryRepository).addProduct(product);
        Mockito.when(inventoryRepository.getAllProducts()).thenReturn(FXCollections.observableArrayList(product));
        // ACT
        inventoryService.addProduct( "Test Product", 100.0, 10, 5, 50, FXCollections.observableArrayList());
        ObservableList<Product> products = inventoryService.getAllProducts();

        // ASSERT
        assert products.size() == 1;
        assert products.get(0)==product;
         //Mockito.verify(inventoryRepository, times(1)).addProduct(product);

    }

    @Test
    void testUpdateProduct(){
        var inventoryRepository = mock(InventoryRepository.class);
        var inventoryService = new InventoryService(inventoryRepository);
        var product = new Product(1, "Test Product", 100.0, 10, 5, 50, FXCollections.observableArrayList());
        var updatedProduct = new Product(1, "Updated Product", 200.0, 20, 10, 100, FXCollections.observableArrayList());

        Mockito.doNothing().when(inventoryRepository).updateProduct(0, updatedProduct);
        Mockito.when(inventoryRepository.getAllProducts()).thenReturn(FXCollections.observableArrayList(updatedProduct));
        inventoryService.updateProduct(0, 1, "Updated Product", 200.0, 20, 10, 100, FXCollections.observableArrayList());
        ObservableList<Product> products= inventoryService.getAllProducts();

        assert products.size() == 1;
        assert products.get(0) == updatedProduct;
    }
}