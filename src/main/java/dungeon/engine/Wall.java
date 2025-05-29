package dungeon.engine;

public class Wall implements MapEntity {

    @Override
    public String getSymbol() {
        return "#";
    }

    @Override
    public String interaction(Player player) {
        return "There seems to be a wall in your way. Can't move there.";
    }

    //WALLS are the ONLY entities that BLOCK
    @Override
    public boolean playerBlocking() {
        return true;
    }
}
