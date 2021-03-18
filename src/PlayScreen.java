package ascii_rl;


import java.awt.Color;
import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import java.util.*;

public class PlayScreen implements screen {
	
	private World world;
    private Creature player;
    private int screenWidth;
    private int screenHeight;
    public int turn;
	private FieldOfView fov;
    
    private List<String> messages;
    
    private screen subscreen;

    public PlayScreen(){
        screenWidth = 110;
        screenHeight = 29;
        turn = 0;
        messages = new ArrayList<String>();
        createWorld();
		fov = new FieldOfView(world);
        
		StuffFactory creatureFactory = new StuffFactory(world);
        createCreatures(creatureFactory);
        
        StuffFactory itemFactory = new StuffFactory(world);
        createItems(itemFactory);
    }
    
    private void createWorld(){
        world = new WorldBuilder(150, 64, 10)
        .makeCaves()
        .build();
    }
    
    private void createCreatures(StuffFactory creatureFactory){
		player = creatureFactory.newPlayer(messages, fov);
		
		for (int z = 0; z < world.depth(); z++){
			for (int f = 0; f < 40; f++){
				if (z < 3)
					creatureFactory.randomLowerCreature(z, player);
				if (z > 2 && z < 6)
					creatureFactory.randomMediumCreature(z, player);
				if (z > 5 && z < 10)
					creatureFactory.randomHigherCreature(z, player);
			}
			if (z == 10)
				creatureFactory.newArtifact(z);
		}
	}
    
    private void createItems(StuffFactory ItemFactory) {
        for (int z = 0; z < world.depth(); z++){
            for (int i = 0; i <  world.height() / 5; i++){
            	ItemFactory.randomItem(z);
                ItemFactory.newWater(z);
            }
            for (int i = 0; i <  world.height() / 10; i++){
            	ItemFactory.randomArmor(z);
                ItemFactory.randomWeapon(z);
            }
            for (int i = 0; i <  world.height() / 15; i++){
            	ItemFactory.randomFood(z);
                ItemFactory.randomScrolls(z);
            }
            for (int i = 0; i <  world.height() / 20; i++){
            	ItemFactory.randomBook(z);
            }
            for (int i = 0; i <  world.height() / 25; i++){
            	ItemFactory.randomPotion(z);
            }
        }
    }
    
    public int getScrollX() {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
    }
    
    private void displayTiles(AsciiPanel terminal, int left, int top) {
		fov.update(player.x, player.y, player.z, player.visionRadius());
		
		for (int x = 0; x < screenWidth; x++){
			for (int y = 0; y < screenHeight; y++){
				int wx = x + left;
				int wy = y + top;

				if (player.canSee(wx, wy, player.z))
					terminal.write(world.glyph(wx, wy, player.z), x, y, world.color(wx, wy, player.z));
				else
					terminal.write(fov.tile(wx, wy, player.z).glyph(), x, y, Color.darkGray);
			}
		}
	}

    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();
   
        terminal.writeCenter("======================================================================", 30);
        displayTiles(terminal, left, top);
        terminal.write(player.glyph(), player.x - left, player.y - top, player.color());
        String stats = String.format(" hp:%d/%d attack:%d defence:%d mana:%d/%d turn:%d satiety:%d/%d thirst:%d/100 lvl:%d xp:%d/%d", 
        		player.hp(), player.maxHp(), player.attackValue(), player.defenseValue(), player.mana(), player.maxMana(),
        		turn, player.food(), player.maxFood(), player.thirst(), player.level(), player.xp(),(int)(Math.pow(player.level(), 1.5) * 20));
        terminal.writeCenter(stats, 39);
        
        displayMessages(terminal, messages);
        

        if (subscreen != null)
        	subscreen.displayOutput(terminal);

    }
    
    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = 31;
        for (int i = 0; i < messages.size(); i++){
            terminal.writeCenter(messages.get(i), top + i);
        }
        messages.clear();
    }
    
    @Override
    public screen respondToUserInput(KeyEvent key) {
    	int level = player.level();
        if (subscreen != null) {
            subscreen = subscreen.respondToUserInput(key);
        } else {        
        	switch (key.getKeyCode()){
        	case KeyEvent.VK_LEFT:
        	case KeyEvent.VK_NUMPAD4: player.moveBy(-1, 0, 0); if (subscreen == null) { world.update(); } turn++; break;
        	case KeyEvent.VK_RIGHT:
        	case KeyEvent.VK_NUMPAD6: player.moveBy( 1, 0, 0); if (subscreen == null) { world.update(); } turn++; break;
        	case KeyEvent.VK_UP:
        	case KeyEvent.VK_NUMPAD8: player.moveBy( 0,-1, 0); if (subscreen == null) { world.update(); } turn++; break;
        	case KeyEvent.VK_DOWN:
        	case KeyEvent.VK_NUMPAD2: player.moveBy( 0, 1, 0); if (subscreen == null) { world.update(); } turn++; break;
        	case KeyEvent.VK_NUMPAD7: player.moveBy( -1,-1, 0); if (subscreen == null) { world.update(); } turn++; break;
        	case KeyEvent.VK_NUMPAD9: player.moveBy( 1,-1, 0); if (subscreen == null) { world.update(); } turn++; break;
        	case KeyEvent.VK_NUMPAD1: player.moveBy( -1, 1, 0); if (subscreen == null) { world.update(); } turn++; break;
        	case KeyEvent.VK_NUMPAD3: player.moveBy( 1, 1, 0); if (subscreen == null) { world.update(); } turn++; break;
            case KeyEvent.VK_D: subscreen = new DropScreen(player); if (subscreen == null) { world.update(); } turn++; break;
            case KeyEvent.VK_E: subscreen = new EatScreen(player); if (subscreen == null) { world.update(); } turn++; break;
            case KeyEvent.VK_W: subscreen = new EquipScreen(player); if (subscreen == null) { world.update(); } turn++; break;
            case KeyEvent.VK_H: subscreen = new HelpScreen(); if (subscreen == null) { world.update(); } turn++; break;
            case KeyEvent.VK_X: subscreen = new ExamineScreen(player); if (subscreen == null) { world.update(); } turn++; break;
            case KeyEvent.VK_V: subscreen = new LookScreen(player, "Looking", 
					player.x - getScrollX(), 
					player.y - getScrollY()); if (subscreen == null) { world.update(); } turn++; break;
            case KeyEvent.VK_T: subscreen = new ThrowScreen(player,
                    player.x - getScrollX(),
                    player.y - getScrollY()); if (subscreen == null) { world.update(); } turn++; break;
            case KeyEvent.VK_F:
                if (player.weapon() == null || player.weapon().rangedAttackValue() == 0)
                 player.notify("You don't have a ranged weapon equiped.");
                else
                 subscreen = new FireWeaponScreen(player,
                     player.x - getScrollX(),
                     player.y - getScrollY()); if (subscreen == null) { world.update(); } turn++; break;
            case KeyEvent.VK_Q: subscreen = new QuaffScreen(player); if (subscreen == null) { world.update(); } turn++; break;
            case KeyEvent.VK_R: subscreen = new ReadScreen(player,
					player.x - getScrollX(), 
					player.y - getScrollY()); if (subscreen == null) { world.update(); } turn++; break;
        	}
        
        	switch (key.getKeyChar()){
            case 'g':
            case ',': player.pickup(); if (subscreen == null) { world.update(); } turn++; break;
            case '<':
                if (userIsTryingToExit())
                 return userExits();
                else
                 player.moveBy( 0, 0, -1); 
                if (subscreen == null) { world.update(); } turn++; break;
            case '>': player.moveBy( 0, 0, 1); if (subscreen == null) { world.update(); } turn++; break;
            }
        }
        
        if (player.level() > level)
            subscreen = new LevelUpScreen(player, player.level() - level);
        
       
        
        // LOSESCREENS
        
        if (player.hp() < 1) {
        	if (player.effects != null) {
        		return new LoseScreenPoison();
        	} else if (player.food() < 1) {
        		return new LoseScreenHunger();
        	} else if (player.thirst() < 1) {
        		return new LoseScreenThirst();
        	} else if (player.maxFood() > 1000 && player.food() == player.maxFood()) {
        		return new LoseScreenOvereating();
        	} else {
        		return new LoseScreen();
        	}
        	
        }
        
        return this;
    }
    
    private boolean userIsTryingToExit(){
        return player.z == 0 && world.tile(player.x, player.y, player.z) == Tile.STAIRS_UP;
    }

    private screen userExits(){
        for (Item item : player.inventory().getItems()){
            if (item != null && item.name().equals("an Artifact"))
                return new WinScreen();
        }
        return new LoseScreen2();
    }
}