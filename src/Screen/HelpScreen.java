package Screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class HelpScreen implements Screen {

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
        terminal.writeCenter("w[a]it a turn", y++);
        terminal.writeCenter("[g]rab something on the ground", y++);
        terminal.writeCenter("[d]rop items from your inventory", y++);
        terminal.writeCenter("[e]at edibles", y++);
        terminal.writeCenter("[u]se usables", y++);
        terminal.writeCenter("[c]raft known recipes", y++);
        terminal.writeCenter("[w]ear armor or wield weapons", y++);
        terminal.writeCenter("[h]elp", y++);
        terminal.writeCenter("e[x]amine your items", y++);
        terminal.writeCenter("[l]ook around", y++);
        terminal.writeCenter("[t]hrow anything at something", y++);
        terminal.writeCenter("[f]ire a ranged weapon at something", y++);
        terminal.writeCenter("[r]ead a book/scroll from the inventory", y++);
        terminal.writeCenter("[q]uaff a potion or drink water", y++);
        terminal.writeCenter("[z]ap with a wand", y++);

        terminal.writeCenter("-- press any key to continue --", 30);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return null;
    }
}