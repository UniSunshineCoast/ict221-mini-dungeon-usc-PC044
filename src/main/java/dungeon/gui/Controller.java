package dungeon.gui;

import dungeon.engine.Cell;
import dungeon.engine.GameEngine;
import dungeon.engine.ScoreManager;
import dungeon.engine.ScoreRecord;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.*;

import java.util.Map;
import java.util.Objects;

public class Controller {
    @FXML public Button upButton;
    @FXML public Button leftButton;
    @FXML public Button rightButton;
    @FXML public Button downButton;
    @FXML public TextArea textArea;
    @FXML private GridPane gridPane;
    @FXML private TextField statusBar;
    @FXML private Button startButton;
    @FXML private Button saveButton;
    @FXML private Button loadButton;

    GameEngine engine;
    private final String SAVE_FILE = "game_save.dat";

    @FXML
    public void initialize() {
        disableMovementButtons(true);
        textArea.setText("Click Start to start the game!");
    }

    @FXML
    public void startUp() {
        engine = new GameEngine(10);
        engine.populateMap(3); //Using the populateMap method created in GameEngine
        gridPane.setGridLinesVisible(true);
        disableMovementButtons(false);
        updateGui();
        textArea.setText(engine.getPlayer().startGameMessage());
        startButton.setText("Restart");
    }

    //Player Movement methods on button click
    @FXML
    private void movePlayerUp() {
        if (checkEndGame()) return;

        textArea.setText(engine.movePlayer("u"));
        updateGui();
        checkEndGame();
    }

    @FXML
    public void movePlayerLeft() {
        if (checkEndGame()) return;

        textArea.setText(engine.movePlayer("l"));
        updateGui();
        checkEndGame();
    }

    @FXML
    public void movePlayerRight() {
        if (checkEndGame()) return;

        textArea.setText(engine.movePlayer("r"));
        updateGui();
        checkEndGame();
    }

    @FXML
    public void movePlayerDown() {
        if (checkEndGame()) return;

        textArea.setText(engine.movePlayer("d"));
        updateGui();
        checkEndGame();
    }

    //all status bar requirements
    @FXML
    public void updateStatusBar() {
        int health = engine.getPlayer().getHealth();
        int score = engine.getPlayer().getScore();
        int steps = engine.getPlayer().getSteps();
        statusBar.setText("Health: " + health + "    Score: " + score + "    Steps: " + steps);
    }

    private void updateGui() {
        //Clear old GUI grid pane
        gridPane.getChildren().clear();

        Cell[][] map = engine.getMap();
        int playerX = engine.getPlayer().getX();
        int playerY = engine.getPlayer().getY();

        //Loop through the map and add a label to each cell corresponding to symbol
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Node cellNode = createEachCell(map[i][j]);

                if (j == playerX && i == playerY) {
                    //Player is imaged first
                    ImageView player = createImageView("/dungeon_player_icon.png");

                    StackPane stack = new StackPane();
                    stack.getChildren().add(cellNode);     // Background entity (if any)
                    stack.getChildren().add(player);
                    gridPane.add(stack, j, i);// Player on top
                } else {
                    gridPane.add(cellNode, j, i); //add the symbol to each X and Y value, then to the gridPane
                }
            }
        }
        updateStatusBar(); //update statusBar after every movement
    }

    //Create each cell by extracting the getSymbol from every class and replacing with image
    private Node createEachCell(Cell cell) {
        if (cell != null && cell.getEntity() != null) {
            String symbol = cell.getEntity().getSymbol();

            //Map each letter to the file path of the png
            Map<String, String> imageMap = Map.of(
                    "T", "/dungeon_trap_icon.png",
                    "E", "/dungeon_entrance_icon.png",
                    "#", "/dungeon_wall_icon.png",
                    "G", "/dungeon_gold_icon.png",
                    "M", "/dungeon_meleemutant_icon.png",
                    "H", "/dungeon_healthpotion_icon.png",
                    "R", "/dungeon_rangedmutant_icon.png",
                    "L", "/dungeon_ladder_icon.png"
            );

            if (imageMap.containsKey(symbol)) {
                return createImageView(imageMap.get(symbol));
            }
            //In-case there is no image for the entities provided, print the symbol
            return createLabel(symbol);
        }
        //instead of returning a null we will return an empty cell
        return createLabel(" ");
    }

    private ImageView createImageView(String imagePath) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(75);
        imageView.setFitHeight(75);
        return imageView;
    }

    //all labels will have this border and size, sizes the boxes (even nulls)
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 20; -fx-alignment: center; -fx-border-color: #708090;");
        label.setMinSize(75, 75);
        return label;
    }

    //check if the user has triggered any Endgame booleans, if they have, disable movement buttons
    private boolean checkEndGame() {
        String endGame = engine.getPlayer().endGameMessage();

        if (endGame != null) {
            textArea.setText(endGame);
            disableMovementButtons(true);
            if (engine.getPlayer().isFinished()) {
                ScoreManager scoreManager = new ScoreManager();
                ScoreRecord record = scoreManager.addScore(engine.getPlayer().getScore());
                textArea.setText(scoreManager.controllerTopScore(record, 5));
            }
            return true;
        }
        return false;
    }

    //disable the selected buttons when triggered [Endgame Setup]
    private void disableMovementButtons(boolean state) {
        upButton.setDisable(state);
        downButton.setDisable(state);
        leftButton.setDisable(state);
        rightButton.setDisable(state);
        saveButton.setDisable(state);
        loadButton.setDisable(state);
    }

    //method using ALERT to create pop-ups on the screen. Help information describing the game.
    @FXML
    private void helpScreen() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("MiniDungeon Information");
        alert.setHeaderText("How to play MiniDungeon?");
        alert.setContentText(
                """
                        Objective:
                        - You have been trapped in the Dungeon! Find your way out and find the ladder to escape!
                        
                        Controls:
                        - Use Up, Down, Left, Right buttons on the GUI to move your player in the desired direction.
                        
                        Rules:
                        - Avoid mutants. There are two kinds of mutants, melee and ranged.
                         Melee mutants fight back when you \
                        arrive on their tile and you lose 2HP.
                         Ranged mutants shoot from a distance of two blocks \
                        and you lose 2HP
                        - There are gold bars scattered around the dungeon. Pick up the gold to increase your score [+2 Score].
                        - Watch your health! Pick up potions to recover health. [Health +4].
                        You have a limited number of steps before you get tired and pass out! [Max = 100 steps]
                        
                        The game ends when:
                        - Your health reaches 0
                        - You run out of steps
                        - You reach the final ladder"""
        );
        alert.showAndWait();
    }

    @FXML
    private void saveGame() {
        try {
            engine.saveGame(new File(SAVE_FILE));
            textArea.setText("You have saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            textArea.setText("Unable to Save. Something went wrong.");
        }
    }

    @FXML
    private void loadGame() {
        try {
            engine = GameEngine.loadGame(new File(SAVE_FILE));
            disableMovementButtons(false);
            updateGui();
            textArea.setText("Game loaded successfully from previous save.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            textArea.setText("Unable to Load. Something went wrong.");
        }
    }
}
