package dungeon.engine;

public class HealthPotion implements MapEntity {

    @Override
    public String getSymbol() {
        return "H";
    }

    @Override
    public String interact(Player player) {
        player.changeHP(+4);
        return "You picked up a health potion and restored 4 HP.";
    }

    @Override
    public boolean isBlocking() {
        return false;
    }
}
