package anderssjoberg97.samurai;

import com.badlogic.gdx.Gdx;

import anderssjoberg97.samurai.game.AssetLoader;
import anderssjoberg97.samurai.game.screens.GameScreen;

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
		Gdx.app.log("Samurai", "Created Game instance");
		//Loadtextures
		AssetLoader.load();
		//Set game screen
		setScreen(new GameScreen());
	}
	
	/**
	 * Dispose variables before closing
	 */
	@Override
	public void dispose(){
		super.dispose();
		AssetLoader.dispose();
	}
}
