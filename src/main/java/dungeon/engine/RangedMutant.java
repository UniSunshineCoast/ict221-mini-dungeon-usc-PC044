package dungeon.engine;

import java.util.Random;

public class RangedMutant implements MapEntity {

    private static Random random = new Random();

    @Override
    public String getSymbol() {
        return "R";
    }

    //Ranged Mutants = +2 Score
    @Override
    public String interaction(Player player) {
        player.addScore(2);
        return "You fought a ranged mutant with ease. \n[Score + 2]";
    }

    @Override
    public boolean playerBlocking() {
        return false;
    }

    //Ranged Mutant 50% Chance to attack method
    public String tryAttack(Player player, int mutantX, int mutantY, int playerX, int playerY) {
        //True if Player in Range (2 Blocks).
        //Calculate if player is on same X or Y value and 2 cells away.
        boolean inRange = (mutantX == playerX && Math.abs(mutantY - playerY) <= 2) ||
                (mutantY == playerY && Math.abs(mutantX - playerX) <= 2);

        if (inRange) {
            if (random.nextBoolean()) { //Randomises True or False values for 50% chance
                player.changeHP(-2);
                player.setCauseOfDeath("Ranged Mutant");
                return "A ranged mutant shot you with an arrow. \n[HP - 2]";
            } else {
                return "A ranged mutant shot an arrow narrowly missing you.";
            }
        }
        return "";
    }
}
