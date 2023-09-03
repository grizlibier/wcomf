package Screen;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import FactoryAndStats.Creature;
import FactoryAndStats.Skill;
import asciiPanel.AsciiPanel;

public class SkillScreen implements Screen{
	
	protected Creature player;
    protected String letters;
    private int sx, sy;
    
    public SkillScreen(Creature player, int sx, int sy) {
        this.player = player;
        this.letters = "abcdefghijklmnopqrstuvwxyz";
        this.sx = sx;
        this.sy = sy;
    }

	@Override
	public void displayOutput(AsciiPanel terminal) {
        ArrayList<String> lines = getList();
    
        int y = 23 + lines.size();
        int x = 4;

        if (lines.size() > 0)
            terminal.clear(' ', x, y, 20, lines.size());
    
        for (String line : lines){
            terminal.write(line, x, y++);
        }
    
        terminal.clear(' ', 0, 23, 80, 1);
        terminal.write("What skill would you like to use?", 2, 23);
    
        terminal.repaint();
	}
	
	private ArrayList<String> getList() {
        ArrayList<String> lines = new ArrayList<String>();
        Skill[] skillsKnown = player.skills().getSkills();
    
        for (int i = 0; i < skillsKnown.length; i++){
            Skill skill = skillsKnown[i];
        
            if (skill == null)
                continue;
        
            String line = letters.charAt(i) + " - " + skill.name() + " " + skill.cooldown();
            
            lines.add(line);
        }
        return lines;
    }

	@Override
	public Screen respondToUserInput(KeyEvent key) {
        char c = key.getKeyChar();

        Skill[] skillsKnown = player.skills().getSkills();
    
        if (letters.indexOf(c) > -1 && skillsKnown.length > letters.indexOf(c) && skillsKnown[letters.indexOf(c)] != null)
            return use(skillsKnown[letters.indexOf(c)]);
        else if (key.getKeyCode() == KeyEvent.VK_ESCAPE)
            return null;
        else
            return this;
    }
	
	protected Screen use(Skill skill) {
		Screen toReturn = null;
		
		switch (skill.effect().getName()) {
			case "SkillSelfcast": player.useSkill(skill, player.x, player.y); player.world.update();break;
			default: toReturn = new UseSkillScreen(player, "", sx, sy, skill); break;
		}
		
		return toReturn;
    }

}
