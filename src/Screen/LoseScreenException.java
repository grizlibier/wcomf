package Screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class LoseScreenException implements screen {

    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("You died.", 1);
        terminal.writeCenter("You were mercilessly trampled by the mass of monsters you", 3);
        terminal.writeCenter("angered.", 4);
        terminal.writeCenter("Blame the dev for making such a lame way to handle an exception.", 6);
        terminal.writeCenter("But it was kind of fun, right?", 8);
        terminal.writeCenter("And if you are lucky enough, you might be reborn...", 21);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
    }

    public screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}