package dungeon.engine;

import javafx.scene.layout.StackPane;

public class Cell extends StackPane {

    private MapEntity entity;

    public Cell() {}

    public void setEntity(MapEntity entity) {
        this.entity = entity;
    }

    public MapEntity getEntity() {
        return entity;
    }

    public String interact(Player player) {
        if (entity != null) {
            String message = entity.interact(player);
            // Remove one-time entities
            if (!(entity instanceof Trap || entity instanceof Wall || entity instanceof Ladder)) {
                entity = null;
            }

            return message;
        }
        return "";
    }

    public boolean isBlocking() {
        return entity != null && entity.isBlocking();
    }

    public String getSymbol() {
        return entity == null ? "." : entity.getSymbol();
    }
}
