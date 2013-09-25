package spaceobject;

public abstract class SpaceObject {
	
	private int x, y;
	private int radius;
	
	public SpaceObject(int x, int y, int radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
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
	public double getDistanceBetween(double x, double y){
		double distance = 0.0;
		distance = Math.sqrt(Math.pow(this.getX() - x,2) + Math.pow(this.getY() - y, 2));
		return distance;
	}
	
	
	public String getDescription;
	
}