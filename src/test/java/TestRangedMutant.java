import dungeon.engine.MeleeMutant;
import dungeon.engine.Player;
import dungeon.engine.RangedMutant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRangedMutant {
    //Tests the 50% attack function of the ranged mutant
    //Ranged deal 2 damage
    @Test
    void testRangedDamageAndMiss() {
        Player player = new Player(2, 0);
        RangedMutant mutant = new RangedMutant();

        int hits = 0, misses = 0;

        //Test the fifty-percent mechanic by reiterating hits 100 times
        for (int i = 0; i < 100; i++) {
            player.changeHP(10 - player.getHealth()); // reset the HP every iteration
            String result = mutant.tryAttack(player, 0, 0, 2, 0); //mutant is two blocks away
            if (result.contains("HP - 2")) hits++;
            else if (result.contains("missing")) misses++; //string result contains 'missing' in test
        }

        //if both get a value
        assertTrue(hits > 0);
        assertTrue(misses > 0);
        //if both statistically get above 25 (very likely - COULD FAIL, if so, run again)
        assertTrue(hits > 25);
        assertTrue(misses > 25);
    }

    //Mutants give a score of +2
    @Test
    void testRangedMutantInteraction() {
        Player player = new Player(0, 0);
        RangedMutant mutant = new RangedMutant();

        mutant.interaction(player); //player wins battle against ranged mutant

        assertEquals(2, player.getScore()); //score should equal +2
    }
}
