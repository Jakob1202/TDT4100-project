package blackjack;

import java.util.Objects;

/** A player who plays a round {@link Round} */
public class Player extends CardHolder {
    /**
     * A player has a name, an entry stack, a current stack, a round count and plays
     * on a date;
     */
    private String name;
    private final double entryStack;
    private double currentStack;

    /**
     * Constructor to create a new player
     * 
     * @param name the name to the player
     * @param name the entry stack to the player
     */
    public Player(String name, double entryStack) {
        super();
        if(entryStack <= 0) {
            throw new IllegalArgumentException("The entry stack must be positive");
        } else {
            this.setCurrentStack(entryStack);
            this.name = Objects.requireNonNull(name);
            this.entryStack = entryStack;
        }
    }

    /**
     * Method to get the name to this player
     * 
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method to get the entry stack to this player
     * 
     * @return the entry stack
     */
    public double getEntryStack() {
        return this.entryStack;
    }

    /**
     * Method to get the current stack to this player
     * 
     * @return the current stack
     */
    public double getCurrentStack() {
        return this.currentStack;
    }

    /**
     * Method to set the current stack to this player
     * 
     * @param newCurrentStack the new current stack
     */
    public void setCurrentStack(double newCurrentStack) {
        if (newCurrentStack >= 0) {
            this.currentStack = newCurrentStack;
        } else {
            throw new IllegalArgumentException("The new current stack must be positive");
        }
    }

    /**
     * Method to check if this player can draw a new card in the game
     * 
     * @return {@code true} if this player can draw a new card, {@code true}
     *         otherwise
     */
    public boolean canDrawCard() {
        if (super.getCardSum() == 21) {
            return false;
        } else if (super.getCardSum() > 21) {
            if (super.getAceCount() > 0) {
                int newPlayerSum = super.getCardSum() - 10;
                super.setCardSum(newPlayerSum);
                super.decreaseAceCount();
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
