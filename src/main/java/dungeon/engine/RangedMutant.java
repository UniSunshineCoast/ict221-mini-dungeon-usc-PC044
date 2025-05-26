package dungeon.engine;

import java.util.Random;

public class RangedMutant implements MapEntity {

    private static Random random = new Random();

    @Override
    public String getSymbol() {
        return "R";
    }

    @Override
    public boolean isBlocking() {
        return false;
    }

    @Override
    public String interact(Player player) {
        player.addScore(2);
        return "You attacked a ranged mutant and won.";
    }

    public String tryAttack(Player player, int mutantX, int mutantY, int playerX, int playerY) {
        boolean inRange = (mutantX == playerX && Math.abs(mutantY - playerY) <= 2) ||
                (mutantY == playerY && Math.abs(mutantX - playerX) <= 2);

        if (inRange) {
            if (random.nextBoolean()) {
                player.changeHP(-2);
                return "A ranged mutant attacked and you lost 2 HP.";
            } else {
                return "A ranged mutant attacked, but missed.";
            }
        }
        return "";
    }
}
