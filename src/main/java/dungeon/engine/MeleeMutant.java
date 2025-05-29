package dungeon.engine;

public class MeleeMutant implements MapEntity {

    @Override
    public String getSymbol() {
        return "M";
    }

    //MeleeMutants deal 2 DMG + Give score of 2 points
    @Override
    public String interaction(Player player) {
        player.addScore(2);
        player.changeHP(-2);
        player.setCauseOfDeath("Melee Mutant");
        return "You come across a mutant with a sword. You fight and win. \n[Score + 2] [HP - 2]";
    }

    @Override
    public boolean playerBlocking() {
        return false;
    }
}
