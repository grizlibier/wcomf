package ascii_rl;

public class CrazedArcherAi extends CreatureAi {
    private Creature player;

    public CrazedArcherAi(Creature creature, Creature player) {
        super(creature);
        this.player = player;
    }

    public void onUpdate(){
        if (canRangedWeaponAttack(player)) {
            creature.rangedWeaponAttack(player);
        } else if (creature.canSee(player.x, player.y, player.z)) {
            hunt(player);
        } else {
            wander();
        }
    }
    
    private boolean canRangedWeaponAttack(Creature other){
        return creature.weapon() != null
                && creature.weapon().rangedAttackValue() > 0
                && creature.canSee(other.x, other.y, other.z);
    }
}
