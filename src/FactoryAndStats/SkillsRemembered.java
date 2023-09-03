package FactoryAndStats;

public class SkillsRemembered {
    
    private Skill[] skills;
    public Skill[] getSkills() { return skills; }
    public Skill get(int i) { return skills[i]; }

    public SkillsRemembered(int max){
    	skills = new Skill[max];
    }
    
    public void add(Skill item){
        for (int i = 0; i < skills.length; i++){
            if (skills[i] == null){
            	skills[i] = item;
                break;
            }
        }
    }
    
    public void update() {
    	for (Skill skill : skills) {
    		if (skill == null) {
    			break;
    		} else {
        		skill.update();
    		}
    	}
    }
    
    public boolean isFull(){
        int size = 0;
        for (int i = 0; i < skills.length; i++){
            if (skills[i] != null)
                size++;
        }
        return size == skills.length;
    }
    
    public boolean contains(Skill item) {
		for (Skill i : skills){
			if (i == item)
				return true;
		}
		return false;
	}

}
