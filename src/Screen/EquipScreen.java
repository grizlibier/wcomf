package Screen;
import FactoryAndStats.Creature;
import FactoryAndStats.Item;

public class EquipScreen extends InventoryBasedScreen {

	  public EquipScreen(Creature player) {
	    super(player);
	  }

	  protected String getVerb() {
	    return "wear or wield";
	  }

	  protected boolean isAcceptable(Item item) {
	    return item.isArmor() || item.isWeapon() || item.isShield() || item.isRanged();
	  }

	  protected Screen use(Item item) {
	    player.equip(item);
        player.world.update();
	    return null;
	  }
	}