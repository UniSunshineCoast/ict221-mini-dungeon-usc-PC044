package dungeon.engine;

import javafx.scene.text.Text;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class GameEngine implements Serializable {

    private transient Cell[][] map; //transient property means it won't be serialized
    private CellData[][] mapData; //all data is placed here for saving and loading
    private final Player player;
    private int currentLevel = 1;
    private static final int FINAL_LEVEL = 2; //The game features 2 levels

    //Creates a square game board. @param size the width and height.
    public GameEngine(int size) {
        map = new Cell[size][size];
        mapData = new CellData[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                CellData data = new CellData();
                Cell cell = new Cell(data);
                Text text = new Text(i + "," + j);
                cell.getChildren().add(text);
                mapData[i][j] = data;
                map[i][j] = cell;
            }
        }
        map[0][0].setStyle("-fx-background-color: #7baaa4");
        map[size-1][size-1].setStyle("-fx-background-color: #7baaa4");
        player = new Player(0, map.length - 1); // bottom-left corner
    }

    //Map of the current game. Returns 2d array.
    public Cell[][] getMap() {
        return map;
    }

    //Moves the player based on string input (DIRECTIONS: up = u, down = d, left = l, right = r)
    public String movePlayer(String direction) {
        int xDir = 0, yDir = 0; //starting values

        switch (direction.toLowerCase()) {
            case "u": yDir = -1; break; //Up
            case "d": yDir = 1; break; //Down
            case "l": xDir = -1; break; //Left
            case "r": xDir = 1; break; //Right
            default: return "Invalid move command.";
        }

        //new player coordinates
        int newX = player.getX() + xDir;
        int newY = player.getY() + yDir;

        //if X or Y coordinates is larger or smaller than the map size, that is out of bounds
        if (newX < 0 || newX >= map.length || newY < 0 || newY >= map.length) {
            return "You tried to move outside the map!";
        }

        Cell target = map[newY][newX]; //Identify the target the player is moving to

        if (target.isBlocking()) {
            MapEntity entity = target.getEntity();
            return entity.interaction(player);
        }

        player.moveTo(newX, newY);
        player.incrementSteps();
        String message = target.interact(player); //take the interaction message before the entity is deleted
        String rangedMutantAttacks = checkRangedMutantAttacks(); //store attacks in string

        if (player.isAdvanceToNextLevel()) {
            currentLevel++; //go up one level
            createNextMap(currentLevel * 2); //need to change difficulty by +2 on new map
            player.setAdvanceToNextLevel(false); // reset since player has reached last level
            return "You found a ladder and advanced to level " + currentLevel + "!";
        }

        // Each movement corresponds to a string describing direction
        return displayPlayerResult(direction, message, rangedMutantAttacks);
    }

    private static String displayPlayerResult(String direction, String message, String rangedMutantAttacks) {
        String movementDirection = switch (direction.toLowerCase()) {
            case "u" -> "up";
            case "d" -> "down";
            case "l" -> "left";
            case "r" -> "right";
            default -> "unknown direction";
        };

        //display all interactions after each move
        String result = "[You moved " + movementDirection + " one step.]\n" + "----------------------\n" + message;

        if (!message.isEmpty() && !rangedMutantAttacks.isEmpty()) {
            result += "\n";  // Add newline only if both have content
        }

        result += rangedMutantAttacks;
        return result;
    }

    //method for calculating all ranged mutants on map if in range of player
    private String checkRangedMutantAttacks() {
        StringBuilder attackMessages = new StringBuilder();

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                MapEntity entity = map[y][x].getEntity();
                if (entity instanceof RangedMutant) {
                    String attackResult = ((RangedMutant) entity).tryAttack(player, x, y, player.getX(), player.getY());
                    if (!attackResult.isEmpty()) {
                        attackMessages.append(attackResult).append("\n");
                    }
                }
            }
        }

        return attackMessages.toString();
    }

    //populate the map with selected entities (based on difficulty)
    public void populateMap(int difficulty) {
        boolean isFinalLevel = getCurrentLevel() == FINAL_LEVEL;
        placeEntity(new Entry(), 0, map.length - 1); // Bottom-left corner

        placeWalls();

        //places ladder based on current level (final ladder = end game)
        placeRandomEntities(new Ladder(isFinalLevel), 1);
        placeRandomEntities(new Gold(), 5);
        placeRandomEntities(new Trap(), 5);
        placeRandomEntities(new MeleeMutant(), 3);
        placeRandomEntities(new RangedMutant(), difficulty); // based on difficulty
        placeRandomEntities(new HealthPotion(), 2);
    }

    //place entity at exact cell value
    private void placeEntity(MapEntity entity, int x, int y) {
        mapData[y][x].setEntity(entity); //save to a serializable map
        map[y][x].setEntity(entity);
    }

    //place entity at randomised values
    private void placeRandomEntities(MapEntity entity, int count) {
        Random rand = new Random();
        int size = map.length;

        while (count > 0) {
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);

            if (map[y][x].getEntity() == null && !(x == 0 && y == size - 1)) { //NO overlapping
                mapData[y][x].setEntity(entity);
                map[y][x].setEntity(entity);
                count--;
            }
        }
    }

    //pre-defined walls for each level
    private void placeWalls() {
        if (currentLevel == 1) {
            int[][] level1Walls = {
                    {1, 1}, {1, 2}, {1, 3}, {0, 8}, {1, 8},
                    {2, 8}, {2, 7}, {4, 4}, {4, 5}, {7, 2},
                    {7, 3}, {7, 4}, {7, 5}, {7, 6}, {7, 7}
            };
            for (int[] coordinate : level1Walls) {
                placeEntity(new Wall(), coordinate[0], coordinate[1]);
            }
        } else if (currentLevel == 2) {
            int[][] level2Walls = {
                    {2, 4}, {3, 4}, {4, 4}, {5, 4}, {5, 7},
                    {6, 7}, {7, 7}, {1, 7}, {2, 7}, {8, 1},
                    {7, 1}, {8, 2}, {8, 5}, {1, 1}, {1, 2}
            };
            for (int[] coordinate : level2Walls) {
                placeEntity(new Wall(), coordinate[0], coordinate[1]);
            }
        }
    }

    //new map for ladder trigger event
    public void createNextMap(int difficulty) {
        map = new Cell[getSize()][getSize()];
        mapData = new CellData[getSize()][getSize()];

        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                CellData data = new CellData();
                mapData[i][j] = data;

                map[i][j] = new Cell(data);
            }
        }
        populateMap(difficulty);
        player.moveTo(0, map.length - 1 ); // reset player position if needed
    }

    //visualize 2d map with entity symbols
    public String renderMap() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (player.getX() == x && player.getY() == y) {
                    sb.append("P "); // Player symbol
                } else {
                    MapEntity entity = map[y][x].getEntity();
                    sb.append(entity != null ? entity.getSymbol() + " " : ". ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    //status bar of HP + Score
    public String getStatusBar() {
        int health = player.getHealth();
        int maxHealth = player.getMaxHealth();
        int score = player.getScore();
        StringBuilder status = new StringBuilder("Health: [");

        for (int i = 0; i < maxHealth; i++) {
            if (i < health) {
                status.append("*");  // filled heart for current health
            } else {
                status.append(" ");  // space for missing health
            }
        }

        status.append("] ").append(health).append("/").append(maxHealth).append(" | Score: ").append(score);
        return status.toString();
    }

    //save the game (used in gui.controller)
    public void saveGame(File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this); 
        }
    }

    //load game from selected file (used in gui.controller)
    public static GameEngine loadGame(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            GameEngine engine = (GameEngine) ois.readObject();
            engine.loadMapFromData(); // Rebuild Map from saved data during engine process
            return engine;
        }
    }

    @Serial
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();  // deserialize non-transient fields like mapData

        // Now rebuild the transient map from mapData
        loadMapFromData();
    }

    //Recreate the whole map from the data saved in mapData
    private void loadMapFromData() {
        int size = mapData.length;
        map = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = new Cell(mapData[i][j]);
            }
        }
    }

    //Getter & Setter
    //Size of the current game. @return is both width and height.
    public int getSize() {
        return map.length;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    //not used since we increment level by 1 instead of setting
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Player getPlayer() {
        return player;
    }

    //MINI DUNGEON GAME [MAIN]
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose your difficulty (1–10), default is 3: ");
        String difficultyInput = scanner.nextLine().trim();
        int difficulty;

        //Catch any invalid inputs and default to a standard (3)
        try {
            difficulty = Integer.parseInt(difficultyInput);
            if (difficulty < 1 || difficulty > 10) {
                System.out.println("Invalid difficulty. Defaulting to 3.");
                difficulty = 3;
            }
        } catch (NumberFormatException e) {
            System.out.println("No valid input. Defaulting to 3.");
            difficulty = 3;
        }

        GameEngine engine = new GameEngine(10);
        engine.populateMap(difficulty); // User input difficulty

        System.out.printf("The size of map is %d * %d\n", engine.getSize(), engine.getSize());
        System.out.println(engine.player.startGameMessage());
        while (true) {
            System.out.println("\nCurrent Map:");
            System.out.println(engine.renderMap());
            System.out.println(engine.getStatusBar());

            System.out.print("Enter move (u/d/l/r): ");
            String movement = scanner.nextLine().trim().toLowerCase();

            if (movement.equalsIgnoreCase("quit")) {
                System.out.println("Thanks for playing MiniDungeon");
                break;
            }

            String result = engine.movePlayer(movement);
            System.out.println(result);

            String endGame = engine.getPlayer().endGameMessage();

            if (endGame != null) {
                System.out.println(endGame);
                if (engine.getPlayer().isFinished()) {
                    ScoreManager scoreManager = new ScoreManager();
                    scoreManager.addScore(engine.getPlayer().getScore());
                    break;
                }
                break;
            }
        }

    }
}
