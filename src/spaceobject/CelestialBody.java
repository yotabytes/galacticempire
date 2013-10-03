package spaceobject;

/**
 * Celesital bodies are Round space objects with a temperature such as stars and planets.
 *
 */

public abstract class CelestialBody extends SpaceObject {
	
	// variabeles and constants:
	
	public static final double KELVIN_CONSTANT = 273.15;
	public final static int COLD_PLANET_TRESHOLD = 100;
	public final static int TEMPERATURE_MULTIPLIER = 837818;
	private double temperature;
	
	// getters and setters:
	
	public void setTemperature(double temperature){
		this.temperature = temperature;
	}

	public double getTemperature() {
		return this.temperature;
	}
	
	// constructors and code:
	
	public CelestialBody(int x, int y, int radius) {
		super(x, y, radius);
	}
}
