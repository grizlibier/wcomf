package ascii_rl;

public class ReadScreen extends InventoryBasedScreen {

    private int sx;
    private int sy;
    
    public ReadScreen(Creature player, int sx, int sy) {
        super(player);
        this.sx = sx;
        this.sy = sy;
    }

    protected String getVerb() {
        return "read";
    }

    protected boolean isAcceptable(Item item) {
        return !item.writtenSpells().isEmpty() || item.isAScroll();
    }

    protected screen use(Item item) {
    	if (item.isAScroll()) {
    		Spell spell = item.writtenSpells().get(0);
    		player.eat(item);
    		return new CastSpellScreen(player, "", sx, sy, spell);
    	} else
    		return new ReadSpellScreen(player, sx, sy, item);
    }
}