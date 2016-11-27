/**
 * 
 */
package anderssjoberg97.samurai.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Class for handling cursor crosshair
 * @author Anders Sjöberg
 *
 */
public class Cursor {
	private Sprite sprite;
	
	/**
	 * Creates a cursor object
	 */
	public Cursor(){		
		//Set up sprite
		sprite = new Sprite(new Texture(Gdx.files.internal("ui/crosshair.png")));
		sprite.setSize(50, 50);
		sprite.setOriginCenter();
		sprite.setCenter(Gdx.input.getX(), 
				Gdx.graphics.getHeight() - Gdx.input.getY());
	}
	
	/**
	 * Updates cursor position
	 */
	public void update(){
		sprite.setCenter(Gdx.input.getX() - Gdx.graphics.getWidth() / 2, 
				Gdx.graphics.getHeight() / 2 - Gdx.input.getY());
	}
	
	/**
	 * Gets the cursor sprite
	 * @return The cursor sprite
	 */
	public Sprite getSprite(){
		return sprite;
	}
}
