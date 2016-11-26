/**
 * 
 */
package anderssjoberg97.samurai.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import anderssjoberg97.samurai.game.objects.characters.Player;
import anderssjoberg97.samurai.game.worlds.World;

/**
 * Handles input and relays it to the player class
 * @author Anders Sjöberg
 *
 */
public class InputHandler implements InputProcessor {
	
	private World world;
	private Player player;
	
	/**
	 * Sets up input handling
	 * @param player A player object
	 */
	public InputHandler(World world){
		this.world = world;
		player = world.getPlayer();
	}
	
	/* Handles keyboard input
	 * @param keycode Key index
	 * @return True if input was handled
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	/* Handles clicks and touches
	 * @param screenX X-position on screen
	 * @param screenY Y-position on screen
	 * @param pointer Pointer
	 * @param button Button index code
	 * @return True if input was handled, otherwise false
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(world.isReady()){
			world.start();
		}
		player.onClick();

		if(world.isGameOver()){
			
			world.restart();
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
