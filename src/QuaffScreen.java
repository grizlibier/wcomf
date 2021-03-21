package ascii_rl;

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

    protected screen use(Item item) {
            player.quaff(item);
            return null;
    }
}