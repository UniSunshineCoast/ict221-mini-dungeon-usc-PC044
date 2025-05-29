import dungeon.engine.Entry;
import dungeon.engine.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEntry {
    //If the user goes back to entrance
    @Test
    void testPlayerInteractsEntrance() {
        Entry entry = new Entry();
        Player player = new Player(0, 0);
        String result = entry.interaction(player);
        assertEquals("The entrance has seemingly shut and locked you in, forward is the only option", result);
    }
}
