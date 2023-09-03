package Screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class ClassScreen implements Screen {

	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter("Choose your class", 3);
		terminal.writeCenter("a) The Rogue : your commonplace glass canon", 10);
		terminal.writeCenter("b) The Wizard : wise and dangerous, but old", 11);
		terminal.writeCenter("c) The Warrior : young and strong, but not intelectually", 12);
		terminal.writeCenter("d) The Monk : weaker, but needs less food", 13);
		terminal.writeCenter("e) Classic : the classic start from early builds", 14);
        terminal.writeCenter("** press  a [letter] to start using the chosen class **", 22);
		
	}

	public Screen respondToUserInput(KeyEvent key) {
		Screen screenToShow = null;
		
		switch(key.getKeyCode()) {
			case KeyEvent.VK_A: screenToShow = new PlayScreen(75, 6, 0, 5, "Rogue"); break;
			case KeyEvent.VK_B: screenToShow = new PlayScreen(50, 4, 1, 2, "Wizard"); break;
			case KeyEvent.VK_C: screenToShow = new PlayScreen(125, 7, 2, 2, "Warrior"); break;
			case KeyEvent.VK_D: screenToShow = new PlayScreen(50, 2, 2, 2, "Monk"); break;
			case KeyEvent.VK_E: screenToShow = new PlayScreen(100, 5, 2, 3, "Survivor"); break;
		}
		
		return  screenToShow != null ? screenToShow : this;
	}

}
