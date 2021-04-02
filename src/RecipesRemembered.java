package ascii_rl;

public class RecipesRemembered {
    
    private Spell[] recipes;
    public Spell[] getRecipes() { return recipes; }
    public Spell get(int i) { return recipes[i]; }

    public RecipesRemembered(int max){
        recipes = new Spell[max];
    }
    
    public void add(Spell item){
        for (int i = 0; i < recipes.length; i++){
            if (recipes[i] == null){
                recipes[i] = item;
                break;
            }
        }
    }
    
    public void remove(Spell item){
        for (int i = 0; i < recipes.length; i++){
            if (recipes[i] == item){
                recipes[i] = null;
                return;
            }
        }
    }
    
    public boolean isFull(){
        int size = 0;
        for (int i = 0; i < recipes.length; i++){
            if (recipes[i] != null)
                size++;
        }
        return size == recipes.length;
    }
    
    public boolean contains(Spell item) {
		for (Spell i : recipes){
			if (i == item)
				return true;
		}
		return false;
	}
}
