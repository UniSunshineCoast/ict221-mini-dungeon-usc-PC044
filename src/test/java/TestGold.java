import dungeon.engine.Gold;
import dungeon.engine.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestGold {

    //If Gold increments score correctly
    @Test
    public void testGoldScoreAdd() {
        Player player = new Player(0, 0);
        Gold gold = new Gold();

        String result = gold.interaction(player);

        assertEquals(2, player.getScore());
        assertEquals("You found some Gold. You stash this for later. \n[Score + 2]", result);
    }

}
