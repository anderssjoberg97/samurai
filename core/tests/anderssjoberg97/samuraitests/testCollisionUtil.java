/**
 * 
 */
package anderssjoberg97.samuraitests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import anderssjoberg97.samurai.collision.Collidable;
import anderssjoberg97.samurai.collision.CollisionUtil;
import anderssjoberg97.samurai.collision.Hit;

/**
 * @author anderssjoberg
 *
 */
public class testCollisionUtil {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetChunks() {
		ArrayList<Integer[]> chunks = CollisionUtil.getChunks( 
				new Vector2(31.44481f, 17.884836f),
				new Vector2(32.451134f, 16.142637f));
		assertEquals(2, chunks.size());
		assertArrayEquals(new Integer[]{3, 2}, chunks.get(0));
		assertArrayEquals(new Integer[]{4, 2}, chunks.get(1));
	}
	
	@Test
	public void testCollision(){
		 Hit hit = CollisionUtil.calculateHit(new Collidable(32, 16, 1, 1),
					new Vector2(31.44481f, 17.884836f),
					new Vector2(32.451134f, 16.142637f));
		 assertNotNull(hit);
	}

}
