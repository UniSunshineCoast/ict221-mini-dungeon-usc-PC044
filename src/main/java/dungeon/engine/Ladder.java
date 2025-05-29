package dungeon.engine;

public class Ladder implements MapEntity {

    //Boolean determines whether the current level is the FINAL level
    private final boolean isFinalLevel;

    public Ladder(boolean isFinalLevel) {
        this.isFinalLevel = isFinalLevel;
    }

    @Override
    public String getSymbol() {
        return "L";
    }

    //Two ladder states; Default: isFinalLevel = False
    //If the player is at the final level and reaches ladder = game end
    @Override
    public String interaction(Player player) {
        if (isFinalLevel) {
            player.setFinished(true);
            //ELSE keep going onto the next level
        } else {
            player.setAdvanceToNextLevel(true);
        }
        return "";
    }

    @Override
    public boolean playerBlocking() {
        return false;
    }
}
