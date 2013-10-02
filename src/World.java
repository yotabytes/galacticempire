import java.util.ArrayList;
import java.util.Collection;

import spaceobject.*;
import spaceobject.ship.Ship;

/**
 * Contains methods to manipulate and verify the objects in the world, also used to pass to the rendering in WorldView.
 * @author Kristof Bruyninckx
 * @author Wouter Bruyninckx
 */

public class World {
	
	// Variables:
	
	/**
	 * The height of this world.
	 */
	final int height;
	/**
	 * The width of this world.
	 */
	final int width;
	
	/**
	 * Holds all planets of this world.
	 */
	private Collection<Planet> planets;
	/**
	 * Holds all stars of this world.
	 */
	private Collection<Star> stars;
	/**
	 * Holds all ships of this world.
	 */
	private Collection<Ship> ships;
	
	// Getters and setters:
	
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Collection<Planet> getPlanets() {
		return planets;
	}

	public Collection<Star> getStars() {
		return stars;
	}
	
	public Collection<Ship> getShips() {
		return ships;
	}
	
	public Collection<CelestialBody> getCelestialBodies() {
		Collection<CelestialBody> allObjects = new ArrayList<CelestialBody>();
		allObjects.addAll(planets);
		allObjects.addAll(stars);
		return allObjects;
	}
	
	public Collection<SpaceObject> getSpaceObjects() {
		Collection<SpaceObject> allObjects = new ArrayList<SpaceObject>();
		allObjects.addAll(getCelestialBodies());
		allObjects.addAll(ships);
		return allObjects;
	}

	// constructors and other code:
	
	/**
	 * 
	 * @param 	width
	 * @param 	height
	 * @param	planets
	 * @param	stars
	 * @param 	ships
	 */
	public World(int width, int height, Collection<Planet> planets, Collection<Star> stars, Collection<Ship> ships){
		this.width = width;
		this.height = height;
		this.planets = planets;
		this.stars = stars;
		this.ships = ships;
	}

	
	
}
					