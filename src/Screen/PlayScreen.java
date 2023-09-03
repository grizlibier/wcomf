package Screen;

import java.awt.Color;
import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import java.util.*;

import FactoryAndStats.Creature;
import FactoryAndStats.CurseOfTimeController;
import FactoryAndStats.FieldOfView;
import FactoryAndStats.StuffFactory;
import FactoryAndStats.Item;
import WorldBuilder.Tile;
import WorldBuilder.World;
import WorldBuilder.WorldBuilder;

public class PlayScreen implements Screen {
	
	private World world;
    private Creature player;
    private int screenWidth;
    private int screenHeight;
    public int turn;
	private FieldOfView fov;
    
    private List<String> messages;
    
    private Screen subscreen;
    
    private int playerMaxHp, playerAttack, playerDefence, playerDex;
    private String playerClass;

    public PlayScreen(int playerMaxHp, int playerAttack, int playerDefence, int playerDex, String playerClass){
        screenWidth = width;
        screenHeight = height - 17;
        turn = 0;
        messages = new ArrayList<String>();
        this.playerMaxHp = playerMaxHp;
        this.playerAttack = playerAttack;
        this.playerDefence = playerDefence;
        this.playerDex = playerDex;
        this.playerClass = playerClass;
        
        createWorld();
		fov = new FieldOfView(world);
        
		StuffFactory creatureFactory = new StuffFactory(world);
        createCreatures(creatureFactory);
        
        StuffFactory itemFactory = new StuffFactory(world);
        createItems(itemFactory);
        
        audioController.startMainTheme();
    }
    
    private void createWorld(){
        world = new WorldBuilder(width, height, 10).makeCaves().build();
    }
    
    // CREATING CREATURES/ITEMS
    
    private void createCreatures(StuffFactory creatureFactory){
		player = creatureFactory.newPlayer(messages, fov, playerMaxHp, playerAttack, playerDefence, playerDex, playerClass);
		
		for (int z = 0; z < world.depth(); z++){
			for (int f = 0; f < 40; f++){
				if (Math.random() < 0.01) {
					creatureFactory.newComrade(z, player);
				}
				if (z < 3)
					creatureFactory.randomLowerCreature(z, player);
				if (z > 2 && z < 6)
					creatureFactory.randomMediumCreature(z, player);
				if (z > 5 && z < 10)
					creatureFactory.randomHigherCreature(z, player);
			}
            for (int i = 0; i <  world.height() / 30; i++){
            	creatureFactory.randomUsables(z, player);
            }
			if (z == 10)
				creatureFactory.newArtifact(z);
		}
	}
    
    private void createItems(StuffFactory itemFactory) {

        for (int z = 0; z < world.depth(); z++){
            for (int i = 0; i <  world.height() / 2; i++){
                itemFactory.newWater(z);
            }
            for (int i = 0; i <  world.height() / 5; i++){
            	itemFactory.randomItem(z);
                itemFactory.newWater(z);
            }
            for (int i = 0; i <  world.height() / 10; i++){
            	itemFactory.randomArmor(z);
                itemFactory.randomWeapon(z);
                itemFactory.randomShield(z);
            }
            for (int i = 0; i <  world.height() / 15; i++){
            	itemFactory.randomFood(z);
                itemFactory.randomScrolls(z);
            }
            for (int i = 0; i <  world.height() / 20; i++){
            	itemFactory.randomBook(z);
            }
            for (int i = 0; i <  world.height() / 25; i++){
            	itemFactory.randomPotion(z);
            	itemFactory.randomWand(z);
            }
        }
    }
    
    // GETTING COORDINATES AND DISPLAY VALUES
    
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
    
    // DISPLAYNG MESSAGES

    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();
   
        terminal.writeCenter("======================================================================", height - 16);
        displayTiles(terminal, left, top);
        terminal.write(player.glyph(), player.x - left, player.y - top, player.color());
        String stats = String.format("hp:%d/%d attack:%d defence:%d dex:%d mana:%d/%d satiety:%d/%d thirst:%d/100 lvl:%d xp:%d/%d", 
        		player.hp(), player.maxHp(), player.attackValue(), player.defenseValue(), player.dexterityValue(), player.mana(), player.maxMana(),
        		player.food(), player.maxFood(), player.thirst(), player.level(), player.xp(),(int)(Math.pow(player.level(), 1.5) * 20));
        terminal.writeCenter(stats, height - 6);
        
        displayMessages(terminal, messages);
        

        if (subscreen != null)
        	subscreen.displayOutput(terminal);

    }
    
    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = height - 15;
        for (int i = 0; i < messages.size(); i++){
            terminal.writeCenter(messages.get(i), top + i);
        }
        messages.clear();
    }
    

    // KEYMAPPINGS

    @Override
    public Screen respondToUserInput(KeyEvent key) {
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
	            case KeyEvent.VK_D: subscreen = new DropScreen(player); turn++; break;
	            case KeyEvent.VK_E: subscreen = new EatScreen(player); turn++; break;
	            case KeyEvent.VK_U: subscreen = new UseScreen(player); turn++; break;
	            case KeyEvent.VK_C: subscreen = new CraftScreen(player); turn++; break;
	            case KeyEvent.VK_W: subscreen = new EquipScreen(player); turn++; break;
	            case KeyEvent.VK_H: subscreen = new HelpScreen(); break;
	            case KeyEvent.VK_X: subscreen = new ExamineScreen(player); turn++; break;
	            case KeyEvent.VK_G: player.pickup(); if (subscreen == null) { world.update(); } turn++; break;
	            case KeyEvent.VK_A: world.update(); turn++; break;
	            case KeyEvent.VK_S: subscreen = new SkillScreen(player,
	                    player.x - getScrollX(),
	                    player.y - getScrollY()); break;
	            case KeyEvent.VK_L: subscreen = new LookScreen(player, "Looking", 
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
	            case KeyEvent.VK_Q: subscreen = new QuaffScreen(player); turn++; break;
	            case KeyEvent.VK_R: subscreen = new ReadScreen(player,
						player.x - getScrollX(), 
						player.y - getScrollY()); turn++; break;
	            case KeyEvent.VK_Z: subscreen = new ZapScreen(player,
						player.x - getScrollX(), 
						player.y - getScrollY()); turn++; break;
        	}
        
        	switch (key.getKeyChar()){
	            case '<':
	                if (userIsTryingToExit())
	                	return userExits();
	                else
	                	player.moveBy( 0, 0, -1); 
	                if (subscreen == null) { world.update(); } turn++; break;
	            case '>': player.moveBy( 0, 0, 1); if (subscreen == null) { world.update(); } turn++; break;
            }
        }
        
        // MISC
        
        if (player.level() > level)
            subscreen = new LevelUpScreen(player, player.level() - level);
        
        if (turn % 150 == 0){
            player.notify("You feel as if darkness surrounds you for a second.");
            CurseOfTimeController curse = new CurseOfTimeController();
            curse.autoCurse(player);
        }
       
        
        // LOSESCREENS
        
        if (player.hp() < 1) {
            if (player.diedCrafting){
                return new LoseScreen(6); // death by failing crafting
            } else if (player.food() < 1) {
        		return new LoseScreen(5); // death by hunger
        	} else if (player.thirst() < 1) {
        		return new LoseScreen(4); // death by thirst
        	} else if (player.maxFood() > 1000 && player.food() == player.maxFood()) {
        		return new LoseScreen(3); // death by over-eating
        	} else if (!player.effects().isEmpty()) {
        		return new LoseScreen(2); // death by poison, burn or other effects
        	} else {
        		return new LoseScreen(1); // common death
        	}
        }
        
        return this;
    }
    
    // ENDINGS ON EXIT
    
    private boolean userIsTryingToExit(){
        return player.z == 0 && world.tile(player.x, player.y, player.z) == Tile.STAIRS_UP;
    }

    private Screen userExits(){
        for (Item item : player.inventory().getItems()){
            if (item != null && item.name().equals("an Artifact"))
                return new WinScreen(); // win screen
        }
        return new LoseScreen(0); // exited without the Artifact
    }
}