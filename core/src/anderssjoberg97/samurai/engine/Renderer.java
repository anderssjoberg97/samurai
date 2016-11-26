/**
 * 
 */
package anderssjoberg97.samurai.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import anderssjoberg97.samurai.game.AssetLoader;
import anderssjoberg97.samurai.game.objects.Grass;
import anderssjoberg97.samurai.game.objects.Pipe;
import anderssjoberg97.samurai.game.objects.ScrollHandler;
import anderssjoberg97.samurai.game.objects.characters.Player;
import anderssjoberg97.samurai.game.worlds.World;

/**
 * Renders the game world
 * @author Anders Sjöberg
 *
 */
public class Renderer {
	//GameWorld to render
	private World world;
	
	//The camera
	private OrthographicCamera camera;
	
	//ShaperRenderer for drawing shapes and lines
	private ShapeRenderer shapeRenderer;
	
	//Stores all sprites
	private SpriteBatch batch;
	
	//Window data
	private float worldViewWidth, worldViewHeight;
	
	//Game objects
	private Player player;
	private ScrollHandler scrollHandler;
	private Grass frontGrass, backGrass;
	private Pipe pipe1, pipe2, pipe3;
	
	//Game assets
	private TextureRegion bg, grass;
	private Animation playerAnimation;
	private TextureRegion playerMid, playerDown, playerUp;
	private TextureRegion skullUp, skullDown, bar;
	
	/**
	 * Creates a renderer
	 * @param world The GameWorld to be rendered
	 * @param worldViewWidth Width of camera view
	 * @param worldViewHeight Height of camera view
	 */
	public Renderer(World world, float worldViewWidth, float worldViewHeight){
		this.world = world;
		
		this.worldViewWidth = worldViewWidth;
		this.worldViewHeight = worldViewHeight;
		
		//Set up camera
		camera = new OrthographicCamera();
		camera.setToOrtho(true, worldViewWidth, worldViewHeight);
		
		//Set up SpriteBatch
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
		//Set up shapeRenderer to use the cameras projection matrix
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		//Initialize assets and objects
		initGameObjects();
		initAssets();
	}
	
	/**
	 * Renders the game world
	 */
	public void render(float runTime){
		
		//Clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		// Begin ShapeRenderer
        shapeRenderer.begin(ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, worldViewHeight / 2 + 66);

        // Draw Grass
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, worldViewHeight / 2 + 66, 136, 11);

        // Draw Dirt
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, worldViewHeight / 2 + 77, 136, 52);

        // End ShapeRenderer
        shapeRenderer.end();
        
        //Draw sprites
        batch.begin();
        batch.disableBlending(); //Disables transparency to save performance
        batch.draw(AssetLoader.bg, 0, worldViewHeight / 2 + 23, 136, 43);
        drawGrass();
        drawPipes();
        batch.enableBlending();
        drawSkulls();
        
        batch.enableBlending();
        if(player.shouldntFlap()){
        	batch.draw(playerMid, player.getX(), player.getY(), 
        			player.getWidth() / 2, player.getHeight() / 2, //Rotation origin
        			player.getWidth(), player.getHeight(), 
        			1, 1, player.getRotation());
        } else {
        	batch.draw(AssetLoader.playerAnimation.getKeyFrame(runTime), 
        			player.getX(), player.getY(),
        			player.getWidth() / 2, player.getHeight() / 2, //Rotation origin
        			player.getWidth(), player.getHeight(), 
        			1, 1, player.getRotation());
        }
        
        
        //UI
        if(world.isReady()){
        	AssetLoader.shadow.draw(batch, "Touch to start", 136 /2 - 42, 76);
        	AssetLoader.font.draw(batch, "Touch to start", 136 /2 - 42, 76);
        } else{
        	if(world.isGameOver()){
        		AssetLoader.shadow.draw(batch, "Game Over", 25, 56);
                AssetLoader.font.draw(batch, "Game Over", 24, 55);
                
                AssetLoader.shadow.draw(batch, "Try again?", 23, 76);
                AssetLoader.font.draw(batch, "Try again?", 24, 75);
        	}
        	
        	//Draw score
            String score = world.getScore() + "";
            AssetLoader.shadow.draw(batch, score, 
            		(136 / 2) - (3 * score.length()), 12);
            AssetLoader.font.draw(batch, score, 
            		(136 / 2) - (3 * score.length()), 12);
        }
        batch.end();
	}
	
	/**
	 * Initializes all game objects
	 */
	private void initGameObjects(){
		player = world.getPlayer();
		scrollHandler = world.getScroller();
        frontGrass = scrollHandler.getFrontGrass();
        backGrass = scrollHandler.getBackGrass();
        pipe1 = scrollHandler.getPipe1();
        pipe2 = scrollHandler.getPipe2();
        pipe3 = scrollHandler.getPipe3();  
	}
	
	/**
	 * Initializes game assets
	 */
	private void initAssets(){
		bg = AssetLoader.bg;
		grass = AssetLoader.grass;
		playerAnimation = AssetLoader.playerAnimation;
		playerMid = AssetLoader.player;
		playerDown = AssetLoader.playerDown;
		playerUp = AssetLoader.playerUp;
		skullUp = AssetLoader.skullUp;
		skullDown = AssetLoader.skullDown;
		bar = AssetLoader.bar;
	}
	
	/**
	 * Draws the grass
	 */
	private void drawGrass(){
		batch.draw(grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        batch.draw(grass, backGrass.getX(), backGrass.getY(),
                backGrass.getWidth(), backGrass.getHeight());
	}
	
	/**
	 * Draws skulls
	 */
	private void drawSkulls(){
		batch.draw(skullUp, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
        batch.draw(skullDown, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

        batch.draw(skullUp, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
        batch.draw(skullDown, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

        batch.draw(skullUp, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
        batch.draw(skullDown, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
	}
	
	/**
	 * Draws pipes
	 */
	private void drawPipes(){
		batch.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
                pipe1.getHeight());
        batch.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45,
                pipe1.getWidth(), worldViewHeight / 2 + 66 - (pipe1.getHeight() + 45));

        batch.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
                pipe2.getHeight());
        batch.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45,
                pipe2.getWidth(), worldViewHeight / 2 + 66 - (pipe2.getHeight() + 45));

        batch.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
                pipe3.getHeight());
        batch.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45,
                pipe3.getWidth(), worldViewHeight / 2 + 66 - (pipe3.getHeight() + 45));
	}
}
