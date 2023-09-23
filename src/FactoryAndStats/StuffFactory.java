package FactoryAndStats;

import asciiPanel.AsciiPanel;

import java.awt.Color;
import java.util.*;

import Ai.AiCommon;
import Ai.BatAi;
import Ai.BonesAi;
import Ai.CrazedArcherAi;
import Ai.DemonAi;
import Ai.FungusAi;
import Ai.GhostAi;
import Ai.HoundAi;
import Ai.HoundMasterAi;
import Ai.LichAi;
import Ai.PlayerAi;
import Ai.TrollAi;
import WorldBuilder.World;

public class StuffFactory {
	private World world;
	
	public StuffFactory(World world){
		this.world = world;
	}
	
	// EFFECTS
	
	public Effect Poisoned(int amount) {
		Effect poison = new Effect("Poison", amount){
	        public void start(Creature creature){
	            creature.notify("You feel the poison enter your bloodstream.");
	        }
	                        
	        public void update(Creature creature){
	            super.update(creature);
	            creature.notify("You feel the poison burning in your veins.");
	            
	            if (!creature.isPlayer() && creature.hp() < 5) {
	            	creature.setHp(1);
	            } else {
		            creature.modifyHp(-5);
	            }
	        }
	    };
		return poison;
	}
	
	public Effect Bleeding(int amount) {
		Effect bleed = new Effect("Bleeding", amount){
	        public void start(Creature creature){
	            creature.notify("You notice that you're bleeding.");
	        }
	                        
	        public void update(Creature creature){
	            super.update(creature);
	            creature.notify("You notice that the bleeding doesn't stop.");
	            
	            if (!creature.isPlayer() && creature.hp() < 5) {
	            	creature.setHp(1);
	            } else {
		            creature.modifyHp(-5);
	            }
	        }
	    };
		return bleed;
	}
	
	public Effect Burn(int amount) {
		Effect burn = new Effect("Burning", amount) {
			public void start(Creature creature) {
				creature.notify("You catch aflame!");
			}
			
			public void update(Creature creature) {
				super.update(creature);
				creature.notify("You continue burning!");
				
	            if (!creature.isPlayer() && creature.hp() < 7) {
	            	creature.setHp(1);
	            } else {
		            creature.modifyHp(-7);
	            }
			}
		};
		return burn;
	}
	
	public Effect Stun(int amount) {
		Effect stun = new Effect("Stunned", amount) {
			public void start(Creature creature) {
				creature.notify("You are stunned and can't move!");
				creature.setStunned(true);
			}
			
			public void end(Creature creature) {
				creature.notify("You regain your composure!");
				creature.setStunned(false);
			}
		};
		return stun;
	}
	
	public Effect TempBlind(int duration, int amount) {
		Effect blind = new Effect("Blind", duration){
	        public void start(Creature creature){
	            creature.modifyVision(-amount);
	            creature.notify("You can't see that well anymore");
	        }
	        public void end(Creature creature){
	        	creature.modifyVision(amount);
	            creature.notify("You regain your original vision");
	        }
	    };
	    return blind;
	}
	
	public Effect InstantMinorHeal(int amount) {
		Effect instantMinorHeal = new Effect("Heal", 1){
	        public void start(Creature creature){
	            if (creature.hp() == creature.maxHp())
	                return;
	                                
	            creature.modifyHp(amount);
	            creature.doAction("see some small wounds disappearing");
	        }
	    };
	    return instantMinorHeal;
	}
	
	public Effect MinorMana() {
		Effect minorMana = new Effect("Mana", 1){
	        public void start(Creature creature){
	            if (creature.mana() == creature.maxMana())
	                return;
	                                
	            creature.modifyMana(20);
	            creature.doAction("feel the magical particles activating inside");
	        }
	    };
	    return minorMana;
	}
	
	public Effect Cure(String effectName) {
		Effect cure = new Effect("Cure", 1) {
			public void start(Creature player){
				for (Effect effect: player.effects()){
					if (effect.getName().equals(effectName) ){
						effect.isDone();
					}
				}
			}
		};
		return cure;
	}
	
	// ITEMS
	
	public Item newArtifact(int depth){
        Item item = new Item
        		.ItemBuilder('A', AsciiPanel.white, "an Artifact", "The Artifact you were send to retrieve.")
        		.build();
        
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newRock(int depth){
        Item item = new Item
        		.ItemBuilder(',', AsciiPanel.white, "a sturdy rock", "A rock, can be used as a weapon or crafting material.")
        		.setWeapon(true)
        		.setAttackValue(2)
        		.setThrownAttackValue(2)
        		.setWeaponEffect(Bleeding(1))
        		.build();
        
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }

	public Item newTorch(int depth){
        Item item = new Item
        		.ItemBuilder('[', AsciiPanel.yellow, "a torch", "A torch to help you see in the darkness. Can be used as a weapon.")
        		.setWeapon(true)
        		.setVisionRadius(3)
        		.setAttackValue(1)
        		.setWeaponEffect(Burn(1))
        		.build();

        world.addAtEmptyItemLocation(item, depth);
        return item;
    }

	public Item newStick(int depth){
		Item item = new Item
				.ItemBuilder('-', AsciiPanel.yellow, "a stick", "A stick is better then nothing in a fight... Or a fire.")
				.setAttackValue(1)
				.setDefenseValue(1)
				.build();

		world.addAtEmptyItemLocation(item, depth);
		return item;
	}

	public Item newBowl(int depth){
		Item item = new Item
				.ItemBuilder('^', AsciiPanel.yellow, "a bowl", "A wooden bowl, it is empty.")
				.build();
		
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}

	// USABLES

	public Item newDirtyBandages(int depth, Creature player){
		Item item = new Item
				.ItemBuilder('*', AsciiPanel.white, "a dirty bandage", "A dirty piece of bandage, using it can infect your wounds.")
				.setAUsable(true)
				.setFoodEffect(Cure("Bleeding"))
				.build();

		if ((double)Math.random() > 0.3)
			item.setQuaffEffect(Poisoned(10));

		world.addAtEmptyItemLocation(item, depth);
		return item;
	}

	public Item newCleanBandages(int depth, Creature player){
		Item item = new Item
				.ItemBuilder('*', AsciiPanel.white, "a clean bandage", "A clean piece of bandage, it feels good on your skin.")
				.setAUsable(true)
				.setFoodEffect(Cure("Bleeding"))
				.build();

		world.addAtEmptyItemLocation(item, depth);
		return item;
	}

	public Item newAntidote(int depth, Creature player){
		Item item = new Item
				.ItemBuilder('*', AsciiPanel.green, "a bottle of antidote", "This bitter liquid can cure any poison.")
				.setAUsable(true)
				.setFoodEffect(Cure("Poison"))
				.build();

		world.addAtEmptyItemLocation(item, depth);
		return item;
	}

	public Item newRestoreLeaf(int depth, Creature player){
		Item item = new Item
				.ItemBuilder('*', AsciiPanel.green, "a leaf of restoration", "Applying this leaf directly to the skin may clease the body of toxins.")
				.setAUsable(true)
				.setFoodEffect(new Effect("Debuff", 1){
					public void start(Creature player){
						for (Effect effect: player.effects()){
							if ((effect.getName().equals("Poison") || effect.getName().equals("Poisoned")) && (double)Math.random() > 0.5 ){
								effect.isDone();
							}
						}
					}
				})
				.build();

		world.addAtEmptyItemLocation(item, depth);
		return item;
	}

	public Item newRestorePaste(int depth, Creature player){
		Item item = new Item
				.ItemBuilder('*', AsciiPanel.green, "a paste of restoration", "Applying this paste directly to the skin will clease the body of toxins.")
				.setAUsable(true)
				.setFoodEffect(new Effect("Debuff", 1){
					public void start(Creature player){
						for (Effect effect: player.effects()){
							if (effect.getName().equals("Poison") ){
								effect.isDone();
							}
						}
					}
				})
				.build();

		world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	// MULTIUSE ITEMS
	
	public Item newWandOfLightning(int depth) {
		Item item = new Item
				.ItemBuilder('/', Color.YELLOW, "a wand of lightning", "A simple wand imbued with lightning magic. Stuns you and the target.")
				.setUses(4)
				.setZappable(true)
				.addWrittenSpell("Lightning", 0, new Effect("Magic", 3) {
					public void start(Creature creature) {
						creature.modifyHp(-15);
						creature.notify("You are stunned and can't move!");
						creature.setStunned(true);
					}
					
					public void end(Creature creature) {
						creature.notify("You regain your composure!");
						creature.setStunned(false);
					}
				})
				.build();

        world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	public Item newWandOfFire(int depth) {
		Item item = new Item
				.ItemBuilder('/', Color.YELLOW, "a wand of fire", "A simple wand imbued with weak fire magic.")
				.setZappable(true)
				.addWrittenSpell("Fire starter", 0, Burn(3))
				.setUses(4)
				.build();

        world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	public Item newWandOfPoison(int depth) {
		Item item = new Item
				.ItemBuilder('/', Color.YELLOW, "a wand of poison", "A simple wand imbued with weak poison magic.")
				.setZappable(true)
				.setUses(4)
				.addWrittenSpell("Weak poison bolt", 0, Poisoned(3))
				.build();

        world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	public Item newWandOfHealing(int depth) {
		Item item = new Item
				.ItemBuilder('/', Color.YELLOW, "a wand of healing", "A simple wand imbued with weak healing magic.")
				.setUses(4)
				.setZappable(true)
				.addWrittenSpell("Instant Heal", 0, InstantMinorHeal(10))
				.build();

        world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	public Item newWandOfMissile(int depth) {
		Item item = new Item
				.ItemBuilder('/', Color.YELLOW, "a wand of magic missile", "A simple wand imbued with weak magic.")
				.setUses(5)
				.setZappable(true)
				.addWrittenSpell("Magic missile", 0, new Effect("Magic", 1) {
					public void start(Creature creature) {
						creature.modifyHp(-10);
					}
				})
				.build();

        world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	public Item newWandOfDebuff(int depth) {
		Item item = new Item
				.ItemBuilder('/', Color.YELLOW, "a wand of nastyness", "A simple wand imbued with nasty magic.")
				.setUses(4)
				.setZappable(true)
				.addWrittenSpell("The Nasty", 0, randomDebuff(3))
				.build();

        world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	// FOOD

	public Item newBread(int depth){
        Item item = new Item
        		.ItemBuilder('-', Color.ORANGE, "a loaf of bread", "A loaf of old, hard bread.")
        		.setFood(true)
        		.setFoodValue(150)
        		.build();

        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newApple(int depth){
        Item item = new Item
        		.ItemBuilder('*', AsciiPanel.red, "an apple", "A surprisingly fresh apple!")
        		.setFood(true)
        		.setFoodValue(50)
        		.build();

        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newWater(int depth){
        Item item = new Item
        		.ItemBuilder((char)168, AsciiPanel.blue, "a bottle of water", "Who left a bottle of water here?")
        		.setDrink(true)
        		.setFoodValue(10)
        		.setThirstValue(35)
        		.build();
		
		if ((double)Math.random() > 0.4){
			item.setQuaffEffect(Poisoned(2));
		}
		
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }

	public Item newBoiledWater(int depth){
        Item item = new Item
        		.ItemBuilder((char)247, AsciiPanel.blue, "a bottle of boiled water", "It tastes slightly better then normal, but some germs might remain.")
        		.setDrink(true)
        		.setThirstValue(30)
        		.setFoodValue(5)
        		.build();
		
		if ((float)Math.random() > 0.95){
			item.setQuaffEffect(Poisoned(2));
		}
        
		world.addAtEmptyItemLocation(item, depth);
        return item;
    }

	public Item newUnknownFlesh(int depth){
        Item item = new Item
        		.ItemBuilder('/', AsciiPanel.yellow, "a patch of unknown flesh", "A patch of weird-smelling meat.")
        		.setFood(true)
        		.setFoodValue(200)
        		.setFoodEffect(Poisoned(3))
        		.build();

        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newCookedMeat(int depth){
		Item item = new Item
				.ItemBuilder('/', AsciiPanel.brightYellow, "a portion of cooked meat", "A piece of cooked meat, it smels so good!")
				.setFood(true)
				.setFoodValue(350)
				.build();

		world.addAtEmptyItemLocation(item, depth);
		return item;
	}

	public Item newSoup(int depth){
		Item item = new Item
				.ItemBuilder('^', AsciiPanel.yellow, "a bowl of soup", "A bowl of soup, not great but better than nothing.")
				.setFood(true)
				.setFoodValue(270)
				.setThirstValue(50)
				.build();

		world.addAtEmptyItemLocation(item, depth);
		return item;
	}

	public Item newSpoiledSoup(int depth){
		Item item = new Item
				.ItemBuilder('^', AsciiPanel.yellow, "a bowl of spoiled soup", "A bowl of spoiled soup, still edible if you remove the fungi...")
				.setFood(true)
				.setFoodValue(150)
				.setThirstValue(40)
				.build();

		if (Math.random() > 0.5){
			item.setFoodEffect(Poisoned(5));
		}
		
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	// WEAPONS

	public Item newDagger(int depth){
		Item item = new Item
				.ItemBuilder(')', AsciiPanel.white, "a dagger", "An old dagger, won't fall apart any soon.")
				.setWeapon(true)
				.setAttackValue(5)
				.setDexterityValue(1)
				.setThrownAttackValue(5)
				.setWeaponEffect(Bleeding(5))
				.build();

		qualitySelector(item);
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}

	public Item newSword(int depth){
		Item item = new Item
				.ItemBuilder(')', AsciiPanel.brightWhite, "a sword", "It's rusty and old, but it gets the job done.")
				.setWeapon(true)
				.setAttackValue(10)
				.build();

		qualitySelector(item);
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}

	public Item newExe(int depth){
		Item item = new Item
				.ItemBuilder(')', AsciiPanel.yellow, "an exe", "A lumberjack's trusty tool. Or that of an executioner.")
				.setWeapon(true)
				.setAttackValue(12)
				.build();

		qualitySelector(item);
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	public Item newCursedSword(int depth, Creature creature){
		Item item = new Item
				.ItemBuilder(')', AsciiPanel.red, "a cursed sword", "A sword cursed by a demon, accept the curse, gain power.")
				.setWeapon(true)
				.setAttackValue(20)
				.setWeaponEffect(Poisoned(5))
				.build();

		qualitySelector(item);
		creature.modifyMaxHp(-20);
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	public Item newGreatSword(int depth){
		Item item = new Item
				.ItemBuilder(')', AsciiPanel.brightWhite, "a greatsword", "A bigger version of a normal sword.")
				.setWeapon(true)
				.setAttackValue(15)
				.build();

		qualitySelector(item);
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}

	public Item newStaff(int depth){
		Item item = new Item
				.ItemBuilder(')', AsciiPanel.yellow, "a staff", "A staff of a nameless mage.")
				.setWeapon(true)
				.setAttackValue(5)
				.setDefenseValue(3)
				.setMaxMana(10)
				.build();

		qualitySelector(item);
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	public Item newFlail(int depth){
		Item item = new Item
				.ItemBuilder(')', AsciiPanel.yellow, "a flail", "Can be either a weapon or a satisfaction of weird interests.")
				.setWeapon(true)
				.setAttackValue(2)
				.setDexterityValue(1)
				.setWeaponEffect(Bleeding(2))
				.build();

		qualitySelector(item);
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}

	public Item newMorningStar(int depth){
		Item item = new Item
				.ItemBuilder(')', AsciiPanel.yellow, "a morning star", "A heavy, spiky ball chained to a stick.")
				.setWeapon(true)
				.setAttackValue(5)
				.setDefenseValue(5)
				.setWeaponEffect(Bleeding(5))
				.build();

		qualitySelector(item);
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	// RANGED AND THROWING WEAPONS
	
	public Item newBow(int depth){
        Item item = new Item
        		.ItemBuilder(')', AsciiPanel.yellow, "a bow with arrows", "A normal bow with a mana-infused quiver.")
        		.setRanged(true)
        		.setAttackValue(1)
        		.setRangedAttackValue(7)
        		.build();

        qualitySelector(item);
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }

	public Item newCrossbow(int depth){
        Item item = new Item
        		.ItemBuilder(')', AsciiPanel.yellow, "a crossbow with bolts", "An old crossbow with a mana-infused quiver.")
        		.setRanged(true)
        		.setAttackValue(2)
        		.setDefenseValue(2)
        		.setDexterityValue(-1)
        		.setRangedAttackValue(10)
        		.build();

        qualitySelector(item);
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newJavelin(int depth){
        Item item = new Item
        		.ItemBuilder(')', AsciiPanel.yellow, "a javelin", "Basically a throwing spear.")
        		.setThrown(true)
        		.setAttackValue(3)
        		.setThrownAttackValue(10)
        		.build();

        qualitySelector(item);
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newShuriken(int depth){
        Item item = new Item
        		.ItemBuilder('*', AsciiPanel.yellow, "a shuriken", "A weapon of stealthy individuals or a last resort.")
        		.setThrown(true)
        		.setThrownAttackValue(15)
        		.build();

        qualitySelector(item);
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	// ARMOR
	
	public Item newStealthArmor(int depth) {
		Item item = new Item('[', AsciiPanel.yellow, "a stealth suit", "Not much in terms of defence, but it makes it easier to move around.");
		item.setArmor();
		item.modifyDexterityValue(4);
		qualitySelector(item);
		world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newWoolArmor(int depth){
		Item item = new Item('[', AsciiPanel.yellow, "a woolen cap", "Keeps you warm, but gets in the way when fighting.");
		item.setArmor();
		item.modifyDefenseValue(1);
		item.modifyDexterityValue(-1);
		qualitySelector(item);
		world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
  
	public Item newLightArmor(int depth){
	    Item item = new Item('[', AsciiPanel.green, "a tunic", "A light tunic made from leather.");
		item.setArmor();
	    item.modifyDefenseValue(2);
	    item.modifyDexterityValue(1);
	    qualitySelector(item);
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}

	public Item newDustyArmor(int depth){
		Item item = new Item('[', AsciiPanel.white, "a dusty armor", "Some armorplates are missing.");
		item.setArmor();
		item.modifyDefenseValue(3);
		qualitySelector(item);
		world.addAtEmptyItemLocation(item, depth);
	    return item;
	}

	public Item newMediumArmor(int depth){
	    Item item = new Item('[', AsciiPanel.white, "a chainmail", "A mail of fine iron rings, some of them are rusted though.");
		item.setArmor();
	    item.modifyDefenseValue(4);
	    item.modifyDexterityValue(-1);
	    qualitySelector(item);
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}

	public Item newReinforcedArmor(int depth){
		Item item = new Item('[', AsciiPanel.yellow, "a lieutenants armor", "A leather armor reinforced with metal plates and rings.");
		item.setArmor();
		item.modifyDefenseValue(5);
		item.modifyDexterityValue(1);
		qualitySelector(item);
		world.addAtEmptyItemLocation(item, depth);
	    return item;
	}

	public Item newHeavyArmor(int depth){
	    Item item = new Item('[', AsciiPanel.brightWhite, "a platemail", "Heavy and bulky, dented at some places, yet reliable.");
		item.setArmor();
	    item.modifyDefenseValue(6);
	    item.modifyDexterityValue(-2);
	    qualitySelector(item);
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newArmorOfHealing(int depth, Creature creature){
	    Item item = new Item('[', AsciiPanel.red, "an armor of healing", "You feel that this armor is different.");
		item.setArmor();
	    item.modifyDefenseValue(4);
	    item.setArmorEffect(InstantMinorHeal(5));
	    qualitySelector(item);
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	// SHIELDS
	
	public Item newBuckler(int depth) {
		Item item = new Item('0', AsciiPanel.yellow, "a wooden buckler", "A simple wooden shield.");
		item.setShield();
		item.shieldConstant = 0.1;
		item.modifyDefenseValue(2);
		qualitySelector(item);
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newRoundShield(int depth) {
		Item item = new Item('0', AsciiPanel.yellow, "a round shield", "A small round shield. Very light.");
		item.setShield();
		item.shieldConstant = 0.15;
		item.modifyDefenseValue(1);
		item.modifyDexterityValue(2);
		qualitySelector(item);
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newSturdyShield(int depth) {
		Item item = new Item('0', AsciiPanel.yellow, "a sturdy shield", "A shield from sturdy wood with metal arnaments.");
		item.setShield();
		item.shieldConstant = 0.2;
		item.modifyDefenseValue(3);
		qualitySelector(item);
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newMetalShield(int depth) {
		Item item = new Item('0', AsciiPanel.yellow, "a metal shield", "A shield made entirely from metal.");
		item.setShield();
		item.shieldConstant = 0.3;
		item.modifyDefenseValue(4);
		item.modifyDexterityValue(-1);
		qualitySelector(item);
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newTowerShield(int depth) {
		Item item = new Item('0', AsciiPanel.yellow, "a tower shield", "A long, think shield. Makes moving difficult.");
		item.setShield();
		item.shieldConstant = 0.4;
		item.modifyDefenseValue(5);
		item.modifyAttackValue(-2);
		item.modifyDexterityValue(-2);
		qualitySelector(item);
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}

	// CREATURES 
	
	// PLAYER
	
	public Creature newPlayer(List<String> messages, FieldOfView fov, int maxHp, int attack, int defense, int dex, String classOfPlayer){
		Creature player = new Creature(world, '@', AsciiPanel.brightWhite, maxHp, attack, defense, dex, classOfPlayer);
		world.addAtEmptyLocation(player, 0);
		
		switch(player.name()) {
			case "Warrior": player.startEquip(newSword(player.z)); player.addStarterSkills(skillWarriorsMight); break;
			case "Wizard": player.startEquip(randomBook(player.z)); player.addStarterSkills(skillWizardsTrick); break;
			case "Rogue": player.startEquip(newDagger(player.z)); player.addStarterSkills(skillRoguesBullsEye); break;
			case "Monk": player.startEquip(newStaff(player.z)); player.addStarterSkills(skillMonksDefense); break;
		}
		
		new PlayerAi(player, messages, fov);
		return player;
	}
	
	// LOWER CREATURES

	public Creature newRat(int depth, Creature player){
		Creature rat = new Creature(world, 'r', AsciiPanel.yellow, 10, 10, 0, 5, "rat");
		world.addAtEmptyLocation(rat, depth);
		new AiCommon(rat, player);
		return rat;
	}
	
	public Creature newFungus(int depth, Creature player){
		Creature fungus = new Creature(world, 'f', AsciiPanel.magenta, 25, 10, 1, 2, "fungus");
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus, this, player);
		return fungus;
	}
	
	public Creature newBat(int depth){
	    Creature bat = new Creature(world, 'b', AsciiPanel.white, 15, 1, 0, 6, "bat");
	    world.addAtEmptyLocation(bat, depth);
	    new BatAi(bat);
	    return bat;
	}
	
	public Creature newSlime(int depth, Creature player){
	    Creature slime = new Creature(world, 's', AsciiPanel.green, 10, 10, 0, 2, "slime");
	    world.addAtEmptyLocation(slime, depth);
	    new AiCommon(slime, player);
	    return slime;
	}
	
	public Creature newBones(int depth, Creature player){
	    Creature bones = new Creature(world, 'B', AsciiPanel.white, 30, 15, 0, 2, "wandering bones");
	    bones.equip(randomArmor(depth));
		if ((double)Math.random() > 0.5)
			bones.equip(newGreatSword(depth));
	    world.addAtEmptyLocation(bones, depth);
	    new BonesAi(bones, player);
	    return bones;
	}
	
	public Creature newKobolt(int depth, Creature player){
	    Creature kobolt = new Creature(world, 'k', AsciiPanel.brightBlue, 35, 15, 5, 5, "kobolt");
	    kobolt.equip(randomWeapon(depth));
	    kobolt.equip(randomArmor(depth));
	    world.addAtEmptyLocation(kobolt, depth);
	    new AiCommon(kobolt, player);
	    return kobolt;
	}
	
	// MEDIUM CREATURES
	
	public Creature newGoblin(int depth, Creature player){
	    Creature goblin = new Creature(world, 'g', AsciiPanel.green, 20, 10, 5, 4, "goblin");
	    goblin.equip(randomWeapon(depth));
	    goblin.equip(randomArmor(depth));
	    world.addAtEmptyLocation(goblin, depth);
	    new AiCommon(goblin, player);
	    return goblin;
	}
	
	public Creature newHoundMaster(int depth, Creature player){
	    Creature houndmaster = new Creature(world, 'H', AsciiPanel.brightYellow, 40, 10, 5, 5, "houndmaster");
	    houndmaster.equip(newFlail(depth));
	    world.addAtEmptyLocation(houndmaster, depth);
	    new HoundMasterAi(houndmaster, this, player);
	    return houndmaster;
	}
	
	public Creature newHound(int depth, Creature player){
	    Creature hound = new Creature(world, 'h', AsciiPanel.yellow, 10, 25, 0, 8, "hound");
	    world.addAtEmptyLocation(hound, depth);
	    new HoundAi(hound, player);
	    return hound;
	}

	public Creature newCrazedArcher(int depth, Creature player){
        Creature archer = new Creature(world, 'a', AsciiPanel.brightBlack, 25, 10,  5, 2, "crazed archer");
        archer.equip(newBow(depth));
        archer.equip(randomArmor(depth));
        world.addAtEmptyLocation(archer, depth);
        new CrazedArcherAi(archer, player);
        return archer;
    }
	
	public Creature newCrazedSwordsman(int depth, Creature player){
        Creature sword = new Creature(world, 's', AsciiPanel.brightBlack, 40, 10, 5, 2, "crazed swordsman");
        sword.equip(newSword(depth));
        sword.equip(randomArmor(depth));
        world.addAtEmptyLocation(sword, depth);
        new AiCommon(sword, player);
        return sword;
    }
	
	public Creature newGhost(int depth, Creature player){
        Creature ghost = new Creature(world, 's', AsciiPanel.brightBlack, 30, 5, 5, 5, "ghost");
        ghost.setGhost();
        world.addAtEmptyLocation(ghost, depth);
        new GhostAi(ghost, player);
        return ghost;
    }
	
	// HIGHER CREATURES
	
	public Creature newDemon(int depth, Creature player){
        Creature demon = new Creature(world, 's', AsciiPanel.red, 70, 20, 10, 7, "demon");
        demon.equip(newCursedSword(depth, demon));
        demon.equip(newArmorOfHealing(depth, demon));
        world.addAtEmptyLocation(demon, depth);
        new DemonAi(demon, player);
        return demon;
    }
	
	public Creature newTroll(int depth, Creature player){
	    Creature troll = new Creature(world, 'T', AsciiPanel.white, 50, 10, 15, 2, "troll");
	    troll.equip(newGreatSword(depth));
	    world.addAtEmptyLocation(troll, depth);
	    new TrollAi(troll, player);
	    return troll;
	}

	public Creature newNightmare(int depth, Creature player){
		Creature nightmare = new Creature(world, 'N', AsciiPanel.brightBlue, 60, 15, 5, 4, "nightmarish horror");
		world.addAtEmptyLocation(nightmare, depth);
		new AiCommon(nightmare, player);
		return nightmare;
	}
	
	public Creature newMutant(int depth, Creature player) {
		Creature mutant = new Creature(world, 'M', AsciiPanel.magenta, 75, 20, 10, 5, "mutant");
		world.addAtEmptyLocation(mutant, depth);
		new AiCommon(mutant, player);
		return mutant;
	}
	
	public Creature newLich(int depth, Creature player) {
		Creature lich = new Creature(world, 'l', AsciiPanel.green, 30, 5, 7, 3, "lich");
		world.addAtEmptyLocation(lich, depth);
		new LichAi(lich, this, player);
		return lich;
	}
	
	public Creature newSkeleton(int depth, Creature player) {
		Creature skeleton = new Creature(world, 'S', AsciiPanel.green, 10, 2, 10, 2, "skeleton");
		skeleton.equip(randomWeapon(depth));
		skeleton.equip(randomShield(depth));
		world.addAtEmptyLocation(skeleton, depth);
		new AiCommon(skeleton, player);
		return skeleton;
	}

	
	// THE COMRADE EASTER EGG
	
	public Creature newComrade(int depth, Creature player) {
		Creature comrade = new Creature(world, 'C', AsciiPanel.red, 5, 1, 0, 0, "comrade");
		comrade.equip(newMosinNagant(depth));
		comrade.setGhost();
		world.addAtEmptyLocation(comrade, depth);
		new GhostAi(comrade, player);
		return comrade;
	}
	
	public Item newMosinNagant(int depth) {
		Item item = new Item('/', AsciiPanel.brightYellow, "a rifle", "A weird instrument that reads 'Mosin Nagant' on its side. Comes with a short knife in front.");
		item.modifyRangedAttackValue(50);
		item.modifyAttackValue(5);
		item.setRanged();
		item.setWeaponEffect(Bleeding(3));
		qualitySelector(item);
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	// POTIONS
	
	public Item newPotionOfHealth(int depth){
	    Item item = new Item('!', AsciiPanel.white, "a health potion", "A potion that can heal an injured body, but not a broken heart.");
	    item.modifyThirstValue(5);
		item.isDrink = true;
	    item.setQuaffEffect(InstantMinorHeal(20));
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newPotionOfPoison(int depth){
	    Item item = new Item('!', AsciiPanel.green, "a potion of poison", "These were often served to kings.");
	    item.modifyThirstValue(5);
		item.isDrink = true;
	    item.setQuaffEffect(Poisoned(10));
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newPotionOfWarrior(int depth){
	    Item item = new Item('!', AsciiPanel.white, "a warrior's potion", "Makes you a bit stronger, don't overuse it.");
	    item.modifyThirstValue(5);
		item.isDrink = true;
	    item.setQuaffEffect(new Effect("Buff", 20){
	        public void start(Creature creature){
	            creature.modifyAttackValue(5);
	            creature.modifyDefenseValue(5);
	            creature.doAction("look stronger");
	        }
	        public void end(Creature creature){
				if ((double)Math.random() > 0.3){
					creature.modifyAttackValue(-5);
	            	creature.modifyDefenseValue(-5);
					creature.doAction("look less strong");
				} else {
					creature.modifyAttackValue(-7);
	            	creature.modifyDefenseValue(-2);
					creature.doAction("feel the side effects kick in");
				}
	        }
	    });
	                
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newPotionOfMana(int depth){
	    Item item = new Item('!', AsciiPanel.blue, "a mana potion", "A potion of liquid mana.");
	    item.modifyThirstValue(5);
		item.isDrink = true;
	    item.setQuaffEffect(MinorMana());
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newPotionOfVision(int depth){
	    Item item = new Item('!', AsciiPanel.brightBlack, "a potion of vision", "See better in the darkness.");
	    item.modifyThirstValue(5);
		item.isDrink = true;
	    item.setQuaffEffect(new Effect("Buff", 10){
	        public void start(Creature creature){
	            creature.modifyVision(2);
	            creature.doAction("can see better then before");
	        }
	        public void end(Creature creature){
	        	creature.modifyVision(-2);
	            creature.doAction("regain your original vision");
	        }
	    });
	                
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newPotionOfBlindness(int depth){
	    Item item = new Item('!', AsciiPanel.brightBlack, "a potion of blindness", "Blind your enemies and escape!");
	    item.modifyThirstValue(5);
		item.isDrink = true;
	    item.setQuaffEffect(TempBlind(2, 2));
	                
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	// CRAFTING SCROLLS
	
	public Item newScrollMeat(int depth) {
		Item scroll = new Item('=', AsciiPanel.brightBlack, "a crafting scroll (cooked meat)", "A crumbled note with a recipe.");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("cook meat", 5, new Effect("Cook", 1) {
			public void start(Creature player) {
				if (player.inventory().contains(newRock(depth)) && player.inventory().contains(newWater(depth)) && player.inventory().contains(newUnknownFlesh(depth))) { 
					player.inventory().remove(newRock(depth));
					player.inventory().remove(newWater(depth));
					player.inventory().remove(newUnknownFlesh(depth));
					player.notify("You use some mana to heat the stone and the water.");
					if ((double)Math.random() > 0.3) {
						player.inventory().add(newCookedMeat(depth));
						player.notify("You manage to cook the meat nicely");
					} else {
						player.modifyHp(-5);
						player.notify("You were clumsy enough to throw boiling water over yourself.");
						if (player.hp() <= 0){
							player.diedCrafting = true;
						}
					}
				} else {
					player.notify("You don't have the needed items!");
				}
			}
		});
		world.addAtEmptyItemLocation(scroll, depth);
		return scroll;
	}

	public Item newScrollJavelin(int depth) {
		Item scroll = new Item('=', AsciiPanel.brightBlack, "a crafting scroll (javelin)", "A crumbled note with a recipe.");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("make a javelin", 2, new Effect("Craft", 1){
			public void start(Creature player){
				if (player.inventory().contains(newStick(depth)) && player.inventory().contains(newRock(depth))){
					player.inventory().remove(newStick(depth));
					player.inventory().remove(newRock(depth));
					player.notify("You sit down and start crafting...");
					if ((double)Math.random() > 0.2) {
						player.inventory().add(newJavelin(depth));
						player.notify("You manage to make a javalin.");
					} else {
						player.modifyHp(-5);
						player.addEffect(Bleeding(2));
						player.notify("You hurt yourself on a sharp stick, he wound isn't deep but it keeps bleeding!");
						if (player.hp() <= 0){
							player.diedCrafting = true;
						}
					}
				} else {
					player.notify("You don't have the needed items!");
				}
			}
		});
		world.addAtEmptyItemLocation(scroll, depth);
		return scroll;
	}

	public Item newScrollSoup(int depth){
		Item scroll = new Item('=', AsciiPanel.brightBlack, "a crafting scroll (soup)", "A crumbled note with a recipe.");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("cook soup", 2, new Effect("Cook", 1){
			public void start(Creature player){
				if (player.inventory().contains(newBowl(depth)) && player.inventory().contains(newUnknownFlesh(depth)) && player.inventory().contains(newWater(depth))){
					player.inventory().remove(newBowl(depth));
					player.inventory().remove(newUnknownFlesh(depth));
					player.inventory().remove(newWater(depth));
					player.notify("You sit down and start cooking...");
					if ((double)Math.random() > 0.2) {
						player.inventory().add(newSoup(depth));
						player.notify("You manage to cook some soup.");
					} else {
						player.modifyHp(-5);
						player.notify("You were clumsy enough to drop the bowl with water on the ground!");
						if (player.hp() <= 0){
							player.diedCrafting = true;
						}
					}
				} else {
					player.notify("You don't have the needed items!");
				}
			}
		});
		world.addAtEmptyItemLocation(scroll, depth);
		return scroll;
	}

	public Item newScrollBandage(int depth){
		Item scroll = new Item('=', AsciiPanel.brightBlack, "a crafting scroll (clean bandage)", "A crumbled note with a recipe.");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("craft bandage", 2, new Effect("Craft", 1){
			public void start(Creature player){
				if (player.inventory().contains(newBowl(depth)) && player.inventory().contains(newDirtyBandages(depth, player)) && player.inventory().contains(newWater(depth))){
					player.inventory().remove(newBowl(depth));
					player.inventory().remove(newDirtyBandages(depth, player));
					player.inventory().remove(newWater(depth));
					player.notify("You sit down and start boiling water...");
					if ((double)Math.random() > 0.2) {
						player.inventory().add(newCleanBandages(depth, player));
						player.notify("You manage to desinfect the bandage in boiling water.");
					} else {
						player.modifyHp(-5);
						player.notify("You dropped the bandage on the ground, slipped on it and fell!");
						if (player.hp() <= 0){
							player.diedCrafting = true;
						}
					}
				} else {
					player.notify("You don't have the needed items!");
				}
			}
		});
		world.addAtEmptyItemLocation(scroll, depth);
		return scroll;
	}

	public Item newScrollPaste(int depth){
		Item scroll = new Item('=', AsciiPanel.brightBlack, "a crafting scroll (paste of restoration)", "A crumbled note with a recipe.");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("craft paste of restoration", 2, new Effect("Cook", 1){
			public void start(Creature player){
				if (player.inventory().contains(newBowl(depth)) && player.inventory().contains(newRestoreLeaf(depth, player)) && player.inventory().contains(newWater(depth))){
					player.inventory().remove(newBowl(depth));
					player.inventory().remove(newRestoreLeaf(depth, player));
					player.inventory().remove(newWater(depth));
					player.notify("You sit down and start crushing the leaf inside the bowl...");
					if ((double)Math.random() > 0.2) {
						player.inventory().add(newRestorePaste(depth, player));
						player.notify("You manage to make a slimy, green-ish paste.");
					} else {
						player.modifyHp(-5);
						player.notify("You failed to follow the recipe and hit yourself against the floor in frustration!");
						if (player.hp() <= 0){
							player.diedCrafting = true;
						}
					}
				} else {
					player.notify("You don't have the needed items!");
				}
			}
		});
		world.addAtEmptyItemLocation(scroll, depth);
		return scroll;
	}

	public Item newScrollWater(int depth){
		Item scroll = new Item('=', AsciiPanel.brightBlack, "a crafting scroll (boiled water)", "A crumbled note with a recipe.");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("craft paste of restoration", 2, new Effect("Cook", 1){
			public void start(Creature player){
				if (player.inventory().contains(newWater(depth))){
					player.inventory().remove(newWater(depth));
					player.notify("You sit down and focus your mana on the vial...");
					if ((double)Math.random() > 0.2) {
						player.inventory().add(newBoiledWater(depth));
						player.notify("You manage to boil the water inside the vial.");
					} else {
						player.modifyHp(-5);
						player.notify("You focus your mana too much and the glass bottle explodes!");
						if (player.hp() <= 0){
							player.diedCrafting = true;
						}
					}
				} else {
					player.notify("You don't have the needed items!");
				}
			}
		});
		world.addAtEmptyItemLocation(scroll, depth);
		return scroll;
	}
	

	// NORMAL SCROLLS AND SPELLBOOKS

	public Item newScrollHeal(int depth) {
		Item scroll = new Item('=', AsciiPanel.brightBlack, "a scroll of healing", "A one-use scroll.");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("minor heal", 2, InstantMinorHeal(25));
		world.addAtEmptyItemLocation(scroll, depth);
		return scroll;
	}
	
	public Item newScrollLightning(int depth) {
		Item scroll = new Item('=', AsciiPanel.blue, "a scroll of lightning bolt", "A one-use scroll.");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("lightning bolt", 5, new Effect("Magic", 1){
            public void start(Creature creature){
                creature.modifyHp(-20);
            }
        });
		world.addAtEmptyItemLocation(scroll, depth);
		return scroll;
	}
	
	public Item newScrollPoison(int depth) {
		Item scroll = new Item('=', AsciiPanel.brightBlack, "a scroll of poisoned bolt", "A one-use scroll.");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("poisoned bolt", 5, new Effect("Magic", 1){
            public void start(Creature creature){
                creature.modifyHp(-10);
                creature.addEffect(Poisoned(5));
            }
        });
		world.addAtEmptyItemLocation(scroll, depth);
		return scroll;
	}
	
	public Item newScrollShield(int depth) {
		Item scroll = new Item('=', AsciiPanel.brightBlack, "a scroll of magic shield", "A one-use scroll.");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("magic shield", 6, new Effect("Magic", 5){
            public void start(Creature creature){
                creature.modifyDefenseValue(5);
                creature.doAction("are covered by magical particles which protect your vitals");
            }
            
            public void end(Creature creature) {
            	creature.modifyDefenseValue(-5);
            	creature.doAction("see your magic shield disappear");
            }
        });
		world.addAtEmptyItemLocation(scroll, depth);
		return scroll;
	}
	
	public Item newScrollSupply(int depth) {
		Item scroll = new Item('=', AsciiPanel.brightBlack, "a scroll of supplies", "A one-use scroll.");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("magical supplies", 10,  new Effect("Magic", 2){
            public void start(Creature creature){
                creature.modifyFood(150);
                creature.modifyThirst(15);
                creature.modifyMana(creature.maxMana());
                creature.notify("A floating table with food and drinks appears in front of you.");
            }
            
            public void update(Creature creature) {
                creature.modifyFood(150);
                creature.modifyThirst(15);
            }
            
            public void end(Creature creature) {
            	creature.notify("The floating table disappears into the nothingness.");
            }
        });
		world.addAtEmptyItemLocation(scroll, depth);
		return scroll;
	}
	
	public Item newWhiteMagesSpellbook(int depth) {
        Item item = new Item('+', AsciiPanel.brightWhite, "a white mage's spellbook", "A book with spells, left by a warmage.");
        item.addWrittenSpell("minor heal", 4, new Effect("Heal", 1){
            public void start(Creature creature){
                if (creature.hp() == creature.maxHp())
                    return;
                
                creature.modifyHp(15);
                creature.notify("Some bruises and minor injuries heal within seconds.");
            }
        });
        
        item.addWrittenSpell("major heal", 8, new Effect("Heal", 1){
            public void start(Creature creature){
                if (creature.hp() == creature.maxHp())
                    return;
                
                creature.modifyHp(50);
                creature.notify("Some nasty injuries heal within seconds.");
            }
        });
        
        item.addWrittenSpell("slow regenerate", 12, new Effect("Heal", 20){
            public void update(Creature creature){
                super.update(creature);
                creature.modifyHp(2);
            }
        });

        item.addWrittenSpell("inner strength", 16, new Effect("Buff", 5){
            public void start(Creature creature){
                creature.modifyAttackValue(2);
                creature.modifyDefenseValue(2);
                creature.modifyVision(1);
                creature.modifyHp(10);
                creature.modifyRegenManaPer1000(-10);
                creature.doAction("seem to glow with inner strength");
            }
            public void update(Creature creature){
                super.update(creature);
                if ((double)Math.random() < 0.25)
                    creature.modifyHp(5);
            }
            public void end(Creature creature){
                creature.modifyAttackValue(-2);
                creature.modifyDefenseValue(-2);
                creature.modifyVision(-1);
                creature.modifyHp(-10);
                creature.modifyRegenManaPer1000(10);
            }
        });
        
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newBlueMagesSpellbook(int depth) {
        Item item = new Item('+', AsciiPanel.brightBlue, "a blue mage's spellbook", "A book with spells, left by a scouting mage.");

        item.addWrittenSpell("blood to mana", 1, new Effect("Magic", 1){
            public void start(Creature creature){
                int amount = Math.min(creature.hp() - 1, creature.maxMana() - creature.mana());
                creature.modifyHp(-amount);
                creature.modifyMana(amount);
            }
        });
        
        item.addWrittenSpell("blink", 6, new Effect("Magic", 1){
            public void start(Creature creature){
                creature.doAction("fade out");
                
                int mx = 0;
                int my = 0;
                
                do
                {
                    mx = (int)(Math.random() * 11) - 5;
                    my = (int)(Math.random() * 11) - 5;
                }
                while (!creature.canEnter(creature.x+mx, creature.y+my, creature.z)
                        && creature.canSee(creature.x+mx, creature.y+my, creature.z));
                
                creature.moveBy(mx, my, 0);
                
                creature.doAction("fade in");
            }
        });
        
        item.addWrittenSpell("detect creatures", 16, new Effect("Magic", 25){
            public void start(Creature creature){
                creature.doAction("look far off into the distance");
                creature.modifyDetectCreatures(1);
            }
            public void end(Creature creature){
                creature.modifyDetectCreatures(-1);
            }
        });
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newRedMagesSpellbook(int depth) {
        Item item = new Item('+', AsciiPanel.red, "a red mage's spellbook", "A book with spells, left by a blood mage.");
        
        item.addWrittenSpell("leech lifeforce", 1, new Effect("Magic", 1){
            public void start(Creature creature, Creature other){
                int amount = Math.min(creature.hp() - 15, creature.maxMana() - creature.mana());
                other.modifyHp(-amount);
                creature.modifyHp(amount);
            }
        });
        
        item.addWrittenSpell("missile of blood", 1, new Effect("Magic", 1){
            public void start(Creature creature, Creature other){
                int amount = Math.min(creature.hp() - 10, creature.maxMana() - creature.mana());
                other.modifyHp(-amount * 3);
                creature.modifyHp(-amount);
            }
        });
        
        item.addWrittenSpell("summon bats", 11, new Effect("Magic", 1){
            public void start(Creature creature){
                for (int ox = -1; ox < 2; ox++){
                    for (int oy = -1; oy < 2; oy++){
                        int nx = creature.x + ox;
                        int ny = creature.y + oy;
                        if (ox == 0 && oy == 0 
                                || creature.creature(nx, ny, creature.z) != null)
                            continue;
                        
                        Creature bat = newBat(0);
                        
                        if (!bat.canEnter(nx, ny, creature.z)){
                            world.remove(bat);
                            continue;
                        }
                        
                        bat.x = nx;
                        bat.y = ny;
                        bat.z = creature.z;
                        
                        creature.summon(bat);
                    }
                }
            }
        });
        
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }

	public Item newOccultSpellbook(int depth) {
        Item item = new Item('+', AsciiPanel.red, "an occult spellbook", "A book with spells, left by an occultist.");
        
        item.addWrittenSpell("leech lifeforce", 1, new Effect("Magic", 1){
            public void start(Creature creature, Creature other){
                int amount = Math.min(creature.hp() - 15, creature.maxMana() - creature.mana());
                other.modifyHp(-amount);
                creature.modifyHp(amount);
            }
        });
        
        item.addWrittenSpell("eldrich tentacle", 5, new Effect("Magic", 1){
            public void start(Creature creature, Creature other){
                int amount = creature.hp() / 3;
                other.modifyHp(-(amount * 2));
                creature.modifyHp(-(amount / 2));
            }
        });
        
		item.addWrittenSpell("the abyss", 10, new Effect("Magic", 5){
			public void start(Creature self){
				self.modifyDefenseValue(10);
				self.modifyAttackValue(15);
				self.notify("You looked into the Abyss and It granted you power.");
			}

			public void end(Creature self){
				self.modifyDefenseValue(-10);
				self.modifyAttackValue(-15);
				self.modifyHp(-10);
				self.notify("The Abyss takes some of your blood as payment for power.");
			}
		});

        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	// SKILLS
		// SELFCASTS ( CLASSBOUND )
	
	public Skill skillRoguesBullsEye = new Skill("Rogue's Bullseye", 5, 5, new Effect("SkillSelfcast", 3){
		public void start(Creature self) {
			self.modifyDexterity(3);
			self.notify("You see the weakpoints of your enemies more clear.");
		}
		
		public void end(Creature self) {
			self.modifyDexterity(-3);
			self.notify("Your awareness of weakpoints weakens.");
		}
	});
	
	public Skill skillWarriorsMight = new Skill("Warrior's Might", 5, 5, new Effect("SkillSelfcast", 3){
		public void start(Creature self) {
			self.modifyAttackValue(3);
			self.notify("You feel your muscles swell.");
		}
		
		public void end(Creature self) {
			self.modifyAttackValue(-3);
			self.notify("Your muscles are tired.");
		}
	});
	
	public Skill skillWizardsTrick = new Skill("Wizard's Trick", 5, 5, new Effect("SkillSelfcast", 1){
		public void start(Creature self) {
			self.modifyMana(10);
			self.notify("You absorb some mana around you.");
		}
	});
	
	public Skill skillMonksDefense = new Skill("Monk's Defense", 5, 5, new Effect("SkillSelfcast", 3){
		public void start(Creature self) {
			self.modifyDefenseValue(3);
			self.notify("Your skin becomes harder then rocks.");
		}
		
		public void end(Creature self) {
			self.modifyDefenseValue(-3);
			self.notify("Your skin is back to normal.");
		}
	});

		// SELFCASTS ( NOT CLASSBOUND )
	
	public Skill skillLastStand = new Skill("Last Stand", 10, 10, new Effect("SkillSelfcast", 4){
		public void start(Creature self) {
			self.modifyHp(25);
			self.modifyDefenseValue(2);
			self.modifyAttackValue(5);
			self.notify("You prepare for a last stand.");
		}
		
		public void end(Creature self) {
			self.modifyDefenseValue(-2);
			self.modifyAttackValue(-5);
			self.notify("The adrenalin rush comes to an end.");
		}
	});
	
	public Skill skillBloodSacrifice = new Skill("Blood Sacrifice", 2, 2, new Effect("SkillSelfcast", 5){
		public void start(Creature self) {
			self.modifyHp(-10);
			self.modifyDefenseValue(1);
			self.modifyAttackValue(2);
			self.notify("You exchange some blood for power of an ancient being.");
		}
		
		public void end(Creature self) {
			self.modifyDefenseValue(-1);
			self.modifyAttackValue(-2);
			self.notify("The blood gods' blessing fades.");
		}
	});

	public Skill skillHunger = new Skill("Hunger", 10, 10, new Effect("SkillSelfcast", 1){
		public void start(Creature self) {
			self.modifyFood(55);
			self.notify("You scrub something off a wall and eat it.");
		}
	});

	public Skill skillThirst = new Skill("Thirst", 10, 10, new Effect("SkillSelfcast", 1){
		public void start(Creature self) {
			self.modifyThirst(11);
			self.notify("You drink some water from a random puddle.");
		}
	});

	public Skill skillMessySacrifice = new Skill("Messy Sacrifice", 10, 10, new Effect("SkillSelfcast", 1){
		public void start(Creature self) {
			self.addEffect(Bleeding(10));
			self.modifyMaxHp(-10);
			self.modifyHp(40);
			self.notify("You cut off a piece of your flesh and sacrifice it.");
		}
	});

	public Skill skillFloatingWeapon = new Skill("Summon Floating Weapon", 15, 15, new Effect("SkillSelfcast", 7){
		public void start(Creature self) {
			self.modifyAttackValue(10);
			self.modifyDefenseValue(2);
			self.notify("You summon a floating weapon from thin air.");
		}
		
		public void end(Creature self) {
			self.modifyAttackValue(-10);
			self.modifyDefenseValue(-2);
			self.notify("The floating weapon desintegrades into nothingness.");
		}
	});

	public Skill skillFloatingShield = new Skill("Summon Floating Shield", 15, 15, new Effect("SkillSelfcast", 7){
		public void start(Creature self) {
			self.modifyAttackValue(2);
			self.modifyDefenseValue(10);
			self.notify("You summon a floating shield from thin air.");
		}
		
		public void end(Creature self) {
			self.modifyAttackValue(-2);
			self.modifyDefenseValue(-10);
			self.notify("The floating shield desintegrades into nothingness.");
		}
	});
	
		// RANGED
	
	public Skill skillDistantStrike = new Skill("Distant Strike", 3, 3, new Effect("SkillRanged", 1){
		public void start(Creature self, Creature other) {
			if (self.dexterityValue() >= 4 && Math.random() > 0.2) {
				int skillAttack = self.attackValue() / 3;
				other.modifyHp(-skillAttack);
				self.notify("You slash the air and its current forms a sharp air-blade!");
				self.notify("The air-blade hits the %s for %d damage!", other.name(), skillAttack);
			} else {
				self.notify("You slash the air but fail to shape its currents!");
			}
		}
	});
	
	public Skill skillRockPrison = new Skill("Rock Prison", 4, 4, new Effect("SkillRanged", 2){
		public void start(Creature self, Creature other) {
			if (self.dexterityValue() >= 5 && Math.random() > 0.2) {
				int skillAttack = self.attackValue() / 5;
				other.modifyHp(-skillAttack);
				other.addEffect(Stun(duration));
				self.notify("You terraform some rocks and catch the %s inside!", other.name());
				self.notify("The rocks pierce the %s for %d damage!", other.name(), skillAttack);
			} else {
				self.notify("You fail to terraform the rocks!");
			}
		}
	});
	
	public Skill skillBoneMissile = new Skill("Bone Missile", 5, 5, new Effect("SkillRanged", 1){
		public void start(Creature self, Creature other) {
			self.modifyHp(-15);
			self.modifyMaxHp(-10);
			other.modifyHp(- (int)(15 * Math.random()) );
			self.notify("You use one of your bones to make a missile and hurt the %s!", other.name());
		}
	});
	
	public Skill skillBloodBoil = new Skill("Blood Boil", 5, 5, new Effect("SkillRanged", 1){
		public void start(Creature self, Creature other) {
			int skillAttack = self.maxMana() + self.mana() / 2;
			other.modifyHp(-skillAttack);
			self.notify("You use your power to boil the blood of the %s!", other.name());
		}
	});
	
		// MELEE
	
	public Skill skillImplale = new Skill("Impale", 5, 5, new Effect("SkillMelee", 1){
		public void start(Creature self, Creature other) {
			if (self.dexterityValue() > other.dexterityValue() - 2) {
				int skillAttack = self.attackValue() / 2;
				other.modifyHp(-skillAttack);
				other.addEffect(Bleeding(3));
				self.notify("You use your weapon and surrounding terrain to impale your enemy!");
				self.notify("The %s recieves %d damage and starts to bleed!", other.name(), skillAttack);
			} else {
				self.notify("You failed to impale your target!");
			}
		}
	});
	
	public Skill skillMesmer = new Skill("Mesmer", 10, 10, new Effect("SkillMelee", 5){
		public void start(Creature self, Creature other) {
			if (self.dexterityValue() >= 5 && Math.random() > 0.2) {
				other.addEffect(Stun(duration));
				self.notify("You mesmer the %s and it freeses in place!", other.name());
			} else {
				self.notify("You failed to mesmer your target!");
			}
		}
	});
	
	public Skill skillLifeSteal = new Skill("Life Steal", 10, 10, new Effect("SkillMelee", 1){
		public void start(Creature self, Creature other) {
			if (self.dexterityValue() >= 6 && Math.random() > 0.2) {
				int skillAttack = other.defenseValue() / 2;
				self.modifyHp(skillAttack);
				other.modifyHp(-skillAttack);
				self.notify("You suck some life force out of the %s !", other.name());
			} else {
				self.notify("You failed to use the skill!");
			}
		}
	});
	
	// QUALITIES SELECTOR
	
	private void qualitySelector(Item item) {
		if (item.isWeapon()) {
			meleeQualitySelector(item);
		} else if (item.isRanged()) {
			rangedQualitySelector(item);
		} else if (item.isArmor() || item.isShield()) {
			armorQualitySelector(item);
		} else if (item.isThrown()) {
			thrownQualitySelector(item);
		}
	}
	
	// QUALITIES ( MELEE WEAPONS )
	
	private void common(Item weapon) {
		weapon.setName( weapon.name() + " ( Common )");
	}
	
	private void uncommonMelee(Item weapon) {
		weapon.modifyAttackValue(2);
		weapon.modifyDefenseValue(1);
		weapon.setName( weapon.name() + " ( Uncommon )");
	}
	
	private void specialMelee(Item weapon) {
		weapon.modifyAttackValue(3);
		weapon.modifyDefenseValue(2);
		weapon.modifyDexterityValue(1);
		weapon.setName( weapon.name() + " ( Special )");
	}
	
	private void legendaryMelee(Item weapon) {
		weapon.modifyAttackValue(5);
		weapon.modifyDefenseValue(2);
		weapon.modifyDexterityValue(2);
		weapon.setName( weapon.name() + " ( Legendary )");
	}
	
	private void godlikeMelee(Item weapon) {
		weapon.modifyAttackValue(10);
		weapon.modifyDefenseValue(5);
		weapon.modifyDexterityValue(3);
		weapon.setName( weapon.name() + " ( Godlike )");
	}
	
	private void meleeQualitySelector(Item weapon) {
		double chance = Math.random();
		
		if (chance >= 0.50) {
			common(weapon);
		} else if (chance >= 0.20) {
			uncommonMelee(weapon);
		} else if (chance >= 0.06) {
			specialMelee(weapon);
		} else if (chance >= 0.02) {
			legendaryMelee(weapon);
		} else {
			godlikeMelee(weapon);
		}
	}
	
	// QUALITIES ( RANGED WEAPONS )
	
	private void uncommonRanged(Item weapon) {
		weapon.modifyRangedAttackValue(1);
		weapon.modifyDefenseValue(1);
		weapon.setName( weapon.name() + " ( Uncommon )");
	}
	
	private void specialRanged(Item weapon) {
		weapon.modifyRangedAttackValue(3);
		weapon.modifyDefenseValue(1);
		weapon.modifyDexterityValue(1);
		weapon.setName( weapon.name() + " ( Special )");
	}
	
	private void legendaryRanged(Item weapon) {
		weapon.modifyRangedAttackValue(5);
		weapon.modifyDefenseValue(2);
		weapon.modifyDexterityValue(2);
		weapon.setName( weapon.name() + " ( Legendary )");
	}
	
	private void godlikeRanged(Item weapon) {
		weapon.modifyRangedAttackValue(10);
		weapon.modifyDefenseValue(2);
		weapon.modifyDexterityValue(4);
		weapon.setName( weapon.name() + " ( Godlike )");
	}
	
	private void rangedQualitySelector(Item weapon) {
		double chance = Math.random();
		
		if (chance >= 0.50) {
			common(weapon);
		} else if (chance >= 0.20) {
			uncommonRanged(weapon);
		} else if (chance >= 0.06) {
			specialRanged(weapon);
		} else if (chance >= 0.02) {
			legendaryRanged(weapon);
		} else {
			godlikeRanged(weapon);
		}
	}
	
	// QUALITIES ( THROWN WEAPONS )
	
	private void uncommonThrow(Item weapon) {
		weapon.modifyThrownAttackValue(1);
		weapon.setName( weapon.name() + " ( Uncommon )");
	}
	
	private void specialThrow(Item weapon) {
		weapon.modifyThrownAttackValue(3);
		weapon.setName( weapon.name() + " ( Special )");
	}
	
	private void legendaryThrow(Item weapon) {
		weapon.modifyThrownAttackValue(5);
		weapon.setName( weapon.name() + " ( Legendary )");
	}
	
	private void godlikeThrow(Item weapon) {
		weapon.modifyThrownAttackValue(10);
		weapon.setName( weapon.name() + " ( Godlike )");
	}
	
	private void thrownQualitySelector(Item weapon) {
		double chance = Math.random();
		
		if (chance >= 0.50) {
			common(weapon);
		} else if (chance >= 0.20) {
			uncommonThrow(weapon);
		} else if (chance >= 0.06) {
			specialThrow(weapon);
		} else if (chance >= 0.02) {
			legendaryThrow(weapon);
		} else {
			godlikeThrow(weapon);
		}
	}
	
	// QUALITIES ( ARMOR & SHIELDS )
	
	private void uncommonArmor(Item armor) {
		armor.modifyDefenseValue(1);
		armor.setName( armor.name() + " ( Uncommon )");
	}
	
	private void specialArmor(Item armor) {
		armor.modifyDefenseValue(3);
		armor.modifyDexterityValue(1);
		armor.setName( armor.name() + " ( Special )");
	}
	
	private void legendaryArmor(Item armor) {
		armor.modifyDefenseValue(5);
		armor.modifyDexterityValue(2);
		armor.setName( armor.name() + " ( Legendary )");
	}
	
	private void godlikeArmor(Item armor) {
		armor.modifyDefenseValue(10);
		armor.modifyDexterityValue(3);
		armor.setName( armor.name() + " ( Godlike )");
	}
	
	private void armorQualitySelector(Item armor) {
		double chance = Math.random();
		
		if (chance >= 0.50) {
			common(armor);
		} else if (chance >= 0.20) {
			uncommonArmor(armor);
		} else if (chance >= 0.06) {
			specialArmor(armor);
		} else if (chance >= 0.02) {
			legendaryArmor(armor);
		} else {
			godlikeArmor(armor);
		}
	}
	
	// RANDOMISERS
	
	public Skill randomSkill() {
		switch ((int)(Math.random() * 13)) {
			case 0: return skillLastStand;
			case 1: return skillRockPrison;
			case 2: return skillImplale;
			case 3: return skillBloodSacrifice;
			case 4: return skillHunger;
			case 5: return skillThirst;
			case 6: return skillMessySacrifice;
			case 7: return skillFloatingWeapon;
			case 8: return skillFloatingShield;
			case 9: return skillBoneMissile;
			case 10: return skillBloodBoil;
			case 11: return skillMesmer;
			case 12: return skillLifeSteal;
			default: return skillDistantStrike;
		}
	}
	
	public Item randomItem(int depth){
	    switch ((int)(Math.random() * 4)){
			case 0: return newTorch(depth);
			case 1: return newStick(depth);
			case 2: return newBowl(depth);
			default: return newRock(depth);
	    }
	}
	
	public Item randomFood(int depth){
	    switch ((int)(Math.random() * 5)){
			case 0: return newApple(depth);
			case 1: return newBread(depth);
			case 2: return newWater(depth);
			case 3: return newSpoiledSoup(depth);
			default: return newUnknownFlesh(depth);
	    }
	}
	
	public Item randomWeapon(int depth){
	    switch ((int)(Math.random() * 10)){
			case 0: return newDagger(depth);
			case 1: return newSword(depth);
			case 2: return newBow(depth);
			case 3: return newJavelin(depth);
			case 4: return newShuriken(depth);
			case 5: return newFlail(depth);
			case 6: return newExe(depth);
			case 7: return newMorningStar(depth);
			case 8: return newCrossbow(depth);
			default: return newStaff(depth);
	    }
	}
	
	public Item randomShield(int depth) {
		switch((int)(Math.random() * 5)) {
			case 0: return newBuckler(depth);
			case 1: return newRoundShield(depth);
			case 2: return newSturdyShield(depth);
			case 3: return newMetalShield(depth);
			default: return newTowerShield(depth);
		}
	}

	public Item randomArmor(int depth){
	    switch ((int)(Math.random() * 6)){
			case 0: return newLightArmor(depth);
			case 1: return newMediumArmor(depth);
			case 2: return newHeavyArmor(depth);
			case 3: return newWoolArmor(depth);
			case 4: return newReinforcedArmor(depth);
			case 5: return newStealthArmor(depth);
			default: return newDustyArmor(depth);
	    }
	}
	
	public Creature randomLowerCreature(int depth, Creature player){
	    switch ((int)(Math.random() * 7)){
			case 0: return newBat(depth);
			case 1: return newCrazedArcher(depth, player);
			case 2: return newSlime(depth, player);
			case 3: return newBones(depth, player);
			case 4: return newKobolt(depth, player);
			case 5: return newRat(depth, player);
			default: return newFungus(depth, player);
	    }
	}
	
	public Creature randomMediumCreature(int depth, Creature player){
	    switch ((int)(Math.random() * 5)){
			case 0: return newHoundMaster(depth, player);
			case 1: return newGoblin(depth, player);
			case 2: return newCrazedSwordsman(depth, player);
			case 3: return newTroll(depth, player);
			default: return newFungus(depth, player);
	    }
	}
	
	public Creature randomHigherCreature(int depth, Creature player){
	    switch ((int)(Math.random() * 6)){
			case 0: return newLich(depth, player);
			case 1: return newMutant(depth, player);
			case 2: return newDemon(depth, player);
			case 3: return newTroll(depth, player);
			case 4: return newNightmare(depth, player);
			default: return newFungus(depth, player);
	    }
	}
	
	public Item randomPotion(int depth){
        switch ((int)(Math.random() * 6)){
			case 0: return newPotionOfHealth(depth);
			case 1: return newPotionOfPoison(depth);
			case 2: return newPotionOfMana(depth);
			case 3: return newPotionOfVision(depth);
			case 4: return newPotionOfBlindness(depth);
			default: return newPotionOfWarrior(depth);
        }
	}
	
	public Item randomWand(int depth) {
		switch((int)(Math.random() * 6)){
			case 0: return newWandOfLightning(depth);
			case 1: return newWandOfHealing(depth);
			case 2: return newWandOfDebuff(depth);
			case 3: return newWandOfPoison(depth);
			case 4: return newWandOfMissile(depth);
			default: return newWandOfFire(depth);
		}
	}
	
	public Effect randomDebuff(int duration) {
		switch((int)(Math.random() * 5)){
			case 0: return Bleeding(duration);
			case 1: return Stun(duration);
			case 2: return Burn(duration);
			case 3: return TempBlind(duration, 2);
			default: return Poisoned(duration);
	}
	}
	
	public Item randomBook(int depth){
        switch ((int)(Math.random() * 4)){
			case 0: return newBlueMagesSpellbook(depth);
			case 1: return newRedMagesSpellbook(depth);
			case 2: return newOccultSpellbook(depth);
			default: return newWhiteMagesSpellbook(depth);
        }
	}
	
	public Item randomScrolls(int depth){
        switch ((int)(Math.random() * 11)){
			case 0: return newScrollLightning(depth);
			case 1: return newScrollPoison(depth);
			case 2: return newScrollShield(depth);
			case 3: return newScrollSupply(depth);
			case 4: return newScrollMeat(depth);
			case 5: return newScrollJavelin(depth);
			case 6: return newScrollSoup(depth);
			case 7: return newScrollBandage(depth);
			case 8: return newScrollPaste(depth);
			case 9: return newScrollWater(depth);
			default: return newScrollHeal(depth);
        }
	}

	public Item randomUsables(int depth, Creature player){
		switch ((int)(Math.random() * 3)){
			case 0: return newAntidote(depth, player);
			case 1: return newRestoreLeaf(depth, player);
			default: return newDirtyBandages(depth, player);
		}
	}
}