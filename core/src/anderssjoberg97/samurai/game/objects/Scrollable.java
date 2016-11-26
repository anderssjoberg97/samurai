/**
 * 
 */
package anderssjoberg97.samurai.game.objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Keeps track off pipes
 * @author Anders Sjöberg
 *
 */
public class Scrollable {
	protected Vector2 position;
	protected Vector2 velocity;
	protected int width;
	protected int height;
	protected boolean isScrolledLeft;
	
	/**
	 * Sets up scrollable
	 * @param x X-position
	 * @param y Y-position
	 * @param width Width
	 * @param height Height
	 * @param scrollSpeed Scrolling speed
	 */
	public Scrollable(float x, float y, int width, int height, float scrollSpeed){
		position = new Vector2(x, y);
		velocity = new Vector2(scrollSpeed, 0);
		this.width = width;
		this.height = height;
		isScrolledLeft = false;
	}
	
	/**
	 * Updates Scrollable state
	 * @param delta Delta-time since last update
	 */
	public void update(float delta){
		position.add(velocity.cpy().scl(delta));
		
		//If Scrollable has passed edge of screen
		if(position.x + width < 0){
			isScrolledLeft = true;
		}
	}
	
	/**
	 * Resets the Scrollable position
	 * @param newX The new x-position
	 */
	public void reset(float newX){
		position.x = newX;
		isScrolledLeft = false;
	}
	
	/**
	 * Stops the scrolling
	 */
	public void stop(){
		velocity.x = 0;
	}
	
	/**
	 * Checks whether or not Scrollable has scrolled out of screenspace
	 * @return True if scrolled out, otherwise false
	 */
	public boolean isScrolledLeft(){
		return isScrolledLeft;
	}
	
	/**
	 * Gets the right side x-position of Scrollable
	 * @return Right side x-position
	 */
	public float getTailX(){
		return position.x + width;
	}
	
	/**
	 * Gets the x-position
	 * @return X-position
	 */
	public float getX(){
		return position.x;
	}
	
	/**
	 * Gets the y-position
	 * @return Y-position
	 */
	public float getY(){
		return position.y;
	}
	
	/**
	 * Gets the width of Scrollable
	 * @return Scrollable width
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * Gets the height of Scrollable
	 * @return Scrollable height
	 */
	public int getHeight(){
		return height;
	}
}
