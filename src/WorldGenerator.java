import java.util.ArrayList;
import java.util.Collection;

import spaceobject.Planet;
import spaceobject.SpaceObject;
import spaceobject.Star;

import java.util.Random;


/**
 * Contains everything related to generating a world.
 * @author Kristof Bruyninckx
 * @author Wouter Bruyninckx
 */

public class WorldGenerator {
	
	// constants:
	
	public final static int MIN_STAR_STAR_DISTANCE = 400;
	public final static int MIN_PLANET_PLANET_DISTANCE = 150;
	public final static int MIN_STAR_PLANET_DISTANCE = 200;
	
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

	public void setPlanets(Collection<Planet> planets) {
		this.planets = planets;
	}

	public void setStars(Collection<Star> stars) {
		this.stars = stars;
	}

	public Collection<Planet> getPlanets() {
		return planets;
	}

	public Collection<Star> getStars() {
		return stars;
	}
	
	// Constructors and other code:
	
	/**
	 * Constructs a world with a given size 
	 * @param 	height
	 * 			The height of this planet.
	 * @param 	width
	 * 			The width of this planet.
	 * @param 	planetDensity
	 * 			The number of planets per square with a size of maximum planet radius x maximum planet radius.
	 * 			Capped between 0.1 and 0.5.
	 * @param 	starDensity
	 * 			The number of stars per square with a size of maximum planet radius x maximum planet radius. Capped between 0.05 and 0.1.
	 * @param 	mineralDensity
	 * 			The number of minerals per planet available in this world. capped between 1 and 5.
	 */
	public WorldGenerator(int width, int height, double planetDensity, double starDensity, double mineralDensity){
		this.height = height;
		this.width = width;
		generateWorld(planetDensity,starDensity,mineralDensity);
	}
	
	private void generateWorld(double planetDensity, double starDensity, double mineralDensity){
		generateStars(starDensity);
		generatePlanets(planetDensity);
		addMinerals(mineralDensity);
		for(Planet plt : this.getPlanets()){
			System.out.println(plt.getTemperature());
		}
	}
	
	private void generateStars(double starDensity){
		int starCount = (int) (((starDensity * getWidth() * getHeight()) / (Star.MAX_RADIUS * Star.MAX_RADIUS)) + 1); // number of stars (minimum 1!).
		Collection<Star> newStars = new ArrayList<Star>();
		
		Random randomGenerator = new Random();
		for (int i=0; i<starCount; i++){
			int rX, rY, rR;
			Star newStar;
			do {
				rX = randomGenerator.nextInt(getWidth() - 2 * Star.MAX_RADIUS) + Star.MAX_RADIUS;
				rY = randomGenerator.nextInt(getHeight() - 2 * Star.MAX_RADIUS) + Star.MAX_RADIUS;
				rR = randomGenerator.nextInt(Star.MAX_RADIUS - Star.MIN_RADIUS) + Star.MIN_RADIUS;
				newStar = new Star(rX,rY,rR);
			} while (!isSpawnableStar(newStars, newStar));
			newStars.add(newStar);
		}
		
		this.setStars(newStars);
	}
	
	private void generatePlanets(double planetDensity){
		int planetCount = (int)(((planetDensity * getWidth() * getHeight()) / (Planet.MAX_RADIUS * Planet.MAX_RADIUS)) + 1);
		Collection<Planet> newPlanets = new ArrayList<Planet>();
		
		Random randomGenerator = new Random();
		for(int i = 0; i < planetCount; i++){
			int rX, rY, rR;
			Planet newPlanet;
			do {
				rX = randomGenerator.nextInt(getWidth() - 2 * Planet.MAX_RADIUS) + Planet.MAX_RADIUS;
				rY = randomGenerator.nextInt(getHeight() - 2 * Planet.MAX_RADIUS) + Planet.MAX_RADIUS;
				rR = randomGenerator.nextInt(Planet.MAX_RADIUS - Planet.MIN_RADIUS) + Planet.MIN_RADIUS;
				newPlanet = new Planet(rX,rY,rR);
			} while (!isSpawnablePlanet(newPlanets, newPlanet));
			newPlanets.add(newPlanet);
			double temp = 0;
			for(Star str : this.getStars()){
				temp += (str.getRadius()/(Math.pow((str.getDistanceBetween(newPlanet.getX(), newPlanet.getY()) - str.getRadius() - newPlanet.getRadius()),2))) * Planet.TEMPERATURE_MULTIPLIER;
			}
			newPlanet.setTemperature(temp);
		}
		
		this.setPlanets(newPlanets);
	}
	
	private boolean isSpawnablePlanet(Collection<Planet> newPlanets, Planet newPlanet) {
		boolean isSpawnable = true;
		for(Planet plt : newPlanets){
			if(plt.getDistanceBetween(newPlanet.getX(), newPlanet.getY()) - plt.getRadius() - newPlanet.getRadius() < MIN_PLANET_PLANET_DISTANCE){
				return false;
			}
			for(Star str : this.getStars()){
				if(newPlanet.getDistanceBetween(str.getX(), str.getY()) - str.getRadius() - newPlanet.getRadius() < MIN_STAR_PLANET_DISTANCE){
					return false;
				}
			}
		}
		return isSpawnable;
		
	}

	private void addMinerals(double mineralDensity){
		
	}
	
	
	/*
	 * Space objects can only if there is a certain distance between the closest other spaceobject.
	 */
	private boolean isSpawnableStar(Collection<Star> stars, Star newStar){
		boolean isSpawnable = true;
		for (Star str: stars){
			if (str.getDistanceBetween(newStar.getX(), newStar.getY()) - str.getRadius() - newStar.getRadius() < MIN_STAR_STAR_DISTANCE)
				return false;
		}
		return isSpawnable;
	}

	public World getWorld() {
		return new World(this.width, this.height,this.planets, this.stars);
	}


}
