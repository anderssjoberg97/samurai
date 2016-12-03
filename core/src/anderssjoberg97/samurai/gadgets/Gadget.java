/**
 * 
 */
package anderssjoberg97.samurai.gadgets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Interface for gadgets
 * @author Anders Sjöberg
 *
 */
public interface Gadget {
	public void render(SpriteBatch batch);
	public void render(ShapeRenderer shapeRenderer);
}
