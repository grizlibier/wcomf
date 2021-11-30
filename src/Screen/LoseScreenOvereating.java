package Screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class LoseScreenOvereating implements screen {

    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("You died.", 1);
        terminal.writeCenter("You ate so much that your stomach stretched way too much.", 3);
        terminal.writeCenter("At one point, it tore to pieces, leaving a gaping hole.", 4);
        terminal.writeCenter("You die of agonizing pain, surrounded by your own guts and half-digested food.", 5);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
    }

    public screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}