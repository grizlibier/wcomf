package FactoryAndStats;

public class CurseOfTimeController {
	private static CurseOfTimeOption[] options = new CurseOfTimeOption[]{
		new CurseOfTimeOption("Lose strength"){
			public void invoke(Creature creature) { 
                creature.gainMaxHp(-5);  
                creature.gainAttackValue(-1);  
                creature.gainDefenseValue(-2);
                creature.notify("You feel your muscles weaken!");
            }
		},
		new CurseOfTimeOption("Lose endurance"){
			public void invoke(Creature creature) { 
                creature.gainDefenseValue(-2);
                creature.gainMaxHp(-5);
                creature.notify("You feel sluggish and careless!");
            }
		},
		new CurseOfTimeOption("Lose agility"){
			public void invoke(Creature creature) { 
                creature.gainDefenseValue(-2); 
                creature.gainAttackValue(-2);
                creature.notify("Your movements become more clumsy!");
            }
		},
		new CurseOfTimeOption("Lose sight"){
			public void invoke(Creature creature) {
                creature.gainVision(-1);
                creature.notify("Your eyesight gets weaker, the shadows laugh at you!");
            }
		}
	};
  
  	public void autoCurse(Creature creature){
  		options[(int)(Math.random() * options.length)].invoke(creature);
  	}
}