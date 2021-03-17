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
        player.notify("It's " + item.name() + "." + item.details());
        return null;
    }
}