/**
 * 
 */
package anderssjoberg97.samurai.world;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import anderssjoberg97.samurai.characters.Player;
import anderssjoberg97.samurai.collision.Hookable;
import anderssjoberg97.samurai.util.AssetLoader;

/**
 * Represents a game world
 * @author Anders Sjöberg
 *
 */
public class World {
	
	public static final int WORLD_WIDTH = 100;
	public static final int WORLD_HEIGHT = 100;
	public static final int CHUNK_SIZE = 8;
	
	
	//Keeps track of game states
	private GameState currentState;
	public enum GameState{
		READY, RUNNING, GAME_OVER
	}
	
	//Player object
	Player player;
	
	//A hookable object
	ArrayList<ArrayList<ArrayList<Hookable>>> hookables;
	
	/**
	 * Sets up the world
	 * @param inputHandler
	 */
	public World(){
		player = new Player(this, 50, 50);
		
		hookables = new ArrayList<ArrayList<ArrayList<Hookable>>>();
		for(int i = 0; i < Math.ceil((float)WORLD_WIDTH / CHUNK_SIZE); ++i){
			hookables.add(new ArrayList<ArrayList<Hookable>>());
			for(int j = 0; j < Math.ceil((float)WORLD_HEIGHT / CHUNK_SIZE); ++j){
				hookables.get(i).add(new ArrayList<Hookable>());
			}
		}
		
		hookables.get(3).get(3).add(new Hookable(20, 20, 10, 10));
		
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
	
	/**
	 * Gets a hookable object
	 */
	public ArrayList<ArrayList<ArrayList<Hookable>>> getHookables(){
		return hookables;
	}
}
