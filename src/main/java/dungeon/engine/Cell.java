package dungeon.engine;

import javafx.scene.layout.StackPane;

public class Cell extends StackPane {
    private final CellData cellData;

    public Cell(CellData cellData) {
        this.cellData = cellData;
    }

    public String interact(Player player) {
        return cellData.interact(player);
    }

    public boolean isBlocking() {
        return cellData.isBlocking();
    }

    public String getSymbol() {
        return cellData.getSymbol();
    }

    public void setEntity(MapEntity entity) {
        cellData.setEntity(entity);
    }

    public MapEntity getEntity() {
        return cellData.getEntity();
    }
}