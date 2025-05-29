import dungeon.engine.Player;
import dungeon.engine.Trap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTrap {
    //Test trap damage accurately injures user by 2HP
    @Test
    public void testTrapDamage() {
        Player player = new Player(0, 0);
        Trap trap = new Trap();

        player.changeHP(10);
        String result = trap.interaction(player);

        assertEquals(8, player.getHealth());
        assertEquals("A spike trap activated under your feet. \n[HP - 2]", result);
    }
}
