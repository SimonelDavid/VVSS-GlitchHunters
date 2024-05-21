package inventory.model;

import static org.junit.jupiter.api.Assertions.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

public class ProductTestMockito {

    @Test
    public void testConstructor() {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        
        Product product = new Product(1, "Test Product", 100.0, 10, 5, 50, parts);

        assertEquals(1, product.getProductId());
        assertEquals("Test Product", product.getName());
        assertEquals(100.0, product.getPrice());
        assertEquals(10, product.getInStock());
        assertEquals(5, product.getMin());
        assertEquals(50, product.getMax());
        assertEquals(parts, product.getAssociatedParts());
    }

    @Test
    public void testIsValidProduct_ValidProduct() {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(new InhousePart(1, "Part 1", 50.0, 5, 1, 10,1));
        parts.add(new InhousePart(2, "Part 2", 30.0, 10, 1, 20,2));

        String errorMessage = "";
        errorMessage = Product.isValidProduct("Test Product", 100.0, 10, 5, 50, parts, errorMessage);
        assertEquals("", errorMessage, "Valid product should return an empty error message");
    }

    @Test
    public void testIsValidProduct_InvalidProduct() {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        // Missing name
        String errorMessage = Product.isValidProduct("", 100.0, 10, 5, 50, parts, "");
        assertTrue(errorMessage.contains("A name has not been entered"), "Empty name should generate an error message");

        // Inventory level too low
         errorMessage = Product.isValidProduct("", 100.0, 10, -1, 50, parts, "");
        assertTrue(errorMessage.contains("The inventory level must be greater than 0"), "Invetory level to low generate an error message");

        // Invalid price
        errorMessage = Product.isValidProduct("Test Product", 0.0, 10, 5, 50, parts, "");
        assertTrue(errorMessage.contains("The price must be greater than $0"), "Price less than 0.01 should generate an error message");

        // Invalid min/max
        errorMessage = Product.isValidProduct("Test Product", 100.0, 10, 10, 5, parts, "");
        assertTrue(errorMessage.contains("The Min value must be less than the Max value"), "Min value greater than Max value should generate an error message");

        // Invalid stock count higher than max
        errorMessage = Product.isValidProduct("Test Product", 100.0, 51, 5, 50, parts, "");
        assertTrue(errorMessage.contains("Inventory level is higher than the maximum value"), "Stock count grater than max should generate an error message");

        // Invalid stock count lower than min
        errorMessage = Product.isValidProduct("Test Product", 100.0, 4, 5, 50, parts, "");
        assertTrue(errorMessage.contains("Inventory level is lower than minimum value"), "Stock count grater than max should generate an error message");

        // Invalid associated parts
        errorMessage = Product.isValidProduct("Test Product", 100.0, 10, 5, 50, FXCollections.observableArrayList(), "");
        assertTrue(errorMessage.contains("Product must contain at least 1 part"), "Empty associated parts should generate an error message");
    }


}
