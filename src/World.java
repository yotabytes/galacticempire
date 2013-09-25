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

	// constructors and other code:
	
	/**
	 * 
	 * @param 	width
	 * @param 	height
	 */
	public World(int width, int height){
		this.width = width;
		this.height = height;
	}
	
}
					