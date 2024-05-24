package inventory.service;

import inventory.model.InhousePart;
import inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileWriter;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {
    private static final String REPOSITORY_DB = "data/items.txt";
    private InventoryRepository inventoryRepository;
    private InventoryService inventoryService;

    @BeforeEach
    void setup() {
        try (FileWriter ignored = new FileWriter(REPOSITORY_DB)) {
            inventoryRepository = new InventoryRepository();
            inventoryService = new InventoryService(inventoryRepository);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @ParameterizedTest
    @MethodSource("validInHouseParts")
    @DisplayName("ECP_1, BVA_3, BVA_5")
    void shouldAddValidInHousePart(String name, double price, int inStock, int min, int max, int partDynamicValue) {
        // ACT
        var errorMessages = InhousePart.isValidPart(name, price, inStock, min, max, "");
        inventoryService.addInhousePart(name, price, inStock, min, max, partDynamicValue);

        // ASSERT
        assertTrue(errorMessages.isEmpty());
        assertEquals(1, inventoryRepository.getInventory().getParts().size());
        InhousePart addedPart = (InhousePart)inventoryRepository.getInventory().getParts().get(0);
        assertEquals(name, addedPart.getName());
        assertEquals(price, addedPart.getPrice());
        assertEquals(inStock, addedPart.getStockCount());
        assertEquals(min, addedPart.getMinCapacity());
        assertEquals(max, addedPart.getMaxCapacity());
        assertEquals(partDynamicValue, addedPart.getMachineId());
    }

    public static Stream<Arguments> validInHouseParts() {
        // ARRANGE
        return Stream.of(
                Arguments.of("alternator", 200d, 10, 2, 100, 25),
                Arguments.of("usa", 200d, 10, 10, 200, 101),
                Arguments.of("far", 200d, 8, 5, 100, 50)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidInHouseParts")
    @DisplayName("BVA_1, BVA_4, ECP_2, ECP_3, ECP_4")
    void shouldRecordErrorsForInvalidPart(String name, double price, int inStock, int min, int max,
                                         List<String> errorMessages) {
        // ACT
        var validationResult = InhousePart.isValidPart(name, price, inStock, min, max, "");

        // ASSERT
        errorMessages.forEach(error -> assertTrue(validationResult.contains(error)));
    }

    public static Stream<Arguments> invalidInHouseParts() {
        // ARRANGE
        return Stream.of(
                Arguments.of("", 10d, 5, 10, 20, List.of("A name has not been entered. ")),
                Arguments.of("motor", 1500d, 150, 0, 300, List.of("The Min value must be greater than 0. ")),
                Arguments.of("usa", 800d, 20, -1, 1000, List.of("The Min value must be greater than 0. ")),
                Arguments.of("a", 20d, 4, 3, 100, List.of("Invalid name. ")),
                Arguments.of("b", 25d, 52, -2, 100, List.of("The Min value must be greater than 0. ", "Invalid name. "))
        );
    }
}