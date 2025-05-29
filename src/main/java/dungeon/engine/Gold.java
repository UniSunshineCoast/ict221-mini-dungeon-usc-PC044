package dungeon.engine;

public class Gold implements MapEntity {

    @Override
    public String getSymbol() {
        return "G";
    }

    //Gold bars = 2 points
    @Override
    public String interaction(Player player) {
        player.addScore(2);
        return  "You found some Gold. You stash this for later. \n[Score + 2]";
    }

    @Override
    public boolean playerBlocking() {
        return false;
    }
}
