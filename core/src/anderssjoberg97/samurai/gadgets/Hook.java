/**
 * 
 */
package anderssjoberg97.samurai.gadgets;

import java.util.ArrayList;
import java.util.HashSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import anderssjoberg97.samurai.characters.Player;
import anderssjoberg97.samurai.collision.CollisionUtil;
import anderssjoberg97.samurai.collision.Hookable;
import anderssjoberg97.samurai.util.MathUtil;
import anderssjoberg97.samurai.world.World;

/**
 * Gadget which when fired hooks on to a material
 * and pulls the player to that material.
 * Works kind of like spiderman
 * @author Anders Sjöberg
 *
 */
public class Hook implements Gadget {
	public enum HookState {
		RETRACTED, SHOOTING, PULLING, RETRACTING
	}
	
	//Positions of rope ends
	private Vector2 farEnd;
	private Vector2 nearEnd;
	private Vector2 target;
	//Hook length
	private float length;
	//If hook has been fired
	private boolean fired;
	//Angle
	private float angle;
	//Hook state
	private HookState hookState;
	
	//Rope thickness
	private float thickness;
	//Hook range
	private float range;
	//Speed when fired
	private float shootingSpeed;
	//Speed when pulling
	private float pullingSpeed;
	
	//Hook sprite
	private Sprite hookSprite;
	//Rope sprite
	private Sprite ropeSprite;
	
	//Player to pull
	private Player player;
	//World to get graspable objects
	private World world;
	
	/**
	 * Creates a Hook object
	 * which pulls the player towards
	 * the material it hits
	 * @param world World object
	 * @param player Player to pull
	 * @param targetX Mouse aim target x
	 * @param targetY Mouse aim target y
	 */
	public Hook(World world, Player player){
		//Semi-constants
		range = 20;
		shootingSpeed = 100;
		pullingSpeed = 30;
		thickness = 0.1f;
		
		length = 0;
		
		this.player = player;
		this.world = world;
		
		ropeSprite = new Sprite(new Texture(Gdx.files.internal("gadgets/hook/rope.png")));
		ropeSprite.setSize(length, thickness);
		ropeSprite.setPosition(player.getPositionX(), player.getPositionY());
		
		
		hookState = HookState.RETRACTED;
	}
	
	/**
	 * Updates the hook
	 * @param delta Delta-time
	 */
	public void update(float delta){
		if(hookState == HookState.SHOOTING){
			if(length < range - shootingSpeed * delta){
				
				checkForCollision(new Vector2(farEnd.x, farEnd.y), 
						new Vector2(nearEnd.x + 
						(float)Math.cos(angle * 180 / Math.PI) * 
						(length + shootingSpeed * delta), 
						nearEnd.y + 
						(float)Math.sin(angle * 180 / Math.PI) * 
						(length + shootingSpeed * delta)));
				length += shootingSpeed * delta;
			} else if(length >= range - shootingSpeed * delta){
				delta -= (range - length) / shootingSpeed;
				length = range;
				hookState = HookState.RETRACTING;
			}
		}
		
		if(hookState == HookState.RETRACTING){
			if(length > shootingSpeed * delta){
				length -= shootingSpeed * delta;				
			} else if(length <= shootingSpeed * delta){
				delta -= length / shootingSpeed;
				length = 0;
				hookState = HookState.RETRACTED;
			}
		}
		if(hookState != HookState.RETRACTED){
			farEnd.x = nearEnd.x + (float)Math.cos(angle * 180 / Math.PI) * length;
			farEnd.y = nearEnd.y + (float)Math.sin(angle * 180 / Math.PI) * length;
			ropeSprite.setRotation(angle);
			ropeSprite.setPosition(nearEnd.x, nearEnd.y);
			ropeSprite.setSize(length, thickness);
		}
	}
	
	public void fire(float targetX, float targetY){
		if(hookState == HookState.RETRACTED){
			nearEnd = new Vector2(this.player.getPosition());
			farEnd = new Vector2(nearEnd);
			angle = MathUtil.angle(nearEnd, targetX, targetY);
			length = 0;
			ropeSprite.setSize(length, thickness);
			hookState = HookState.SHOOTING;
		}
		Gdx.app.log("Hook", "Fire called");
	}
	
	/**
	 * Renders the hook
	 */
	@Override
	public void render(SpriteBatch batch){
		if(hookState != HookState.RETRACTED){
			ropeSprite.draw(batch);
		}
	}
	
	/**
	 * Checks if hook has hit anything
	 * @param thisFrame Position in the current frame
	 * @param nextFrame Position in the next frame
	 * @return Returns the a Hookable object if colliding, otherwise null
	 */
	private Hookable checkForCollision(Vector2 thisFrame, Vector2 nextFrame){
		ArrayList<ArrayList<ArrayList<Hookable>>> hookables = world.getHookables();
		
		System.out.println(CollisionUtil.getChunks(thisFrame, nextFrame).size());
		for(Integer[] chunk : CollisionUtil.getChunks(thisFrame, nextFrame)){
			for(Hookable hookable : hookables.get(chunk[0]).get(chunk[1])){
				Gdx.app.log("Hook", 
						"Hookable object" + hookable.getX() + " " + hookable.getY());
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the vector for the ropeend closest to the player
	 * @return Near end vector
	 */
	public Vector2 getNearEnd(){
		return nearEnd;
	}
	
	/**
	 * Gets the vector for the ropeend most far away from the player
	 * @return Far end vector
	 */
	public Vector2 getFarEnd(){
		return farEnd;
	}
}
