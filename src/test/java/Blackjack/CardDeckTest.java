package Blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import blackjack.Card;
import blackjack.CardDeck;

/** Test for card deck {@link CardDeck} */
public class CardDeckTest {
    private Card card1;
    private Card card2;
    private Card card3;
    private Card card4;

    private List<Card> cards;

    @BeforeEach
    public void setUt() {
        card1 = new Card('S', 1);
        card2 = new Card('S', 5);
        card3 = new Card('S', 6);
        card4 = new Card('S', 8);

        cards = new ArrayList<>(List.of(card1, card2, card3, card4));
    }

    @Test
    @DisplayName("Test that remove card work")
    public void testCardDeck() {
        CardDeck cardDeck = new CardDeck(cards);

        assertEquals(cardDeck.removeCard(), card4);
        assertEquals(cardDeck.getCards(), List.of(card1, card2, card3));

        assertEquals(cardDeck.removeCard(), card3);
        assertEquals(cardDeck.getCards(), List.of(card1, card2));

        assertEquals(cardDeck.removeCard(), card2);
        assertEquals(cardDeck.getCards(), List.of(card1));

        assertEquals(cardDeck.removeCard(), card1);
        assertEquals(cardDeck.getCards(), List.of());

    }
}
