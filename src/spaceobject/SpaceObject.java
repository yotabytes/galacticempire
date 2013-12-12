package spaceobject;


import java.util.Collection;

import org.newdawn.slick.Image;


public abstract class SpaceObject {
	protected float x, y;
	protected Image image;
	
	public SpaceObject(float x, float y, int radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Return the distance between this coordinate and an other. 
	 * given x and y coordinate.
	 * 
	 * @param 	x
	 * 			A given x coordinate.
	 * @param	y
	 * 			A given y coordinate.
	 * @return	The distance between this coordinate and the other coordinate.
	 * 			| result ==
	 * 			| Math.sqrt(Math.pow(this.getX() - x ,2)
	 * 			| 		 +	Math.pow(this.getY() - y ,2))	
	 */
	public double getDistanceBetween(float x, float y){
		double distance = 0.0;
		distance = Math.sqrt(Math.pow(this.getX() - x,2) + Math.pow(this.getY() - y, 2));
		return distance;
	}
	
	public Image getImage(){
		return this.image;
	}
	
	public void setImage(Image image){
		this.image = image;
	}
	
	public int getRadius() {
		return radius;
	}

	protected int radius;

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public String getDescription;
	
	//Return the information about this object as text.
	public abstract Collection<String> getStats();
	
}
