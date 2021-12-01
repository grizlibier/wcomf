package Ai;

import java.util.List;

import FactoryAndStats.Creature;
import FactoryAndStats.LevelUpController;
import Screen.LoseScreenException;
import Screen.screen;
import WorldBuilder.Line;
import WorldBuilder.Path;
import WorldBuilder.Point;
import WorldBuilder.Tile;

public class CreatureAi {
	protected Creature creature;
	
	public CreatureAi(Creature creature){
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}
	
	public void onEnter(int x, int y, int z, Tile tile){
		if (tile.isGround()){
			creature.x = x;
			creature.y = y;
			creature.z = z;
		} else {
			creature.doAction("bump into a wall");
		}
	}
	
	public void onUpdate(){
	}
	
	public void onNotify(String message){
	}

	public boolean canSee(int wx, int wy, int wz) {
		if (creature.z != wz)
			return false;
		
		if ((creature.x-wx)*(creature.x-wx) + (creature.y-wy)*(creature.y-wy) > creature.visionRadius()*creature.visionRadius())
			return false;
		
		for (Point p : new Line(creature.x, creature.y, wx, wy)){
			if (creature.realTile(p.x, p.y, wz).isGround() || p.x == wx && p.y == wy)
				continue;
			
			return false;
		}
		
		return true;
	}
	
	public void wander(){
		int mx = (int)(Math.random() * 3) - 1;
		int my = (int)(Math.random() * 3) - 1;
		
		Creature other = creature.creature(creature.x + mx, creature.y + my, creature.z);
		
		if (other != null && other.name().equals(creature.name()) 
				|| !creature.tile(creature.x+mx, creature.y+my, creature.z).isGround())
			return;
		else
			creature.moveBy(mx, my, 0);
	}

	public void onGainLevel() {
		new LevelUpController().autoLevelUp(creature);
	}

	public Tile rememberedTile(int wx, int wy, int wz) {
		return Tile.UNKNOWN;
	}
	
	public void hunt(Creature target) {
		List<Point> points = new Path(creature, target.x, target.y).points();
		
		int mx = 0;
		int my = 0;
		
		try {
			mx += points.get(0).x - creature.x;
			my += points.get(0).y - creature.y;
		} catch(Exception e) { }
		
		try {
			creature.moveBy(mx, my, 0);
		} catch (Exception e) {
			exceptionHandling();
		}
	}
	
	public screen exceptionHandling() {
		return new LoseScreenException();
	}
	
	//private boolean canRangedWeaponAttack(Creature other){
    //    return creature.weapon() != null
    //            && creature.weapon().rangedAttackValue() > 0
    //            && creature.canSee(other.x, other.y, other.z);
    //}

    //private boolean canThrowAt(Creature other) {
    //    return creature.canSee(other.x, other.y, other.z)
    //      && getWeaponToThrow() != null;
    //}

    //private Item getWeaponToThrow() {
    //    Item toThrow = null;
    //
    //    for (Item item : creature.inventory().getItems()){
    //    if (item == null || creature.weapon() == item || creature.armor() == item)
    //        continue;
    //    
    //    if (toThrow == null || item.thrownAttackValue() > toThrow.attackValue())
    //        toThrow = item;
    //    }
    //
    //    return toThrow;
    //}

    //private boolean canPickup() {
    //    return creature.item(creature.x, creature.y, creature.z) != null
    //      && !creature.inventory().isFull();
    //}
}