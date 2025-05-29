import dungeon.engine.Ladder;
import dungeon.engine.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLadder {
    //tests if player finished game when interacting with final ladder
    @Test
    void testTrueFinishedLevel() {
        Ladder finalLadder = new Ladder(true); //sets boolean to true
        Player player = new Player(0, 0);
        String result = finalLadder.interaction(player);
        assertTrue(player.isFinished());
        assertEquals("", result);
    }

    //tests if user advances to next level (NOT final ladder)
    @Test
    void testFalseFinishedLevel() {
        Ladder ladder = new Ladder(false); //sets boolean to false
        Player player = new Player(0, 0);
        String result = ladder.interaction(player);
        assertTrue(player.isAdvanceToNextLevel());
        assertEquals("", result); //the actual string is found in .engine, this only tests the boolean accuracy
    }
}
