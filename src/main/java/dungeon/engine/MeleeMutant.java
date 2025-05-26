package dungeon.engine;

public class MeleeMutant implements MapEntity {

    @Override
    public String getSymbol() {
        return "M";
    }

    @Override
    public String interact(Player player) {
        player.addScore(2);
        player.changeHP(-2);
        return "You attacked a melee mutant and won.";
    }

    @Override
    public boolean isBlocking() {
        return false;
    }
}
