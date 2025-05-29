package dungeon.engine;

public class Player {
    private int x; //Player Coordinates
    private int y;
    private int hp = 10;
    private static final int maxHp = 10; //End-game setup (Death)
    private int score = 0;
    private int steps = 0;
    private static final int maxSteps = 100; //End-game setup (Out of Steps)
    private boolean finished;
    private boolean advanceToNextLevel;
    private String causeOfDeath;

    //Set Player with initial position (X,Y) coordinates
    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    //Move Player upon request with new coordinates
    public void moveTo(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public String endGameMessage() {
        if (isFinished()) {
            return "You found the way out of the Dungeon! You run off with all the gold you stole.";
        } else if (isDead()) {
            return "You bled to death. This is Game Over.\nYou were killed by " + causeOfDeath + "!";
        } else if (isOutOfSteps()) {
            return "You've become too tired and collapsed. You took too many steps. Game Over.";
        }
        return null;
    }

    public void setCauseOfDeath(String cause) {
        this.causeOfDeath = cause;
    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    //Player changes HP based on event - MAX HP gain = 10
    public void changeHP(int delta) {
        hp = Math.min(10, hp + delta);
    }

    //Player earned score based on event
    public void addScore(int scoreEarned) {
        score += scoreEarned;
    }

    // Common Getter & Setter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //Player is 'Dead' at 0 HP - Returns True
    public boolean isDead() {
        return hp <= 0;
    }

    public void incrementSteps() {
        steps++;
    }

    public int getSteps() {
        return steps;
    }

    public int getHealth() {
        return hp;
    }

    public int getMaxHealth() {
        return maxHp;
    }

    public int getScore() {
        return score;
    }

    //Returns True if steps >= 100
    public boolean isOutOfSteps() {
        return steps >= maxSteps;
    }

    //For resetting boolean purposes

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setAdvanceToNextLevel(boolean advanceToNextLevel) {
        this.advanceToNextLevel = advanceToNextLevel;
    }

    public boolean isAdvanceToNextLevel() {
        return advanceToNextLevel;
    }
}
