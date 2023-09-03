package AudioControllers;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioController {
	Clip theme, sound;
	
	public void startMainTheme() {
        try {
        	URL mainTheme = getClass().getResource("/Music/mainTheme.aif");
        	
        	AudioInputStream input = AudioSystem.getAudioInputStream(mainTheme);
        	theme = AudioSystem.getClip();
        	theme.open(input);
        	
        	FloatControl gainControl = (FloatControl) theme.getControl(FloatControl.Type.MASTER_GAIN);
        	gainControl.setValue(-15.0f);
        	
        	theme.start();
        	theme.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (LineUnavailableException e) {
        	e.printStackTrace();
        }
	}
	
	public void playSoundEffect(String name) {
		try {

			URL soundEffectName = getClass().getResource("/Music/" + name + ".aif");
			
        	AudioInputStream input = AudioSystem.getAudioInputStream(soundEffectName);
        	sound = AudioSystem.getClip();
        	sound.open(input);
        	
        	FloatControl gainControl = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
        	switch (name) {
        		case "step": gainControl.setValue(5.0f); break;
        		default: gainControl.setValue(-10.0f); break;
        	}
        	
        	sound.start();
        } catch (UnsupportedAudioFileException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (LineUnavailableException e) {
        	e.printStackTrace();
        }
	}
}
