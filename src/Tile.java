package ascii_rl;

import java.awt.Color;
import asciiPanel.AsciiPanel;

public enum Tile {
	UNKNOWN(' ', AsciiPanel.white, "The darkness of the caves."),
	STAIRS_DOWN('>', AsciiPanel.white, "Stairs leading down, deeper into the unknown."),
    STAIRS_UP('<', AsciiPanel.white, "Stairs leading up, closer to the Surface."),
    FLOOR((char)250, AsciiPanel.white, "The floor of the cave."),
    WALL((char)177, AsciiPanel.yellow, "The walls of stone and earth."),
    BOUNDS('x', AsciiPanel.brightBlack, "");

    private String details;
    public String details(){ return details; }

    Tile(char glyph, Color color, String details){
        this.glyph = glyph;
        this.color = color;
        this. details = details;
    }
	
	private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    Tile(char glyph, Color color){
        this.glyph = glyph;
        this.color = color;
    }
    
    public boolean isDiggable() {
        return this == Tile.WALL;
    }
    
    public boolean isGround() {
        return this != WALL && this != BOUNDS;
    }
}