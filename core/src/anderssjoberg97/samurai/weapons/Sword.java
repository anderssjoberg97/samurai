/**
 * 
 */
package anderssjoberg97.samurai.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import anderssjoberg97.samurai.characters.Player;
import anderssjoberg97.samurai.util.MathUtil;

/**
 * Sword
 * @author Anders Sjöberg
 *
 */
public class Sword implements Weapon {
	//Position of sword
	private Vector2 position;
	//Sword rotation
	private float rotation;
	//Sword length
	private float length;
	
	//Player
	private Player player;
	
	public Sword(Player player){
		this.player = player;
		
		length = 1;
		
		position = new Vector2(player.getPositionX() + 
				(float)Math.sin(player.getFacingDirection() * Math.PI / 180), 
				player.getPositionY() + 
				(float)Math.cos(player.getFacingDirection() * Math.PI / 180));
	}
	
	/* Updates the sword state
	 * @param delta Delta-time in seconds
	 * @see anderssjoberg97.samurai.weapons.Weapon#update(float)
	 */
	public void update(float delta){
		position.x = player.getPositionX() + 
				(float)Math.sin(player.getFacingDirection() * Math.PI / 180);
		position.y = player.getPositionY() - 
				(float)Math.cos(player.getFacingDirection() * Math.PI / 180);
		Gdx.app.log("Sword", "Facing: " + player.getFacingDirection());
	}
	
	/* Renders the sword as a shape
	 * @param shapeRenderer A ShapeRenderer
	 * @see anderssjoberg97.samurai.weapons.Weapon#render(com.badlogic.gdx.graphics.glutils.ShapeRenderer)
	 */
	@Override
	public void render(ShapeRenderer shapeRenderer) {
		shapeRenderer.rect(position.x, position.y, 
				0f, 0.03f, 
				length, 0.06f, 
				1, 1, 
				player.getFacingDirection());

	}

}
