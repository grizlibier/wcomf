package Screen;

import FactoryAndStats.Creature;
import FactoryAndStats.Skill;

public class UseSkillScreen extends TargetBasedScreen{
	private Skill skill;
	
	public UseSkillScreen(Creature player, String caption, int sx, int sy, Skill skill) {
		super(player, caption, sx, sy);
		this.skill = skill;
	}
	
	public void selectWorldCoordinate(int x, int y, int screenX, int screenY){
		player.useSkill(skill, x, y);
	}
}