package ascii_rl;

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

    protected screen use(Item item) {
        player.eat(item);
        return null;
    }
}