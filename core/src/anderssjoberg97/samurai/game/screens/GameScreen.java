package anderssjoberg97.samurai.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import anderssjoberg97.samurai.engine.Renderer;
import anderssjoberg97.samurai.engine.input.InputHandler;
import anderssjoberg97.samurai.game.worlds.World;

public class GameScreen implements Screen {
	
	//Represents the game world
	private World world;
	//Renders the game world
	private Renderer renderer;
	//Keeps track of time for animations
	private float runTime;
	
	/**
	 * Class constructor
	 */
	public GameScreen(){
		
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 136;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		
		world = new World(gameWidth, gameHeight);
		renderer = new Renderer(world, gameWidth, gameHeight);
		
		//Assign InputHandler class as inputProcessor
		Gdx.input.setInputProcessor(new InputHandler(world));
	}
	
	/**
	 * Render method.
	 */
	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(runTime);
	}
	
	/**
	 * For when window is resized
	 * @param width Window width
	 * @param height Window height
	 */
	@Override
	public void resize(int width, int height) {
		Gdx.app.log("GameScreen", "Resizing");

	}
	
	/**
	 *	When screen is showed.
	 */
	@Override
	public void show() {
		Gdx.app.log("GameScreen", "Show called");

	}

	
	/**
	 * On pause
	 */
	@Override
	public void pause() {
		Gdx.app.log("GameScreen", "Pause called");

	}
	
	/**
	 * On resume
	 */
	@Override
	public void resume() {
		Gdx.app.log("GameScreen", "Resume called");

	}
	
	/**
	 * On hide
	 */
	@Override
	public void hide() {
		Gdx.app.log("GameScreen", "Hide called");

	}
	
	/**
	 * Destroys setup variables
	 */
	@Override
	public void dispose() {
		Gdx.app.log("GameScreen", "Dispose called");

	}

}
