package ascii_rl;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class LoseScreenFailedCraft implements screen {

    public void displayOutput(AsciiPanel terminal) {
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

    public screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}