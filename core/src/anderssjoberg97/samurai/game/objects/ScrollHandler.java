/**
 * 
 */
package anderssjoberg97.samurai.game.objects;

import anderssjoberg97.samurai.game.AssetLoader;
import anderssjoberg97.samurai.game.objects.characters.Player;
import anderssjoberg97.samurai.game.worlds.World;

/**
 * Handles all scrollable objects
 * @author Anders Sjöberg
 *
 */
public class ScrollHandler {
	private World world;
	
	//Scrollables to handle
	private Grass frontGrass, backGrass;
	private Pipe pipe1, pipe2, pipe3;
	
	//Scroll constants
	public static final int SCROLL_SPEED = -59;
	public static final int PIPE_GAP = 49;
	
	/**
	 * Class constructor
	 * @param yPos Origin for creating Scrollables
	 */
	public ScrollHandler(World world, float yPos){
		this.world = world;
		frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
		backGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11, SCROLL_SPEED);
		
		pipe1 = new Pipe(210, 0, 22, 60, SCROLL_SPEED, yPos);
		pipe2 = new Pipe(pipe1.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
		pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
	}
	
	/**
	 * Updates all scrollables
	 * @param delta Delta-time since last update
	 */
	public void update(float delta){
		//Call object update methods
		frontGrass.update(delta);
		backGrass.update(delta);
		pipe1.update(delta);
		pipe2.update(delta);
		pipe3.update(delta);
		
		//Reset pipes if has scrolled out of screen
		if (pipe1.isScrolledLeft()) {
            pipe1.reset(pipe3.getTailX() + PIPE_GAP);
        } else if (pipe2.isScrolledLeft()) {
            pipe2.reset(pipe1.getTailX() + PIPE_GAP);

        } else if (pipe3.isScrolledLeft()) {
            pipe3.reset(pipe2.getTailX() + PIPE_GAP);
        }
		
        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());

        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());

        }
	}
	
	/**
	 * Resets instance variables and prepares for restart
	 */
	public void onRestart(){
		frontGrass.onRestart(0, SCROLL_SPEED);
		backGrass.onRestart(frontGrass.getTailX(), SCROLL_SPEED);
        pipe1.onRestart(210, SCROLL_SPEED);
        pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
        pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, SCROLL_SPEED);
	}
	
	/**
	 * Stops scrolling
	 */
	public void stop(){
		frontGrass.stop();
	    backGrass.stop();
	    pipe1.stop();
	    pipe2.stop();
	    pipe3.stop();
	}
	
	/**
	 * Checks for collision
	 * @param player Player object
	 * @return True Player object collides with something
	 * otherwise false
	 */
	public boolean collides(Player player){
		if (!pipe1.isScored()
                && pipe1.getX() + (pipe1.getWidth() / 2) < player.getX()
                        + player.getWidth()) {
            addScore(1);
            pipe1.setScored(true);
            AssetLoader.coin.play();
        } else if (!pipe2.isScored()
                && pipe2.getX() + (pipe2.getWidth() / 2) < player.getX()
                        + player.getWidth()) {
            addScore(1);
            pipe2.setScored(true);
            AssetLoader.coin.play();

        } else if (!pipe3.isScored()
                && pipe3.getX() + (pipe3.getWidth() / 2) < player.getX()
                        + player.getWidth()) {
            addScore(1);
            pipe3.setScored(true);
            AssetLoader.coin.play();

        }

        return (pipe1.collides(player) || pipe2.collides(player) || pipe3
                .collides(player));
	}
	
	/**
	 * Adds points to score count
	 * @param increment Points to add
	 */
	private void addScore(int increment){
		world.addScore(1);
	}
	
	/**
	 * Gets the front grass
	 * @return Front grass object
	 */
	public Grass getFrontGrass(){
		return frontGrass;
	}
	
	/**
	 * Gets the back grass
	 * @return Front back object
	 */
	public Grass getBackGrass(){
		return backGrass;
	}
	
	/**
	 * Gets pipe
	 * @return A pipe
	 */
	public Pipe getPipe1(){
		return pipe1;
	}
	
	/**
	 * Gets pipe
	 * @return A pipe
	 */
	public Pipe getPipe2(){
		return pipe2;
	}
	/**
	 * Gets pipe
	 * @return A pipe
	 */
	public Pipe getPipe3(){
		return pipe3;
	}
}
