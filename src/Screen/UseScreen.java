package Screen;
import FactoryAndStats.Creature;
import FactoryAndStats.Item;

public class UseScreen extends InventoryBasedScreen {

    public UseScreen(Creature player) {
        super(player);
    }

    protected String getVerb() {
        return "use";
    }

    protected boolean isAcceptable(Item item) {
        return item.isAUsable;
    }

    protected Screen use(Item item) {
        player.eat(item);
        player.world.update();
        return null;
    }
}