/**
 * 
 */
package anderssjoberg97.samurai.collision;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Objects which are hookable
 * @author Anders Sjöberg
 *
 */
public class Hookable {
	Rectangle rectangle;
	
	/**
	 * Creates a new hookable object
	 */
	public Hookable(float x, float y, float width, float height){
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
}
