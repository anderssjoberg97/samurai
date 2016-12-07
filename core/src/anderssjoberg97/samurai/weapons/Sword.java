/**
 * 
 */
package anderssjoberg97.samurai.weapons;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import anderssjoberg97.samurai.characters.Player;

/**
 * Sword
 * @author Anders Sjöberg
 *
 */
public class Sword implements Weapon {
	//Position of sword
	Vector2 positionHandle, positionTip;
	
	//Player
	Player player
	
	public Sword(Player player){
		
	}
	
	/* Updates the sword state
	 * @param delta Delta-time in seconds
	 * @see anderssjoberg97.samurai.weapons.Weapon#update(float)
	 */
	public void update(float delta){
		
	}
	
	/* Renders the sword as a shape
	 * @param shapeRenderer A ShapeRenderer
	 * @see anderssjoberg97.samurai.weapons.Weapon#render(com.badlogic.gdx.graphics.glutils.ShapeRenderer)
	 */
	@Override
	public void render(ShapeRenderer shapeRenderer) {
		// TODO Auto-generated method stub

	}

}
