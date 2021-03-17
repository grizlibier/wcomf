package ascii_rl;

public class QuaffScreen extends InventoryBasedScreen {

    public QuaffScreen(Creature player) {
            super(player);
    }

    protected String getVerb() {
            return "quaff";
    }

    protected boolean isAcceptable(Item item) {
            return item.thirstValue() != 0 || item.quaffEffect() != null && item.thirstValue() != 0;
    }

    protected screen use(Item item) {
            player.quaff(item);
            return null;
    }
}