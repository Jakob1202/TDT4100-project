package blackjack;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/** Blackjack controller to the application */
public class BlackjackController implements Initializable {

    /** FXML elements to the application */
    @FXML
    private TextField nameField;

    @FXML
    private TextField stackField;

    @FXML
    private Button enterNewTableButton;

    @FXML
    private Button enterOldTableButton;

    @FXML
    private TextField playerTextField;

    @FXML
    private TextField dealerTextField;

    @FXML
    private ListView<String> playerCardsListView;

    @FXML
    private ListView<String> dealerCardsListView;

    @FXML
    private TextField playerSumTextField;

    @FXML
    private TextField dealerSumTextField;

    @FXML
    private Button hitButton;

    @FXML
    private Button standButton;

    @FXML
    private Button leaveTableButton;

    @FXML
    private TextField betField;

    @FXML
    private Button newRoundButton;

    @FXML
    private TextField currentStackField;

    @FXML
    private TableView<Table> tablesTable;

    @FXML
    private TableColumn<Table, Integer> tablePlayersColumn;

    @FXML
    private TableColumn<Table, Integer> tableRoundColumn;

    @FXML
    private TableColumn<Table, Double> tableBetsColumn;

    @FXML
    private TableColumn<Table, Integer> tableWinsColumn;

    @FXML
    private TableColumn<Table, Integer> tableLossesColumn;

    @FXML
    private TableColumn<Table, Integer> tableTiesColumn;

    @FXML
    private ChoiceBox<String> tablesFilterChoiceBox;

    /**
     * The table, dealer, current player, current round and a table manager to the
     * application
     */
    private Table table;
    private Dealer dealer;
    private Player currentPlayer;
    private Round currentRound;
    private TableManager tableManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dealer = new Dealer();
        table = new Table(dealer);
        tableManager = new TableManager();
        handleStart();
    }

    /**
     * Method to handle the application when it is started
     */
    public void handleStart() {
        nameField.setTextFormatter(
                new TextFormatter<>(change -> change.getControlNewText().matches("[a-zA-Z\\s]*") ? change : null));
        stackField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (!newText.contains("-") && newText.matches("\\d*(\\.\\d*)?")) {
                return change;
            } else {
                return null;
            }
        }));

        betField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (!newText.contains("-") && newText.matches("\\d*(\\.\\d*)?")) {
                return change;
            } else {
                return null;
            }
        }));
        dealerTextField.setEditable(false);
        playerTextField.setEditable(false);
        playerCardsListView.setEditable(false);
        dealerCardsListView.setEditable(false);
        playerSumTextField.setEditable(false);
        dealerSumTextField.setEditable(false);
        hitButton.setDisable(true);
        standButton.setDisable(true);
        leaveTableButton.setDisable(true);
        currentStackField.setEditable(false);
        betField.setEditable(false);
        newRoundButton.setDisable(true);

        ObservableList<String> filters = FXCollections.observableArrayList(
                "Tables by date",
                "Tables by highest win probability",
                "Tables by lowest win probability");
        tablesFilterChoiceBox.setItems(filters);

        try {
            tableManager.loadTables();
            showFilteredTables("Tables by date");
        } catch (IOException e) {
            showAlert("Failed to load the stored tables", AlertType.ERROR);
        }

        tablePlayersColumn.setCellValueFactory(cellData -> {
            int playersValue = cellData.getValue().getPlayersCount();
            return Bindings.createObjectBinding(() -> playersValue);
        });

        tableRoundColumn.setCellValueFactory(cellData -> {
            int roundsValue = cellData.getValue().getRoundCount();
            return Bindings.createObjectBinding(() -> roundsValue);
        });

        tableBetsColumn.setCellValueFactory(cellData -> {
            double betsValue = cellData.getValue().getTotalBets();
            return Bindings.createObjectBinding(() -> betsValue);
        });

        tableWinsColumn.setCellValueFactory(cellData -> {
            int winsValue = cellData.getValue().getTotalWins();
            return Bindings.createObjectBinding(() -> winsValue);
        });

        tableLossesColumn.setCellValueFactory(cellData -> {
            int lossesValue = cellData.getValue().getTotalLosses();
            return Bindings.createObjectBinding(() -> lossesValue);
        });

        tableTiesColumn.setCellValueFactory(cellData -> {
            int tiesValue = cellData.getValue().getTotalTies();
            return Bindings.createObjectBinding(() -> tiesValue);
        });

        showFilteredTables("Tables by date");
    }

    /**
     * Method to handle the application when it is closed
     * 
     * @throws IOException if it fails to save the table
     */
    public void handleClose() throws IOException {
        tableManager.saveTable(table);
    }

    /**
     * Method to enter this table in the application
     */
    @FXML
    public void handleEnterTable() {
        String playerName = nameField.getText().trim();
        String playerStack = stackField.getText().trim();
        String error = null;
        if (playerName.isEmpty()) {
            error = "Name cannot be empty";
            nameField.clear();
        } else if (playerStack.isEmpty()) {
            error = "Stack cannot be empty";
            stackField.clear();
        } else if (Double.parseDouble(playerStack) <= 0) {
            error = "Stack field must contain a positive double value";
            stackField.clear();
        }

        if (error != null) {
            showAlert(error, AlertType.WARNING);
        } else {
            currentPlayer = new Player(playerName, Double.parseDouble(playerStack));
            table.enterTable(currentPlayer);

            nameField.setEditable(false);
            stackField.setEditable(false);
            enterNewTableButton.setDisable(true);

            newRoundButton.setDisable(false);
            betField.setEditable(true);

            updateStack();
        }
    }

    /**
     * Method to start a new round at this table in the application
     * 
     * @throws IOException if the file handling fails
     */
    @FXML
    public void handleNewRound() throws IOException {
        String playerBet = betField.getText().trim();
        String error = null;
        if (playerBet.isEmpty()) {
            error = "Bet cannot be empty";
        } else if (Double.parseDouble(playerBet) > currentPlayer.getCurrentStack()) {
            error = "Bet cannot be higher than your current stack";
        } else if (Double.parseDouble(playerBet) <= 0) {
            error = "Bet cannot be negative or zero";
        }

        if (error != null) {
            showAlert(error, AlertType.WARNING);
            betField.clear();
        } else {
            table.newRound(currentPlayer, Double.parseDouble(playerBet));
            currentRound = table.getRounds().getLast();

            resetCards();
            resetSum();

            updateCards();
            updateSum();

            hitButton.setDisable(false);
            standButton.setDisable(false);
            leaveTableButton.setDisable(true);

            betField.setEditable(false);
            newRoundButton.setDisable(true);

            if (currentPlayer.getCardSum() == 21) {
                handleEndRound();
            }
        }
    }

    /**
     * Method for the player to hit on this table in the application
     * 
     */
    @FXML
    public void handleHit() {
        currentRound.hit();

        updateCards();
        updateSum();

        if (currentPlayer.getCardSum() > 21) {
            handleEndRound();
        }
    }

    /**
     * Method for the player to stand on this table in the application
     * 
     */
    @FXML
    public void handleStand() {
        currentRound.stand();

        updateCards();
        updateSum();
        handleEndRound();
    }

    /**
     * Method to end the round on this table in the application
     * 
     */
    @FXML
    public void handleEndRound() {
        updateStack();
        String message = null;
        if (currentPlayer.getCardSum() == 21) {
            message = "You got blackjack and won the round. You won " + String
                    .valueOf(currentRound.getBet() * (1 + table.PAYOUT));

        } else if (((currentPlayer.getCardSum() > dealer.getCardSum()) && currentPlayer.getCardSum() <= 21)) {
            message = "You have higher cards than the dealer and won the round. You won " + String
                    .valueOf(currentRound.getBet() * (1 + table.PAYOUT));
        } else if ((currentPlayer.getCardSum() < dealer.getCardSum()) && dealer.getCardSum() <= 21) {
            message = "The dealer have higher cards than you and you lost the round. You lost "
                    + String.valueOf(currentRound.getBet());
        } else if (currentPlayer.getCardSum() > 21) {
            message = "You busted and the dealer won the round. You lost " + String.valueOf(currentRound.getBet());
        } else if (dealer.getCardSum() > 21) {
            message = "The dealer busted and you won the round. You won " + String
                    .valueOf(currentRound.getBet() * (1 + table.PAYOUT));
        } else {
            message = "You tied. Your stack is the same as before";
        }

        showAlert(message, AlertType.INFORMATION);

        hitButton.setDisable(true);
        standButton.setDisable(true);
        leaveTableButton.setDisable(false);

        if (currentPlayer.getCurrentStack() == 0) {
            handleLeaveTable();
        } else {
            betField.clear();
            betField.setEditable(true);
            newRoundButton.setDisable(false);
        }
    }

    /**
     * Method to leave this table in the application
     * 
     */
    @FXML
    public void handleLeaveTable() {
        resetUI();

        if (currentPlayer.getCurrentStack() == 0) {
            showAlert(
                    "You must leave the table because your stack is empty",
                    AlertType.INFORMATION);
        } else {
            showAlert("You left the table",
                    AlertType.INFORMATION);
        }

    }

    /**
     * Method to show an alert in the application
     * 
     * @param message   the message to the alert
     * @param alertType the type to the alert
     */
    public void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Method to reset the UI in the application
     */
    public void resetUI() {
        nameField.clear();
        nameField.setEditable(true);

        stackField.clear();
        stackField.setEditable(true);

        betField.clear();
        betField.setEditable(false);

        enterNewTableButton.setDisable(false);
        hitButton.setDisable(true);
        standButton.setDisable(true);
        newRoundButton.setDisable(true);
        leaveTableButton.setDisable(true);

        resetCards();
        resetSum();
        resetStack();
    }

    /**
     * Method to update the drawn cards to the dealer and the player to this table
     * in the application
     */
    public void updateCards() {
        playerCardsListView.getItems().clear();
        currentPlayer.getCardHand().getCards().stream()
                .map(card -> card.toString())
                .forEach(cardString -> playerCardsListView.getItems().add(cardString));

        dealerCardsListView.getItems().clear();
        dealer.getCardHand().getCards().stream()
                .map(card -> card.toString())
                .forEach(cardString -> dealerCardsListView.getItems().add(cardString));
    }

    /**
     * Method to resert the drawn cards to the dealer and the player to this table
     * in the application
     */
    private void resetCards() {
        playerCardsListView.getItems().clear();
        dealerCardsListView.getItems().clear();
    }

    /**
     * Method to update the sum to the dealer and the player to this table in the
     * application
     */
    public void updateSum() {
        playerSumTextField.setText(String.valueOf(currentPlayer.getCardSum()));
        dealerSumTextField.setText(String.valueOf(dealer.getCardSum()));
    }

    /**
     * Method to reset the sum to the dealer and the player to this table in the
     * application
     */
    public void resetSum() {
        playerSumTextField.clear();
        dealerSumTextField.clear();
    }

    /**
     * Method to update the stack to the player to this table in the application
     */
    public void updateStack() {
        currentStackField.setText(String.valueOf(currentPlayer.getCurrentStack()));
    }

    /**
     * Method to reset the stack to the player to this table in the application
     */
    public void resetStack() {
        currentStackField.clear();
    }

    /**
     * Method to handle the filter change to the stored tables
     */
    public void handleFilterChange() {
        String selectedFilter = tablesFilterChoiceBox.getValue();
        showFilteredTables(selectedFilter);
    }

    /**
     * Method to show the filtered tables in the table view
     * 
     * @param selectedFilter the selectec filter to apply
     */
    public void showFilteredTables(String selectedFilter) {
        ObservableList<Table> filteredTables = FXCollections.observableArrayList();
        switch (selectedFilter) {
            case "Tables by date":
                filteredTables.addAll(tableManager.getTables());
                break;
            case "Tables by highest win probability":
                filteredTables.addAll(tableManager.getTablesByHighestWinPropability());
                break;
            case "Tables by lowest win probability":
                filteredTables.addAll(tableManager.getTablesByLowestWinPropability());
                break;
            default:
                break;
        }
        tablesTable.setItems(filteredTables);
    }

    /**
     * Method to get the round count to this table
     * 
     * @return the round count
     */
    public int getRoundCount() {
        return table.getRoundCount();
    }

}
