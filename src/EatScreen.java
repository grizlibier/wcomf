package ascii_rl;

public class EatScreen extends InventoryBasedScreen {

    public EatScreen(Creature player) {
        super(player);
    }

    protected String getVerb() {
        return "eat";
    }

    protected boolean isAcceptable(Item item) {
        return item.foodValue() != 0 && item.foodValue() > item.thirstValue();
    }

    protected screen use(Item item) {
        player.eat(item);
        return null;
    }
}