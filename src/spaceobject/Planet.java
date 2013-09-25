package spaceobject;

import java.util.ArrayList;

import minerals.Mineral;

public class Planet extends SpaceObject {
	
	private int radius; //radius of the Planet
	private ArrayList<Mineral> Minerals;
	private boolean oxygen;
	final static int MAX_RADIUS = 100;
	final static int MIN_RADIUS = 10;

	public Planet(int x, int y, int radius) {
		super(x, y);
		if (radius > MAX_RADIUS)
			this.radius = MAX_RADIUS;
		else if (radius < MIN_RADIUS)
			this.radius = MIN_RADIUS;
		else
			this.radius = radius;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public void addMineral(Mineral source) {
		(this.Minerals).add(source);
	}

	public boolean hasOxygen() {
		return oxygen;
	}
	
	public String getDescription() {
		return "A planet that may contain valuable resources to extract. Various dangers may reside on and below the surface.";
	}
	
}
