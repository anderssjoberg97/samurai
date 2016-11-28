/**
 * 
 */
package anderssjoberg97.samurai.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import anderssjoberg97.samurai.characters.Player;
import anderssjoberg97.samurai.util.AssetLoader;

/**
 * Represents a game world
 * @author Anders Sjöberg
 *
 */
public class World {
	
	
	//Keeps track of game states
	private GameState currentState;
	public enum GameState{
		READY, RUNNING, GAME_OVER
	}
	
	//Player object
	Player player;
	
	/**
	 * Sets up the world
	 * @param inputHandler
	 */
	public World(){
		player = new Player(this, 50, 50);
		
		currentState = GameState.RUNNING;
	}
		
	/**
	 * Updates the game world
	 * @param delta Delta time since last update in seconds
	 */
	public void update(float delta){
		switch(currentState){
		case READY:
			updateReady(delta);
			break;
		case RUNNING:
		default: 
			updateRunning(delta);
			break;
		}
	}
	
	/**
	 * Updates world when gamestate is READY.
	 * @param delta Delta-time since last update.
	 */
	private void updateReady(float delta){
		
	}
	/**
	 * Updates world when gamestate is RUNNING.
	 * @param delta Delta-time since last update.
	 */
	private void updateRunning(float delta){
		player.update(delta);
	}
	
	/**
	 * Checks whether state is ready or not.
	 * @return True if state is ready, otherwise false.
	 */
	public boolean isReady(){
		return currentState == GameState.READY;
	}
	
	/**
	 * Checks if game is over
	 * @return True if game is over, otherwise false
	 */
	public boolean isGameOver(){
		return currentState == GameState.GAME_OVER;
	}
	
	/**
	 * Changes state to running.
	 */
	public void start(){
		currentState = GameState.RUNNING;
	}
	
	/**
	 * Resets all instance variables and restarts game
	 */
	public void restart(){
		currentState = GameState.READY;
	}
	
	/**
	 * Gets the player object
	 * @return The player object
	 */
	public Player getPlayer(){
		return player;
	}
}
