package ascii_rl;

import java.awt.Color;
import asciiPanel.AsciiPanel;

public enum Tile {
	UNKNOWN(' ', AsciiPanel.white, "The darkness of the caves.", "unknown"),
	STAIRS_DOWN('>', AsciiPanel.white, "Stairs leading down, deeper into the unknown.", "stairs down"),
    STAIRS_UP('<', AsciiPanel.white, "Stairs leading up, closer to the Surface.", "stairs up"),
    FLOOR((char)250, AsciiPanel.white, "The floor of the cave.", "floor"),
    WALL((char)177, AsciiPanel.yellow, "The walls of stone and earth.", "wall"),
    POISON_PIT((char)247, AsciiPanel.green, "A shallow pool of poison.", "poison"),
    LAVA_PIT((char)247, AsciiPanel.red, "A pool of hot lava.", "lava"),
    BOUNDS('x', AsciiPanel.brightBlack, "", "");

    private String details;
    public String details() { return details; }

    private String tilename;
    public String tilename() { return tilename; }

    Tile(char glyph, Color color, String details, String tilename){
        this.glyph = glyph;
        this.color = color;
        this. details = details;
        this.tilename = tilename;
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