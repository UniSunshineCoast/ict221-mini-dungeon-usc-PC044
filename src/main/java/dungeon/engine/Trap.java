package dungeon.engine;

public class Trap implements MapEntity {

    @Override
    public String getSymbol() {
        return "T";
    }

    @Override
    public String interact(Player player) {
        player.changeHP(-2);
        return  "You just stepped on a trap and lost 2 HP.";
    }

    @Override
    public boolean isBlocking() {
        return false;
    }
}
