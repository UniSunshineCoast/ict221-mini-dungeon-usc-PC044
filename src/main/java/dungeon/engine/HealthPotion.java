package dungeon.engine;

public class HealthPotion implements MapEntity {

    @Override
    public String getSymbol() {
        return "H";
    }

    //Health Potions = +4 health points
    @Override
    public String interaction(Player player) {
        player.changeHP(+4);
        return "You pick up a health potion and drink it. \n[HP restored by 4]";
    }

    @Override
    public boolean playerBlocking() {
        return false;
    }
}
