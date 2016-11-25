package anderssjoberg97.samurai;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Samurai extends ApplicationAdapter {
	//Textures
	private Texture dropImage;
	private Texture bucketImage;
	
	//Sound
	private Sound dropSound;
	private Music rainMusic;
	
	//Rendering fields
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	//Game objects
	private Rectangle bucket;
	private Array<Rectangle> raindrops;
	
	//Keeps track of dropspawning
	private long lastDropTime;
	
	@Override
	public void create () {
		//Load images
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		
		//Load sound
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		
		//Start music
		rainMusic.setLooping(true);
		rainMusic.play();
		
		//Create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		//Create spritebatch
		batch = new SpriteBatch();
		
		//Create bucket
		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;
		
		//Define raindrops and spawn a raindrop
		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}

	@Override
	public void render () {
		//Clear the screen
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Update camera matrices
		camera.update();
		
		//Tell batch to render in coordinate system 
		//specified by the camera
		batch.setProjectionMatrix(camera.combined);
		
		//Render game objects
		batch.begin();
		batch.draw(bucketImage,  bucket.x, bucket.y);
		for(Rectangle raindrop : raindrops){
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		batch.end();
		
		//Process touch input
		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}
		
		//Process keyboard input
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			bucket.x += 200 * Gdx.graphics.getDeltaTime();
		}
		
		if(bucket.x < 0){
			bucket.x = 0;
		} else if (bucket.x > 800 - 64){
			bucket.x = 800 - 64;
		}
		
		//Check if its time to spwan a raindrop
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000){
			spawnRaindrop();
		}
		
		//Move and remove raindrops
		Iterator<Rectangle> iter = raindrops.iterator();
		while(iter.hasNext()){
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0){
				iter.remove();
			} else if(raindrop.overlaps(bucket)){
				dropSound.play();
				iter.remove();
			}
		}
	}
	
	@Override
	public void dispose () {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}
	
	private void spawnRaindrop(){
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800 - 64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}
}
