package blackjack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** A table with a dealer {@link Dealer} to play rounds on {@link Round} */
public class Table {

    /**
     * A table consist of a dealer, a list of rounds, a players count and total
     * bets, wins losses and ties
     */
    private Dealer dealer;
    private List<Round> rounds;
    private List<Player> players;
    private double totalBets;
    private int playersCount;
    private int roundCount;
    private int totalWins;
    private int totalLosses;
    private int totalTies;

    /** A round has a payout ratio. For a win your return is 3:2 */
    public final double PAYOUT = 0.5;

    /**
     * Constructor to create a new table
     * 
     * @param dealer the dealer to the table
     */
    public Table(Dealer dealer) {
        this.dealer = Objects.requireNonNull(dealer);
        this.rounds = new ArrayList<>();
        this.players = new ArrayList<>();
        this.totalBets = 0;
        this.playersCount = 0;
        this.roundCount = 0;
        this.totalWins = 0;
        this.totalLosses = 0;
        this.totalTies = 0;
    }

    /**
     * Method for a player to enter this table
     * 
     * @param player player the player
     */
    public void enterTable(Player player) {
        if (this.players.contains(player)) {
            throw new IllegalArgumentException("The player has already entered this table");
        } else {
            players.add(player);
        }
        this.playersCount = this.players.size();
    }

    /**
     * Method to create a new round at this table
     * 
     * @param player the player to the round
     * @param bet    the bet to the round
     * @throws IOException
     */
    public void newRound(Player player, double bet) throws IOException {
        if (bet > player.getCurrentStack() || bet <= 0) {
            throw new IllegalArgumentException(
                    "The bet must be positive and not higher than the current stack to this player");
        } else {
            Round newRound = new Round(player, bet, this);
            this.rounds.add(newRound);
            this.roundCount++;
            this.totalBets += bet;
        }
    }

    /**
     * Method to get the dealer at this table
     * 
     * @return the dealer
     */
    public Dealer getDealer() {
        return this.dealer;
    }

    /**
     * Methdo to get the current round at this table
     * 
     * @return the current round
     */
    public Round getCurrentRound() {
        if (this.rounds.isEmpty()) {
            throw new IllegalStateException("No rounds have been played on this table");
        } else {
            return this.rounds.getLast();
        }
    }

    /**
     * Method to get the rounds at this table
     * 
     * @return the rounds
     */
    public List<Round> getRounds() {
        return Collections.unmodifiableList(this.rounds);
    }

    /**
     * Method to get the round count to this table
     * 
     * @return the round count
     */
    public int getRoundCount() {
        return this.roundCount;
    }

    /**
     * Method to get the player count to this table
     * 
     * @return the player count
     */
    public int getPlayersCount() {
        return this.playersCount;
    }

    /**
     * Method to get the total bets at this table
     * 
     * @return the total bets
     */
    public double getTotalBets() {
        return this.totalBets;
    }

    /**
     * Method to get the total wins at this table
     * 
     * @return the total wins
     */
    public int getTotalWins() {
        return this.totalWins;
    }

    /**
     * Method to increase the total wins at this table
     */
    public void increaseTotalWins() {
        this.totalWins++;
    }

    /**
     * Method to get the total losses at this table
     * 
     * @return the total losses
     */
    public int getTotalLosses() {
        return this.totalLosses;
    }

    /**
     * Method to increase the total losses at this table
     */
    public void increaseTotalLosses() {
        this.totalLosses++;
    }

    /**
     * Method to get the total ties at this table
     * 
     * @return the total ties
     */
    public int getTotalTies() {
        return this.totalTies;
    }

    /**
     * Method to increase the total ties at this table
     */
    public void increaseTotalTies() {
        this.totalTies++;
    }

    /**
     * Method to create a new round at this table. Used for testing only
     * 
     * @param player   the player to the round
     * @param bet      the bet to the round
     * @param table    the table to the round
     * @param cardDeck the card deck to the round
     * @throws IOException
     */
    public void newRound(Player player, double bet, Table table, CardDeck cardDeck) throws IOException {
        if (bet > player.getCurrentStack() || bet <= 0) {
            throw new IllegalArgumentException(
                    "The bet must be positive and not higher than the current stack to this player");
        } else {
            Round newRound = new Round(player, bet, this, cardDeck);
            this.rounds.add(newRound);
            this.roundCount++;
            this.totalBets += bet;
        }
    }

    /**
     * Constructor to create a new table. Used for storing
     * 
     * @param playersCount the players count to the table
     * @param roundCount   the round count to the table
     * @param totalBets    the total bets to the table
     * @param totalWins    the total wins to the table
     * @param totalLosses  the total losses to this table
     * @param totalTies    the total ties to the table
     */
    public Table(int playersCount, int roundCount, Double totalBets, int totalWins, int totalLosses, int totalTies) {
        if (totalBets < 0 || roundCount < 0 || totalWins < 0 | totalLosses < 0 || totalTies < 0) {
            throw new IllegalArgumentException("Total bets, rounds, players, wins, losses and ties cannot be negative");
        } else {
            this.totalBets = totalBets;
            this.playersCount = playersCount;
            this.roundCount = roundCount;
            this.totalWins = totalWins;
            this.totalLosses = totalLosses;
            this.totalTies = totalTies;
        }
    }
}
