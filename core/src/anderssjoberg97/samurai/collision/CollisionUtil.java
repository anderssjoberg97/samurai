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
			chunks.add(startChunk);
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
			float yDistance = (float)(Math.tan(angle * Math.PI / 180) * 
					distanceToVerticalBorder);
			if(traverseUp){
				lineHitChunkY = (int)((vector1.y + yDistance) / 
						World.CHUNK_SIZE);
			} else{
				lineHitChunkY = (int)((vector1.y - yDistance) / 
						World.CHUNK_SIZE);
			}
			
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
		return chunks;
	}
	
	/**
	 * Calculates where collidable object was hit by line, if hit.
	 * @param collisionObject Object to check for collision with
	 * @param start Line segment's starting point
	 * @param end Line segment's end point
	 * @return A Hit object if object was hit, otherwise null
	 */
	public static Hit calculateHit(Collidable collisionObject, Vector2 start, Vector2 end) {
		//Line angle
		float angle = MathUtil.angle(start, end);
		//When line comes in from the top
		if(start.x >= collisionObject.getX() &&
				start.x <= collisionObject.getX() + collisionObject.getWidth() &&
				start.y > collisionObject.getY() + collisionObject.getHeight()){
			System.out.println("Top");
			//If line end is not located under the upper border, object can't be hit
			if(end.y > collisionObject.getY() + collisionObject.getHeight()){
				System.out.println("OVER " + end.x + " " + end.y);
				return null;
			}
			//Special case if aimed right down
			else if(angle == 270.0f){
				return new Hit(collisionObject, new Vector2(start.x, collisionObject.getY()));
			}
			
			System.out.println("Start x: " + start.x + " y: " + start.y);
			
			//The distance to the object from the start of line
			float distanceToObject = start.y - collisionObject.getY() - collisionObject.getHeight();
			System.out.println("Vertical distance " + distanceToObject);
			//Calculate the x-position of line when the 
			//y-position is equal to objects top border y-position
			float positionX = (float)((-1) * distanceToObject / 
					Math.tan(angle * Math.PI / 180)) + 
					start.x;
			System.out.println("Tan " + (angle) + " = " + Math.tan(angle * Math.PI / 180));
			if(positionX >= collisionObject.getX() &&
					positionX <= collisionObject.getX() + collisionObject.getWidth()){
				Gdx.app.log("CollisionUtil", "Collision at - X: " + positionX + 
						" Y: " + (collisionObject.getY() + collisionObject.getHeight()));
				return new Hit(collisionObject, 
						new Vector2(positionX, 
								collisionObject.getY() + collisionObject.getHeight()
						));
			} else {
				return null;
			}
		}
		
		//When coming in from the top right side
		else if(start.x > collisionObject.getX() + collisionObject.getWidth() &&
				start.y > collisionObject.getY() + collisionObject.getHeight()){
			//If line end is outside object on top or right side
			//object can't be hit
			if(end.x > collisionObject.getX() + collisionObject.getWidth() ||
				end.y > collisionObject.getY() + collisionObject.getHeight()){
				return null;
			}
			//Check 
			float startToCornerAngle = MathUtil.angle(start, 
					collisionObject.getX() + collisionObject.getWidth(), 
					collisionObject.getY() + collisionObject.getHeight());
			//If line angle and angle from start point to corner
			//is equal, then line hits the corner
			if(angle == startToCornerAngle ){
				return new Hit(collisionObject, new Vector2(
						collisionObject.getX() + collisionObject.getWidth(),
						collisionObject.getY() + collisionObject.getHeight()));
			} 
			//When line angle is smaller than the angle from
			//start point to corner then line must hit the top of the object
			else if(angle < startToCornerAngle){
				//The distance to the objects y position from start point
				float distanceToObject = start.y - collisionObject.getY() - collisionObject.getHeight();
				
				//Calculate the x-position of line when the 
				//y-position is equal to objects top border y-position
				float positionX = (float)((-1) * distanceToObject / 
						Math.tan(angle * Math.PI / 180)) + 
						start.x;
				if(positionX >= collisionObject.getX() &&
						positionX <= collisionObject.getX() + collisionObject.getWidth()){
					Gdx.app.log("CollisionUtil", "Collision at - X: " + positionX + 
							" Y: " + (collisionObject.getY() + collisionObject.getHeight()));
					return new Hit(collisionObject, 
							new Vector2(positionX, 
									collisionObject.getY() + collisionObject.getHeight()
							));
				} else {
					return null;
				}
			}
			//When line angle is bigger than the angle from
			//start point to corner then line must hit the right side of object
			else{
				//The distance to the objects x position from start point
				float distanceToObject = start.x - collisionObject.getX() - collisionObject.getWidth();
				
				//Calculate the y-position of line when the 
				//x-position is equal to objects right border x-position
				float positionY = (float)((-1) * distanceToObject * 
						Math.tan(angle * Math.PI / 180)) + 
						start.y;
				if(positionY >= collisionObject.getY() &&
						positionY <= collisionObject.getY() + collisionObject.getHeight()){
					Gdx.app.log("CollisionUtil", "Collision at - X: " + 
							(collisionObject.getX() + collisionObject.getWidth()) + 
							" Y: " + positionY);
					return new Hit(collisionObject, 
							new Vector2(collisionObject.getX() + collisionObject.getWidth(), 
									positionY));
				} else {
					return null;
				}
			}
		}
		//When line comes in from the right
		else if(start.y >= collisionObject.getY() &&
				start.y <= collisionObject.getY() + collisionObject.getHeight() &&
				start.x > collisionObject.getX() + collisionObject.getWidth()){
			//If line end is not located on the left 
			//of the right border, object can't be hit
			if(end.x > collisionObject.getX() + collisionObject.getWidth()){
				return null;
			}
			
			//The distance to the objects x position from start point
			float distanceToObject = start.x - collisionObject.getX() - collisionObject.getWidth();
			
			//Calculate the x-position of line when the 
			//y-position is equal to objects y-position
			float positionY = (float)((-1) * distanceToObject * 
					Math.tan(angle * Math.PI / 180)) + 
					start.y;
			System.out.println("Position y " + positionY);
			if(positionY >= collisionObject.getY() &&
					positionY <= collisionObject.getY() + collisionObject.getHeight()){
				Gdx.app.log("CollisionUtil", "Collision at - X: " + 
						(collisionObject.getX() + collisionObject.getWidth()) + 
						" Y: " + positionY);
				return new Hit(collisionObject, 
						new Vector2(collisionObject.getX() + collisionObject.getWidth(), 
								positionY));
			} else {
				return null;
			}
		}
		
		//When coming in from the bottom right side
		else if(start.x > collisionObject.getX() + collisionObject.getWidth() &&
				start.y < collisionObject.getY()){
			//If line end is outside object on bottom or right side
			//object can't be hit
			if(end.x > collisionObject.getX() + collisionObject.getWidth() ||
				end.y < collisionObject.getY()){
				return null;
			}
			//Check 
			float startToCornerAngle = MathUtil.angle(start, 
					collisionObject.getX() + collisionObject.getWidth(), 
					collisionObject.getY());
			//If line angle and angle from start point to corner
			//is equal, then line hits the corner
			if(angle == startToCornerAngle){
				return new Hit(collisionObject, new Vector2(
						collisionObject.getX() + collisionObject.getWidth(),
						collisionObject.getY()));
			} 
			//When line angle is bigger than the angle from
			//start point to corner then line must hit the bottom of the object
			else if(angle > startToCornerAngle){
				//The distance to the objects y position from start point
				float distanceToObject =  collisionObject.getY() - start.y;
				
				//Calculate the x-position of line when the 
				//y-position is equal to objects top border y-position
				float positionX = (float)(distanceToObject / 
						Math.tan(angle * Math.PI / 180)) + 
						start.x;
				if(positionX >= collisionObject.getX() &&
						positionX <= collisionObject.getX() + collisionObject.getWidth()){
					Gdx.app.log("CollisionUtil", "Collision at - X: " + positionX + 
							" Y: " + (collisionObject.getY()));
					return new Hit(collisionObject, 
							new Vector2(positionX, 
									collisionObject.getY()));
				} else {
					return null;
				}
			}
			//When line angle is smaller than the angle from
			//start point to corner then line must hit the right side of object
			else{
				//The distance to the objects x position from start point
				float distanceToObject = start.x - collisionObject.getX() - collisionObject.getWidth();
				
				//Calculate the y-position of line when the 
				//x-position is equal to objects right border x-position
				float positionY = (float)((-1) * distanceToObject * 
						Math.tan(angle * Math.PI / 180)) + 
						start.y;
				if(positionY >= collisionObject.getY() &&
						positionY <= collisionObject.getY() + collisionObject.getHeight()){
					Gdx.app.log("CollisionUtil", "Collision at - X: " + 
							(collisionObject.getX() + collisionObject.getWidth()) + 
							" Y: " + positionY);
					return new Hit(collisionObject, 
							new Vector2(collisionObject.getX() + collisionObject.getWidth(), 
									positionY));
				} else {
					return null;
				}
			}
		}
		
		//When line comes in from the bottom
		else if(start.x >= collisionObject.getX() &&
				start.x <= collisionObject.getX() + collisionObject.getWidth() &&
				start.y < collisionObject.getY()){
			//If line end is not located under the upper border, object can't be hit
			if(end.y < collisionObject.getY()){
				return null;
			}
			
			//Special case if aimed right up
			else if(angle == 90.0f){
				return new Hit(collisionObject, new Vector2(start.x, collisionObject.getY()));
			}
			//The distance to the object from the start of line
			float distanceToObject = collisionObject.getY() - start.y;
			//Calculate the x-position of line when the 
			//y-position is equal to objects top border y-position
			float positionX = (float)(distanceToObject / 
					Math.tan(angle * Math.PI / 180)) + 
					start.x;
			if(positionX >= collisionObject.getX() &&
					positionX <= collisionObject.getX() + collisionObject.getWidth()){
				Gdx.app.log("CollisionUtil", "Collision at - X: " + positionX + 
						" Y: " + (collisionObject.getY()));
				return new Hit(collisionObject, 
						new Vector2(positionX, 
								collisionObject.getY()
						));
			} else {
				return null;
			}
		}
		
		//When coming in from the bottom left side
		else if(start.x < collisionObject.getX() &&
				start.y < collisionObject.getY()){
			//If line end is outside object on bottom or right side
			//object can't be hit
			if(end.x < collisionObject.getX() ||
				end.y < collisionObject.getY()){
				return null;
			}
			//Check 
			float startToCornerAngle = MathUtil.angle(start, 
					collisionObject.getX(), 
					collisionObject.getY());
			//If line angle and angle from start point to corner
			//is equal, then line hits the corner
			if(angle == startToCornerAngle){
				return new Hit(collisionObject, new Vector2(
						collisionObject.getX(),
						collisionObject.getY()));
			} 
			//When line angle is smaller than the angle from
			//start point to corner then line must hit the bottom of the object
			else if(angle < startToCornerAngle){
				//The distance to the objects y position from start point
				float distanceToObject =  collisionObject.getY() - start.y;
				
				//Calculate the x-position of line when the 
				//y-position is equal to objects top border y-position
				float positionX = (float)(distanceToObject / 
						Math.tan(angle * Math.PI / 180)) + 
						start.x;
				System.out.println("Post x " + positionX);
				if(positionX >= collisionObject.getX() &&
						positionX <= collisionObject.getX() + collisionObject.getWidth()){
					Gdx.app.log("CollisionUtil", "Collision at - X: " + positionX + 
							" Y: " + (collisionObject.getY()));
					return new Hit(collisionObject, 
							new Vector2(positionX, 
									collisionObject.getY()));
				} else {
					return null;
				}
			}
			//When line angle is bigger than the angle from
			//start point to corner then line must hit the left side of object
			else{
				//The distance to the objects x position from start point
				float distanceToObject = collisionObject.getX() - start.x;
				System.out.println("Distance " + distanceToObject + 
						" " + start.y + " " + (float)(distanceToObject * 
								Math.tan(angle * Math.PI / 180)));
				//Calculate the y-position of line when the 
				//x-position is equal to objects right border x-position
				float positionY = (float)(distanceToObject * 
						Math.tan(angle * Math.PI / 180)) + 
						start.y;
				System.out.println("position y: " + positionY);
				if(positionY >= collisionObject.getY() &&
						positionY <= collisionObject.getY() + collisionObject.getHeight()){
					Gdx.app.log("CollisionUtil", "Collision at - X: " + 
							(collisionObject.getX()) + 
							" Y: " + positionY);
					return new Hit(collisionObject, 
							new Vector2(collisionObject.getX(), 
									positionY));
				} else {
					return null;
				}
			}
		}
		
		//When line comes in from the left
		else if(start.y >= collisionObject.getY() &&
				start.y <= collisionObject.getY() + collisionObject.getHeight() &&
				start.x < collisionObject.getX()){
			
			//If line end is not located on the right 
			//of the left border, object can't be hit
			if(end.x < collisionObject.getX()){
				return null;
			}
			//The distance to the objects x position from start point
			float distanceToObject = collisionObject.getX() - start.x;
			
			//Calculate the x-position of line when the 
			//y-position is equal to objects y-position
			float positionY = (float)(distanceToObject * 
					Math.tan(angle * Math.PI / 180)) + 
					start.y;
			if(positionY >= collisionObject.getY() &&
					positionY <= collisionObject.getY() + collisionObject.getHeight()){
				Gdx.app.log("CollisionUtil", "Collision at - X: " + 
						collisionObject.getX() + 
						" Y: " + positionY);
				return new Hit(collisionObject, 
						new Vector2(collisionObject.getX(), 
								positionY));
			} else {
				return null;
			}
		}
		
		//When coming in from the top left side
		else if(start.x < collisionObject.getX() &&
				start.y > collisionObject.getY() + collisionObject.getHeight()){
			//If line end is outside object on top or right side
			//object can't be hit
			if(end.x < collisionObject.getX() ||
				end.y > collisionObject.getY() + collisionObject.getHeight()){
				return null;
			}
			//Check 
			float startToCornerAngle = MathUtil.angle(start, 
					collisionObject.getX(), 
					collisionObject.getY() + collisionObject.getHeight());
			//If line angle and angle from start point to corner
			//is equal, then line hits the corner
			if(angle == startToCornerAngle ){
				return new Hit(collisionObject, new Vector2(
						collisionObject.getX(),
						collisionObject.getY() + collisionObject.getHeight()));
			} 
			//When line angle is bigger than the angle from
			//start point to corner then line must hit the top of the object
			else if(angle > startToCornerAngle){
				//The distance to the objects y position from start point
				float distanceToObject = start.y - collisionObject.getY() - collisionObject.getHeight();
				//Calculate the x-position of line when the 
				//y-position is equal to objects top border y-position
				float positionX = (float)((-1) * distanceToObject / 
						Math.tan(angle * Math.PI / 180)) + 
						start.x;
				System.out.println("Position X " + positionX);
				if(positionX >= collisionObject.getX() &&
						positionX <= collisionObject.getX() + collisionObject.getWidth()){
					Gdx.app.log("CollisionUtil", "Collision at - X: " + positionX + 
							" Y: " + (collisionObject.getY() + collisionObject.getHeight()) + "from");
					return new Hit(collisionObject, 
							new Vector2(positionX, 
									collisionObject.getY() + collisionObject.getHeight()
							));
				} else {
					return null;
				}
			}
			//When line angle is smaller than the angle from
			//start point to corner then line must hit the left side of object
			else{
				//The distance to the objects x position from start point
				float distanceToObject = collisionObject.getX() - start.x;
				
				//Calculate the y-position of line when the 
				//x-position is equal to objects right border x-position
				float positionY = (float)(distanceToObject * 
						Math.tan(angle * Math.PI / 180)) + 
						start.y;
				if(positionY >= collisionObject.getY() &&
						positionY <= collisionObject.getY() + collisionObject.getHeight()){
					Gdx.app.log("CollisionUtil", "Collision at - X: " + 
							(collisionObject.getX()) + 
							" Y: " + positionY);
					return new Hit(collisionObject, 
							new Vector2(collisionObject.getX(), 
									positionY));
				} else {
					return null;
				}
			}
		}
		
		return null;
	}
}
