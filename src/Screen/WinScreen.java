package Screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class WinScreen implements screen {

    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("You bask in the warmth of the sun as you ascend the stairs", 1);
        terminal.writeCenter("the Artifact in hand.", 2);
        terminal.writeCenter("You completed your mission, your destiny.", 4);
        terminal.writeCenter("You collapse under the weight of exhaustion, both physical and mental.", 5);
        terminal.writeCenter("Just before you drift off to sleep, you can feel someone", 6);
        terminal.writeCenter("taking the Artifact and laying you on something soft and warm.", 7);
        terminal.writeCenter("Just like that you drift into deep sleep...", 9);
        terminal.writeCenter("Yet, once you wake up, you can see a familiar sight.", 12);
        terminal.writeCenter("The sight of the Caves.", 13);
        terminal.writeCenter("** press [ENTER] to restart **", 22);
    }

    public screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}