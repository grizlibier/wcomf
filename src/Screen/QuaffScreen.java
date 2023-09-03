package Screen;
import FactoryAndStats.Creature;
import FactoryAndStats.Item;

public class QuaffScreen extends InventoryBasedScreen {

    public QuaffScreen(Creature player) {
            super(player);
    }

    protected String getVerb() {
            return "quaff";
    }

    protected boolean isAcceptable(Item item) {
            return item.isDrink;
    }

    protected Screen use(Item item) {
            player.eat(item);
            player.world.update();
            return null;
    }
}