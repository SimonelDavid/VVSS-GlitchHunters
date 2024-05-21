package inventory.repository;

import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InventoryRepositoryTestMockito {

    @Test
    void testAddProduct() {
        // ARRANGE
        var inventoryRepository = new InventoryRepository();
        var product = mock(Product.class);
        var part = mock(Part.class);
        Mockito.when(product.getProductId()).thenReturn(0);
        Mockito.when(product.getAssociatedParts()).thenReturn(FXCollections.observableArrayList(part));

        // ACT
        inventoryRepository.addProduct(product);
        ObservableList<Product> products = inventoryRepository.getAllProducts();
        // ASSERT
        assert products.size() == 1;
        assert products.get(0).getProductId() == 0;

    }

    @Test
    void testDeleteProduct() {
        //ARRANGE
        var inventoryRepository = new InventoryRepository();
        var product1 = mock(Product.class);
        var product2 = mock(Product.class);
        var part = mock(Part.class);

        Mockito.when(product1.getProductId()).thenReturn(0);
        Mockito.when(product2.getProductId()).thenReturn(1);
        Mockito.when(product1.getAssociatedParts()).thenReturn(FXCollections.observableArrayList(part));
        Mockito.when(product2.getAssociatedParts()).thenReturn(FXCollections.observableArrayList(part));

        // ACT
        inventoryRepository.addProduct(product1);
        inventoryRepository.addProduct(product2);
        ObservableList<Product> products = inventoryRepository.getAllProducts();

        assert products.size() == 2;

        inventoryRepository.deleteProduct(product1);
        products = inventoryRepository.getAllProducts();

        // ASSERT
        assert products.size() == 1;
        assert products.get(0).getProductId() == 1;
    }

}