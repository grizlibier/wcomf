package ascii_rl;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class LoseScreenThirst implements screen {

    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("You died.", 1);
        terminal.writeCenter("The severe dehydration turned you into a skeleton.", 3);
        terminal.writeCenter("Now you wander the caves and remind others what horrors one", 4);
        terminal.writeCenter("can commit for a drop of water.", 5);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
    }

    public screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}