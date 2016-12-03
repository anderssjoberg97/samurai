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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

import anderssjoberg97.samurai.characters.Player;
import anderssjoberg97.samurai.collision.CollisionUtil;
import anderssjoberg97.samurai.collision.Hit;
import anderssjoberg97.samurai.collision.Collidable;
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
	private Vector2 nextFrameEnd;
	//Hook length
	private float length;
	//If hook has been fired
	private boolean fired;
	//Angle
	private float angle;
	//Hook state
	private HookState hookState;
	
	//Encapsulates where hook hit and what it hit
	Hit hit;
	//Temporary value for hit to be compared to
	Hit tempHit;
	
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
		range = 10;
		shootingSpeed = 100;
		pullingSpeed = 50;
		thickness = 0.1f;
		
		
		
		this.player = player;
		this.world = world;
		
		//These will only be used after firing
		nearEnd = new Vector2(this.player.getPosition());
		farEnd = new Vector2(nearEnd);
		nextFrameEnd = new Vector2(nearEnd);
		length = 0;
		
		ropeSprite = new Sprite(new Texture(Gdx.files.internal("gadgets/hook/rope.png")));
		ropeSprite.setSize(length, thickness);
		ropeSprite.setOrigin(0, thickness / 2);
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
				//Calculate where far end will be in the next frame
				nextFrameEnd.x = nearEnd.x + 
							(float)Math.cos(angle * Math.PI / 180) * 
							(length + shootingSpeed * delta);
				nextFrameEnd.y = nearEnd.y + 
							(float)Math.sin(angle * Math.PI / 180) * 
							(length + shootingSpeed * delta);
				System.out.println("Farend " + farEnd.x + "f, " + farEnd.y + "f");
				System.out.println("Nextframe " + nextFrameEnd.x + "f, " + nextFrameEnd.y + "f");
				//Check if hook collides
				System.out.println("Calling function");
				Hit collidable = checkForCollision(farEnd, nextFrameEnd);
				if(collidable != null){
					
					float distanceToCollision = farEnd.dst(collidable.getPosition());
					delta -= distanceToCollision / shootingSpeed;
					length += distanceToCollision;
					farEnd = collidable.getPosition();
					hookState = HookState.PULLING;
					
					
				} else{
					length += shootingSpeed * delta;
				}
			} else if(length >= range - shootingSpeed * delta){
				float tempDelta = delta - (range - length) / shootingSpeed;
				//Calculate where far end will be in the next frame
				nextFrameEnd.x = nearEnd.x + 
							(float)Math.cos(angle * Math.PI / 180) * 
							(length + shootingSpeed * tempDelta);
				nextFrameEnd.y = nearEnd.y + 
							(float)Math.sin(angle * Math.PI / 180) * 
							(length + shootingSpeed * tempDelta);
				
				System.out.println("Farend " + farEnd.x + "f, " + farEnd.y + "f");
				System.out.println("Nextframe " + nextFrameEnd.x + "f, " + nextFrameEnd.y + "f");
				//Check for collisions
				Hit collidable = checkForCollision(farEnd, nextFrameEnd);
				if(collidable != null){
					
					float distanceToCollision = farEnd.dst(collidable.getPosition());
					delta -= distanceToCollision / shootingSpeed;
					length += distanceToCollision;
					farEnd = collidable.getPosition();
					hookState = HookState.PULLING;
					
					
				} else{
					delta = tempDelta;
					length = range;
					hookState = HookState.RETRACTING;
				}
			}
		}
		
		if(hookState == HookState.PULLING){
			if(length > pullingSpeed * delta){
				length -= pullingSpeed * delta;
				nearEnd.x = farEnd.x - (float)Math.cos(angle * Math.PI / 180) * length;
				nearEnd.y = farEnd.y - (float)Math.sin(angle * Math.PI / 180) * length;
				player.setPosition(nearEnd);
			} else {
				delta -= length / pullingSpeed;
				length = 0;
				nearEnd.x = farEnd.x;
				nearEnd.y = farEnd.y;
				hookState = HookState.RETRACTED;
				System.out.println("-------RETRACTED-----");
			}
		} else if(hookState == HookState.RETRACTING){
			if(length > shootingSpeed * delta){
				length -= shootingSpeed * delta;				
			} else if(length <= shootingSpeed * delta){
				delta -= length / shootingSpeed;
				length = 0;
				hookState = HookState.RETRACTED;
				System.out.println("-------RETRACTED-----");
			}
		}
		
		if(hookState != HookState.RETRACTED){
			farEnd.x = nearEnd.x + (float)Math.cos(angle * Math.PI / 180) * length;
			farEnd.y = nearEnd.y + (float)Math.sin(angle * Math.PI / 180) * length;
			ropeSprite.setRotation(angle);
			ropeSprite.setPosition(nearEnd.x, nearEnd.y);
			ropeSprite.setSize(length, thickness);
		}
	}
	
	public void fire(float targetX, float targetY){
		if(hookState == HookState.RETRACTED){
			nearEnd.x = player.getPositionX();
			nearEnd.y = player.getPositionY();
			farEnd.x = nearEnd.x;
			farEnd.y = nearEnd.y;
			nextFrameEnd.x = nearEnd.x;
			nextFrameEnd.y = nearEnd.y;
			angle = MathUtil.angle(nearEnd, targetX, targetY);
			length = 0;
			ropeSprite.setSize(length, thickness);
			hookState = HookState.SHOOTING;
		}
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
	 * Renders the hook
	 */
	@Override
	public void render(ShapeRenderer shapeRenderer){
		if(hookState != HookState.RETRACTED){
			shapeRenderer.line(nearEnd, farEnd);
		}
	}
	
	/**
	 * Checks if hook has hit anything
	 * @param thisFrame Position in the current frame
	 * @param nextFrame Position in the next frame
	 * @return Returns the a Hookable object if colliding, otherwise null
	 */
	private Hit checkForCollision(Vector2 thisFrame, Vector2 nextFrame){
		//Make sure these are null
		//so that false positives won't happen
		hit = null;
		tempHit = null;
		
		//All hookables in world
		ArrayList<ArrayList<ArrayList<Collidable>>> allCollidables = world.getHookables();
		
		//Chunks to look in
		ArrayList<Integer[]> chunks = CollisionUtil.getChunks(thisFrame, nextFrame);
		
		//Look in chunks
		for(int chunkIndex = 0; chunkIndex < chunks.size(); ++chunkIndex){
			ArrayList<Collidable> collidables = 
					allCollidables.get(chunks.get(chunkIndex)[0]).
					get(chunks.get(chunkIndex)[1]);
			for(Collidable collidable : collidables){
				//Hit detection
				tempHit = CollisionUtil.calculateHit(collidable, thisFrame, nextFrame);
				if(tempHit != null){
					if(hit == null){
						hit = tempHit;
					} else{
						if(thisFrame.dst(tempHit.getPosition()) < thisFrame.dst(hit.getPosition())){
							hit = tempHit;
						}
					}
				}	
			}
			//Return if collision was detected in the chunk
			if(hit != null){
				return hit;
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
	
	/**
	 * Disposes the hook
	 */
	public void dispose() {
		hookSprite.getTexture().dispose();
		ropeSprite.getTexture().dispose();
		
	}
}
