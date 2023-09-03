package Ai;
import FactoryAndStats.Creature;

public class BonesAi extends CreatureAi{
	
	private Creature player;
	
	public BonesAi(Creature creature, Creature player) {
        super(creature);
        this.player = player;
    }

	public void onUpdate(){
		creature.somethingOnTheGround();
		if (Math.random() > 0.5) {
		      if (creature.canSee(player.x, player.y, player.z))
		          hunt(player);
		      else
		          wander();
		}
	}
}