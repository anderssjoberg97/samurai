/**
 * 
 */
package anderssjoberg97.samurai.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import anderssjoberg97.samurai.characters.Player;

/**
 * Handles input events
 * @author Anders Sjöberg
 *
 */
public class InputHandler implements InputProcessor {
	private OrthographicCamera camera;
	private OrthographicCamera uiCamera;
	
	private Player player;
	
	public InputHandler(OrthographicCamera camera, OrthographicCamera uiCamera, Player player){
		this.camera = camera;
		this.uiCamera = uiCamera;
		this.player = player;
	}
	
	/**
	 * Polls for input relays any keypresses
	 */
	public void poll(){
		
		//Set player facing direction
		player.setFacingDirection(
				(Gdx.input.getX() - Gdx.graphics.getWidth() / 2) * 
					camera.viewportWidth / 
					Gdx.graphics.getWidth() + 
					camera.position.x
				,
				(Gdx.graphics.getHeight() / 2 - Gdx.input.getY()) * 
					camera.viewportHeight / 
					Gdx.graphics.getHeight() + 
					camera.position.y
				);
	}
	
	/* (non-Javadoc)
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

	/* Handles click and touch events
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//Call players clickevent
		player.onClick(
				(screenX - Gdx.graphics.getWidth() / 2) * 
					camera.viewportWidth / 
					Gdx.graphics.getWidth() + 
					camera.position.x
				,
				(Gdx.graphics.getHeight() / 2 - screenY) * 
					camera.viewportHeight / 
					Gdx.graphics.getHeight() + 
					camera.position.y
				, button);
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
