package spaceobject;

import java.util.ArrayList;
import java.util.Collection;

import buildings.Building;
import spaceobject.ship.Ship;
import minerals.Mineral;

public class Planet extends CelestialBody {
	
	// variables and constants
	
	private Collection<Mineral> minerals;
	private Collection<Ship> ships; // holds references to all ships currently on this planet.
	private Collection<Building> buildings; // holds references to all buildings currently on this planet.
	private boolean oxygen;
	private boolean water;
	
	public final static int HOT_PLANET_THRESHOLD = 1000;
	public final static int MIN_LIVABLE_TEMPERATURE = 253;
	public final static int MAX_LIVABLE_TEMPERATURE = 323;
	public final static int MAX_RADIUS = 60;
	public final static int MIN_RADIUS = 20;
	
	private boolean isLivable;

	// getters and setters:
	
	public Collection<Mineral> getMinerals() {
		return minerals;
	}

	public void setMinerals(Collection<Mineral> minerals) {
		this.minerals = minerals;
	}

	public void addMineral(Mineral source) {
		getMinerals().add(source);
	}
	
	public Collection<Ship> getShips(){
		return ships;
	}
	
	public void setShips(Collection<Ship> newShips){
		this.ships = newShips;
	}
	
	public void addShip(Ship source){
		getShips().add(source);
		source.setCurrentPlanet(this);// biderectional association
	}
	
	public void removeShip(Ship source){
		getShips().remove(source);
		source.setCurrentPlanet(null); // biderectional association
	}
	
	public Collection<Building> getBuildings(){
		return buildings;
	}
	
	public void setBuildings(Collection<Building> newBuildings){
		this.buildings = newBuildings;
	}
	
	public void addBuilding(Building source){
		getBuildings().add(source);
	}

	public boolean hasOxygen() {
		return oxygen;
	}
	
	public boolean hasWater(){
		return water;
	}
	
	public void toggleWater(){
		if (!this.hasWater())
			this.water = true;
		else
			this.water = false;
		checkIsLivable();
	}
	public void toggleOxygen(){
		if (!this.hasOxygen())
			this.oxygen = true;
		else
			this.oxygen = false;
		checkIsLivable();
	}
	
	public String getDescription() {
		return "A planet that may contain valuable resources to extract. Various dangers may reside on and below the surface.";
	}
	
	@Override
	public void setTemperature(double temperature){
		super.setTemperature(temperature);
		checkIsLivable();
	}
	
	private void checkIsLivable(){
		if(MIN_LIVABLE_TEMPERATURE < this.getTemperature()  && this.getTemperature() < MAX_LIVABLE_TEMPERATURE && this.hasWater() && this.hasOxygen()){
			this.isLivable = true;
		}
	}
	
	public boolean isLivable(){
		return isLivable;
	}
	
	// constructors and other code
	/**
	 * Initiates a planet.
	 * @param 	x
	 * @param 	y
	 * @param 	radius
	 * @post 	Radius is always a value between MIN_RADIUS and MAX_RADIUS.
	 */
	public Planet(int x, int y, int radius) {
		super(x, y, radius);
		if (radius > MAX_RADIUS)
			this.setRadius(MAX_RADIUS);
		else if (radius < MIN_RADIUS)
			this.setRadius(MIN_RADIUS);
		else
			this.setRadius(radius);
		this.setMinerals(new ArrayList<Mineral>());
		this.setShips(new ArrayList<Ship>()); 
	}
	
	public Collection<String> getStats() {
		ArrayList<String> stats = new ArrayList<String>();
		stats.add("Minerals:");
		for (Mineral min : getMinerals()) {
			stats.add(min.toString());
		}
		stats.add("Information:");
		stats.add("Temp: " + super.getTemperature() + "�C");
		stats.add("Oxygen: " + this.hasOxygen());
		stats.add("Water: " + this.hasWater());
		stats.add("Inhabitable: " + this.isLivable());
		return stats;
	}
}
