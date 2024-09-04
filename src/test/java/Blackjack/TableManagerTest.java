package Blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import blackjack.Table;
import blackjack.TableManager;

/** Test when managing result {@link TableManager} */
public class TableManagerTest {
    private TableManager tableManager;

    private Table table1;
    private Table table2;
    private Table table3;

    @BeforeEach
    public void setUp() {
        tableManager = new TableManager("test.csv");
        table1 = new Table(1, 5, 1000.0, 5, 0, 0);
        table2 = new Table(1, 4, 1000.0,3, 1, 0);
        table3 = new Table(1, 3, 1000.0, 1, 1, 1);
    }

    @Test
    @DisplayName("Test that add table works")
    public void testAddTable() {
        tableManager.addTable(table1);
        assertEquals(List.of(table1), tableManager.getTables());
        assertThrows(IllegalArgumentException.class, () -> tableManager.addTable(table1));
    }

    @Test
    @DisplayName("Test that remove table work")
    public void testRemoveTable() {
        assertThrows(IllegalArgumentException.class, () -> tableManager.removeTable(table1));
        tableManager.addTable(table1);
        tableManager.addTable(table2);
        tableManager.removeTable(table2);
        assertEquals(List.of(table1), tableManager.getTables());
    }

    @Test
    @DisplayName("Test that get default tables work ")
    public void getDefaultTables() {
        tableManager.addTable(table1);
        tableManager.addTable(table2);
        tableManager.addTable(table3);
        assertEquals(List.of(table1, table2, table3), tableManager.getTables());
    }

    @Test
    @DisplayName("Test that sort the tables by highest- and lowest win propability works")
    public void getTablesByWinPropability() {
        tableManager.addTable(table1);
        tableManager.addTable(table2);
        tableManager.addTable(table3);
        assertEquals(List.of(table1, table2, table3), tableManager.getTablesByHighestWinPropability());
        assertEquals(List.of(table3, table2, table1), tableManager.getTablesByLowestWinPropability());
    }

    @Test
    @DisplayName("Test loading and saving files works")
    public void testFileHandling() throws IOException {
        tableManager.saveTable(table1);
        tableManager.saveTable(table2);
        tableManager.saveTable(table3);

        tableManager.loadTables();
        List<Table> actualTables = tableManager.getTables();

        List<Table> expectedTables = new ArrayList<>();
        expectedTables.add(table1);
        expectedTables.add(table2);
        expectedTables.add(table3);

        assertEquals(expectedTables.size(), actualTables.size());

        for (int i = 0; i < expectedTables.size(); i++) {
            assertEquals(expectedTables.get(i).getTotalBets(), actualTables.get(i).getTotalBets());
            assertEquals(expectedTables.get(i).getPlayersCount(), actualTables.get(i).getPlayersCount());
            assertEquals(expectedTables.get(i).getRoundCount(), actualTables.get(i).getRoundCount());
            assertEquals(expectedTables.get(i).getTotalWins(), actualTables.get(i).getTotalWins());
            assertEquals(expectedTables.get(i).getTotalWins(), actualTables.get(i).getTotalWins());
            assertEquals(expectedTables.get(i).getTotalLosses(), actualTables.get(i).getTotalLosses());
            assertEquals(expectedTables.get(i).getTotalTies(), actualTables.get(i).getTotalTies());
        }

        Path filePath = Path.of("test.csv");
        Files.delete(filePath);
    }
}
