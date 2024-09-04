package Blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import blackjack.Card;
import blackjack.CardHolder;
import blackjack.Dealer;
import blackjack.Player;

/** Test for card holder {@link CardHolder} */
public class CardHolderTest {

    private Card card1;
    private Card card2;
    private Card card3;
    private Card card4;

    @BeforeEach
    public void setUp() {
        card1 = new Card('S', 1);
        card2 = new Card('S', 5);
        card3 = new Card('S', 6);
        card4 = new Card('S', 8);
    }

    @Test
    @DisplayName("Test dealing out cards to a card holder casted as a player")
    public void testPlayerCardHand() {
        CardHolder cardHolder = new Player("Jakob", 1000);

        assertEquals(cardHolder.getCardHand().getCards(), new ArrayList<>());
        assertEquals(0, cardHolder.getCardSum());
        assertEquals(0, cardHolder.getAceCount());
        assertTrue(((Player) cardHolder).canDrawCard());

        cardHolder.drawCard(card1);

        assertEquals(cardHolder.getCardHand().getCards(), new ArrayList<>(List.of(card1)));
        assertEquals(11, cardHolder.getCardSum());
        assertEquals(1, cardHolder.getAceCount());
        assertTrue(((Player) cardHolder).canDrawCard());

        cardHolder.drawCard(card2);

        assertEquals(cardHolder.getCardHand().getCards(), new ArrayList<>(List.of(card1, card2)));
        assertEquals(16, cardHolder.getCardSum());
        assertEquals(1, cardHolder.getAceCount());
        assertTrue(((Player) cardHolder).canDrawCard());

        cardHolder.drawCard(card3);

        assertEquals(cardHolder.getCardHand().getCards(), new ArrayList<>(List.of(card1, card2, card3)));
        assertEquals(12, cardHolder.getCardSum());
        assertEquals(0, cardHolder.getAceCount());
        assertTrue(((Player) cardHolder).canDrawCard());

        cardHolder.drawCard(card4);

        assertEquals(cardHolder.getCardHand().getCards(), new ArrayList<>(List.of(card1, card2, card3, card4)));
        assertEquals(20, cardHolder.getCardSum());
        assertEquals(0, cardHolder.getAceCount());
        assertTrue(((Player) cardHolder).canDrawCard());
    }

    @Test
    @DisplayName("Test dealing out cards to a card holder casted as a dealer")
    public void testDealerCardHand() {
        CardHolder cardHolder = new Dealer();

        assertEquals(cardHolder.getCardHand().getCards(), new ArrayList<>());
        assertEquals(0, cardHolder.getCardSum());
        assertEquals(0, cardHolder.getAceCount());
        assertTrue(((Dealer) cardHolder).canDrawCard());

        cardHolder.drawCard(card1);

        assertEquals(cardHolder.getCardHand().getCards(), new ArrayList<>(List.of(card1)));
        assertEquals(11, cardHolder.getCardSum());
        assertEquals(1, cardHolder.getAceCount());
        assertTrue(((Dealer) cardHolder).canDrawCard());

        cardHolder.drawCard(card2);

        assertEquals(cardHolder.getCardHand().getCards(), new ArrayList<>(List.of(card1, card2)));
        assertEquals(16, cardHolder.getCardSum());
        assertEquals(1, cardHolder.getAceCount());
        assertTrue(((Dealer) cardHolder).canDrawCard());

        cardHolder.drawCard(card3);

        assertEquals(cardHolder.getCardHand().getCards(), new ArrayList<>(List.of(card1, card2, card3)));
        assertEquals(12, cardHolder.getCardSum());
        assertEquals(0, cardHolder.getAceCount());
        assertTrue(((Dealer) cardHolder).canDrawCard());

        cardHolder.drawCard(card4);

        assertEquals(cardHolder.getCardHand().getCards(), new ArrayList<>(List.of(card1, card2, card3, card4)));
        assertEquals(20, cardHolder.getCardSum());
        assertEquals(0, cardHolder.getAceCount());
        assertFalse(((Dealer) cardHolder).canDrawCard());
    }
}
