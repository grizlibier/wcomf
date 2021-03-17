package ascii_rl;

public class DemonAi extends CreatureAi {
	
	private Creature player;
	
	public DemonAi(Creature creature, Creature player) {
        super(creature);
        this.player = player;
    }

	public void onUpdate(){
	      if (creature.canSee(player.x, player.y, player.z))
	          hunt(player);
	      else {
	          wander();
	          if (Math.random() > 0.5)
	        	  wander();
	      }
	  }
}