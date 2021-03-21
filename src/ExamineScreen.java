package ascii_rl;

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

    protected screen use(Item item) {
        player.notify(item.details());
        player.notify(item.description());
        return null;
    }
}