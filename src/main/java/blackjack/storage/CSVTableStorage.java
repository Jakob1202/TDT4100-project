package blackjack.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import blackjack.Table;

/** A storage to store tables */
public class CSVTableStorage implements TableStorage {

    /** A storage consist of a file path */
    private final Path path;

    /**
     * Constructor to create a new storage
     * 
     * @param path the path to the storage
     */
    public CSVTableStorage(Path path) {
        this.path = Objects.requireNonNull(path);
    }

    @Override
    public void saveTable(Table table) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(this.path, StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            if (table.getRoundCount() == 0) {

            } else {
                String line = table.getPlayersCount() + "," +
                        table.getRoundCount() + "," +
                        table.getTotalBets() + "," +
                        table.getTotalWins() + "," +
                        table.getTotalLosses() + "," +
                        table.getTotalTies() + "\n";
                writer.write(line);
            }
        }
    }

    @Override
    public List<Table> loadTables() throws IOException {
            try (BufferedReader reader = Files.newBufferedReader(this.path)) {
                return reader.lines().map(line -> {
                    String[] parts = line.split(",");
                    if (parts.length == 6) {

                        int playersCount = Integer.parseInt(parts[0].trim());
                        int roundCount = Integer.parseInt(parts[1].trim());
                        double totalBets = Double.parseDouble(parts[2].trim());
                        int totalWins = Integer.parseInt(parts[3].trim());
                        int totalLosses = Integer.parseInt(parts[4].trim());
                        int totalTies = Integer.parseInt(parts[5].trim());

                        if (roundCount == totalWins + totalLosses + totalTies) {
                            return new Table(playersCount, roundCount, totalBets, totalWins, totalLosses, totalTies);
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }
                }).filter(table -> table != null).toList();
            }
        }
    }

