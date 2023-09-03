package Screen;
import FactoryAndStats.Creature;
import FactoryAndStats.Item;

public class ExamineScreen extends InventoryBasedScreen {

    public ExamineScreen(Creature player) {
        super(player);
    }

    protected String getVerb() {
        return "examine";
    }

    protected boolean isAcceptable(Item item) {
        return true;
    }

    protected Screen use(Item item) {
        player.notify(item.details());
        player.notify(item.description());
        player.world.update();
        return null;
    }
}