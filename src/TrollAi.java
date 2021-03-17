package ascii_rl;

public class TrollAi extends CreatureAi {
	
	private Creature player;
	
	public TrollAi(Creature creature, Creature player) {
        super(creature);
        this.player = player;
    }

	public void onUpdate(){
		if (Math.random() > 0.4) {
	      if (creature.canSee(player.x, player.y, player.z))
	          hunt(player);
	      else {
	    	  wander();
	      }
		}
	}
}