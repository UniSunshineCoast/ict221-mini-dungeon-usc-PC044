package dungeon.engine;

import javafx.scene.layout.StackPane;

public class Cell extends StackPane {

    private MapEntity entity;

    public Cell() {}

    //Common interaction method for each cell
    public String interact(Player player) {
        if (entity != null) {
            String message = entity.interaction(player);
            // REPLACE ONE-TIME tiles after interacting with 'nothing'
            // Initialize persistent tiles (DON'T remove)
            if (!(entity instanceof Trap || entity instanceof Wall || entity instanceof Ladder)) {
                entity = null;
            }
            return message;
        }
        return "";
    }

    //Getter & Setter
    //Return true if the cell has entity + can block the player
    public boolean isBlocking() {
        return entity != null && entity.playerBlocking();
    }

    //Symbols for null cells are '.' ; Symbols for entities are their attributed class symbol
    public String getSymbol() {
        return entity == null ? "." : entity.getSymbol();
    }

    public void setEntity(MapEntity entity) {
        this.entity = entity;
    }

    public MapEntity getEntity() {
        return entity;
    }

}
