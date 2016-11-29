/**
 * 
 */
package anderssjoberg97.samurai.collision;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import anderssjoberg97.samurai.util.MathUtil;
import anderssjoberg97.samurai.world.World;

/**
 * Class for collision detection utitilities
 * @author Anders Sjöberg
 *
 */
public class CollisionUtil {
	/**
	 * Gets the chunks which a line 
	 * between two vectors passes.
	 * @param vector1 Line start point
	 * @param vector2 Line end point
	 * @return A hashset with all the chunks
	 */
	public static ArrayList<Integer[]> getChunks(Vector2 vector1, Vector2 vector2){
		System.out.println("Start " + vector1.x + " " + vector1.y);
		System.out.println("End " + vector2.x + " " + vector2.y);
		//Declare an ArrayList which will store passed chunks
		ArrayList<Integer[]> chunks = new ArrayList<Integer[]>();
		//Start and end chunk
		Integer[] startChunk = new Integer[2];
		startChunk[0] = (int)vector1.x / World.CHUNK_SIZE;
		startChunk[1] = (int)vector1.y / World.CHUNK_SIZE;
		Integer[] endChunk = new Integer[2];
		endChunk[0] = (int)vector2.x / World.CHUNK_SIZE;
		endChunk[1] = (int)vector2.y / World.CHUNK_SIZE;
		
		//Return if startchunk and endchunk are the same
		if(startChunk[0] == endChunk[0] && startChunk[1] == endChunk[1]){
			return chunks;
		}
		//If line never passes a vertical border of chunk
		//get all the chunks above or under the startchunk
		if(startChunk[0] == endChunk[0] && startChunk[1] < endChunk[1]){
			for(int i = startChunk[1] + 1; i <= endChunk[1]; ++i){
				Integer[] tempChunk = new Integer[2];
				tempChunk[0] = startChunk[0];
				tempChunk[1] = i;
				chunks.add(tempChunk);
			}
			return chunks;
		} else if(startChunk[0] == endChunk[0] && startChunk[1] > endChunk[1]){
			for(int i = endChunk[1]; i < startChunk[1]; ++i){
				Integer[] tempChunk = new Integer[2];
				tempChunk[0] = startChunk[0];
				tempChunk[1] = i;
				chunks.add(tempChunk);
			}
			return chunks;
		}
		
		//Angle between vectors
		float angle = MathUtil.angle(vector1, vector2);
		
		//Modify traversing direction
		boolean traverseUp = vector1.y < vector2.y;
		boolean traverseRight = vector1.x < vector2.x;
		
		//Modify angle
		if(traverseUp && traverseRight){
			
		} else if(traverseUp && !traverseRight){
			angle = 180 - angle;
		} else if(!traverseUp && !traverseRight){
			angle = angle - 180;
		} else {
			angle = 360 - angle;
		}
		
		//Loop through all vertical boundaries between chunks
		int currentX = startChunk[0];
		int currentY = startChunk[1];
		while(true){
			//Check distance to vertical border
			float distanceToVerticalBorder;
			if(traverseRight){
				distanceToVerticalBorder = (currentX + 1) * World.CHUNK_SIZE - vector1.x;
			} else {
				distanceToVerticalBorder = vector1.x - currentX * World.CHUNK_SIZE;
			}
			
			//Calculate where line hits this border
			int lineHitChunkY;
			System.out.println("new angle" + angle);
			System.out.println("Distance " + (Math.tan(angle * Math.PI / 180) * 
					distanceToVerticalBorder));
			float yDistance = (float)(Math.tan(angle * Math.PI / 180) * 
					distanceToVerticalBorder);
			if(traverseUp){
				lineHitChunkY = (int)((vector1.y + yDistance) / 
						World.CHUNK_SIZE);
			} else{
				lineHitChunkY = (int)((vector1.y - yDistance) / 
						World.CHUNK_SIZE);
			}
			System.out.println(lineHitChunkY);
			
			//Add all chunks from chunk with currentY as y-value
			//to chunk with linHitChunkY as Y-value
			if(currentX != endChunk[0]){
				while(true){
					chunks.add(new Integer[]{currentX, currentY});
					if(currentY == lineHitChunkY){
						break;
					}
					if(traverseUp){
						++currentY;
					} else {
						--currentY;
					}
				}
			} else {
				while(true){
					chunks.add(new Integer[]{currentX, currentY});
					if(currentY == endChunk[1]){
						break;
					}
					if(traverseUp){
						++currentY;
					} else {
						--currentY;
					}
				}
			}
			if(currentX == endChunk[0]){
				break;
			}
			if(traverseRight){
				++currentX;
			} else {
				--currentX;
			}
		}
		System.out.println("-----------");
		return chunks;
	}
}
