package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** A card deck to a round {@link Round} */
public class CardDeck extends CardContainer {

    /**
     * Constructor to create a new card deck and shuffle the deck
     * 
     */
    public CardDeck() {
        super();
        for (char suit : VALID_SUITS) {
            for (int rank = 1; rank <= 13; rank++) {
                this.cards.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(cards);
    }

    /**
     * Method to remove a card to this card deck
     * 
     * @return the removed card
     * 
     */
    public Card removeCard() {
        if (!this.cards.isEmpty()) {
            return this.cards.removeLast();
        } else {
            throw new IllegalStateException("The card deck is empty");
        }
    }

    /**
     * Creates a card deck with the given cards. Used for testing only
     * 
     * @param cards the cards
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public CardDeck(List<Card> cards) {
        this.cards = new ArrayList(cards);
    }
}
