package buildings;

import spaceobject.Planet;

public abstract class Building {
	
	// variabeles and constants:
	
	final Planet planet;

	// getters and setters:
	
	public Planet getPlanet() {
		return planet;
	}
	
	// constructors and other code:
	
	public Building(Planet planet){
		this.planet = planet;
	}
}
