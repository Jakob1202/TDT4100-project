package blackjack;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import blackjack.storage.CSVTableStorage;

/*** A table manager with tables {@link Table} */
public class TableManager {

    /**
     * A results manager has list of tables and a storage;
     */
    private List<Table> tables;
    private CSVTableStorage storage;

    /**
     * Constructor to create a new table manager with an empty list of tables
     *
     */
    public TableManager() {
        this.tables = new ArrayList<>();
        this.storage = new CSVTableStorage(Path.of("tables.csv"));
    }

    /**
     * Method to load the stores tables
     * 
     * @throws IOException if it fails to load the tables from file
     */
    public void loadTables() throws IOException {
        this.tables = this.storage.loadTables();

    }

    /**
     * Method to save a table
     * 
     * @param rounds the table to be saved
     * @throws IOException if it fails to save the table to file
     */
    public void saveTable(Table table) throws IOException {
        this.storage.saveTable(table);
    }

    /**
     * Method to add a table to this tables. The table must not be
     * registered before
     * 
     * @param table the table to be added
     */
    public void addTable(Table table) {
        if (!this.tables.contains(table)) {
            this.tables.add(table);
        } else {
            throw new IllegalArgumentException("The table is already registered");
        }
    }

    /**
     * Method to remove a table from this tables. The table must be
     * registered before
     * 
     * @param table the table to be removed
     */
    public void removeTable(Table table) {
        if (this.tables.contains(table)) {
            this.tables.remove(table);
        } else {
            throw new IllegalArgumentException("The table is not registered");
        }
    }

    /**
     * Method to get the default tables not sorted
     * 
     * @return the unsorted tables
     */
    public List<Table> getTables() {
        return Collections.unmodifiableList(this.tables);
    }

    /**
     * Method to get the tables sorted by highest win probalility
     * 
     * @return the tables sorted
     */
    public List<Table> getTablesByHighestWinPropability() {
        return Collections.unmodifiableList(
                tables.stream()
                        .sorted(Comparator
                                .comparingDouble(table -> ((double) table.getTotalWins() / table.getRoundCount())))
                        .collect(Collectors.toList()).reversed());
    }

    /**
     * Method to get the tables sorted by lowest win probalility
     * 
     * @return the tables sorted
     */
    public List<Table> getTablesByLowestWinPropability() {
        return Collections.unmodifiableList(
                tables.stream()
                        .sorted(Comparator
                                .comparingDouble(table -> ((double) table.getTotalWins() / table.getRoundCount())))
                        .collect(Collectors.toList()));
    }

    /**
     * Constructor to create a new table manager with an empty list of tables. Used
     * for testing only
     * 
     * @param file the file to store and load the tables from
     */
    public TableManager(String file) {
        this.tables = new ArrayList<>();
        this.storage = new CSVTableStorage(Path.of(file));
    }

}
