package spaceobject;

/**
 * A star is an object in the galaxy that emits light and warmth. The amount that propagates through space and afflicts nearby entities is linear to the size of the star.
 * @author Wouter
 *
 */
public class Star extends SpaceObject {

	private int radius;
	public final static int MAX_RADIUS = 100;
	public final static int MIN_RADIUS = 10;
	private final double temperature;
	public Star(int x, int y, int radius) {
		super(x, y, radius);
		if (radius > MAX_RADIUS)
			this.radius = MAX_RADIUS;
		else if (radius < MIN_RADIUS)
			this.radius = MIN_RADIUS;
		else
			this.radius = radius;
		this.temperature = Math.pow(this.radius,3);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public double getTemperature(){
		return this.temperature;
	}
	
	
	public String getDescription() {
		return "A star that emits light and warmth";
	}
}
