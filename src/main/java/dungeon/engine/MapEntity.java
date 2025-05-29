package dungeon.engine;

public interface MapEntity {
    String getSymbol();              // Each entity will be represented by a Symbol e.g. "G", "T"
    String interaction(Player player);    // Interactions with the user, defines what happens
    boolean playerBlocking();            // returns true if the entity has blocking capabilities
}
