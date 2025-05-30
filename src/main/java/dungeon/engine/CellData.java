package dungeon.engine;

import java.io.Serializable;

public class CellData implements Serializable {

    private MapEntity entity;

    public CellData() {}

    public String interact(Player player) {
        if (entity != null) {
            String message = entity.interaction(player);
            if (!(entity instanceof Trap || entity instanceof Wall || entity instanceof Ladder)) {
                entity = null;
            }
            return message;
        }
        return "";
    }

    public boolean isBlocking() {
        return entity != null && entity.playerBlocking();
    }

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
