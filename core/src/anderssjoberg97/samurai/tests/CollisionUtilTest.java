package anderssjoberg97.samurai.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import anderssjoberg97.samurai.collision.CollisionUtil;

public class CollisionUtilTest {

	@Test
	public void testGetChunksCrossingHorizontally() {
		Vector2 vector1 = new Vector2(47.870106f, 57.761486f);
		Vector2 vector2 = new Vector2(48.14224f, 57.761486f);
		Vector2 vector3 = new Vector2(47.870106f, 56.0828f);
		Vector2 vector4 = new Vector2(48.14224f, 56.0828f);
		
		ArrayList<Integer[]> chunks1 = CollisionUtil.getChunks(vector1, vector4);
		assertEquals(2, chunks1.size());
		assertArrayEquals(new Integer[]{5,7}, chunks1.get(0));
		assertArrayEquals(new Integer[]{6,7}, chunks1.get(1));
		
		ArrayList<Integer[]> chunks2 = CollisionUtil.getChunks(vector4, vector1);
		assertEquals(2, chunks2.size());
		assertArrayEquals(new Integer[]{6,7}, chunks2.get(0));
		assertArrayEquals(new Integer[]{5,7}, chunks2.get(1));
	
		ArrayList<Integer[]> chunks3 = CollisionUtil.getChunks(vector2, vector3);
		assertEquals(2, chunks3.size());
		assertArrayEquals(new Integer[]{6,7}, chunks3.get(0));
		assertArrayEquals(new Integer[]{5,7}, chunks3.get(1));
		
		ArrayList<Integer[]> chunks4 = CollisionUtil.getChunks(vector3, vector2);
		assertEquals(2, chunks4.size());
		assertArrayEquals(new Integer[]{5,7}, chunks4.get(0));
		assertArrayEquals(new Integer[]{6,7}, chunks4.get(1));
	}
	
	@Test
	public void testGetChunksSpanningMultipleAbove(){
		Vector2 vector1 = new Vector2(2, 2);
		Vector2 vector2 = new Vector2(9, 14);
		Vector2 vector3 = new Vector2(2, 14);
		Vector2 vector4 = new Vector2(9, 2);
		
		ArrayList<Integer[]> chunks1 = CollisionUtil.getChunks(vector1, vector2);
		assertEquals(3, chunks1.size());
		assertArrayEquals(new Integer[]{0,0}, chunks1.get(0));
		assertArrayEquals(new Integer[]{0,1}, chunks1.get(1));
		assertArrayEquals(new Integer[]{1,1}, chunks1.get(2));
		
		ArrayList<Integer[]> chunks2 = CollisionUtil.getChunks(vector2, vector1);
		assertEquals(3, chunks2.size());
		assertArrayEquals(new Integer[]{1,1}, chunks2.get(0));
		assertArrayEquals(new Integer[]{0,1}, chunks2.get(1));
		assertArrayEquals(new Integer[]{0,0}, chunks2.get(2));
		
		ArrayList<Integer[]> chunks3 = CollisionUtil.getChunks(vector3, vector4);
		assertEquals(3, chunks3.size());
		assertArrayEquals(new Integer[]{0,1}, chunks3.get(0));
		assertArrayEquals(new Integer[]{0,0}, chunks3.get(1));
		assertArrayEquals(new Integer[]{1,0}, chunks3.get(2));
		
		ArrayList<Integer[]> chunks4 = CollisionUtil.getChunks(vector4, vector3);
		assertEquals(3, chunks4.size());
		assertArrayEquals(new Integer[]{1,0}, chunks4.get(0));
		assertArrayEquals(new Integer[]{0,0}, chunks4.get(1));
		assertArrayEquals(new Integer[]{0,1}, chunks4.get(2));
	}
	
	@Test
	public void testGetChunkTempErr(){
		Vector2 vector1 = new Vector2(65.06006f, 23.845837f);
		Vector2 vector2 = new Vector2(63.967335f, 22.556313f);
		
		ArrayList<Integer[]> chunks1 = CollisionUtil.getChunks(vector1, vector2);
		assertEquals(2, chunks1.size());
		assertArrayEquals(new Integer[]{8,2}, chunks1.get(0));
		assertArrayEquals(new Integer[]{7,2}, chunks1.get(1));
	}

}
