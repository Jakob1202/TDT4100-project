package blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** A card container with cards {@link Card} */
public class CardContainer {

    /** A card cotainer has a list of cards {@link Card} */
    protected List<Card> cards;

    /** The valid suits to a card */
    public final List<Character> VALID_SUITS = Arrays.asList('S', 'H', 'D', 'C');

    /**
     * Constructor to create a new card container
     */
    public CardContainer() {
        this.cards = new ArrayList<>();
    }

    /**
     * Method to get the cards to this card container
     * 
     * @return the cards
     */
    public List<Card> getCards() {
        return Collections.unmodifiableList(this.cards);
    }
}
