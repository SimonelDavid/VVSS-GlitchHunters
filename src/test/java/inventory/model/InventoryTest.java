package inventory.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private static final String REPOSITORY_DB = "data/items.txt";
    private static final int PART_ID_1 = 50;
    private static final int PART_ID_2 = 51;
    private static final String NAME = "far";
    private static final double PRICE = 2.22d;
    private static final int STOCK_CAPACITY = 200;
    private static final int MIN_CAPACITY = 100;
    private static final int MAX_CAPACITY = 1000;
    private static final int PART_DYNAMIC_VALUE = 500;
    private Inventory inventory;
    private final Part part = new InhousePart(PART_ID_1, "surub", PRICE,
            STOCK_CAPACITY, MIN_CAPACITY, MAX_CAPACITY,
            PART_DYNAMIC_VALUE
    );

    @BeforeEach
    void setup() {
        try (var ignored = new FileWriter(REPOSITORY_DB)) {
            inventory = new Inventory();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    @DisplayName("F02_TC01")
    void whenNoPartsAndSearchItemNull_ShouldReturnNull() {
        assertNull(inventory.lookupPart(null));
    }

    @Test
    @DisplayName("F02_TC03")
    void whenNoPartsAndSearchItemValid_ShouldReturnNull() {
        var searchItem = "far";

        assertNull(inventory.lookupPart(searchItem));
    }

    @Test
    @DisplayName("F02_TC05")
    void whenValidPartAndSearchItemNotCorresponding_ShouldReturnNull() {
        part.setName("surub");
        inventory.addPart(part);

        assertNull(inventory.lookupPart(NAME));
    }

    @Test
    @DisplayName("F02_TC06")
    void whenValidPartAndSearchItemIsInName_ShouldReturnPart() {
        part.setName(NAME);
        inventory.addPart(part);

        assertEquals(part, inventory.lookupPart(NAME));
    }

    @Test
    @DisplayName("F02_TC07")
    void whenMultipleValidPartsAndSearchItemNotCorresponding_ShouldReturnNull() {
        part.setName("surub");
        inventory.addPart(part);
        inventory.addPart(new InhousePart(PART_ID_2, "bujie", PRICE,
                STOCK_CAPACITY, MIN_CAPACITY, MAX_CAPACITY, PART_DYNAMIC_VALUE)
        );

        assertNull(inventory.lookupPart(NAME));
    }

    @Test
    @DisplayName("F02_TC08")
    void whenValidPartsAndSearchItemIsInName_ShouldReturnPart() {
        part.setName(NAME);
        inventory.addPart(part);
        inventory.addPart(new InhousePart(PART_ID_2, "bujie", PRICE,
                STOCK_CAPACITY, MIN_CAPACITY, MAX_CAPACITY, PART_DYNAMIC_VALUE)
        );

        assertEquals(part, inventory.lookupPart(NAME));
    }

    @Test
    @DisplayName("F02_TC09")
    void whenValidPartsAndSearchItemIsId_ShouldReturnPart() {
        part.setName(NAME);
        inventory.addPart(part);
        inventory.addPart(new InhousePart(PART_ID_2, "bujie", PRICE,
                STOCK_CAPACITY, MIN_CAPACITY, MAX_CAPACITY, PART_DYNAMIC_VALUE)
        );

        assertEquals(part, inventory.lookupPart(String.valueOf(PART_ID_1)));
    }
}