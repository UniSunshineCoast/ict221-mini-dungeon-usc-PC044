package dungeon.engine;

public class Player {
    private int x;
    private int y;
    private int hp = 10;
    private static final int maxHp = 10;
    private int score = 0;
    private int steps = 0;
    private static final int maxSteps = 100;
    private boolean finished;
    private boolean advanceToNextLevel;

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public void moveTo(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        steps++;
    }

    // Getters, Setters

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void changeHP(int delta) {
        hp = Math.min(10, hp + delta);
    }

    public void addScore(int delta) {
        score += delta;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public void incrementSteps() {
        steps++;
    }

    public int getSteps() {
        return steps;
    }

    public boolean isOutOfSteps() {
        return steps >= maxSteps;
    }

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

    public int getHealth() {
        return hp;
    }

    public int getMaxHealth() {
        return maxHp;
    }

    public int getScore() {
        return score;
    }
}
