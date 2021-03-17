package ascii_rl;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class LoseScreen2 implements screen {

    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("Be it your cowardice, incompetence or the realization that nothing will change", 3);
        terminal.writeCenter("the fate of the world, you exit the caves without finding the Artifact.", 4);
        terminal.writeCenter("Within months, the entire planet got overrun by the Fungus.", 6);
        terminal.writeCenter("A handful of survivors founded an outpost, Ultus, the last beacon of hope.", 7);
        terminal.writeCenter("Some of those survivors, however, spoke of an untouched land somewhere", 8);
        terminal.writeCenter("in the midst of the Fungus. An oasis, a paradise.", 9);
        terminal.writeCenter("But that ain't your story anymore, that is the story for later...", 20);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
    }

    public screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}