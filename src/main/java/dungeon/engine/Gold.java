package dungeon.engine;

public class Gold implements MapEntity {

    @Override
    public String getSymbol() {
        return "G";
    }

    @Override
    public String interact(Player player) {
        player.addScore(2);
        return  "You just picked up some Gold.";
    }

    @Override
    public boolean isBlocking() {
        return false;
    }
}
