/**
 * 
 */
package anderssjoberg97.samurai.game.objects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import anderssjoberg97.samurai.game.objects.characters.Player;

/**
 * Represents in game pipes
 * @author Anders Sjöberg
 *
 */
public class Pipe extends Scrollable{
	private Random randomGenerator;
	private boolean isScored;
	
	//Collision
	private Rectangle skullUp, skullDown, barUp, barDown;
	private float groundY;
	//Collision constants
	public static final int VERTICAL_GAP = 45;
	public static final int SKULL_WIDTH = 24;
	public static final int SKULL_HEIGHT = 11;
	

	/**
	 * Invokes the superclass Scrollable constructor
	 * @param x X-position
	 * @param y Y-position
	 * @param width Width
	 * @param height Height
	 * @param scrollSpeed Scrolling speed
	 */
	public Pipe(float x, float y, 
			int width, int height, 
			float scrollSpeed, float groundY){
		super(x, y, width, height, scrollSpeed);
		
		randomGenerator = new Random();
		isScored = false;
		
		//Set up collision
		skullUp = new Rectangle();
		skullDown = new Rectangle();
		barUp = new Rectangle();
		barDown = new Rectangle();
		this.groundY = groundY;
	}
	
	/**
	 * Updates pipe state
	 * @param delta Delta-time since last update
	 */
	@Override
	public void update(float delta){
		super.update(delta);
		
		//Update collision
		barUp.set(position.x, position.y, width, height);
        barDown.set(position.x, position.y + height + VERTICAL_GAP, width,
                groundY - (position.y + height + VERTICAL_GAP));
        skullUp.set(position.x - (SKULL_WIDTH - width) / 2, position.y + height
                - SKULL_HEIGHT, SKULL_WIDTH, SKULL_HEIGHT);
        skullDown.set(position.x - (SKULL_WIDTH - width) / 2, barDown.y,
                SKULL_WIDTH, SKULL_HEIGHT);
		
	}
	
	/**
	 * Resets the position of the pipe
	 * @param newX New x-position
	 */
	@Override
	public void reset(float newX){
		//Use superclass reset
		super.reset(newX);
		
		//Set height to random
		height = randomGenerator.nextInt(90) + 15;
		
		isScored = false;
	}
	
	/**
	 * Prepares pipe for restart
	 * @param x X-position
	 * @param scrollSpeed Scrolling speed
	 */
	public void onRestart(float x, float scrollSpeed){
		position.x = x;
		velocity.x = scrollSpeed;
	}
	
	/**
	 * Checks for collisions
	 * @param player Player object to check collision with
	 */
	public boolean collides(Player player){
		if(position.x < player.getX() + player.getWidth()){
			return (Intersector.overlaps(player.getBoundingCircle(), barUp) ||
					Intersector.overlaps(player.getBoundingCircle(), barDown) || 
					Intersector.overlaps(player.getBoundingCircle(), skullUp) ||
					Intersector.overlaps(player.getBoundingCircle(), skullDown)
			);
		}
		return false;
	}
	
	/**
	 * Checks if pipe is scored.
	 * @return True if pipe has been scored, otherwise false.
	 */
	public boolean isScored(){
		return isScored;
	}
	
	/**
	 * Sets isScored
	 * @param value Value to be set
	 */
	public void setScored(boolean value){
		isScored = value;
	}
	
	/**
	 * Gets the skullUp collision rectangle
	 */
	public Rectangle getSkullUp(){
		return skullUp;
	}
	
	/**
	 * Gets the skullDown collision rectangle
	 */
	public Rectangle getSkullDown(){
		return skullDown;
	}
	
	/**
	 * Gets the barUp collision rectangle
	 */
	public Rectangle getBarUp(){
		return barUp;
	}
	
	/**
	 * Gets the barDown collision rectangle
	 */
	public Rectangle getBarDown(){
		return barDown;
	}
}
