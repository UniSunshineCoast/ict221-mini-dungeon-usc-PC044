import dungeon.engine.GameEngine;
import dungeon.engine.Player;
import dungeon.engine.Wall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestWall {
    //test if boolean returns True
    @Test
    void testPlayerBlocking() {
        Wall wall = new Wall();
        assertTrue(wall.playerBlocking());
    }

    //tests if user walks into a wall, position should not move
    @Test
    void testPlayerCollision() {
        GameEngine engine = new GameEngine(3); //generate a small map for testing
        Player player = new Player(0, 0);

        // Places a wall above the player (1, 0)
        engine.getMap()[1][0].setEntity(new Wall());

        // Move the player into the wall (up)
        String result = engine.movePlayer("u");

        assertEquals("There seems to be a wall in your way. Can't move there.", result);
        assertEquals(0, player.getX()); // position should remain the same
        assertEquals(0, player.getY());
    }
}
