package Screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class StartScreen implements screen {

    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("Weird Caves of Mediocre Fun", 1);
        terminal.writeCenter("A shoutout to Trystan's blog on roguelikes' developement!", 3);
        terminal.writeCenter("Press [h] to see the help-screen for keybindings and story once you start the game", 5);
        terminal.writeCenter("Developed by doorfed", 10);
        terminal.writeCenter("** press [ENTER] to start **", 22);
    }

    public screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}