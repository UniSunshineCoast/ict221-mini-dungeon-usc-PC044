package dungeon.engine;

import javafx.scene.text.Text;
import java.util.Random;
import java.util.Scanner;

public class GameEngine {

    /**
     * An example board to store the current game state.
     *
     * Note: depending on your game, you might want to change this from 'int' to String or something?
     */
    private Cell[][] map;
    private Player player;
    private int currentLevel = 1;
    private static final int FINAL_LEVEL = 2;


    /**
     * Creates a square game board.
     *
     * @param size the width and height.
     */
    public GameEngine(int size) {
        map = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = new Cell();
                Text text = new Text(i + "," + j);
                cell.getChildren().add(text);
                map[i][j] = cell;
            }
        }

        map[0][0].setStyle("-fx-background-color: #7baaa4");
        map[size-1][size-1].setStyle("-fx-background-color: #7baaa4");
        player = new Player(0, map.length - 1); // bottom-left corner
    }

    /**
     * The size of the current game.
     *
     * @return this is both the width and the height.
     */
    public int getSize() {
        return map.length;
    }

    /**
     * The map of the current game.
     *
     * @return the map, which is a 2d array.
     */
    public Cell[][] getMap() {
        return map;
    }

    /**
     * Plays a text-based game
     */

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public String movePlayer(String direction) {
        int dx = 0, dy = 0;

        switch (direction.toLowerCase()) {
            case "u": dy = -1; break;
            case "d": dy = 1; break;
            case "l": dx = -1; break;
            case "r": dx = 1; break;
            default: return "Invalid move command.";
        }

        int newX = player.getX() + dx;
        int newY = player.getY() + dy;

        if (newX < 0 || newX >= map.length || newY < 0 || newY >= map.length) {
            return "You tried to move outside the map!";
        }

        Cell target = map[newY][newX]; // note: y is row, x is col

        if (target.isBlocking()) {
            return "You tried to move but it is a wall.";
        }

        player.moveTo(newX, newY);
        player.incrementSteps();
        String message = target.interact(player);
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

        if (player.isAdvanceToNextLevel()) {
            currentLevel++;
            generateNewMap(currentLevel * 2); //need to change difficulty by +2 on new map
            player.setAdvanceToNextLevel(false); // reset the flag
            return "You found a ladder and advanced to level " + currentLevel + "!";
        }

        return "\nYou moved to " + newX + "," + newY + ".\n" + message + "\n" + attackMessages;
    }

    private void placeEntity(MapEntity entity, int x, int y) {
        map[y][x].setEntity(entity);
    }

    private void placeRandomEntities(MapEntity entity, int count) {
        Random rand = new Random();
        int size = map.length;

        while (count > 0) {
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);

            if (map[y][x].getEntity() == null && !(x == 0 && y == size - 1)) {
                map[y][x].setEntity(entity);
                count--;
            }
        }
    }

    private void populateMap(int difficulty) {
        boolean isFinalLevel = currentLevel == FINAL_LEVEL;
        placeEntity(new Entry(), 0, map.length - 1); // Bottom-left corner

        placeRandomEntities(new Ladder(isFinalLevel), 1);
        placeRandomEntities(new Gold(), 5);
        placeRandomEntities(new Trap(), 5);
        placeRandomEntities(new MeleeMutant(), 3);
        placeRandomEntities(new RangedMutant(), difficulty); // based on difficulty
        placeRandomEntities(new HealthPotion(), 2);
    }

    public void generateNewMap(int difficulty) {
        map = new Cell[getSize()][getSize()];
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                map[i][j] = new Cell();
            }
        }
        populateMap(difficulty);
        player.moveTo(0, map.length - 1 ); // reset player position if needed
    }

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

        status.append("] " + health + "/" + maxHealth + " | Score: ").append(score);
        return status.toString();
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose your difficulty (1–10), default is 3: ");
        String difficultyInput = scanner.nextLine().trim();
        int difficulty;

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
        engine.populateMap(difficulty); // Initial difficulty level

        System.out.printf("The size of map is %d * %d\n", engine.getSize(), engine.getSize());

        System.out.println("Welcome to MiniDungeon!");
        while (true) {
            System.out.println("\nCurrent Map:");
            System.out.println(engine.renderMap());
            System.out.println(engine.getStatusBar());

            System.out.print("Enter move (u/d/l/r): ");
            String movement = scanner.nextLine().trim().toLowerCase();

            if (movement.equalsIgnoreCase("quit")) {
                System.out.println("Thanks for playing!");
                break;
            }

            String result = engine.movePlayer(movement);
            System.out.println(result);

            if (engine.getPlayer().isFinished()) {
                System.out.println("You reached the final ladder and won!");
                ScoreManager scoreManager = new ScoreManager();
                scoreManager.addScore(engine.getPlayer().getScore());
                break;
            } else if (engine.getPlayer().isDead()) {
                System.out.println("You died! Game over.");
                break;
            } else if (engine.getPlayer().isOutOfSteps()) {
                System.out.println("You've run out of steps! Game over.");
                break;
            }
        }

    }
}
