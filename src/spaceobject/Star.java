package spaceobject;

import java.util.ArrayList;
import java.util.Collection;

import minerals.Mineral;

/**
 * A star is an object in the galaxy that emits light and warmth. The amount that propagates through space and afflicts nearby entities is linear to the size of the star.
 * @author Wouter
 *
 */
public class Star extends CelestialBody {

	public final static int MAX_RADIUS = 100;
	public final static int MIN_RADIUS = 20;
	public Star(int x, int y, int radius) {
		super(x, y, radius);
		if (radius > MAX_RADIUS)
			this.radius = MAX_RADIUS;
		else if (radius < MIN_RADIUS)
			this.radius = MIN_RADIUS;
		else
			this.radius = radius;
		this.setTemperature(Math.pow(this.radius,3));
	}

	public String getDescription() {
		return "A star that emits light and warmth";
	}
	
	public Collection<String> getStats() {
		ArrayList<String> stats = new ArrayList<String>();
		stats.add("Information:");
		stats.add("Temp: " + super.getTemperature() + "°C");
		return stats;
	}
}
