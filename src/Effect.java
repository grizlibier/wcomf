package ascii_rl;

public class Effect implements Cloneable {
    protected String name;
    protected int duration;
    
    public boolean isDone() { return duration < 1; }
    
    public Effect(String name ,int duration){
        this.name = name;    
        this.duration = duration;
    }
    
    public Effect(Effect other){
        this.duration = other.duration; 
    }
    
    public void update(Creature creature){
            duration--;
    }
    
    public Object clone() {
		try {
			return super.clone();
		}
		catch (CloneNotSupportedException e) {
			// This should never happen
			throw new InternalError(e.toString());
		}
	}

    public String getName(){
        return name;
    }
    
    public void start(Creature creature){
            
    }
    
    public void start(Creature creature, Creature other){
        
    }
    
    public void end(Creature creature){
            
    }
    
    public void end(Creature creature, Creature other){
        
    }
}