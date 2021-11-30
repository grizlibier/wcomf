package Ai;
import FactoryAndStats.Creature;
import FactoryAndStats.StuffFactory;

public class LichAi extends CreatureAi {
	private StuffFactory factory;
	private int spreadcount;
	private Creature player;
	
	public LichAi(Creature creature, StuffFactory factory, Creature player) {
		super(creature);
		this.factory = factory;
		this.player = player;
	}

	public void onUpdate(){
		creature.somethingOnTheGround();
		if (creature.canSee(player.x, player.y, player.z)) {
	    	if (spreadcount < 4)
				spread();
	    	hunt(player);
	    } else
	    	wander();
	}
	
	private void spread(){
		int x = creature.x + (int)(Math.random() * 11) - 5;
		int y = creature.y + (int)(Math.random() * 11) - 5;
		
		if (!creature.canEnter(x, y, creature.z))
			return;
		
		creature.doAction("spawn a skeleton");
		
		Creature child = factory.newSkeleton(creature.z, player);
		child.x = x;
		child.y = y;
		child.z = creature.z;
		spreadcount++;
	}
}