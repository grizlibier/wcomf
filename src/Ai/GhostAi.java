package Ai;

import FactoryAndStats.Creature;

public class GhostAi extends CreatureAi {

	private Creature player;
	
	public GhostAi(Creature creature, Creature player) {
		super(creature);
        this.player = player;
	}

	public void onUpdate(){
		hunt(player);
	}
}
