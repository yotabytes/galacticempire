package spaceobject;

import java.util.ArrayList;

import minerals.Mineral;

public class Planet extends SpaceObject {
	
	private int radius; //radius of the Planet
	private ArrayList<Mineral> Minerals;
	private boolean oxygen;

	public Planet(int x, int y, int radius) {
		super(x, y);
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
	
}
