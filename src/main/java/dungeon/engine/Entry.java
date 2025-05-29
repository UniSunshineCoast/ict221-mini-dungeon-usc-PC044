package dungeon.engine;

public class Entry implements MapEntity {

    @Override
    public String getSymbol() {
        return "E";
    }

    @Override
    public String interaction(Player player) {
        return "The entrance has seemingly shut and locked you in, forward is the only option";
    }

    @Override
    public boolean playerBlocking() {
        return false;
    }
}
