package Blackjack;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import blackjack.Player;

/** Test when creating a new player {@link Player} */
public class PlayerTest {

    @Test
    @DisplayName("Test that you can create a new player")
    public void testValidConstructor() {
        new Player("Jakob", 1000);
    }

    @Test
    @DisplayName("Test invalid name")
    public void testInvalidName() {
        assertThrows(NullPointerException.class, () -> new Player(null, 1000));
    }

    @Test
    @DisplayName("Test invalid stack")
    public void testInvalidStack() {
        assertThrows(IllegalArgumentException.class, () -> new Player("Jakob", 0));
        assertThrows(IllegalArgumentException.class, () -> new Player("Jakob", -1000));
    }
}
