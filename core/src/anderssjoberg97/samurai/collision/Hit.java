/**
 * 
 */
package anderssjoberg97.samurai.collision;

import com.badlogic.gdx.math.Vector2;

/**
 * Encapsulates hit data such as hit object and hit location
 * @author Anders Sjöberg
 *
 */
public class Hit {
	private Vector2 position;
	private Collidable collisionObject;
	
	/**
	 * Creates a hit object
	 * @param collisionObject Object which was hit
	 * @param position Position of impact in world coordinates
	 */
	public Hit(Collidable collisionObject, Vector2 position) {
		this.collisionObject = collisionObject;
		this.position = position;
	}

	/**
	 * Sets the position of the hit
	 * @param position Position of impact in world coordinates
	 */
	public void setPosition(Vector2 position){
		this.position = position;
	}
	
	/**
	 * Gets the position of the hit
	 * @return Position of impact in world coordinates
	 */
	public Vector2 getPosition(){
		return position;
	}
	
	/**
	 * Gets the x-position of the hit
	 * @return Position of impact in world x-coordinates
	 */
	public float getPositionX(){
		return position.x;
	}
	
	/**
	 * Gets the y-position of the hit
	 * @return Position of impact in world y-coordinates
	 */
	public float getPositionY(){
		return position.y;
	}
	
	/**
	 * Sets the object which was hit
	 * @param hitObject Hit object
	 */
	public void setCollisionObject(Collidable collisionObject){
		this.collisionObject = collisionObject;
	}
	
	/**
	 * Gets the object which was hit
	 * @return The collision object
	 */
	public Collidable getCollisionObject(){
		return collisionObject;
	}
}
