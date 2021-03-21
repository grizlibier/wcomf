package ascii_rl;

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

    protected screen use(Item item) {
        player.eat(item);
        return null;
    }
}