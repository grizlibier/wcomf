package FactoryAndStats;

public abstract class CurseOfTimeOption {
	  private String name;
	  public String name() { return name; }

	  public CurseOfTimeOption(String name){
	    this.name = name;
	  }

	  public abstract void invoke(Creature creature);
}