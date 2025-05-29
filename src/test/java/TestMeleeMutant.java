import dungeon.engine.MeleeMutant;
import dungeon.engine.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMeleeMutant {
    //Melee Mutants damage the user for 2HP and increases score by +2
    @Test
    void testMeleeDamage() {
        Player player = new Player(0, 0);
        MeleeMutant mutant = new MeleeMutant();

        player.changeHP(10);
        String result = mutant.interaction(player);

        assertEquals(8, player.getHealth());
        assertEquals(2, player.getScore());
        assertEquals("You come across a mutant with a sword. You fight and win. \n[Score + 2] \n[HP - 2]", result);
    }
}
