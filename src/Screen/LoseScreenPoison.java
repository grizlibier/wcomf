package Screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class LoseScreenPoison implements screen {

    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("You died.", 1);
        terminal.writeCenter("The poison, bleedings and many other ailments weakened your body.", 3);
        terminal.writeCenter("There can't be a strong mind in a weak body, not here.", 4);
        terminal.writeCenter("It didn't take long before a monster, that you once hunted, made you its main dish.", 5);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
    }

    public screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}