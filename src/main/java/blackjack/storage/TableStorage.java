package blackjack.storage;

import java.io.IOException;
import java.util.List;

import blackjack.Table;

/**
 * An interface representing some way to persist {@link Table}
 */
public interface TableStorage {

    /**
     * Method to save a rounds after a player leaves the table
     */
    public void saveTable(Table table) throws IOException;

    /**
     * Method to load the stored rounds at a table
     * 
     * @return a list of loades rounds
     */
    public List<Table> loadTables() throws IOException;
}
