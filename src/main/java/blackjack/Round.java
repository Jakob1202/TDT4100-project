package blackjack;

import java.util.Objects;

/**
 * A round with a player {@link Player} and a card deck {@link CardDeck} at a
 * table {@link Table}
 */
public class Round {
    /**
     * A round consist of a player, a bet, a table and a card deck
     */
    private Player player;
    private double bet;
    private Table table;
    private CardDeck cardDeck;

    /**
     * Constructor to create a new round
     * 
     * @param bet    the bet to this round
     * @param player the player to this round
     * @param table  the table to this round
     */
    public Round(Player player, double bet, Table table) {
        if (bet > player.getCurrentStack() || bet <= 0) {
            throw new IllegalArgumentException(
                    "The bet must be positive and not higher than the current stack to this player");
        } else {
            this.player = Objects.requireNonNull(player);
            this.bet = bet;
            this.table = Objects.requireNonNull(table);
            this.cardDeck = new CardDeck();

            this.player.resetCardHand();
            this.player.setCardSum(0);

            this.table.getDealer().resetCardHand();
            this.table.getDealer().setCardSum(0);

            this.table.getDealer().drawCard(this.cardDeck.removeCard());
            this.player.drawCard(this.cardDeck.removeCard());
            this.player.drawCard(this.cardDeck.removeCard());

            if (this.player.getCardSum() == 21) {
                this.endRound();
            }
        }
    }

    /**
     * Method to get the bet to this round
     * 
     * @return the bet
     */
    public double getBet() {
        return this.bet;
    }

    /**
     * Method for the player to hit in a round
     * 
     */
    public void hit() {
        if (this.player.canDrawCard()) {
            this.player.drawCard(this.cardDeck.removeCard());

            if (this.player.getCardSum() > 21) {
                this.endRound();
            }
        } else {
            throw new IllegalStateException("You cannot hit anymore");
        }
    }

    /**
     * Method for the player to stand in a round
     * 
     */
    public void stand() {
        if (this.table.getDealer().getCardSum() == 21) {
            this.endRound();
            return;
        } else {
            this.table.getDealer().drawCard(this.cardDeck.removeCard());

            while (this.table.getDealer().canDrawCard()) {
                this.table.getDealer().drawCard(this.cardDeck.removeCard());

                if (this.table.getDealer().getCardSum() > 21) {
                    this.endRound();
                    return;
                }
            }
            this.endRound();
        }
    }

    /**
     * Method to end the round
     * 
     */
    public void endRound() {
        double newCurrentStack;
        double oldCurrentStack = this.player.getCurrentStack();

        if ((this.player.getCardSum() > this.table.getDealer().getCardSum() && this.player.getCardSum() <= 21)
                || this.table.getDealer().getCardSum() > 21) {
            newCurrentStack = oldCurrentStack + ((this.table.PAYOUT + 1) * this.bet);
            this.table.increaseTotalWins();
        } else if ((this.player.getCardSum() < this.table.getDealer().getCardSum()
                && this.table.getDealer().getCardSum() <= 21)
                || this.player.getCardSum() > 21) {
            newCurrentStack = oldCurrentStack - this.bet;
            this.table.increaseTotalLosses();
        } else {
            newCurrentStack = oldCurrentStack;
            this.table.increaseTotalTies();
        }

        this.player.setCurrentStack(newCurrentStack);
    }

    /**
     * Constructor to create a new round. Used for testing only
     * 
     * @param bet    the bet to this round
     * @param player the player to this round
     * @param table  the table to this round
     * @param table  the card deck to this round
     */
    public Round(Player player, double bet, Table table, CardDeck cardDeck) {
        if (bet > player.getCurrentStack() || bet <= 0) {
            throw new IllegalArgumentException(
                    "The bet must be positive and not higher than the current stack to this player");
        } else {
            this.player = Objects.requireNonNull(player);
            this.bet = bet;
            this.table = Objects.requireNonNull(table);
            this.cardDeck = Objects.requireNonNull(cardDeck);

            this.player.resetCardHand();
            this.player.setCardSum(0);

            this.table.getDealer().resetCardHand();
            this.table.getDealer().setCardSum(0);

            this.table.getDealer().drawCard(this.cardDeck.removeCard());
            this.player.drawCard(this.cardDeck.removeCard());
            this.player.drawCard(this.cardDeck.removeCard());

            if (this.player.getCardSum() == 21) {
                this.endRound();
            }
        }
    }
}
