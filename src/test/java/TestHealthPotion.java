import dungeon.engine.HealthPotion;
import dungeon.engine.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHealthPotion {

    //HP potions add health to user (HP Potions = 4 HP)
    @Test
    void testHealthPotionAdd() {
        Player player = new Player(0, 0);
        player.changeHP(-8); //Decrease HP, results should end in 2 + 4 = 6
        HealthPotion potion = new HealthPotion();

        String result = potion.interaction(player);

        assertEquals(6, player.getHealth()); // should not exceed max HP
        assertEquals("You pick up a health potion and drink it. \n[HP restored by 4]", result);
    }
}
