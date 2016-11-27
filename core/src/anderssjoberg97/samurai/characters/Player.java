/**
 * 
 */
package anderssjoberg97.samurai.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * Represents the player character
 * @author Anders Sjöberg
 *
 */
public class Player {
	//Center position
	private Vector2 position;
	//Velocity
	private Vector2 velocity;
	//Direction which the player is facing
	private float direction;
	//Player sprite texture
	private Sprite sprite;
	
	/**
	 * Creates a new player
	 */
	public Player(float positionX, float positionY){
		position = new Vector2(positionX, positionY);
		direction = 0;
		sprite = new Sprite(new Texture(Gdx.files.internal("player/player-main.png")));
		sprite.setSize(5, 5);
		sprite.setOriginCenter();
		sprite.setCenter(position.x, position.y);
		
		
	}
	
	/**
	 * Updates player state
	 * @param delta Delta-time
	 */
	public void update(float delta){
		movement(delta);
		sprite.setCenter(position.x, position.y);
		sprite.setRotation(direction);
	}
	
	/**
	 * Handles player movement by polling
	 * @param delta Delta-time
	 */
	private void movement(float delta){
		//Up and down movement
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			position.y += 10 * delta;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			position.y -= 10 * delta;
		}
		//Left and right movement
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			position.x -= 10 * delta;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			position.x += 10 * delta;
		}
		
		
	}
	
	/**
	 * Adjusts player facing direction
	 */
	public void setDirection(float direction){
		this.direction = direction;
	}
	
	/**
	 * Gets the position as a vector
	 * @return The player position
	 */
	public Vector2 getPosition(){
		return position;
	}
	
	/**
	 * Gets the player x-position
	 * @return The x-position
	 */
	public float getPositionX(){
		return position.x;
	}
	
	/**
	 * Gets the player y-position
	 * @return The y-position
	 */
	public float getPositionY(){
		return position.y;
	}
	
	/**
	 * Gets the player sprite
	 * @return The player sprite
	 */
	public Sprite getSprite(){
		return sprite;
	}
}
