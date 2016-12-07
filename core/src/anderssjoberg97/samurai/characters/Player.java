/**
 * 
 */
package anderssjoberg97.samurai.characters;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import anderssjoberg97.samurai.gadgets.Gadget;
import anderssjoberg97.samurai.gadgets.Hook;
import anderssjoberg97.samurai.util.MathUtil;
import anderssjoberg97.samurai.world.World;

/**
 * 
 * Represents the player character
 * @author Anders Sjöberg
 *
 */
public class Player {
	//World object
	World world;
	
	//Center position
	private Vector2 position;
	//Velocity
	private Vector2 velocity;
	//Walking direction
	private float movingDirection;
	//Direction which the player is facing
	private float facingDirection;
	//Player sprite texture
	private Sprite sprite;
	
	//Weapons
	private ArrayList<Weapons> weapons;
	
	//Gadgets
	private ArrayList<Gadget> gadgets;
	private Hook hook;
	
	/**
	 * Creates a new player
	 */
	public Player(World world, float positionX, float positionY){
		this.world = world;
		
		position = new Vector2(positionX, positionY);
		facingDirection = 0;
		sprite = new Sprite(new Texture(Gdx.files.internal("player/player-main.png")));
		sprite.setSize(1, 1);
		sprite.setOriginCenter();
		sprite.setCenter(position.x, position.y);
		
		hook = new Hook(this.world, this);
		
	}
	
	/**
	 * Updates player state
	 * @param delta Delta-time
	 */
	public void update(float delta){
		hook.update(delta);
		movement(delta);
		sprite.setCenter(position.x, position.y);
		sprite.setRotation(facingDirection);
		
		
	}
	
	/**
	 * Renders the player and gadgets
	 * @param batch SpriteBatch helper
	 */
	public void render(SpriteBatch batch){
		sprite.draw(batch);
		hook.render(batch);
	}
	
	/**
	 * Renders the player and gadgets
	 * @param shapeRenderer Shaperenderer
	 */
	public void render(ShapeRenderer shapeRenderer){
		/*shapeRenderer.rect(position.x - 0.15f, position.y - 0.5f, 
				0.15f, 0.5f, 
				0.3f, 1, 
				1, 1, 
				facingDirection);*/
		//hook.render(shapeRenderer);
		
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
	 * Handles click events. 
	 * Like using weapons and gadgets
	 * @param worldX Mouse world x-coordinates
	 * @param worldY Mouse world y-coordinates
	 * @param button Mouse button index code
	 */
	public void onClick(float worldX, float worldY, int button){
		hook.fire(worldX, worldY);
	}
	
	/**
	 * Adjusts player facing direction
	 */
	public void setFacingDirection(float facingDirection){
		this.facingDirection = facingDirection;
	}
	
	/**
	 * Adjusts player facing direction based on mouse world coordinates
	 * @param worldX Mouse world x-coordinates
	 * @param worldY Mouse wolrd y-coordinates
	 */
	public void setFacingDirection(float worldX, float worldY){
		facingDirection = MathUtil.angle(position, worldX, worldY);
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
	
	/**
	 * Disposes player
	 */
	public void dispose() {
		sprite.getTexture().dispose();
		
		hook.dispose();
		
	}
	
	/**
	 * Sets the position of the player
	 * @param position Position to be set
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
		
	}
}
