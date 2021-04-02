package ascii_rl;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import asciiPanel.AsciiPanel;

public class CraftScreen implements screen {

    protected Creature player;
    protected String letters;

    public CraftScreen(Creature player) {
        this.player = player;
        this.letters = "abcdefghijklmnopqrstuvwxyz";
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        ArrayList<String> lines = getList();
    
        int y = 23 + lines.size();
        int x = 4;

        if (lines.size() > 0)
            terminal.clear(' ', x, y, 20, lines.size());
    
        for (String line : lines){
            terminal.write(line, x, y++);
        }
    
        terminal.clear(' ', 0, 23, 80, 1);
        terminal.write("What would you like to craft?", 2, 23);
    
        terminal.repaint();
    }

    private ArrayList<String> getList() {
        ArrayList<String> lines = new ArrayList<String>();
        Spell[] recipesKnown = player.recipes().getRecipes();
    
        for (int i = 0; i < recipesKnown.length; i++){
            Spell recipe = recipesKnown[i];
        
            if (recipe == null || !isAcceptable(recipe))
                continue;
        
            String line = letters.charAt(i) + " - " + recipe.name() + " " + recipe.manaCost();
            
            lines.add(line);
        }
        return lines;
    }

    @Override
    public screen respondToUserInput(KeyEvent key) {
        char c = key.getKeyChar();

        Spell[] recipesKnown = player.recipes().getRecipes();
    
        if (letters.indexOf(c) > -1
             && recipesKnown.length > letters.indexOf(c)
             && recipesKnown[letters.indexOf(c)] != null
             && isAcceptable(recipesKnown[letters.indexOf(c)]))
            return use(recipesKnown[letters.indexOf(c)]);
        else if (key.getKeyCode() == KeyEvent.VK_ESCAPE)
            return null;
        else
            return this;
    }

    protected boolean isAcceptable(Spell recipe) {
        Spell[] recipesKnown = player.recipes().getRecipes();

        for (int i = 0; i < recipesKnown.length; i++){
            if (recipesKnown[i].name().equals(recipe.name())){
                return true;
            }
        }
        return false;
    }

    protected screen use(Spell recipe) {
        player.castSpell(recipe, player.x, player.y);
        return null;
    }
}
