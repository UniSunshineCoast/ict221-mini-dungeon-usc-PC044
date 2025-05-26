package dungeon.engine;

public class Ladder implements MapEntity {

    private final boolean isFinalLevel;

    public Ladder(boolean isFinalLevel) {
        this.isFinalLevel = isFinalLevel;
    }

    @Override
    public String getSymbol() {
        return "L";
    }

    @Override
    public String interact(Player player) {
        if (isFinalLevel) {
            player.setFinished(true);
            return "";
        } else {
            player.setAdvanceToNextLevel(true);
            return "You found a ladder! Advancing to the next level...";
        }
    }

    @Override
    public boolean isBlocking() {
        return false;
    }
}
