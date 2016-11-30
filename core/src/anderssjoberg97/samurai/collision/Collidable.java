/**
 * 
 */
package anderssjoberg97.samurai.collision;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Objects which have collision
 * @author Anders Sjöberg
 *
 */
public class Collidable {
	Rectangle rectangle;
	
	/**
	 * Creates a new hookable object
	 */
	public Collidable(float x, float y, float width, float height){
		rectangle = new Rectangle(x, y, width, height);
	}
	
	/**
	 * Renders a red box where object is
	 */
	public void render(ShapeRenderer shapeRenderer){
		shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}
	
	/**
	 * Gets the position x-coordinate
	 * @return X-coordinate
	 */
	public float getX(){
		return rectangle.x;
	}
	
	/**
	 * Gets the position y-coordinate
	 * @return y-coordinate
	 */
	public float getY(){
		return rectangle.y;
	}
	
	/**
	 * Gets the width of the collidable object
	 * @return Width of object
	 */
	public float getWidth(){
		return rectangle.width;
	}
	
	/**
	 * Gets the height of the collidable object
	 * @return Height of object
	 */
	public float getHeight(){
		return rectangle.height;
	}
	
	/**
	 * Gets the collision rectangle
	 * @return The collision rectangle
	 */
	public Rectangle getRectangle(){
		return rectangle;
	}
}
