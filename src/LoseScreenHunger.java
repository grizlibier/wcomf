package ascii_rl;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class LoseScreenHunger implements screen {

    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("You died.", 1);
        terminal.writeCenter("The hunger was so unbearable that you started to consume", 3);
        terminal.writeCenter("everything you came across, friend or foe, venomous or not.", 4);
        terminal.writeCenter("Even now the survivors talk of an unbeatable creature that lurkes in the caves.", 5);
        terminal.writeCenter("Something that was human, but no more.", 6);
        terminal.writeCenter("It's consumed by Hunger. You are Hunger.", 8);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
    }

    public screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}