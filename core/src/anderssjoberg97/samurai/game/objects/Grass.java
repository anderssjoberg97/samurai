/**
 * 
 */
package anderssjoberg97.samurai.game.objects;

/**
 * Grass class
 * @author Anders Sjöberg
 *
 */
public class Grass extends Scrollable{
	
	/**
	 * Invokes the super constructor
	 * @param x X-position
	 * @param y Y-position
	 * @param width Width
	 * @param height Height
	 * @param scrollSpeed Scrolling speed
	 */
	public Grass(float x, float y, int width, int height, float scrollSpeed){
		super(x, y, width, height, scrollSpeed);
	}
	
	/**
	 * Prepares for restart.
	 * @param x X-position
	 * @param scrollSpeed Scrolling speed
	 */
	public void onRestart(float x, float scrollSpeed){
		position.x = x;
		velocity.x = scrollSpeed;
	}
}
