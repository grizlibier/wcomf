package ascii_rl;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class LoseScreen implements screen {

    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("You died.", 1);
        terminal.writeCenter("The mix of nasty wounds, broken bones, monsters' venom and torn muscles makes you collapse.", 3);
        terminal.writeCenter("You are consumed by the caves, like many before and after you.", 4);
        terminal.writeCenter("Many will try to get the Artifact to the Surface, many will fail.", 6);
        terminal.writeCenter("You did your best and now you have an eternity to find out what you did wrong.", 8);
        terminal.writeCenter("But if you are lucky enough, you might be reborn...", 21);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
    }

    public screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}