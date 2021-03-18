package ascii_rl;

import asciiPanel.AsciiPanel;

import java.awt.Color;
import java.util.*;

public class StuffFactory {
	private World world;
	
	public StuffFactory(World world){
		this.world = world;
	}
	
	// EFFECTS
	
	public Effect Poisoned(int amount) {
		Effect poison = new Effect(amount){
	        public void start(Creature creature){
	            creature.notify("You feel the poison enter your bloodstream.");
	        }
	                        
	        public void update(Creature creature){
	            super.update(creature);
	            creature.notify("You feel the poison burning in your veins.");
	            creature.modifyHp(-5);
	        }
	    };
		return poison;
	}
	
	public Effect Bleeding(int amount) {
		Effect bleed = new Effect(amount){
	        public void start(Creature creature){
	            creature.notify("You notice that you're bleeding.");
	        }
	                        
	        public void update(Creature creature){
	            super.update(creature);
	            creature.notify("You notice that the bleeding doesn't stop.");
	            creature.modifyHp(-5);
	        }
	    };
		return bleed;
	}
	
	public Effect InstantMinorHeal() {
		Effect instantMinorHeal = new Effect(1){
	        public void start(Creature creature){
	            if (creature.hp() == creature.maxHp())
	                return;
	                                
	            creature.modifyHp(20);
	            creature.doAction("see some small wounds disappearing");
	        }
	    };
	    return instantMinorHeal;
	}
	
	public Effect MinorMana() {
		Effect minorMana = new Effect(1){
	        public void start(Creature creature){
	            if (creature.mana() == creature.maxMana())
	                return;
	                                
	            creature.modifyMana(20);
	            creature.doAction("feel the magical particles activating inside");
	        }
	    };
	    return minorMana;
	}
	
	// ITEMS
	
	public Item newArtifact(int depth){
        Item item = new Item('A', AsciiPanel.white, "an Artifact");
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newRock(int depth){
        Item item = new Item(',', AsciiPanel.white, "a sturdy rock");
        item.modifyAttackValue(2);
        item.modifyThrownAttackValue(2);
        item.setWeaponEffect(Bleeding(1));
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newBread(int depth){
        Item item = new Item('-', Color.ORANGE, "a loaf of bread");
        item.modifyFoodValue(150);
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newApple(int depth){
        Item item = new Item('*', AsciiPanel.red, "an apple");
        item.modifyFoodValue(50);
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newWater(int depth){
        Item item = new Item((char)247, AsciiPanel.blue, "a bottlefull of water");
        item.modifyFoodValue(10);
        item.modifyThirstValue(35);
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newTorch(int depth){
        Item item = new Item('[', AsciiPanel.yellow, "a torch");
        item.modifyVisionRadius(5);
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newUnknownFlesh(int depth){
        Item item = new Item('/', AsciiPanel.yellow, "a patch of unknown flesh");
        item.modifyFoodValue(250);
        item.setFoodEffect(Poisoned(10));
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newCookedMeat(int depth) {
		Item item = new Item('/', AsciiPanel.brightYellow, "a portion of cooked meat");
		item.modifyFoodValue(350);
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	// WEAPONS

	public Item newDagger(int depth){
		Item item = new Item(')', AsciiPanel.white, "a dagger");
		item.modifyAttackValue(5);
		item.modifyThrownAttackValue(5);
		item.setWeaponEffect(Bleeding(5));
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}

	public Item newSword(int depth){
		Item item = new Item(')', AsciiPanel.brightWhite, "a sword");
		item.modifyAttackValue(10);
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	public Item newCursedSword(int depth, Creature creature){
		Item item = new Item(')', AsciiPanel.red, "a cursed sword");
		item.modifyAttackValue(20);
		item.setWeaponEffect(Poisoned(5));
		creature.modifyMaxHp(-20);
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	public Item newGreatSword(int depth){
		Item item = new Item(')', AsciiPanel.brightWhite, "a greatsword");
		item.modifyAttackValue(15);
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}

	public Item newStaff(int depth){
		Item item = new Item(')', AsciiPanel.yellow, "a staff");
		item.modifyAttackValue(5);
		item.modifyDefenseValue(3);
		item.modifyMaxMana(10);
		item.modifyMana(10);
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	public Item newFlail(int depth){
		Item item = new Item(')', AsciiPanel.yellow, "a flail");
		item.modifyAttackValue(2);
		item.setWeaponEffect(Bleeding(2));
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}
	
	// RANGED AND THROWING WEAPONS
	
	public Item newBow(int depth){
        Item item = new Item(')', AsciiPanel.yellow, "a bow with arrows");
        item.modifyAttackValue(1);
        item.modifyRangedAttackValue(7);
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newJavelin(int depth){
        Item item = new Item(')', AsciiPanel.yellow, "a javelin");
        item.modifyAttackValue(3);
        item.modifyThrownAttackValue(10);
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	public Item newShuriken(int depth){
        Item item = new Item('*', AsciiPanel.yellow, "a shuriken");
        item.modifyThrownAttackValue(15);
        world.addAtEmptyItemLocation(item, depth);
        return item;
    }
	
	// ARMOR
  
	public Item newLightArmor(int depth){
	    Item item = new Item('[', AsciiPanel.green, "a tunic");
	    item.modifyDefenseValue(2);
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}

	public Item newMediumArmor(int depth){
	    Item item = new Item('[', AsciiPanel.white, "a chainmail");
	    item.modifyDefenseValue(4);
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}

	public Item newHeavyArmor(int depth){
	    Item item = new Item('[', AsciiPanel.brightWhite, "a platemail");
	    item.modifyDefenseValue(6);
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newArmorOfHealing(int depth, Creature creature){
	    Item item = new Item('[', AsciiPanel.red, "an armor of healing");
	    item.modifyDefenseValue(4);
	    item.setArmorEffect(new Effect(1){
	        public void update(Creature creature){
	            while (creature.hp() < 50 ) {
	            	creature.modifyHp(20);
	            	creature.doAction("feel the armor heal some injuries");
	            }
	        }
	    });
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	// RANDOMISERS
	
	public Item randomItem(int depth){
	    switch ((int)(Math.random() * 2)){
	    case 1: return newTorch(depth);
	    default: return newRock(depth);
	    }
	}
	
	public Item randomFood(int depth){
	    switch ((int)(Math.random() * 4)){
	    case 0: return newApple(depth);
	    case 1: return newBread(depth);
	    case 2: return newWater(depth);
	    default: return newUnknownFlesh(depth);
	    }
	}
	
	public Item randomWeapon(int depth){
	    switch ((int)(Math.random() * 7)){
	    case 0: return newDagger(depth);
	    case 1: return newSword(depth);
	    case 2: return newBow(depth);
	    case 3: return newJavelin(depth);
	    case 4: return newShuriken(depth);
	    case 5: return newFlail(depth);
	    default: return newStaff(depth);
	    }
	}

	public Item randomArmor(int depth){
	    switch ((int)(Math.random() * 3)){
	    case 0: return newLightArmor(depth);
	    case 1: return newMediumArmor(depth);
	    default: return newHeavyArmor(depth);
	    }
	}
	
	public Creature randomLowerCreature(int depth, Creature player){
	    switch ((int)(Math.random() * 4)){
	    case 0: return newBat(depth);
	    case 1: return newCrazedArcher(depth, player);
	    case 2: return newSlime(depth, player);
	    case 3: return newBones(depth, player);
	    default: return newFungus(depth, player);
	    }
	}
	
	public Creature randomMediumCreature(int depth, Creature player){
	    switch ((int)(Math.random() * 4)){
	    case 0: return newHoundMaster(depth, player);
	    case 1: return newGoblin(depth, player);
	    case 2: return newCrazedSwordsman(depth, player);
	    case 3: return newTroll(depth, player);
	    default: return newFungus(depth, player);
	    }
	}
	
	public Creature randomHigherCreature(int depth, Creature player){
	    switch ((int)(Math.random() * 4)){
	    case 0: return newCrazedSwordsman(depth, player);
	    case 1: return newCrazedArcher(depth, player);
	    case 2: return newDemon(depth, player);
	    case 3: return newTroll(depth, player);
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
	
	public Item randomBook(int depth){
        switch ((int)(Math.random() * 3)){
        case 0: return newBlueMagesSpellbook(depth);
        case 1: return newRedMagesSpellbook(depth);
        default: return newWhiteMagesSpellbook(depth);
        }
	}
	
	public Item randomScrolls(int depth){
        switch ((int)(Math.random() * 5)){
        case 0: return newScrollLightning(depth);
        case 1: return newScrollPoison(depth);
        case 2: return newScrollShield(depth);
        case 3: return newScrollSupply(depth);
        default: return newScrollHeal(depth);
        }
	}
	
	// CREATURES
	
	public Creature newPlayer(List<String> messages, FieldOfView fov){
		Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 10, 5, "human");
		world.addAtEmptyLocation(player, 0);
		new PlayerAi(player, messages, fov);
		return player;
	}
	
	public Creature newFungus(int depth, Creature player){
		Creature fungus = new Creature(world, 'f', AsciiPanel.magenta, 25, 5, 1, "fungus");
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus, this, player);
		return fungus;
	}
	
	public Creature newHoundMaster(int depth, Creature player){
	    Creature houndmaster = new Creature(world, 'H', AsciiPanel.brightYellow, 40, 10, 5, "houndmaster");
	    houndmaster.equip(newFlail(depth));
	    world.addAtEmptyLocation(houndmaster, depth);
	    new HoundMasterAi(houndmaster, this, player);
	    return houndmaster;
	}
	
	public Creature newHound(int depth, Creature player){
	    Creature hound = new Creature(world, 'h', AsciiPanel.yellow, 10, 25, 0, "hound");
	    world.addAtEmptyLocation(hound, depth);
	    new HoundAi(hound, player);
	    return hound;
	}
	
	public Creature newBat(int depth){
	    Creature bat = new Creature(world, 'b', AsciiPanel.white, 15, 1, 0, "bat");
	    world.addAtEmptyLocation(bat, depth);
	    new BatAi(bat);
	    return bat;
	}
	
	public Creature newSlime(int depth, Creature player){
	    Creature slime = new Creature(world, 's', AsciiPanel.green, 10, 10, 0, "slime");
	    world.addAtEmptyLocation(slime, depth);
	    new AiCommon(slime, player);
	    return slime;
	}
	
	public Creature newBones(int depth, Creature player){
	    Creature bones = new Creature(world, 'B', AsciiPanel.green, 30, 15, 0, "wandering bones");
	    bones.equip(randomArmor(depth));
	    world.addAtEmptyLocation(bones, depth);
	    new BonesAi(bones, player);
	    return bones;
	}
	
	public Creature newGoblin(int depth, Creature player){
	    Creature goblin = new Creature(world, 'g', AsciiPanel.green, 20, 10, 5, "goblin");
	    goblin.equip(randomWeapon(depth));
	    goblin.equip(randomArmor(depth));
	    world.addAtEmptyLocation(goblin, depth);
	    new AiCommon(goblin, player);
	    return goblin;
	}
	
	public Creature newCrazedArcher(int depth, Creature player){
        Creature archer = new Creature(world, 'a', AsciiPanel.brightBlack, 25, 10,  5, "crazed archer");
        archer.equip(newBow(depth));
        archer.equip(randomArmor(depth));
        world.addAtEmptyLocation(archer, depth);
        new CrazedArcherAi(archer, player);
        return archer;
    }
	
	public Creature newCrazedSwordsman(int depth, Creature player){
        Creature sword = new Creature(world, 's', AsciiPanel.brightBlack, 40, 10,  5, "crazed swordsman");
        sword.equip(newSword(depth));
        sword.equip(randomArmor(depth));
        world.addAtEmptyLocation(sword, depth);
        new AiCommon(sword, player);
        return sword;
    }
	
	public Creature newDemon(int depth, Creature player){
        Creature demon = new Creature(world, 's', AsciiPanel.red, 70, 20,  10, "demon");
        demon.equip(newCursedSword(depth, demon));
        demon.equip(newArmorOfHealing(depth, demon));
        world.addAtEmptyLocation(demon, depth);
        new DemonAi(demon, player);
        return demon;
    }
	
	public Creature newTroll(int depth, Creature player){
	    Creature troll = new Creature(world, 'T', AsciiPanel.white, 50, 10, 15, "troll");
	    troll.equip(newGreatSword(depth));
	    world.addAtEmptyLocation(troll, depth);
	    new TrollAi(troll, player);
	    return troll;
	}
	
	// POTIONS
	
	public Item newPotionOfHealth(int depth){
	    Item item = new Item('!', AsciiPanel.white, "a health potion");
	    item.modifyThirstValue(5);
	    item.setQuaffEffect(InstantMinorHeal());
	                
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newPotionOfPoison(int depth){
	    Item item = new Item('!', AsciiPanel.green, "a potion of poison");
	    item.modifyThirstValue(5);
	    item.setQuaffEffect(Poisoned(10));
	                
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newPotionOfWarrior(int depth){
	    Item item = new Item('!', AsciiPanel.white, "a warrior's potion");
	    item.modifyThirstValue(5);
	    item.setQuaffEffect(new Effect(20){
	        public void start(Creature creature){
	            creature.modifyAttackValue(5);
	            creature.modifyDefenseValue(5);
	            creature.doAction("look stronger");
	        }
	        public void end(Creature creature){
	            creature.modifyAttackValue(-5);
	            creature.modifyDefenseValue(-5);
	            creature.doAction("look less strong");
	        }
	    });
	                
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newPotionOfMana(int depth){
	    Item item = new Item('!', AsciiPanel.blue, "a mana potion");
	    item.modifyThirstValue(5);
	    item.setQuaffEffect(MinorMana());
	                
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	public Item newPotionOfVision(int depth){
	    Item item = new Item('!', AsciiPanel.brightBlack, "a potion of vision");
	    item.modifyThirstValue(5);
	    item.setQuaffEffect(new Effect(10){
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
	    Item item = new Item('!', AsciiPanel.brightBlack, "a potion of blindness");
	    item.modifyThirstValue(5);
	    item.setQuaffEffect(new Effect(5){
	        public void start(Creature creature){
	            creature.modifyVision(-2);
	            creature.doAction("can't see that well anymore");
	        }
	        public void end(Creature creature){
	        	creature.modifyVision(2);
	            creature.doAction("regain your original vision");
	        }
	    });
	                
	    world.addAtEmptyItemLocation(item, depth);
	    return item;
	}
	
	// BOOKS, SCROLLS, NOTES
	
	public Item newScrollMeat(int depth) {
		Item scroll = new Item('=', AsciiPanel.brightBlack, "a crafting scroll (cooked meat)");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("cook meat", 5, new Effect(1) {
			public void start(Creature player) {
				if (player.inventory().contains(newRock(depth)) && player.inventory().contains(newWater(depth)) && player.inventory().contains(newUnknownFlesh(depth))) { 
					player.inventory().remove(newRock(depth));
					player.inventory().remove(newWater(depth));
					player.inventory().remove(newUnknownFlesh(depth));
					player.notify("You use some mana to heat the stone and the water.");
					if (Math.random() > 0.3) {
						player.inventory().add(newCookedMeat(depth));
						player.notify("You manage to cook the meat nicely");
					} else {
						player.modifyHp(-5);
						player.notify("You were clumsy enough to throw boiling water over yourself.");
					}
				} else {
					player.notify("You don't have the needed items!");
				}
			}
		});
		world.addAtEmptyItemLocation(scroll, depth);
		return scroll;
	}
	
	public Item newScrollHeal(int depth) {
		Item scroll = new Item('=', AsciiPanel.brightBlack, "a scroll of healing");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("minor heal", 2, InstantMinorHeal());
		world.addAtEmptyItemLocation(scroll, depth);
		return scroll;
	}
	
	public Item newScrollLightning(int depth) {
		Item scroll = new Item('=', AsciiPanel.blue, "a scroll of lightning bolt");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("lightning bolt", 5, new Effect(1){
            public void start(Creature creature){
                creature.modifyHp(-20);
            }
        });
		world.addAtEmptyItemLocation(scroll, depth);
		return scroll;
	}
	
	public Item newScrollPoison(int depth) {
		Item scroll = new Item('=', AsciiPanel.brightBlack, "a scroll of poisoned bolt");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("poisoned bolt", 5, new Effect(1){
            public void start(Creature creature){
                creature.modifyHp(-10);
                creature.addEffect(Poisoned(5));
            }
        });
		world.addAtEmptyItemLocation(scroll, depth);
		return scroll;
	}
	
	public Item newScrollShield(int depth) {
		Item scroll = new Item('=', AsciiPanel.brightBlack, "a scroll of magic shield");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("magic shield", 6, new Effect(5){
            public void start(Creature creature){
                creature.modifyDefenseValue(5);
                creature.doAction("are covered by magical particles which protect your vitalas");
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
		Item scroll = new Item('=', AsciiPanel.brightBlack, "a scroll of supplies");
		scroll.setScroll(scroll);
		scroll.addWrittenSpell("magical supplies", 10,  new Effect(2){
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
        Item item = new Item('+', AsciiPanel.brightWhite, "a white mage's spellbook");
        item.addWrittenSpell("minor heal", 4, new Effect(1){
            public void start(Creature creature){
                if (creature.hp() == creature.maxHp())
                    return;
                
                creature.modifyHp(20);
                creature.notify("Some bruises and minor injuries heal within seconds.");
            }
        });
        
        item.addWrittenSpell("major heal", 8, new Effect(1){
            public void start(Creature creature){
                if (creature.hp() == creature.maxHp())
                    return;
                
                creature.modifyHp(50);
                creature.notify("Some nasty injuries heal within seconds.");
            }
        });
        
        item.addWrittenSpell("slow regenerate", 12, new Effect(20){
            public void update(Creature creature){
                super.update(creature);
                creature.modifyHp(2);
            }
        });

        item.addWrittenSpell("inner strength", 16, new Effect(5){
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
                if (Math.random() < 0.25)
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
        Item item = new Item('+', AsciiPanel.brightBlue, "a blue mage's spellbook");

        item.addWrittenSpell("blood to mana", 1, new Effect(1){
            public void start(Creature creature){
                int amount = Math.min(creature.hp() - 1, creature.maxMana() - creature.mana());
                creature.modifyHp(-amount);
                creature.modifyMana(amount);
            }
        });
        
        item.addWrittenSpell("blink", 6, new Effect(1){
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
        
        item.addWrittenSpell("detect creatures", 16, new Effect(75){
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
        Item item = new Item('+', AsciiPanel.red, "a red mage's spellbook");
        
        item.addWrittenSpell("leech lifeforce", 1, new Effect(1){
            public void start(Creature creature, Creature other){
                int amount = Math.min(creature.hp() - 15, creature.maxMana() - creature.mana());
                other.modifyHp(-amount);
                creature.modifyHp(amount);
            }
        });
        
        item.addWrittenSpell("missile of blood", 1, new Effect(1){
            public void start(Creature creature, Creature other){
                int amount = Math.min(creature.hp() - 10, creature.maxMana() - creature.mana());
                other.modifyHp(-amount * 3);
                creature.modifyHp(-amount);
            }
        });
        
        item.addWrittenSpell("summon bats", 11, new Effect(1){
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
}