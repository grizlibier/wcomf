package Screen;
import FactoryAndStats.Creature;
import FactoryAndStats.Item;
import FactoryAndStats.Spell;

public class ZapScreen extends InventoryBasedScreen {

    private int sx;
    private int sy;
    
    public ZapScreen(Creature player, int sx, int sy) {
        super(player);
        this.sx = sx;
        this.sy = sy;
    }
    
    public ZapScreen(Creature player) {
        super(player);
    }

    protected String getVerb() {
        return "zap";
    }

    protected boolean isAcceptable(Item item) {
        return item.isZappable;
    }

    protected screen use(Item item) {
    	Spell spell = item.writtenSpells().get(0);
		player.eat(item);
		return new CastSpellScreen(player, "", sx, sy, spell);
    }
}