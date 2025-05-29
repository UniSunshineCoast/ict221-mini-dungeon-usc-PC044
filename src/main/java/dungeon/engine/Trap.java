package dungeon.engine;

public class Trap implements MapEntity {

    @Override
    public String getSymbol() {
        return "T";
    }

    //Spike Trap = -2 HP
    @Override
    public String interaction(Player player) {
        player.changeHP(-2);
        player.setCauseOfDeath("Spike Trap");
        return  "A spike trap activated under your feet. \n[HP - 2]";
    }

    @Override
    public boolean playerBlocking() {
        return false;
    }
}
