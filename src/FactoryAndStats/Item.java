package FactoryAndStats;

import java.util.*;
import java.awt.Color;

public class Item {

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    private String name;
    public String name() { return name; }
    public void setName(String string) { name = string; }

    private String description;
    public String description() { return description; }
    
    private int uses;
    public int uses() { return uses; }
    public void modifyUses(int amount) { uses += amount; }
    public boolean isZappable;
    
    private int foodValue;
    public int foodValue() { return foodValue; }
    public void modifyFoodValue(int amount) { foodValue += amount; }
    public boolean isFood;
    
    private int thirstValue;
    public int thirstValue() { return thirstValue; }
    public void modifyThirstValue(int amount) { thirstValue += amount; }
    public boolean isDrink;
    
    private int attackValue;
    public int attackValue() { return attackValue; }
    public void modifyAttackValue(int amount) { attackValue += amount; }

    private int defenseValue;
    public int defenseValue() { return defenseValue; }
    public void modifyDefenseValue(int amount) { defenseValue += amount; }

    private int dexterityValue;
    public int dexterityValue() { return dexterityValue; }
    public void modifyDexterityValue(int amount) { dexterityValue += amount; }
    
    private boolean isWeapon;
    public boolean isWeapon() { return isWeapon; }
    public void setWeapon() { isWeapon = true; }
    
    private boolean isArmor;
    public boolean isArmor() { return isArmor; }
    public void setArmor() { isArmor = true; }
    
    private boolean isShield;
    public boolean isShield() { return isShield; }
    public void setShield() { isShield = true; }
    public double shieldConstant;
    
    private boolean isThrown;
    public boolean isThrown() { return isThrown; }
	public void setThrown() { isThrown = true; }

	private boolean isRanged;
	public boolean isRanged() { return isRanged; }
	public void setRanged() { isRanged = true; }
    
    private int visionRadius;
    public int visionRadius() { return visionRadius; }
    public void modifyVisionRadius(int amount) { visionRadius += amount; }
    
    private int thrownAttackValue = 1;
    public int thrownAttackValue() { return thrownAttackValue; }
    public void modifyThrownAttackValue(int amount) { thrownAttackValue += amount; }
    
    private int rangedAttackValue;
    public int rangedAttackValue() { return rangedAttackValue; }
    public void modifyRangedAttackValue(int amount) { rangedAttackValue += amount; }
    
    private Effect quaffEffect;
    public Effect quaffEffect() { return quaffEffect; }
    public void setQuaffEffect(Effect effect) { this.quaffEffect = effect; }
    
    private Effect weaponEffect;
    public Effect weaponEffect() { return weaponEffect; }
    public void setWeaponEffect(Effect effect) { this.weaponEffect = effect; }
    
    private Effect foodEffect;
    public Effect foodEffect() { return foodEffect; }
    public void setFoodEffect(Effect effect) { this.foodEffect = effect; }
    
    private Effect armorEffect;
    public Effect armorEffect() { return armorEffect; }
    public void setArmorEffect(Effect effect) { this.armorEffect = effect; }
    
    private boolean isAScroll;
    public boolean isAScroll() { return isAScroll; }
    public void setScroll(Item item) { this.isAScroll = true; }

    private int mana;
    public int mana() { return mana; }
    public void modifyMana(int amount) { mana += amount; }
    
    private int maxMana;
    public int maxMana() { return maxMana; }
    public void modifyMaxMana(int amount) { maxMana += amount; }

    public boolean isAUsable;
    
    private List<Spell> writtenSpells;
    public List<Spell> writtenSpells() { return writtenSpells; }
    
    public class ItemBuilder {
    	private char glyph;
        private Color color;
        private String name;
        private String description;
        private int uses;
        private boolean isZappable;
        private int foodValue;
        private boolean isFood;
        private int thirstValue;
        private boolean isDrink;
        private int attackValue;
        private int defenseValue;
        private int dexterityValue;
        private boolean isWeapon;
        private boolean isArmor;
        private boolean isShield;
        private double shieldConstant;
        private boolean isThrown;
    	private boolean isRanged;
        private int visionRadius;
        private int thrownAttackValue = 1;
        private int rangedAttackValue;
        private Effect quaffEffect;
        private Effect weaponEffect;
        private Effect foodEffect;
        private Effect armorEffect;
        private boolean isAScroll;
        private int mana;
        private int maxMana;
        private boolean isAUsable;
        private List<Spell> writtenSpells;
    	
    	public ItemBuilder(char glyph, Color color, String name, String description) {
            this.glyph = glyph;
            this.color = color;
            this.name = name;
            this.description = description;
            writtenSpells = new ArrayList<Spell>();
            uses = 1;
    	}

		public ItemBuilder setGlyph(char glyph) {
			this.glyph = glyph;
			return this;
		}

		public ItemBuilder setColor(Color color) {
			this.color = color;
			return this;
		}

		public ItemBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ItemBuilder setDescription(String description) {
			this.description = description;
			return this;
		}

		public ItemBuilder setUses(int uses) {
			this.uses = uses;
			return this;
		}

		public ItemBuilder setZappable(boolean isZappable) {
			this.isZappable = isZappable;
			return this;
		}

		public ItemBuilder setFoodValue(int foodValue) {
			this.foodValue = foodValue;
			return this;
		}

		public ItemBuilder setFood(boolean isFood) {
			this.isFood = isFood;
			return this;
		}

		public ItemBuilder setThirstValue(int thirstValue) {
			this.thirstValue = thirstValue;
			return this;
		}

		public ItemBuilder setDrink(boolean isDrink) {
			this.isDrink = isDrink;
			return this;
		}

		public ItemBuilder setAttackValue(int attackValue) {
			this.attackValue = attackValue;
			return this;
		}

		public ItemBuilder setDefenseValue(int defenseValue) {
			this.defenseValue = defenseValue;
			return this;
		}

		public ItemBuilder setDexterityValue(int dexterityValue) {
			this.dexterityValue = dexterityValue;
			return this;
		}

		public ItemBuilder setWeapon(boolean isWeapon) {
			this.isWeapon = isWeapon;
			return this;
		}

		public ItemBuilder setArmor(boolean isArmor) {
			this.isArmor = isArmor;
			return this;
		}

		public ItemBuilder setShield(boolean isShield) {
			this.isShield = isShield;
			return this;
		}

		public ItemBuilder setShieldConstant(double shieldConstant) {
			this.shieldConstant = shieldConstant;
			return this;
		}

		public ItemBuilder setThrown(boolean isThrown) {
			this.isThrown = isThrown;
			return this;
		}

		public ItemBuilder setRanged(boolean isRanged) {
			this.isRanged = isRanged;
			return this;
		}

		public ItemBuilder setVisionRadius(int visionRadius) {
			this.visionRadius = visionRadius;
			return this;
		}

		public ItemBuilder setThrownAttackValue(int thrownAttackValue) {
			this.thrownAttackValue = thrownAttackValue;
			return this;
		}

		public ItemBuilder setRangedAttackValue(int rangedAttackValue) {
			this.rangedAttackValue = rangedAttackValue;
			return this;
		}

		public ItemBuilder setQuaffEffect(Effect quaffEffect) {
			this.quaffEffect = quaffEffect;
			return this;
		}

		public ItemBuilder setWeaponEffect(Effect weaponEffect) {
			this.weaponEffect = weaponEffect;
			return this;
		}

		public ItemBuilder setFoodEffect(Effect foodEffect) {
			this.foodEffect = foodEffect;
			return this;
		}

		public ItemBuilder setArmorEffect(Effect armorEffect) {
			this.armorEffect = armorEffect;
			return this;
		}

		public ItemBuilder setAScroll(boolean isAScroll) {
			this.isAScroll = isAScroll;
			return this;
		}

		public ItemBuilder setMana(int mana) {
			this.mana = mana;
			return this;
		}

		public ItemBuilder setMaxMana(int maxMana) {
			this.maxMana = maxMana;
			return this;
		}

		public ItemBuilder setAUsable(boolean isAUsable) {
			this.isAUsable = isAUsable;
			return this;
		}

		public ItemBuilder setWrittenSpells(List<Spell> writtenSpells) {
			this.writtenSpells = writtenSpells;
			return this;
		}
		
		public Item build() {
			return new Item(this);
		}
    }

    private Item(ItemBuilder itemBuilder){
    	this.glyph = itemBuilder.glyph;
    	this.color = itemBuilder.color;
    	this.name = itemBuilder.name;
    	this.description = itemBuilder.description;
    	this.uses = itemBuilder.uses;
    	this.isZappable = itemBuilder.isZappable;
    	this.foodValue = itemBuilder.foodValue;
    	this.isFood = itemBuilder.isFood;
    	this.thirstValue = itemBuilder.thirstValue;
    	this.isDrink = itemBuilder.isDrink;
    	this.attackValue = itemBuilder.attackValue;
    	this.defenseValue = itemBuilder.defenseValue;
    	this.dexterityValue = itemBuilder.dexterityValue;
    	this.isWeapon = itemBuilder.isWeapon;
    	this.isArmor = itemBuilder.isArmor;
    	this.isShield = itemBuilder.isShield;
    	this.shieldConstant = itemBuilder.shieldConstant;
    	this.isThrown = itemBuilder.isThrown;
    	this.isRanged = itemBuilder.isRanged;
    	this.visionRadius = itemBuilder.visionRadius;
    	this.thrownAttackValue = itemBuilder.thrownAttackValue;
    	this.rangedAttackValue = itemBuilder.rangedAttackValue;
        this.quaffEffect = itemBuilder.quaffEffect;
        this.weaponEffect = itemBuilder.weaponEffect;
        this.foodEffect = itemBuilder.foodEffect;
        this.armorEffect = itemBuilder.armorEffect;
        this.isAScroll = itemBuilder.isAScroll;
        this.mana = itemBuilder.mana;
        this.maxMana = itemBuilder.maxMana;
        this.isAUsable = itemBuilder.isAUsable;
        this.writtenSpells = itemBuilder.writtenSpells;
    }
    
    public String details() {
        String details = "";

        if (attackValue != 0)
            details += " | attack:" + attackValue + " |";

        if (defenseValue != 0)
            details += "| defense:" + defenseValue + " |";

        if (dexterityValue != 0)
            details += "| dex:" + dexterityValue + " |";
        
        if (foodValue != 0)
            details += "| filling:" + foodValue + " |";
        
        if (thirstValue != 0)
        	details += "| hydration:" + thirstValue + " |";
        
        if (visionRadius != 0)
        	details += "| extra vision:" + visionRadius + " |";
        
        if (thrownAttackValue != 0)
        	details += "| thrown attack:" + thrownAttackValue + " |";
        
        if (rangedAttackValue != 0)
        	details += "| ranged attack:" + rangedAttackValue + " |";
        
        return details;
    }

    public void addWrittenSpell(String name, int manaCost, Effect effect){
        writtenSpells.add(new Spell(name, manaCost, effect));
    }
}