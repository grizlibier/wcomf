package ascii_rl;

import java.util.ArrayList;
import java.util.List;

public class LevelUpController {

	private static LevelUpOption[] options = new LevelUpOption[]{
		new LevelUpOption("Train strength"){
			public void invoke(Creature creature) { 
				creature.gainMaxHp(10);  
				creature.gainAttackValue(2);  
				creature.gainDefenseValue(1); 
				creature.notify("You do some physical training to get stronger!");
			}
		},
		new LevelUpOption("Train endurance"){
			public void invoke(Creature creature) { 
				creature.gainDefenseValue(5); 
				creature.gainMaxHp(5); 
				creature.notify("You learn some defencive moves and faints!");
			}
		},
		new LevelUpOption("Train agility"){
			public void invoke(Creature creature) { 
				creature.gainDefenseValue(1); 
				creature.gainAttackValue(5); 
				creature.notify("You train to hit where it hurts!");
			}
		},
		new LevelUpOption("Meditate"){
			public void invoke(Creature creature) { 
				if (creature.visionRadius() != 10)
					creature.gainVision(1); 
				creature.gainMaxMana(10); 
				creature.gainRegenMana(5);
				creature.notify("You spend some time meditating.");
			}
		}
	};
  
  	public void autoLevelUp(Creature creature){
  		options[(int)(Math.random() * options.length)].invoke(creature);
  	}
  	
  	public List<String> getLevelUpOptions(){
		List<String> names = new ArrayList<String>();
		for (LevelUpOption option : options){
			names.add(option.name());
		}
		return names;
	}
	
	public LevelUpOption getLevelUpOption(String name){
		for (LevelUpOption option : options){
			if (option.name().equals(name))
				return option;
		}
		return null;
	}
}