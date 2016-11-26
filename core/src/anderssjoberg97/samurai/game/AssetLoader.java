/**
 * 
 */
package anderssjoberg97.samurai.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Loads game textures
 * @author Anders Sjöberg
 *
 */
public class AssetLoader {
	public static Texture texture;
	public static TextureRegion bg, grass;
	
	public static Animation playerAnimation;
	public static TextureRegion player, playerDown, playerUp;
	
	public static TextureRegion skullUp, skullDown, bar;
	
	//Sound effects
	public static Sound dead, flap, coin;
	
	public static BitmapFont font, shadow;
	
	/**
	 * Loads texture file assets
	 */
	public static void load(){
		//Load texture file
		texture = new Texture(Gdx.files.internal("data/texture.png"));
		//Set texture filter
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		//Set background texture region
		bg = new TextureRegion(texture, 0, 0, 136, 43);
		bg.flip(false, true);
		
		//Set grass texture region
		grass = new TextureRegion(texture, 0, 43, 143, 11);
		grass.flip(false, true);
		
		//Set up player frame animation
		playerDown = new TextureRegion(texture, 136, 0, 17, 12);
		playerDown.flip(false, true);
		player = new TextureRegion(texture, 153, 0, 17, 12);
		player.flip(false, true);
		playerUp = new TextureRegion(texture, 170, 0, 17, 12);
		playerUp.flip(false, true);
		TextureRegion[] playerFrames = {playerDown, player, playerUp};
		playerAnimation = new Animation(0.06f, playerFrames);
		playerAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		//Set up skull texture regions
		skullUp = new TextureRegion(texture, 192, 0, 24, 14);
		skullDown = new TextureRegion(texture, 192, 0, 24, 14);
		skullDown.flip(false, true);
		
		//Set up bar texture region
		bar = new TextureRegion(texture, 136, 16, 22, 3);
		bar.flip(false, true);
		
		//Sound when dying
		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
		
		//Fonts
		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.getData().setScale(.25f, -.25f);
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.getData().setScale(.25f, -.25f);
		
	}
	
	/**
	 * Disposes textures
	 */
	public static void dispose(){
		//Dispose textures
		texture.dispose();
		
		// Dispose sounds
        dead.dispose();
        flap.dispose();
        coin.dispose();
        
        //Dispose fonts
        font.dispose();
        shadow.dispose();
	}
	
	
}
