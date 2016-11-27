/**
 * 
 */
package anderssjoberg97.samurai.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import anderssjoberg97.samurai.characters.Player;
import anderssjoberg97.samurai.ui.UI;
import anderssjoberg97.samurai.util.AssetLoader;
import anderssjoberg97.samurai.world.World;

/**
 * Renders the game world
 * @author Anders Sjöberg
 *
 */
public class Renderer {
	//Renderer constants
	static final int CAMERA_SPAN_HEIGHT = 30;
	
	//World
	private World world;
	//Characters
	private Player player;
	
	//Objects
	private Sprite map;
	
	//UI
	UI ui;
	
	//World and UI camera
	private OrthographicCamera camera;
	private OrthographicCamera uiCamera;
	
	//Sprite renderer helper
	private SpriteBatch batch;
	private SpriteBatch uiBatch;
	
	
	
	
	
	
	/**
	 * Creates a renderer
	 * @param world The GameWorld to be rendered
	 * @param worldViewWidth Width of camera view
	 * @param worldViewHeight Height of camera view
	 */
	public Renderer(World world, UI ui){
		//Get the world and player
		this.world = world;
		player = this.world.getPlayer();
		
		//Set up ui
		this.ui = ui;
		
		//Set world camera
		camera = new OrthographicCamera(
				CAMERA_SPAN_HEIGHT * Gdx.graphics.getWidth() / Gdx.graphics.getHeight(),
				CAMERA_SPAN_HEIGHT);
		
		camera.position.set(player.getPositionX(),
				player.getPositionY(), 0);
		camera.update();
		
		//Set up UI camera
		uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		uiCamera.position.set(0, 0, 0);
		uiCamera.update();
		
		//Setup textures
		map = new Sprite(new Texture(Gdx.files.internal("map.jpg")));
		map.setPosition(0, 0);
		map.setSize(100, 100);
		
		//Set up SpriteBatches
		batch = new SpriteBatch();
		uiBatch = new SpriteBatch();
		uiBatch.setProjectionMatrix(uiCamera.combined);
	}
	
	/**
	 * Renders the game world
	 */
	public void render(float runTime){
		//Update world matrix
		camera.position.set(player.getPositionX(),
				player.getPositionY(), 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		//Update UI matrix
		
		
		//Clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Draw world
		batch.begin();
		batch.disableBlending();
		map.draw(batch);
		batch.enableBlending();
		player.getSprite().draw(batch);
		batch.end();
		
		//Draw UI
		uiBatch.begin();
		ui.getCursorSprite().draw(uiBatch);
		uiBatch.end();
	}
	
	/**
	 * Handles resizing
	 * @param width Physical width of window
	 * @param height Physical height of window
	 */
	public void resize(int width, int height){
		
		camera.viewportWidth = CAMERA_SPAN_HEIGHT * 
				Gdx.graphics.getWidth() / 
				Gdx.graphics.getHeight();
		camera.viewportHeight = CAMERA_SPAN_HEIGHT;
		camera.update();
		
		uiCamera.viewportWidth = Gdx.graphics.getWidth();
		uiCamera.viewportHeight = Gdx.graphics.getHeight();
		uiCamera.update();
		uiBatch.setProjectionMatrix(uiCamera.combined);
	}
	
	/**
	 * Gets the world camera
	 * @return The world camera
	 */
	public OrthographicCamera getWorldCamera(){
		return camera;
	}
	
	
	/**
	 * Gets the UI camera
	 * @return The UI camera
	 */
	public OrthographicCamera getUiCamera(){
		return uiCamera;
	}
}
