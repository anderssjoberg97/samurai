package anderssjoberg97.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import anderssjoberg97.samurai.input.InputHandler;
import anderssjoberg97.samurai.render.Renderer;
import anderssjoberg97.samurai.ui.UI;
import anderssjoberg97.samurai.world.World;

public class GameScreen implements Screen {
	
	//Represents the game world
	private World world;
	//HAndles UI
	private UI ui;
	
	//Renders the game world
	private Renderer renderer;
	
	//Handles input
	private InputHandler inputHandler;
	
	//Keeps track of time for animations
	private float runTime;
	
	/**
	 * Creates an new screen
	 */
	public GameScreen(){
		
		//Create world
		world = new World();
		
		//Create a UI
		ui = new UI();
		
		//Create a renderer
		renderer = new Renderer(world, ui);
		
		//Set input processor
		inputHandler = new InputHandler(renderer.getWorldCamera(), 
				renderer.getUiCamera(), 
				world.getPlayer());
		Gdx.input.setInputProcessor(inputHandler);
	}
	
	/**
	 * Render method.
	 */
	@Override
	public void render(float delta) {
		runTime += delta;
		inputHandler.poll();
		world.update(delta);
		ui.update(delta);
		renderer.render(runTime);
	}
	
	/**
	 * For when window is resized
	 * @param width Window width
	 * @param height Window height
	 */
	@Override
	public void resize(int width, int height) {
		renderer.resize(width, height);

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
