package ascii_rl;

public class HoundAi extends CreatureAi {
	
	private Creature player;
	
	public HoundAi(Creature creature, Creature player) {
        super(creature);
        this.player = player;
    }

	public void onUpdate(){
	      if (creature.canSee(player.x, player.y, player.z)) {
	          hunt(player);
	          hunt(player);
	      } else {
	          wander();
	          wander();
	      }
	  }
}