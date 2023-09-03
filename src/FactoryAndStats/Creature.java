package FactoryAndStats;

import java.awt.Color;
import java.util.*;

import Ai.CreatureAi;
import WorldBuilder.Line;
import WorldBuilder.Point;
import WorldBuilder.Tile;
import WorldBuilder.World;

import Screen.Screen;

public class Creature {
	public World world;

	public int x, y, z;

	private List<Effect> effects;

	public List<Effect> effects() {
		return effects;
	}

	private String name;

	public String name() {
		return name;
	}

	private char glyph;

	public char glyph() {
		return glyph;
	}

	private Color color;

	public Color color() {
		return color;
	}

	private CreatureAi ai;

	public CreatureAi ai() {
		return ai;
	}

	public void setCreatureAi(CreatureAi ai) {
		this.ai = ai;
	}

	private int maxHp;

	public int maxHp() {
		return maxHp;
	}

	private int hp;

	public int hp() {
		return hp;
	}
	
	public void setHp(int amount) {
		hp = amount;
	}

	public boolean diedCrafting = false;

	private int attackValue;

	public int attackValue() {
		return attackValue + (weapon == null ? 0 : weapon.attackValue()) + (armor == null ? 0 : armor.attackValue())
				+ (shield == null ? 0 : shield.attackValue());
	}

	public void modifyAttackValue(int amount) {
		attackValue += amount;
		if (attackValue < 1) {
			attackValue = 1;
		}
	}

	private int defenseValue;

	public int defenseValue() {
		return defenseValue + (weapon == null ? 0 : weapon.defenseValue()) + (armor == null ? 0 : armor.defenseValue())
				+ (shield == null ? 0 : shield.defenseValue());
	}

	public void modifyDefenseValue(int amount) {
		defenseValue += amount;
		if (defenseValue < 1) {
			defenseValue = 1;
		}
	}

	private int dexterityValue;

	public int dexterityValue() {
		return dexterityValue + (weapon == null ? 0 : weapon.dexterityValue())
				+ (armor == null ? 0 : armor.dexterityValue()) + (shield == null ? 0 : shield.dexterityValue());
	}

	public void modifyDexterity(int amount) {
		dexterityValue += amount;
		if (dexterityValue < 1) {
			dexterityValue = 1;
		}
		
	}

	private int visionRadius = 5;

	public int visionRadius() {
		return visionRadius + (weapon == null ? 0 : weapon.visionRadius());
	}

	public void modifyVision(int amount) {
		visionRadius += amount;
	}

	private Inventory inventory;

	public Inventory inventory() {
		return inventory;
	}

	private RecipesRemembered recipes;

	public RecipesRemembered recipes() {
		return recipes;
	}
	
	private SkillsRemembered skillsRemembered;
	
	public SkillsRemembered skills() {
		return skillsRemembered;
	}

	public boolean canSee(int wx, int wy, int wz) {
		return (detectCreatures > 0 && world.creature(wx, wy, wz) != null || ai.canSee(wx, wy, wz));
	}

	private int maxFood;

	public int maxFood() {
		return maxFood;
	}

	private int food;

	public int food() {
		return food;
	}

	public void modifyFood(int amount) {
		food += amount;

		if (food > maxFood && isPlayer()) {
			maxFood = maxFood + (food - maxFood) / 2;
			food = maxFood;
			notify("You can't believe your stomach can hold that much!");
			modifyHp(-10);
		} else if (food < 0 && isPlayer()) {
			food = 0;
			modifyHp(-25);
			notify("The pain in your stomach reminds you of how hungry you are.");

		} else if (food > (maxFood / 2)) {
			modifyHp(2);
			notify("Your stomach feels pleasantly full.");
		}
	}

	private int thirst = 60;

	public int thirst() {
		return thirst;
	}

	public void modifyThirst(int amount) {
		thirst += amount;

		if (thirst > 100 && isPlayer()) {
			thirst = 100;
		} else if (thirst < 0 && isPlayer()) {
			thirst = 0;
			modifyHp(-25);
			notify("You are severely dehydrated.");
		} else if (thirst > (100 / 2)) {
			modifyHp(2);
			notify("Your thirst is satisfied.");
		}
	}

	public boolean isPlayer() {
		return glyph == '@';
	}

	public void eat(Item item) {
		if (item.isDrink) {
			doAction("drink " + item.name());
			consume(item);
		} else if (item.isFood) {
			doAction("eat " + item.name());
			consume(item);
		} else if (item.isAScroll()) {
			doAction("read " + item.name());
			consume(item);
		} else if (item.isAUsable) {
			doAction("use " + item.name());
			consume(item);
		} else if (item.isZappable) {
			doAction("zap " + item.name());
			if (item.uses() > 1) {
				item.modifyUses(-1);
			} else {
				notify("The wand used all of its magic and crumbles in your hands.");
				consume(item);
			}
		}
	}

	private void consume(Item item) {
		addEffect(item.quaffEffect());
		addEffect(item.foodEffect());

		modifyFood(item.foodValue());
		if (item.thirstValue() != 0)
			modifyThirst(item.thirstValue());

		getRidOf(item);
	}

	public void addEffect(Effect effect) {
		if (effect == null) {
			return;
		}

		effects.add(effect);
		effect.start(this);
	}
	
	public void addEffect(Effect effect, Creature target, Creature caster) {
		if (effect == null) {
			return;
		}

		target.effects.add(effect);
		effect.start(caster, target);
	}

	private boolean isStunned = false;

	public boolean isStunned() {
		return isStunned;
	}

	public void setStunned(boolean state) {
		isStunned = state;
	}

	private void updateEffects() {
		List<Effect> done = new ArrayList<Effect>();

		for (Effect effect : effects) {
			effect.update(this);
			if (effect.isDone()) {
				effect.end(this);
				done.add(effect);
			}
		}
		regenerateMana();
		effects.removeAll(done);
	}
	
	private void updateSkillCooldowns() {
		skillsRemembered.update();
	}

	public void addRecipe(Spell recipe) {
		if (recipes.isFull()) {
			notify("Your head is already full of all sorts of things to make!");
		} else {
			recipes.add(recipe);
		}
	}
	
	public void addSkill(Skill skill) {
		if (skillsRemembered.isFull()) {
			notify("You don't think that you can learn any new moves.");
		} else {
			skillsRemembered.add(skill);
		}
	}
	
	public void addStarterSkills(Skill skill) {
		skillsRemembered.add(skill);
	}

	private int level;

	public int level() {
		return level;
	}

	private boolean isGhost;

	public void setGhost() {
		isGhost = true;
	}

	public boolean getGhost() {
		return isGhost;
	}
	

	public Creature(World world, char glyph, Color color, int maxHp, int attack, int defense, int dex, String name) {
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.attackValue = attack;
		this.defenseValue = defense;
		this.dexterityValue = dex;
		this.name = name;
		this.inventory = new Inventory(10);
		this.recipes = new RecipesRemembered(20);
		this.skillsRemembered = new SkillsRemembered(10);
		this.maxFood = 1000;
		this.food = 500;
		this.level = 1;
		this.effects = new ArrayList<Effect>();
	}

	public void moveBy(int mx, int my, int mz) {
		if (mx == 0 && my == 0 && mz == 0)
			return;
		Tile tile = world.tile(x + mx, y + my, z + mz);

		if (mz == -1) {
			if (tile == Tile.STAIRS_DOWN) {
				doAction("walk up the stairs to level %d", z + mz + 1);
			} else {
				doAction("try to go up but there are no stairs to climb");
				return;
			}
		} else if (mz == 1) {
			if (tile == Tile.STAIRS_UP) {
				doAction("walk down the stairs to level %d", z + mz + 1);
			} else {
				doAction("try to go down but are there are no stairs to descend");
				return;
			}
		}

		Creature other = world.creature(x + mx, y + my, z + mz);

		if (other == null) {
			ai.onEnter(x + mx, y + my, z + mz, tile);
			playSound("step");
		} else if (isGhost) {
			haunt(other);
		} else
			attack(other);
	}

	public void haunt(Creature target) {
		if (target.mana() > 0) {
			target.modifyMana(-1);
		} else {
			target.modifyHp(-5);
		}
		switch ((int) (Math.random() * 3)) {
		case 0:
			target.notify("The %s spooks around you!", this.name());
			break;
		case 1:
			target.notify("The %s suddenly appears right in front of you!", this.name());
			break;
		default:
			target.notify("You feel uneasy while the %s watches you!", this.name());
			break;
		}
	}

	public void doAction(String message, Object... params) {
		int r = 9;
		for (int ox = -r; ox < r + 1; ox++) {
			for (int oy = -r; oy < r + 1; oy++) {
				if (ox * ox + oy * oy > r * r)
					continue;

				Creature other = world.creature(x + ox, y + oy, z);

				if (other == null)
					continue;

				if (other == this)
					other.notify("You " + message + ".", params);
				else if (other.canSee(x, y, z))
					other.notify(String.format("The %s %s.", name, makeSecondPerson(message)), params);
			}
		}
	}

	private String makeSecondPerson(String text) {
		String[] words = text.split(" ");
		words[0] = words[0] + "s";

		StringBuilder builder = new StringBuilder();
		for (String word : words) {
			builder.append(" ");
			builder.append(word);
		}

		return builder.toString().trim();
	}

	public void pickup() {
		Item item = world.item(x, y, z);

		if (item == null) {
			doAction("find nothing on the ground..");
		} else if (inventory.isFull()) {
			doAction("don't have enough pockets to stash %s", item.name());
		} else {
			doAction("pickup %s", item.name());
			world.remove(x, y, z);
			inventory.add(item);
		}
	}

	public void somethingOnTheGround() {
		Tile tile = world.tile(x, y, z);

		if (tile.tilename().equals("poison")) {
			notify("You step into the pool of weak poison, your legs hurt a bit");
			modifyHp(-5);
		} else if (tile.tilename().equals("lava")) {
			notify("You step into the lava, your feet start to burn");
			modifyHp(-15);
		}

		Item item = world.item(x, y, z);

		if (item == null) {
			return;
		} else {
			notify("You see %s on the ground", item.name());
		}
	}

	public void drop(Item item) {
		if (world.addAtEmptySpace(item, x, y, z)) {
			doAction("drop " + item.name());
			inventory.remove(item);
			if (item == armor) {
				armor = null;
			} else if (item == weapon) {
				weapon = null;
			} else if (item == shield) {
				shield = null;
			}
		}
	}

	private void attackCrit(Creature other) {
		int hit = Math.max(0, attackValue() - other.defenseValue());
		hit = ((int) (Math.random() * hit) + 1) * (3 / 2);

		if (this.isPlayer()) {
			notify("You land a critical hit on %s and deal %d damage!", other.name, hit);
		} else if (other.isPlayer()) {
			other.notify("The %s lands a critical hit on you and deals %d damage!", name, hit);
		}
		other.modifyHp(-hit);

		playSound("slash");

		if (weapon() != null) {
			other.addEffect(this.weapon.weaponEffect());
			if (this.isPlayer()) {
				notify("Your weapon inflicts %s on %s!", this.weapon.weaponEffect().getName(), other.name());
			}
		}

		if (other.hp < 1) {
			gainXp(other);
			deathHandling(other.name);
		}
	}

	private void attackHit(Creature other) {
		if (other.shield() != null && Math.random() <= other.shield().shieldConstant) {
			notify("The %s blocks your attack!", other.name());
			other.notify("You block an attack with your shield, %s looks furious!", name());
			playSound("parry");
		} else {
			int hit = (int)((Math.max(0, attackValue() - other.defenseValue())) * Math.random()) + 1;

			if (this.isPlayer()) {
				notify("You attack the %s for %d damage.", other.name, hit);
			} else if (other.isPlayer()) {
				other.notify("The %s attacks you for %d damage.", name, hit);
			}
			other.modifyHp(-hit);

			playSound("slash");

			if (weapon() != null && this.isPlayer() && this.weapon.weaponEffect() != null) {
				notify("Your weapon inflicts %s on %s!", this.weapon.weaponEffect().getName(), other.name());
				other.addEffect(this.weapon.weaponEffect());
			}

			if (other.hp < 1) {
				gainXp(other);
				deathHandling(other.name);
			}
		}
	}

	public void attack(Creature other) {
		int miss = (int) (Math.random() * dexterityValue()) + dexterityValue();

		if (miss >= dexterityValue() * 2) {

			attackCrit(other);

		} else if (miss >= other.dexterityValue()) {

			attackHit(other);

		} else {
			if (this.isPlayer()) {
				notify("You miss the %s!", other.name());
			} else if (other.isPlayer()) {
				other.notify("You evade the %s!", name());
			}
			playSound("miss");
		}
	}

	public void gainXp(Creature other) {
		int amount = other.maxHp / 5;

		if (amount > 0)
			modifyXp(amount);
	}

	public void modifyHp(int amount) {
		hp += amount;
		if (hp > maxHp) {
			hp = maxHp;
		}

		if (hp < 1) {
			leaveCorpse();
			if (Math.random() > 0.5) {
				world.addAtEmptySpace(newUnknownFlesh(z), x, y, z);
			}
			world.remove(this);
		}
	}

	public void modifyMaxHp(int amount) {
		maxHp += amount;
	}

	private void leaveCorpse() {
		Item corpse = new Item('%', color, name + " corpse", "A corpse of a slain monster. Is still eatable.");
		corpse.modifyFoodValue(maxHp * 2);
		corpse.isFood = true;
		world.addAtEmptySpace(corpse, x, y, z);
		for (Item item : inventory.getItems()) {
			if (item != null)
				drop(item);
		}
	}

	public Item newUnknownFlesh(int depth) {
		Item item = new Item('/', color, "a patch of unknown flesh", "A patch of weird-smelling meat.");
		item.modifyFoodValue(250);
		item.isFood = true;
		item.setFoodEffect(new Effect("Poison", 5) {
			public void start(Creature creature) {
				creature.doAction("look very sick");
			}

			public void update(Creature creature) {
				super.update(creature);
				creature.modifyHp(-10);
			}
		});
		world.addAtEmptyItemLocation(item, depth);
		return item;
	}

	public void dig(int wx, int wy, int wz) {
		modifyFood(-25);
		world.dig(wx, wy, wz);
		doAction("dig through a wall");
		playSound("dig");
	}

	public void update() {
		if (!isStunned()) {
			ai.onUpdate();
		}
		updateEffects();
		updateSkillCooldowns();
	}

	public boolean canEnter(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz).isGround() && world.creature(wx, wy, wz) == null;
	}

	public void notify(String message, Object... params) {
		ai.onNotify(String.format(message, params));
	}

	private Item weapon;

	public Item weapon() {
		return weapon;
	}

	private Item armor;

	public Item armor() {
		return armor;
	}

	private Item shield;

	public Item shield() {
		return shield;
	}

	public void unequip(Item item) {
		if (item == null)
			return;

		if (item == armor) {
			doAction("remove " + item.name());
			armor = null;
		} else if (item == weapon) {
			doAction("put away " + item.name());
			weapon = null;
		} else if (item == shield) {
			doAction("remove " + item.name());
			shield = null;
		}
	}

	public void equip(Item item) {
		if (!inventory.contains(item)) {
			if (inventory.isFull()) {
				notify("There are no pockets left to fill.", item.name());
				return;
			} else {
				world.remove(item);
				inventory.add(item);
			}
		}

		if (item.attackValue() == 0 && item.rangedAttackValue() == 0 && item.defenseValue() == 0
				&& item.visionRadius() == 0 && item.dexterityValue() == 0)
			return;

		if (item.isWeapon() || item.isRanged()) {
			unequip(weapon);
			doAction("wield " + item.name());
			weapon = item;
		} else if (item.isArmor()) {
			unequip(armor);
			doAction("put on " + item.name());
			armor = item;
		} else if (item.isShield()) {
			unequip(shield);
			doAction("fix %s on your arm", item.name());
			shield = item;
		}
	}
	
	public void startEquip(Item item) {
		inventory.add(item);

		if (item.isWeapon()) {
			weapon = item;
		} else if (item.isArmor()) {
			armor = item;
		} else if (item.isShield()) {
			shield = item;
		}
	}

	private int xp;

	public int xp() {
		return xp;
	}

	public void modifyXp(int amount) {
		xp += amount;

		notify("You %s %d xp.", amount < 0 ? "lose" : "gain", amount);

		while (xp >= (int) (Math.pow(level, 1.5) * 20)) {
			xp = xp - (int) (Math.pow(level, 1.5) * 20);
			level++;
			doAction("advance to level %d", level);
			ai.onGainLevel();
			modifyMaxHp(level * 2);
			modifyHp(maxHp);
		}
	}

	public void gainMaxHp(int amount) {
		maxHp += amount;
		hp += amount;
	}

	public void gainAttackValue(int amount) {
		attackValue += amount;
	}

	public void gainDefenseValue(int amount) {
		defenseValue += amount;
	}

	public void gainVision(int amount) {
		visionRadius += amount;
	}

	public void gainMaxMana(int amount) {
		maxMana += amount;
		mana += amount;
	}

	public void gainRegenMana(int amount) {
		regenManaPer1000 += amount;
	}

	public String details() {
		return String.format("     attack:%d     defense:%d     hp:%d", attackValue(), defenseValue(), hp);
	}

	public Tile realTile(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz);
	}

	public Tile tile(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz))
			return world.tile(wx, wy, wz);
		else
			return ai.rememberedTile(wx, wy, wz);
	}

	public Creature creature(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz))
			return world.creature(wx, wy, wz);
		else
			return null;
	}

	public Item item(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz))
			return world.item(wx, wy, wz);
		else
			return null;
	}

	public void throwItem(Item item, int wx, int wy, int wz) {
		Point end = new Point(x, y, 0);

		for (Point p : new Line(x, y, wx, wy)) {
			if (!realTile(p.x, p.y, z).isGround())
				break;
			end = p;
		}

		wx = end.x;
		wy = end.y;

		Creature c = creature(wx, wy, wz);

		if (c != null)
			throwAttack(item, c);
		else
			doAction("throw a %s", item.name());

		unequip(item);
		inventory.remove(item);

		playSound("miss");

		if (item.isDrink) {
			notify("The vial shatters on impact!");
		} else if ((double) Math.random() > 0.6) {
			world.addAtEmptySpace(item, wx, wy, wz);
		} else {
			notify("The item you threw breaks on impact!");
		}

	}

	private void thrownCrit(Item item, Creature other) {
		int amount = Math.max(0, attackValue / 2 + item.thrownAttackValue() - other.defenseValue());

		amount = ((int) (Math.random() * amount) + 1) * (3 / 2);
		
		if (this.isPlayer()) {
			notify("You throw %s at the %s and hit a weakspot for %d damage!", item.name(), other.name, amount);
		} else if (other.isPlayer()) {
			other.notify("The %s throws %s and hits your weakspot for %d damage!", name(), item.name(), amount);
		}


		other.addEffect(item.quaffEffect());
		other.addEffect(item.weaponEffect());
		if (this.isPlayer() && item.weaponEffect() != null) {
			notify("Your throw inflicts %s on %s!", item.quaffEffect().getName(), other.name());
		} else if (this.isPlayer() && item.quaffEffect() != null) {
			notify("Your weapon inflicts %s on %s!", item.weaponEffect().getName(), other.name());
		}

		other.modifyHp(-amount);

		if (other.hp <= 0) {
			gainXp(other);
			deathHandling(other.name);
		}
	}

	private void thrownHit(Item item, Creature other) {
		if (other.shield() != null && Math.random() <= other.shield().shieldConstant) {
			notify("The %s blocks your attack!", other.name());
			other.notify("You block an attack with your shield, %s looks furious!", name());
		} else {
			int amount = Math.max(0, attackValue / 2 + item.thrownAttackValue() - other.defenseValue());

			amount = (int) (Math.random() * amount) + 1;

			doAction("throw %s at the %s for %d damage", item.name(), other.name, amount);

			other.addEffect(item.quaffEffect());
			other.addEffect(item.weaponEffect());
			if (this.isPlayer() && item.weaponEffect() != null) {
				notify("Your throw inflicts %s on %s!", item.quaffEffect().getName(), other.name());
			} else if (this.isPlayer() && item.quaffEffect() != null) {
				notify("Your weapon inflicts %s on %s!", item.weaponEffect().getName(), other.name());
			}

			other.modifyHp(-amount);

			if (other.hp <= 0) {
				gainXp(other);
				deathHandling(other.name);
			}
		}
	}

	private void throwAttack(Item item, Creature other) {
		modifyFood(-5);
		int miss = (int) (Math.random() * dexterityValue()) + dexterityValue();
		if (miss >= dexterityValue() * 2) {

			thrownCrit(item, other);

		} else if (miss >= other.dexterityValue()) {

			thrownHit(item, other);

		} else {
			notify("You fail to hit your target!");
			other.notify("You evade an item thrown at you by the %s", name());
		}
	}

	private void rangedCrit(Creature other) {
		int amount = Math.max(0, attackValue / 2 + weapon.rangedAttackValue() - other.defenseValue());

		amount = ((int) (Math.random() * amount) + 1) * (3 / 2);

		notify("You fire %s and hit %s in a weakspot for %d damage!", weapon.name(), other.name, amount);
		other.notify("The %s attacks your weakspot with %s and deals %d damage!", name(), weapon.name(), amount);

		other.modifyHp(-amount);

		if (this.isPlayer() && this.weapon.name() != "a rifle") {
			playSound("shotArrow");
		} else if (this.isPlayer()) {
			playSound("shotMosin");
		}

		if (other.hp <= 0) {
			gainXp(other);
			deathHandling(other.name);
		}
	}

	private void rangedHit(Creature other) {
		if (other.shield() != null && Math.random() <= other.shield().shieldConstant) {
			notify("The %s blocks your attack!", other.name());
			other.notify("You block an attack with your shield, %s looks furious!", name());

			playSound("parry");
		} else {
			int amount = Math.max(0, attackValue / 2 + weapon.rangedAttackValue() - other.defenseValue());

			amount = (int) (Math.random() * amount) + 1;

			if (this.isPlayer()) {
				notify("You fire %s at %s for %d damage", weapon.name(), other.name, amount);
			} else if (other.isPlayer()) {
				other.notify("The %s attacks you with %s and deals %d damage", other.name, weapon.name(), amount);
			}

			other.modifyHp(-amount);

			if (this.isPlayer() && this.weapon.name() != "a rifle") {
				playSound("shotArrow");
			} else if (this.isPlayer()) {
				playSound("shotMosin");
			}

			if (other.hp <= 0) {
				gainXp(other);
				deathHandling(other.name);
			}
		}
	}

	public void rangedWeaponAttack(Creature other) {
		int miss = (int) (Math.random() * dexterityValue()) + dexterityValue();
		if (miss >= dexterityValue() * 2) {

			rangedCrit(other);

		} else if (miss >= other.dexterityValue()) {

			rangedHit(other);

		} else {
			notify("You miss the %s!", other.name());
			other.notify("You evade the projectile fired by the %s!", name());
			if (this.isPlayer() && this.weapon.name() != "a rifle") {
				playSound("miss");
			} else if (this.isPlayer()) {
				playSound("shotMosin");
			}
		}
	}

	private void getRidOf(Item item) {
		inventory.remove(item);
		unequip(item);
	}

	private void putAt(Item item, int wx, int wy, int wz) {
		inventory.remove(item);
		unequip(item);
		world.addAtEmptySpace(item, wx, wy, wz);
	}

	private int maxMana;

	public int maxMana() {
		return maxMana + (weapon == null ? 0 : weapon.maxMana()) + (armor == null ? 0 : armor.maxMana());
	}

	private int mana;

	public int mana() {
		return mana + (weapon == null ? 0 : weapon.mana()) + (armor == null ? 0 : armor.mana());
	}

	public void modifyMana(int amount) {
		mana += amount;
		if (mana > maxMana())
			mana = maxMana();
	}

	public void modifyMaxMana(int amount) {
		maxMana += amount;
	}

	private int regenManaCooldown;
	private int regenManaPer1000;

	public void modifyRegenManaPer1000(int amount) {
		regenManaPer1000 += amount;
	}

	private void regenerateMana() {
		regenManaCooldown -= regenManaPer1000;
		if (regenManaCooldown < 0) {
			if (mana < maxMana) {
				modifyMana(1);
			}
		}
	}

	public void summon(Creature other) {
		world.add(other);
	}

	private int detectCreatures;

	public void modifyDetectCreatures(int amount) {
		detectCreatures += amount;
	}

	public void castSpell(Spell spell, int x2, int y2) {
		Creature other = creature(x2, y2, z);

		if (spell.manaCost() > mana) {
			doAction("point and mumble but nothing happens!");
			return;
		} else if (other == null) {
			doAction("point and mumble at nothing.");
			modifyMana(-spell.manaCost());
			return;
		}

		if (spell.effect().getName().equals("Cook") || spell.effect().getName().equals("Craft")) {
			if (!this.recipes.contains(spell)) {
				addRecipe(spell);
			}
		}

		notify("You cast " + spell.name() + " on " + other.name() + "!");
		other.addEffect(spell.effect(), other, this);
		other.addEffect(spell.effect());
		modifyMana(-spell.manaCost());
		playSound("spellCast");
		if (other.hp <= 0) {
			gainXp(other);
			deathHandling(other.name);
		}
	}
	
	public void useSkill(Skill skill, int x2, int y2) {
		if (skill.cooldown() > 0) {
			notify("This skill is still on cooldown.");
		} else {
			Creature other = creature(x2, y2, z);
			
			if (other == null || (skill.effect().getName() == "SkillMelee" && (x2 > this.x+1 || x2 < this.x-1 || y2 > this.y+1 || y2 < this.y-1))) {
				notify("You can't use that skill there.");
			} else {
				notify("You use " + skill.name() + " on " + other.name() + "!");
				other.addEffect(skill.effect(), other, this);
				playSound("spellCast");
				if (other.hp <= 0) {
					gainXp(other);
					deathHandling(other.name);
				}
				skill.resetCooldown();
			}
		}
	}

	public void deathHandling(String nameVictim) {
		playSound("kill");

		if (nameVictim == "comrade") {
			int code = (int) (Math.random() * 5);
			switch (code) {
			case 0:
				notify("You hear a voice from near you: 'Not a step back, comrade!'");
				break;
			case 1:
				notify("You hear a voice from near you: 'YPA!'");
				break;
			case 2:
				notify("You hear a voice from near you: 'Za rodinu!'");
				break;
			case 3:
				notify("You hear a voice from near you: 'Vpered tovarishi!'");
				break;
			default:
				notify("You hear a voice from near you: 'Stand fast, comrade!'");
				break;
			}
		} else {
			int code = (int) (Math.random() * 5);
			switch (code) {
			case 0:
				notify("The %s lets out its final breath!", nameVictim);
				break;
			case 1:
				notify("You decapitate the %s!", nameVictim);
				break;
			case 2:
				notify("The %s falls from fatal wounds!", nameVictim);
				break;
			case 3:
				notify("Something inside the %s breaks and it falls on the ground!", nameVictim);
				break;
			default:
				notify("Your attack finishes the %s off!", nameVictim);
				break;
			}
		}
	}

	public void playSound(String name) {
		if (this.isPlayer()) {
			Screen.audioController.playSoundEffect(name);
		}
	}

}