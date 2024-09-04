package blackjack;

import java.util.ArrayList;
import java.util.Objects;

/*** A card holder with a card hand {@link CardHand} */
public class CardHolder {
    /** A card holder has an ace count, a card sum and a card hand */
    private int aceCount;
    private int cardSum;
    private CardHand cardHand;

    /**
     * Constructor to create a new card holder
     */
    public CardHolder() {
        this.aceCount = 0;
        this.cardSum = 0;
        this.cardHand = Objects.requireNonNull(new CardHand());
    }

    /**
     * Method to add a card to the card hand to this card holder
     * 
     * @param card a card
     */
    public void drawCard(Card card) {
        if (!this.cardHand.getCards().contains(card)) {
            this.cardHand.addCard(card);
            if (card.getFace() == 1) {
                this.cardSum += 11;
                this.increaseAceCount();
            } else if (card.getFace() >= 11) {
                this.cardSum += 10;
            } else {
                this.cardSum += card.getFace();
            }

            if (this.cardSum > 21 && this.aceCount > 0) {
                this.decreaseAceCount();
                int newCardSum = this.getCardSum() - 10;
                this.setCardSum(newCardSum);
            }
        } else {
            throw new IllegalArgumentException("The card is already in the hand");
        }
    }

    /**
     * Method to get the card sum to this card holder
     * 
     * @return the card sum
     */
    public int getCardSum() {
        return this.cardSum;
    }

    /**
     * Method to set the card sum to this card holder
     * 
     * @param newSum the new card sum
     */
    public void setCardSum(int newCardSum) {
        if (newCardSum >= 0) {
            this.cardSum = newCardSum;
        } else {
            throw new IllegalArgumentException("The card sum cannot be negative");
        }
    }

    /**
     * Method to get the card hand to this card holder
     * 
     * @return the card hand
     */
    public CardHand getCardHand() {
        return this.cardHand;
    }

    /**
     * Method to clear the card hand to this card hand
     */
    public void resetCardHand() {
        this.cardHand.cards = new ArrayList<>();
    }

    /**
     * Method to get the ace count to this card holder
     * 
     * @return the ace count
     */
    public int getAceCount() {
        return this.aceCount;
    }

    
    /**
     * Method to increase the ace count to this card holder
     * 
     */
    public void increaseAceCount() {
        this.aceCount++;
    }

    /**
     * Method to decrease the ace count to this card holder
     * 
     */
    public void decreaseAceCount() {
        this.aceCount--;
    }

}
