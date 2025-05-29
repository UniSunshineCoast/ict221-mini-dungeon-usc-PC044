package dungeon.gui;

import dungeon.engine.Cell;
import dungeon.engine.GameEngine;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private GridPane gridPane;

    GameEngine engine;

    @FXML
    public void initialize() {
        engine = new GameEngine(10);

        updateGui();
    }

    private void updateGui() {
        //Clear old GUI grid pane
        gridPane.getChildren().clear();

        //Loop through map board and add each cell into grid pane
        for(int i = 0; i < engine.getSize(); i++) {
            for (int j = 0; j < engine.getSize(); j++) {
                Label label = getLabel(i, j);

                gridPane.add(label, j, i);  // Add to GridPane
            }
        }

        gridPane.setGridLinesVisible(true);
    }

    private Label getLabel(int i, int j) {
        Cell cell = engine.getMap()[i][j];

        Label label = new Label(cell.getSymbol());  // getSymbol() should return a string representation
        label.setPrefSize(40, 40);  // Make sure all cells are uniform in size
        label.setStyle("-fx-font-size: 20; -fx-alignment: center; -fx-border-color: black; -fx-background-color: white;");
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        label.setMinSize(40, 40);
        label.setAlignment(javafx.geometry.Pos.CENTER);
        return label;
    }

}
