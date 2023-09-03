package Screen;
import FactoryAndStats.Creature;
import FactoryAndStats.Item;

public class EatScreen extends InventoryBasedScreen {

    public EatScreen(Creature player) {
        super(player);
    }

    protected String getVerb() {
        return "eat";
    }

    protected boolean isAcceptable(Item item) {
        return item.isFood;
    }

    protected Screen use(Item item) {
        player.eat(item);
        player.world.update();
        return null;
    }
}