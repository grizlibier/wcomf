package Screen;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;

import AudioControllers.AudioController;
import asciiPanel.AsciiPanel;

public interface Screen {
	final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public int width = gd.getDisplayMode().getWidth() / 9 + 1;
	public int height = gd.getDisplayMode().getHeight() / 16 + 1;
	
	public AudioController audioController = new AudioController();
	
    public void displayOutput(AsciiPanel terminal);

    public Screen respondToUserInput(KeyEvent key);
}