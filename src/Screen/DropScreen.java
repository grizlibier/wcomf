package Screen;
import FactoryAndStats.Creature;
import FactoryAndStats.Item;

public class DropScreen extends InventoryBasedScreen {

    public DropScreen(Creature player) {
        super(player);
    }
    
    protected String getVerb() {
        return "drop";
    }
    
    protected boolean isAcceptable(Item item) {
        return true;
    }
    
    protected screen use(Item item) {
        player.drop(item);
        player.world.update();
        return null;
    }
}