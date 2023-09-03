package Ai;
import FactoryAndStats.Creature;

public class AiCommon extends CreatureAi {
	
	private Creature player;
	
	public AiCommon(Creature creature, Creature player) {
        super(creature);
        this.player = player;
    }

	public void onUpdate(){
		creature.somethingOnTheGround();
		if (creature.canSee(player.x, player.y, player.z))
	          hunt(player);
	    else
	          wander();
	}
}