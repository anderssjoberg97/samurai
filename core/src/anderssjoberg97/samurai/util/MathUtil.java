/**
 * 
 */
package anderssjoberg97.samurai.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * @author anders
 *
 */
public class MathUtil {
	
	/**
	 * Calculates the angle between two vectors
	 * @param vector1 Origin vector
	 * @param vector2 Far point vector
	 * @return The angle between the vectors
	 */
	public static float angle(Vector2 vector1, Vector2 vector2){
		float differenceX = vector2.x - vector1.x;
		float differenceY = vector2.y - vector1.y;
		return angle(differenceX, differenceY);
	}
	
	/**
	 * Calculates the angle between two vectors
	 * @param vector1 Origin vector
	 * @param vector2X Far point vector x-coordinate
	 * @param vector2Y Far point vector y-coordinate
	 * @return The angle between the vectors
	 */
	public static float angle(Vector2 vector1, float vector2X, float vector2Y){
		float differenceX = vector2X - vector1.x; 
		float differenceY = vector2Y - vector1.y;
		return angle(differenceX, differenceY);
	}
	
	/**
	 * Helper method for angle that calculates the angle from difference.
	 * @param differenceX Horizontal difference between points
	 * @param differenceY Vertical difference between points
	 * @return The angle between 0 inclusive and 360 exclusive
	 */
	public static float angle(float differenceX, float differenceY){
		if(differenceX == 0.0f){
			if(differenceY >= 0.0f){
				return 90f;
			} else {
				return - 90f;
			}
		} else if(differenceX >= 0 && differenceY >= 0 ) {
			
			return (float) Math.atan(differenceY / differenceX) * 
					180 / (float)Math.PI;
		} else if(differenceX < 0 && differenceY >= 0 ){
			return 180 + (float) Math.atan(differenceY / differenceX) * 
					180 / (float) Math.PI;
		} else if(differenceX < 0 && differenceY < 0) {
			return 180 + (float) Math.atan(differenceY / differenceX) * 
					180 / (float) Math.PI;
		} else {
			return (360 + (float) Math.atan(differenceY / differenceX) * 
					180 / (float) Math.PI);
		}
	}
}
