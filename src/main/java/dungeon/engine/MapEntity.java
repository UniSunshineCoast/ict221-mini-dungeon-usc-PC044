package dungeon.engine;

public interface MapEntity {
    String getSymbol();              // Each entity will be represented by a Symbol e.g. "G", "T"
    String interact(Player player);    // Interactions with the user, defines what happens
    boolean isBlocking();            // returns true for Wall, false for all other items
}
