package Ai;

import java.util.List;

import FactoryAndStats.Creature;
import FactoryAndStats.FieldOfView;
import WorldBuilder.Tile;

public class PlayerAi extends CreatureAi {

	private List<String> messages;
	private FieldOfView fov;
	
	public PlayerAi(Creature creature, List<String> messages, FieldOfView fov) {
		super(creature);
		this.messages = messages;
		this.fov = fov;
		
		if (creature.name() == "Wizard") {
			creature.modifyMaxMana(20);
			creature.modifyMana(20);
		} else {
			creature.modifyMaxMana(10);
			creature.modifyMana(10);
		}
	}
	
	public void onUpdate(){
	    creature.somethingOnTheGround();
	    if (creature.name() == "Monk" ) {
	    	creature.modifyFood(-3);
	    } else {
		    creature.modifyFood(-5);
	    }
	    creature.modifyThirst(-1);
	    creature.modifyMana(1);
	}
	
	public void onGainLevel(){
	  }

	public void onEnter(int x, int y, int z, Tile tile){
		if (tile.isGround()){
			creature.x = x;
			creature.y = y;
			creature.z = z;
		} else if (tile.isDiggable()) {
			creature.dig(x, y, z);
		}
	}
	
	public void onNotify(String message){
		messages.add(message);
	}
	
	public boolean canSee(int wx, int wy, int wz) {
		return fov.isVisible(wx, wy, wz);
	}
	
	public Tile rememberedTile(int wx, int wy, int wz) {
        return fov.tile(wx, wy, wz);
    }
}