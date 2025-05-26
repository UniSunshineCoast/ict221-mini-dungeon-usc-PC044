package dungeon.engine;

public class Wall implements MapEntity {

    @Override
    public String getSymbol() {
        return "#";
    }

    @Override
    public String interact(Player player) {
        return "You tried to move, but there is a wall in your way.";
    }

    @Override
    public boolean isBlocking() {
        return true;
    }
}
