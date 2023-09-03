package Screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {

    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("Weird Caves of Mediocre Fun", 1);
        terminal.writeCenter("A shoutout to Trystan's blog on roguelikes' developement!", 3);
        terminal.writeCenter("Press [h] to see the help-screen for keybindings and story once you start the game", 5);
        terminal.writeCenter("Developed by grizlibier", 10);
        terminal.writeCenter("** press [ENTER] to resume towards class selection **", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new ClassScreen() : this;
    }
}