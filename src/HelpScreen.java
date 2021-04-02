package ascii_rl;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class HelpScreen implements screen {

    public void displayOutput(AsciiPanel terminal) {
        terminal.clear();
        terminal.writeCenter("Story", 1);
        terminal.writeCenter("The Surface is in danger of being overtaken by the Fungus.", 3);
        terminal.writeCenter("The cave you wander in, is the Holy Cave of Vengeance, a cave that contains an", 4);
        terminal.writeCenter("Artifact that might save you and the entire planet.", 5);
        terminal.writeCenter("On the behalf of all species, find the Artifact and bring it to the Surface!", 6);
    
        int y = 8;
        terminal.writeCenter("Keybindings", y++);
        terminal.writeCenter("[numpad] or [arrows] to move around", y++);
        terminal.writeCenter("[g] or [,] to pick up stuff from the ground", y++);
        terminal.writeCenter("[d] to drop items from your inventory", y++);
        terminal.writeCenter("[e] to eat edibles", y++);
        terminal.writeCenter("[u] to use usables", y++);
        terminal.writeCenter("[c] to craft known recipes", y++);
        terminal.writeCenter("[w] to wear armor or wield weapons", y++);
        terminal.writeCenter("[h] for help", y++);
        terminal.writeCenter("[x] to examine your items", y++);
        terminal.writeCenter("[v] to look around", y++);
        terminal.writeCenter("[t] to throw anything at something", y++);
        terminal.writeCenter("[f] to fire a ranged weapon at something", y++);
        terminal.writeCenter("[r] to read a book/scroll from the inventory", y++);
        terminal.writeCenter("[q] to quaff a potion or drink water", y++);
    
        terminal.writeCenter("-- press any key to continue --", 22);
    }

    public screen respondToUserInput(KeyEvent key) {
        return null;
    }
}