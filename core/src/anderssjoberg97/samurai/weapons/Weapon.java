/**
 * 
 */
package anderssjoberg97.samurai.weapons;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Interface for weapons
 * @author Anders Sjöberg
 *
 */
public interface Weapon {
	public void update(float delta);
	public void render(ShapeRenderer shapeRenderer);
}
