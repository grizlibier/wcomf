package FactoryAndStats;

public class Spell {

	private String name;
	public String name() { return name; }

	private int manaCost;
	public int manaCost() { return manaCost; }

	private Effect effect;
	public Effect effect() { return (Effect)effect.clone(); }

	public boolean requiresTarget() { return true; }
	
	public Spell(String name, int manaCost, Effect effect){
		this.name = name;
		this.manaCost = manaCost;
		this.effect = effect;
	}
}