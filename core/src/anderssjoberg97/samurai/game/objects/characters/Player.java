/**
 * 
 */
package anderssjoberg97.samurai.game.objects.characters;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import anderssjoberg97.samurai.game.AssetLoader;

/**
 * Class representing the player
 * @author Anders Sjöberg
 *
 */
public class Player {
	
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;
	
	private float rotation;
	private int width;
	private int height;
	
	private boolean isAlive;
	
	private Circle boundingCircle;
	
	/**
	 * Sets up player
	 * @param x X-position
	 * @param y Y-position
	 * @param width Width
	 * @param height Height
	 */
	public Player(float x, float y, int width, int height){
		this.width = width;
		this.height = height;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 460);
		
		isAlive = true;
		
		boundingCircle = new Circle();
	}
	
	/**
	 * Updates player state
	 * @param delta Delta time since last update
	 */
	public void update(float delta){
		velocity.add(acceleration.cpy().scl(delta));
		
		if(velocity.y > 200){
			velocity.y = 200;
		}
		
		position.add(velocity.cpy().scl(delta));
		
		//Set collision hitbox position
		boundingCircle.set(position.x + 9, position.y + 6, 6.5f);
		
		if(velocity.y < 0){
			rotation -= 600 * delta;
			
			if(rotation < -20){
				rotation = -20;
			}
		} else if (isFalling() || !isAlive){
			rotation += 480 * delta;
			if(rotation > 90){
				rotation = 90;
			}
		}
	}
	
	/**
	 * Resets player instance variables
	 * @param y Y-position
	 */
	public void onRestart(int y){
		rotation = 0;
		position.y = y;
		velocity.x = 0;
		velocity.y = 0;
		acceleration.x = 0;
		acceleration.y = 460;
		isAlive = true;
	}
	
	/**
	 * Kills the player
	 */
	public void die(){
		isAlive = false;
		velocity.y = 0;
	}
	
	/**
	 * Stops the bird from accelerating downwards
	 */
	public void decelerate(){
		acceleration.y = 0;
	}
	
	/**
	 * Checks whether or not player is falling
	 * @return True if falling, otherwise false
	 */
	public boolean isFalling(){
		return velocity.y > 110;
	}
	
	/**
	 * Checks whether wings should flap or not
	 * @return True if wings should flap, otherwise false
	 */
	public boolean shouldntFlap(){
		return velocity.y > 70 || !isAlive;
	}
	
	/**
	 * When screen is clicked make the player jump
	 */
	public void onClick(){
		if(isAlive){
			AssetLoader.flap.play();
			velocity.y = -140;
		}
		
	}
	
	/**
	 * Gets the x-position
	 * @return The x-position
	 */
	public float getX(){
		return position.x;
	}
	
	/**
	 * Gets the y-position
	 * @return The y-position
	 */
	public float getY(){
		return position.y;
	}
	
	/**
	 * Gets the width of the player
	 * @return Width of the player
	 */
	public float getWidth(){
		return width;
	}
	
	/**
	 * Gets the heihgt of the player
	 * @return Height of the player
	 */
	public float getHeight(){
		return height;
	}
	
	/**
	 * Gets the rotation of the player
	 * @return The rotation
	 */
	public float getRotation(){
		return rotation;
	}
	
	/**
	 * Gets the collision hitbox circle
	 */
	public Circle getBoundingCircle(){
		return boundingCircle;
	}
	
	/**
	 * Checks if player is alive
	 * @return True if player is alive, otherwise false
	 */
	public boolean isAlive(){
		return isAlive;
	}
	
	
}
