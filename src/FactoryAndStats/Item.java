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

    public void addWrittenSpell(String name, int manaCost, Effect effect){
        writtenSpells.add(new Spell(name, manaCost, effect));
    }

    public Item(char glyph, Color color, String name, String description){
        this.glyph = glyph;
        this.color = color;
        this.name = name;
        this.description = description;
        writtenSpells = new ArrayList<Spell>();
        uses = 1;
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
}