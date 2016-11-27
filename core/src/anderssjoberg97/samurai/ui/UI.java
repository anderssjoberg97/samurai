/**
 * 
 */
package anderssjoberg97.samurai.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Handles UI
 * @author Anders Sjöberg
 *
 */
public class UI {
	private Cursor cursor;
	
	/**
	 * Sets up UI
	 */
	public UI(){
		cursor = new Cursor();
	}
	
	/**
	 * Updates the UI
	 * @param delta Delta-time
	 */
	public void update(float delta){
		cursor.update();
	}
	
	/**
	 * Gets the cursor sprite
	 * @return The cursor sprite
	 */
	public Sprite getCursorSprite(){
		return cursor.getSprite();
	}
}
