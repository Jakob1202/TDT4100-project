package Blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import blackjack.Card;
import blackjack.CardDeck;
import blackjack.Dealer;
import blackjack.Player;
import blackjack.Table;

/** Test for table {@link Table} */
public class TableTest {
    private CardDeck cardDeck1;
    private CardDeck cardDeck2;
    private CardDeck cardDeck3;
    private CardDeck cardDeck4;
    private CardDeck cardDeck5;
    private CardDeck cardDeck6;
    private CardDeck cardDeck7;
    private CardDeck cardDeck8;

    private Dealer dealer;
    private Player player1;
    private Player player2;
    private Player player3;
    private Table table;

    @BeforeEach
    public void setUp() {
        cardDeck1 = new CardDeck(
                Arrays.asList(new Card('S', 1), new Card('S', 10), new Card('S', 9)));
        cardDeck2 = new CardDeck(
                Arrays.asList(new Card('S', 3), new Card('S', 7), new Card('S', 9), new Card('S', 10),
                        new Card('S', 8)));
        cardDeck3 = new CardDeck(
                Arrays.asList(new Card('H', 6), new Card('H', 4), new Card('S', 1), new Card('S', 8),
                        new Card('S', 10)));
        cardDeck4 = new CardDeck(
                Arrays.asList(new Card('H', 8), new Card('S', 6), new Card('S', 4), new Card('S', 8),
                        new Card('S', 10)));
        cardDeck5 = new CardDeck(
                Arrays.asList(new Card('S', 1), new Card('S', 10), new Card('S', 9), new Card('S', 8), new Card('S', 7),
                        new Card('S', 6), new Card('S', 5), new Card('S', 4), new Card('S', 3), new Card('S', 2),
                        new Card('S', 1)));
        cardDeck6 = new CardDeck(
                Arrays.asList(new Card('S', 8), new Card('S', 5), new Card('S', 1),
                        new Card('S', 10)));

        cardDeck7 = new CardDeck(
                Arrays.asList(new Card('S', 7), new Card('S', 4), new Card('S', 8), new Card('S', 5), new Card('S', 9),
                        new Card('S', 1)));

        cardDeck8 = new CardDeck(
                Arrays.asList(new Card('S', 8), new Card('S', 2), new Card('S', 5), new Card('S', 10),
                        new Card('S', 11)));

        dealer = new Dealer();
        player1 = new Player("Jakob", 1000);
        player2 = new Player("Jens", 1000);
        player3 = new Player("Jaran", 1000);
        table = new Table(dealer);
    }

    @Test
    @DisplayName("Test creating a new round")
    public void testNewRound() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> table.newRound(player1, 0));
        assertThrows(IllegalArgumentException.class, () -> table.newRound(player1, 2000));
    }

    @Test
    @DisplayName("Test that player wins with blackjack")
    public void testBlackjack() throws IOException {
        table.newRound(player1, 100, table, cardDeck1);

        assertEquals(100, table.getCurrentRound().getBet());
        assertEquals(21, player1.getCardSum());
        assertEquals(9, dealer.getCardSum());

        assertEquals(1000, player1.getEntryStack());
        assertEquals(1150, player1.getCurrentStack());

        assertEquals(1, table.getRoundCount());
        assertEquals(1, table.getTotalWins());
        assertEquals(0, table.getTotalLosses());
        assertEquals(0, table.getTotalTies());
    }

    @Test
    @DisplayName("Test that player wins with highest card sum")
    public void testplayer1Win() throws IOException {
        table.newRound(player1, 100, table, cardDeck2);

        assertEquals(100, table.getCurrentRound().getBet());
        assertEquals(19, player1.getCardSum());
        assertEquals(8, dealer.getCardSum());

        table.getCurrentRound().stand();

        assertEquals(18, dealer.getCardSum());

        assertEquals(1000, player1.getEntryStack());
        assertEquals(1150, player1.getCurrentStack());

        assertEquals(1, table.getRoundCount());
        assertEquals(1, table.getTotalWins());
        assertEquals(0, table.getTotalLosses());
        assertEquals(0, table.getTotalTies());
    }

    @Test
    @DisplayName("Test that dealer wins with highest card sum")
    public void testDealerWin() throws IOException {
        table.newRound(player1, 100, table, cardDeck3);

        assertEquals(100, table.getCurrentRound().getBet());
        assertEquals(19, player1.getCardSum());
        assertEquals(10, dealer.getCardSum());

        table.getCurrentRound().stand();

        assertEquals(19, player1.getCardSum());
        assertEquals(20, dealer.getCardSum());

        assertEquals(1000, player1.getEntryStack());
        assertEquals(900, player1.getCurrentStack());

        assertEquals(1, table.getRoundCount());
        assertEquals(0, table.getTotalWins());
        assertEquals(1, table.getTotalLosses());
        assertEquals(0, table.getTotalTies());
    }

    @Test
    @DisplayName("Test that tie works when player and dealer has equal card sum")
    public void testTie() throws IOException {
        table.newRound(player1, 100, table, cardDeck4);

        assertEquals(100, table.getCurrentRound().getBet());
        assertEquals(12, player1.getCardSum());
        assertEquals(10, dealer.getCardSum());

        table.getCurrentRound().hit();

        assertEquals(18, player1.getCardSum());
        assertEquals(10, dealer.getCardSum());

        table.getCurrentRound().stand();

        assertEquals(18, player1.getCardSum());
        assertEquals(18, dealer.getCardSum());

        assertEquals(1000, player1.getEntryStack());
        assertEquals(1000, player1.getCurrentStack());

        assertEquals(1, table.getRoundCount());
        assertEquals(0, table.getTotalWins());
        assertEquals(0, table.getTotalLosses());
        assertEquals(1, table.getTotalTies());
    }

    @Test
    @DisplayName("Test when player hit")
    public void testHit() throws IOException {
        assertThrows(IllegalStateException.class, () -> table.getCurrentRound());
        table.newRound(player1, 100, table, cardDeck5);

        assertEquals(100, table.getCurrentRound().getBet());
        assertEquals(5, player1.getCardSum());
        assertEquals(11, dealer.getCardSum());
        assertTrue(player1.canDrawCard());

        table.getCurrentRound().hit();

        assertEquals(9, player1.getCardSum());
        assertEquals(11, dealer.getCardSum());
        assertTrue(player1.canDrawCard());

        table.getCurrentRound().hit();

        assertEquals(14, player1.getCardSum());
        assertEquals(11, dealer.getCardSum());
        assertTrue(player1.canDrawCard());

        table.getCurrentRound().hit();

        assertEquals(20, player1.getCardSum());
        assertEquals(11, dealer.getCardSum());
        assertTrue(player1.canDrawCard());

        table.getCurrentRound().hit();

        assertEquals(27, player1.getCardSum());
        assertEquals(11, dealer.getCardSum());
        assertFalse(player1.canDrawCard());

        assertThrows(IllegalStateException.class, () -> table.getCurrentRound().hit());

        assertEquals(27, player1.getCardSum());
        assertEquals(11, dealer.getCardSum());
        assertFalse(player1.canDrawCard());

        assertEquals(1, table.getRoundCount());
        assertEquals(0, table.getTotalWins());
        assertEquals(1, table.getTotalLosses());
        assertEquals(0, table.getTotalTies());
    }

    @Test
    @DisplayName("Test when player stand")
    public void testStand() throws IOException {
        table.newRound(player1, 100, table, cardDeck5);

        assertEquals(100, table.getCurrentRound().getBet());
        assertEquals(5, player1.getCardSum());
        assertEquals(11, dealer.getCardSum());
        assertTrue(player1.canDrawCard());

        table.getCurrentRound().stand();

        assertEquals(5, player1.getCardSum());
        assertEquals(23, dealer.getCardSum());

        assertEquals(1, table.getRoundCount());
        assertEquals(1, table.getTotalWins());
        assertEquals(0, table.getTotalLosses());
        assertEquals(0, table.getTotalTies());
    }

    @Test
    @DisplayName("Test that dealer win when player bust")
    public void testplayer1Bust() throws IOException {
        table.newRound(player1, 100, table, cardDeck5);

        assertEquals(100, table.getCurrentRound().getBet());
        assertEquals(5, player1.getCardSum());
        assertEquals(11, dealer.getCardSum());
        assertTrue(player1.canDrawCard());

        table.getCurrentRound().hit();

        assertEquals(9, player1.getCardSum());
        assertEquals(11, dealer.getCardSum());
        assertTrue(player1.canDrawCard());

        table.getCurrentRound().hit();

        assertEquals(14, player1.getCardSum());
        assertEquals(11, dealer.getCardSum());
        assertTrue(player1.canDrawCard());

        table.getCurrentRound().hit();

        assertEquals(20, player1.getCardSum());
        assertEquals(11, dealer.getCardSum());
        assertTrue(player1.canDrawCard());

        table.getCurrentRound().hit();

        assertEquals(27, player1.getCardSum());
        assertEquals(11, dealer.getCardSum());
        assertFalse(player1.canDrawCard());

        assertEquals(1000, player1.getEntryStack());
        assertEquals(900, player1.getCurrentStack());

        assertEquals(1, table.getRoundCount());
        assertEquals(0, table.getTotalWins());
        assertEquals(1, table.getTotalLosses());
        assertEquals(0, table.getTotalTies());

    }

    @Test
    @DisplayName("Test that player win when dealer bust")
    public void testDealerBust() throws IOException {
        table.newRound(player1, 100, table, cardDeck5);

        assertEquals(100, table.getCurrentRound().getBet());
        assertEquals(5, player1.getCardSum());
        assertEquals(11, dealer.getCardSum());
        assertTrue(player1.canDrawCard());

        table.getCurrentRound().stand();

        assertEquals(5, player1.getCardSum());
        assertEquals(23, dealer.getCardSum());

        assertEquals(1, table.getRoundCount());
        assertEquals(1, table.getTotalWins());
        assertEquals(0, table.getTotalLosses());
        assertEquals(0, table.getTotalTies());
    }

    @Test
    @DisplayName("Test ace count when player bust")
    public void testAceplayer1() throws IOException {
        table.newRound(player1, 100, table, cardDeck6);

        assertEquals(16, player1.getCardSum());
        assertEquals(1, player1.getAceCount());
        table.getCurrentRound().hit();

        assertEquals(14, player1.getCardSum());
        assertEquals(0, player1.getAceCount());

    }

    @Test
    @DisplayName("Test ace count when dealer bust")
    public void testAceDealer() throws IOException {
        table.newRound(player1, 100, table, cardDeck7);

        assertEquals(11, dealer.getCardSum());
        assertEquals(1, dealer.getAceCount());

        table.getCurrentRound().stand();

        assertEquals(20, dealer.getCardSum());
        assertEquals(0, dealer.getAceCount());
    }

    @Test
    @DisplayName("Test multiple rounds")
    public void testRounds() throws IOException {
        assertEquals(0, table.getRoundCount());

        table.enterTable(player1);
        table.newRound(player1, 100, table, cardDeck1);
        assertEquals(100, table.getCurrentRound().getBet());

        assertEquals(21, player1.getCardSum());
        assertEquals(9, dealer.getCardSum());
        assertEquals(1000, player1.getEntryStack());
        assertEquals(1150, player1.getCurrentStack());

        assertEquals(1, table.getRoundCount());
        assertEquals(1, table.getPlayersCount());
        assertEquals(100, table.getTotalBets());
        assertEquals(1, table.getTotalWins());
        assertEquals(0, table.getTotalLosses());
        assertEquals(0, table.getTotalTies());

        assertThrows(IllegalArgumentException.class, () -> table.enterTable(player1));

        table.newRound(player1, 100, table, cardDeck2);
        assertEquals(100, table.getCurrentRound().getBet());

        table.getCurrentRound().stand();

        assertEquals(19, player1.getCardSum());
        assertEquals(18, dealer.getCardSum());
        assertEquals(1000, player1.getEntryStack());
        assertEquals(1300, player1.getCurrentStack());

        assertEquals(2, table.getRoundCount());
        assertEquals(1, table.getPlayersCount());
        assertEquals(200, table.getTotalBets());
        assertEquals(2, table.getTotalWins());
        assertEquals(0, table.getTotalLosses());
        assertEquals(0, table.getTotalTies());

        table.enterTable(player2);
        table.newRound(player2, 100, table, cardDeck3);
        assertEquals(100, table.getCurrentRound().getBet());

        table.getCurrentRound().stand();

        assertEquals(19, player1.getCardSum());
        assertEquals(20, dealer.getCardSum());
        assertEquals(1000, player2.getEntryStack());
        assertEquals(900, player2.getCurrentStack());

        assertEquals(3, table.getRoundCount());
        assertEquals(2, table.getPlayersCount());
        assertEquals(300, table.getTotalBets());
        assertEquals(2, table.getTotalWins());
        assertEquals(1, table.getTotalLosses());
        assertEquals(0, table.getTotalTies());

        table.enterTable(player3);
        table.newRound(player3, 100, table, cardDeck4);
        assertEquals(100, table.getCurrentRound().getBet());

        table.getCurrentRound().hit();
        table.getCurrentRound().stand();

        assertEquals(18, player3.getCardSum());
        assertEquals(18, dealer.getCardSum());
        assertEquals(1000, player3.getEntryStack());
        assertEquals(1000, player3.getCurrentStack());

        assertEquals(4, table.getRoundCount());
        assertEquals(3, table.getPlayersCount());
        assertEquals(400, table.getTotalBets());
        assertEquals(2, table.getTotalWins());
        assertEquals(1, table.getTotalLosses());
        assertEquals(1, table.getTotalTies());

        assertThrows(IllegalArgumentException.class, () -> table.enterTable(player1));
        table.newRound(player1, 1300, table, cardDeck8);
        assertEquals(1300, table.getCurrentRound().getBet());

        table.getCurrentRound().hit();
        table.getCurrentRound().stand();

        assertEquals(17, player1.getCardSum());
        assertEquals(18, dealer.getCardSum());
        assertEquals(1000, player1.getEntryStack());
        assertEquals(0, player1.getCurrentStack());

        assertEquals(5, table.getRoundCount());
        assertEquals(3, table.getPlayersCount());
        assertEquals(1700, table.getTotalBets());
        assertEquals(2, table.getTotalWins());
        assertEquals(2, table.getTotalLosses());
        assertEquals(1, table.getTotalTies());

        assertThrows(IllegalArgumentException.class, () -> table.newRound(player1, 10, table, cardDeck1));

    }
}
