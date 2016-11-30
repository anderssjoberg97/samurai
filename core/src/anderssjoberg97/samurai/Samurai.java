package anderssjoberg97.samurai;

import com.badlogic.gdx.Gdx;

import anderssjoberg97.samurai.screens.GameScreen;
import anderssjoberg97.samurai.util.AssetLoader;

import com.badlogic.gdx.Game;

/**
 * Samurai games main class
 * @author Anders Sjöberg
 *
 */
public class Samurai extends Game {
	
	
	/**
	 * Creates a game instance
	 */
	@Override
	public void create(){
		//Set game screen
		setScreen(new GameScreen());
	}
	
	/**
	 * Dispose variables before closing
	 */
	@Override
	public void dispose(){
		super.dispose();
	}
}
