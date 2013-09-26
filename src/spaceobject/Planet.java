package spaceobject;

import java.util.ArrayList;
import java.util.Collection;

import org.newdawn.slick.Image;

import minerals.Mineral;

public class Planet extends SpaceObject {
	
	private int radius; //radius of the Planet
	private Collection<Mineral> Minerals;
	private boolean oxygen;

	private boolean isLivable;
	public final static int COLD_PLANET_TRESHOLD = 100;
	public final static int HOT_PLANET_TRESHOLD = 1000;
	public final static int MIN_LIVABLE_TEMPERATURE = 253;
	public final static int MAX_LIVABLE_TEMPERATURE = 323;
	public final static int MAX_RADIUS = 60;
	public final static int MIN_RADIUS = 20;
	public final static int TEMPERATURE_MULTIPLIER = 837818;
	private double temperature;

	// getters and setters:
	
	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public Collection<Mineral> getMinerals() {
		return Minerals;
	}

	public void setMinerals(Collection<Mineral> minerals) {
		Minerals = minerals;
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
	
	public void setTemperature(double temperature){
		this.temperature = temperature;
		checkIsLivable();
	}

	public double getTemperature() {
		return this.temperature;
	}
	
	private void checkIsLivable(){
		if(MIN_LIVABLE_TEMPERATURE < this.temperature  && this.temperature < MAX_LIVABLE_TEMPERATURE){
			this.isLivable = true;
		}
	}
	
	public boolean isLivable(){
		return isLivable;
	}
	
	// constructors and other code
	
	public Planet(int x, int y, int radius) {
		super(x, y, radius);
		if (radius > MAX_RADIUS)
			this.radius = MAX_RADIUS;
		else if (radius < MIN_RADIUS)
			this.radius = MIN_RADIUS;
		else
			this.radius = radius;
		this.setMinerals(new ArrayList<Mineral>());
	}
	
}
