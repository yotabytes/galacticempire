import java.util.ArrayList;
import java.util.Collection;

import spaceobject.*;

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
	Collection<Planet> planets;
	/**
	 * Holds all stars of this world.
	 */
	Collection<Star> stars;
	
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
	
	public Collection<SpaceObject> getSpaceObjects() {
		Collection<SpaceObject> allObjects = new ArrayList<SpaceObject>();
		allObjects.addAll(planets);
		allObjects.addAll(stars);
		return allObjects;
	}

	// constructors and other code:
	
	/**
	 * 
	 * @param 	width
	 * @param 	height
	 * @param	planets
	 * @param	stars
	 */
	public World(int width, int height, Collection<Planet> planets, Collection<Star> stars){
		this.width = width;
		this.height = height;
		this.planets = planets;
		this.stars = stars;
	}
	
}
					