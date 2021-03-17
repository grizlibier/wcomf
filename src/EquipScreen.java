package ascii_rl;

public class EquipScreen extends InventoryBasedScreen {

	  public EquipScreen(Creature player) {
	    super(player);
	  }

	  protected String getVerb() {
	    return "wear or wield";
	  }

	  protected boolean isAcceptable(Item item) {
	    return item.attackValue() > 0 || item.defenseValue() > 0 || item.visionRadius() > 0;
	  }

	  protected screen use(Item item) {
	    player.equip(item);
	    return null;
	  }
	}