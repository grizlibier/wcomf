package Screen;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class LoseScreen implements Screen {
	
	private int outcomeCode;
	
	public LoseScreen(int outcomeCode) {
		this.outcomeCode = outcomeCode;
	}

    public void displayOutput(AsciiPanel terminal) {
    	switch (outcomeCode) {
    		case 0: exitWithoutArtifact(terminal); break;
    		case 1: commonDeath(terminal); break;
    		case 2: poisonDeath(terminal); break;
    		case 3: overeatingDeath(terminal); break;
    		case 4: thirstDeath(terminal); break;
    		case 5: hungerDeath(terminal); break;
    		case 6: craftDeath(terminal); break;
    		case 13: lameException(terminal); break;
    	}
    }
    
    private void commonDeath(AsciiPanel terminal) {
    	terminal.writeCenter("You died.", 1);
        terminal.writeCenter("The mix of nasty wounds, broken bones, monsters' venom and torn muscles makes you collapse.", 3);
        terminal.writeCenter("You are consumed by the caves, like many before and after you.", 4);
        terminal.writeCenter("Many will try to get the Artifact to the Surface, many will fail.", 6);
        terminal.writeCenter("You did your best and now you have an eternity to find out what you did wrong.", 8);
        terminal.writeCenter("But if you are lucky enough, you might be reborn...", 21);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
	}
    
    private void exitWithoutArtifact(AsciiPanel terminal) {
    	terminal.writeCenter("Be it your cowardice, incompetence or the realization that nothing will change", 3);
        terminal.writeCenter("the fate of the world, you exit the caves without finding the Artifact.", 4);
        terminal.writeCenter("Within months, the entire planet got overrun by the Fungus.", 6);
        terminal.writeCenter("A handful of survivors founded an outpost, Ultus, the last beacon of hope.", 7);
        terminal.writeCenter("Some of those survivors, however, spoke of an untouched land somewhere", 8);
        terminal.writeCenter("in the midst of the Fungus. An oasis, a paradise.", 9);
        terminal.writeCenter("But that ain't your story anymore, that is the story for later...", 20);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
	}
    
    private void poisonDeath(AsciiPanel terminal) {
    	terminal.writeCenter("You died.", 1);
        terminal.writeCenter("The poison, bleedings and many other ailments weakened your body.", 3);
        terminal.writeCenter("There can't be a strong mind in a weak body, not here.", 4);
        terminal.writeCenter("It didn't take long before a monster, that you once hunted, made you its main dish.", 5);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
	}
    
    private void overeatingDeath(AsciiPanel terminal) {
    	terminal.writeCenter("You died.", 1);
        terminal.writeCenter("You ate so much that your stomach stretched way too much.", 3);
        terminal.writeCenter("At one point, it tore to pieces, leaving a gaping hole.", 4);
        terminal.writeCenter("You die of agonizing pain, surrounded by your own guts and half-digested food.", 5);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
	}
    
    private void thirstDeath(AsciiPanel terminal) {
    	terminal.writeCenter("You died.", 1);
        terminal.writeCenter("The severe dehydration turned you into a skeleton.", 3);
        terminal.writeCenter("Now you wander the caves and remind others what horrors one", 4);
        terminal.writeCenter("can commit for a drop of water.", 5);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
	}
    
    private void hungerDeath(AsciiPanel terminal) {
    	terminal.writeCenter("You died.", 1);
        terminal.writeCenter("The hunger was so unbearable that you started to consume", 3);
        terminal.writeCenter("everything you came across, friend or foe, venomous or not.", 4);
        terminal.writeCenter("Even now the survivors talk of an unbeatable creature that lurkes in the caves.", 5);
        terminal.writeCenter("Something that was human, but is no more.", 6);
        terminal.writeCenter("It's consumed by Hunger. You are Hunger.", 8);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
	}
    
    private void craftDeath(AsciiPanel terminal) {
    	terminal.writeCenter("You died.", 1);
        terminal.writeCenter("You kept failing to craft or cook anything usefull.", 3);
        terminal.writeCenter("At a certain moment, your frustration and anger took over your mind", 4);
        terminal.writeCenter("and you decided to hit your head against the wall of the cave.", 5);
        terminal.writeCenter("As your mind was clouded by anger, you failed to notice a sharp rock growing", 7);
        terminal.writeCenter("from the wall.", 8);
        terminal.writeCenter("The rock had split your skull in two, leaving your lifeless body as a", 9);
        terminal.writeCenter("grim reminder that everyone should practice cooking and crafting to some degree.", 10);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
	}
    
    private void lameException(AsciiPanel terminal) {
    	terminal.writeCenter("You died.", 1);
        terminal.writeCenter("You were mercilessly trampled by the mass of monsters you", 3);
        terminal.writeCenter("angered.", 4);
        terminal.writeCenter("Blame the dev for making such a lame way to handle an exception.", 6);
        terminal.writeCenter("But it was kind of fun, right?", 8);
        terminal.writeCenter("And if you are lucky enough, you might be reborn...", 21);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new ClassScreen() : this;
    }
}