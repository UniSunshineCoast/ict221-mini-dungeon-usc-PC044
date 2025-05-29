import dungeon.engine.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPlayer {
    //IF player moves to correct cell
    @Test
    public void testPlayerMovement() {
        Player player = new Player(0, 0);
        player.moveTo(4, 8);

        assertEquals(4, player.getX());
        assertEquals(8, player.getY());
    }

    //If player correctly gets their health reduced
    @Test
    public void testHealthReduction() {
        Player player = new Player(0, 0);
        player.changeHP(10);

        player.changeHP(-9);
        assertEquals(1, player.getHealth());
    }

    //If the player can exceed the max hp (10)
    @Test
    public void testExceedingHP() {
        Player player = new Player(0, 0);
        player.changeHP(10);

        player.changeHP(6);  // Testing to 16 health, should max at 10
        assertEquals(10, player.getHealth());
    }

    //When the player loses all health, check if it dies correctly
    @Test
    public void testPlayerDeath() {
        Player player = new Player(0, 0);
        player.changeHP(10);

        player.changeHP(-10);
        assertTrue(player.isDead());
    }

    //Player increments steps by one each movement
    @Test
    public void testStepsIncremented() {
        Player player = new Player(0, 0);
        player.incrementSteps();
        assertEquals(1, player.getSteps());
    }

    //Test end-game mechanic - running out of steps (stepping over 100 times)
    @Test
    public void testMaxSteps() {
        Player player = new Player(0, 0);
        for (int i = 0; i < 100; i++) {
            player.incrementSteps();
        }
        assertTrue(player.isOutOfSteps());
    }
}
