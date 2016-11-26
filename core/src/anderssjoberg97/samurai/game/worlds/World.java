/**
 * 
 */
package anderssjoberg97.samurai.game.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import anderssjoberg97.samurai.game.AssetLoader;
import anderssjoberg97.samurai.game.objects.Grass;
import anderssjoberg97.samurai.game.objects.Pipe;
import anderssjoberg97.samurai.game.objects.ScrollHandler;
import anderssjoberg97.samurai.game.objects.characters.Player;

/**
 * Represents a game world
 * @author Anders Sjöberg
 *
 */
public class World {
	
	
	
	private int score = 0;
	
	//Game objects
	private Player player;
	private ScrollHandler scrollHandler;
	
	//Ground collision
	private Rectangle ground;
	
	//Keeps track of game states
	private GameState currentState;
	public enum GameState{
		READY, RUNNING, GAMEOVER
	}
	
	//World seen through camera
	private float worldViewWidth, worldViewHeight;
	
	/**
	 * Sets up the world
	 * @param worldViewWidth Width of camera view
	 * @param worldViewHeight Height of camera view
	 */
	public World(float worldViewWidth, float worldViewHeight){
		
		this.worldViewWidth = worldViewWidth;
		this.worldViewHeight = worldViewHeight;
		currentState = GameState.READY;
		player = new Player(33, worldViewHeight / 2, 17, 12);
		scrollHandler = new ScrollHandler(this, worldViewHeight / 2 + 66);
		
		ground = new Rectangle(0, worldViewHeight / 2 + 66, 136, 11);
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
		if(delta > .15f){
			delta = .15f;
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
		scrollHandler.update(delta);
		
		if(scrollHandler.collides(player) && player.isAlive()){
			scrollHandler.stop();
			player.die();
			AssetLoader.dead.play();
		}
		
		if(Intersector.overlaps(player.getBoundingCircle(), ground)){
			scrollHandler.stop();
			player.die();
			player.decelerate();
			currentState = GameState.GAMEOVER;
		}
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
		return currentState == GameState.GAMEOVER;
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
		score = 0;
		player.onRestart((int)(worldViewHeight / 2 - 5));
		scrollHandler.onRestart();
		currentState = GameState.READY;
	}
	
	/**
	 * Gets the player
	 * @return A player object
	 */
	public Player getPlayer(){
		return player;
	}
	
	/**
	 * Gets the scrollHandler
	 */
	public ScrollHandler getScroller(){
		return scrollHandler;
	}
	
	/**
	 * Gets the score
	 */
	public int getScore(){
		return score;
	}
	
	/**
	 * Increments score
	 * @param increment Value to increment by
	 */
	public void addScore(int increment){
		score += increment;
	}
}
