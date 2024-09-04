package blackjack;

import java.util.Arrays;
import java.util.List;

/** A card to a card container {@link CardContainer} */
public class Card {
    
    /** A card has a suit and a face */
    private char suit;
    private int face;

    /** The valid suits to a card */
    public final List<Character> VALID_SUITS = Arrays.asList('S', 'H', 'D', 'C');

    /***
     * Creates a new card. The card must have a valid suit {@link #VALID_SUITS} 
     * 
     * @param suit the suit to the card
     * @param face the face to the card
     */
    public Card(char suit, int face) {
        if (validateCard(suit, face)) {
            this.suit = suit;
            this.face = face;
        } else {
            throw new IllegalArgumentException("The card is not valid");
        }
    }

    /**
     * Method to validate a card
     * 
     * @param suit the suit to the card
     * @param face the face to the card
     * @return true if the card is valid, false otwerwise
     */
    public boolean validateCard(char suit, int face) {
        if (face >= 1 && face <= 13 && VALID_SUITS.contains(suit)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to get the suit to this card
     * 
     * @return the suit
     */
    public char getSuit() {
        return this.suit;
    }

    /**
     * Method to get the face to this card
     * 
     * @return the face
     */
    public int getFace() {
        return this.face;
    }

    @Override
    public String toString() {
        return this.suit + Integer.toString(face);
    }
}
