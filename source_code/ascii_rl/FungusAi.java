package ascii_rl;

public class FungusAi extends CreatureAi {
	private StuffFactory factory;
	private int spreadcount;
	private Creature player;
	
	public FungusAi(Creature creature, StuffFactory factory, Creature player) {
		super(creature);
		this.factory = factory;
		this.player = player;
	}

	public void onUpdate(){
	    if (creature.canSee(player.x, player.y, player.z)) {
	    	if (spreadcount < 2 && Math.random() < 0.10)
				spread();
	    	hunt(player);
	    } else
	    	spread();
	}
	
	private void spread(){
		int x = creature.x + (int)(Math.random() * 11) - 5;
		int y = creature.y + (int)(Math.random() * 11) - 5;
		
		if (!creature.canEnter(x, y, creature.z))
			return;
		
		creature.doAction("spawn a child");
		
		Creature child = factory.newFungus(creature.z, player);
		child.x = x;
		child.y = y;
		child.z = creature.z;
		spreadcount++;
	}
}