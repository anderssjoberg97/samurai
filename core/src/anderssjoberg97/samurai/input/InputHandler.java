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
		adjustPlayerDirection();
	}
	
	/**
	 * Changes the facing direction of the player
	 */
	private void adjustPlayerDirection(){
		
		float direction = 0.0f;
		float differenceX = 
				(Gdx.input.getX() - Gdx.graphics.getWidth() / 2) * 
				camera.viewportWidth / 
				Gdx.graphics.getWidth() +
				camera.position.x - 
				player.getPositionX();
		float differenceY = (Gdx.graphics.getHeight() / 2 - Gdx.input.getY()) * 
				camera.viewportHeight / 
				Gdx.graphics.getHeight() + 
				camera.position.y - 
				player.getPositionY();
		
		if(differenceX == 0.0f){
			if(differenceY >= 0.0f){
				direction = 90f;
			} else {
				direction = - 90f;
			}
		} else {
			if(differenceX < 0){
				direction = (float) Math.atan(differenceY / differenceX) * 
						180 / (float)Math.PI + 180;
			} else {
				direction = (float) Math.atan(differenceY / differenceX) * 
						180 / (float) Math.PI;
			}
		}
		player.setDirection(direction = direction  );
		Gdx.app.log("InputHandler", "" + direction);
		
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

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
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
