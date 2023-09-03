package WorldBuilder;

import javax.swing.JFrame;

import Screen.StartScreen;
import Screen.Screen;
import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Window extends JFrame implements KeyListener {
    private static final long serialVersionUID = 1060623638149583738L;

    private AsciiPanel terminal;
    private Screen screen;

    public Window(){
    	super();
    	
    	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    	int width = gd.getDisplayMode().getWidth() / 9 + 1;
    	int height = gd.getDisplayMode().getHeight() / 16 + 1;
    	
        terminal = new AsciiPanel(width, height);
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    public void repaint(){
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    public void keyReleased(KeyEvent e) { }

    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
    	Window app = new Window();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setExtendedState(JFrame.MAXIMIZED_BOTH);
        app.setVisible(true);
    }
}