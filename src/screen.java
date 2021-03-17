package ascii_rl;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public interface screen {
    public void displayOutput(AsciiPanel terminal);

    public screen respondToUserInput(KeyEvent key);
}