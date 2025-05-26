package dungeon.engine;

public class Entry implements MapEntity {

    @Override
    public String getSymbol() {
        return "E";
    }

    @Override
    public String interact(Player player) {
        return "You are at the entrance";
    }

    @Override
    public boolean isBlocking() {
        return false;
    }
}
