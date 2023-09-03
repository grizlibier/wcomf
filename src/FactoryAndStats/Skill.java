package FactoryAndStats;

public class Skill {

	private String name;
	public String name() { return name; }

	private Effect effect;
	public Effect effect() { return (Effect)effect.clone(); }
	
	private int cooldown;
	public int cooldown() { return cooldown; }
	
	private int maxCooldown;
	public int maxCooldown() { return maxCooldown; }

	public boolean requiresTarget() { return true; }
	
	public Skill(String name, int cooldown, int maxCooldown, Effect effect){
		this.name = name;
		this.cooldown = cooldown;
		this.maxCooldown = maxCooldown;
		this.effect = effect;
	}
	
	public void update() {
		cooldown--;
		
		if (cooldown < 0) {
			cooldown = 0;
		}
	}
	
    public boolean isDone() { return cooldown == 0; }
    
    public void resetCooldown() {
    	cooldown = maxCooldown;
    }
}
