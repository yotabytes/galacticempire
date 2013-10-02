package buildings;

import spaceobject.Planet;

public class Residence extends Building {
	
	// variabeles and constant:
	
	int inhabitants;

	final static int MAX_INHABITANTS = 500;
	
	// getters, setters and checkers:
	
	public int getInhabitants() {
		return inhabitants;
	}

	/**
	 * Sets the number of inhabitants.
	 * @param 	inhabitants
	 * 			New number of inhabitants
	 * @post	The new number of inhabitants is never higher than the maximum inhabitants and always zero or higher.
	 */
	public void setInhabitants(int inhabitants) {
		if (isValidInhabitants(inhabitants))
			this.inhabitants = inhabitants;
		else if (inhabitants > MAX_INHABITANTS)
			this.inhabitants = inhabitants;
		else 
			this.inhabitants = 0;
	}

	/**
	 * Adds (or removes) a number of inhabitants to the original inhabitants.
	 * @param 	extraInhabitants
	 * 			Number of additional inhabitants.
	 * @post	The number of inhabitants is never higher than the maximum inhabitants and always zero or higher.
	 */
	public void addInhabitants(int extraInhabitants){
		if (isValidInhabitants(this.inhabitants + extraInhabitants))
			this.inhabitants += extraInhabitants;
		else 
			this.inhabitants = MAX_INHABITANTS;
	} 
	
	public static boolean isValidInhabitants(int inhabitants){
		if (inhabitants > MAX_INHABITANTS || inhabitants < 0)
			return false;
		return true;
	}
	
	// constructors and other code:

	public Residence(Planet planet) {
		super(planet);
	}

}
