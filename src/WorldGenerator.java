import java.util.ArrayList;
import java.util.Collection;

import spaceobject.Planet;
import spaceobject.Star;
import spaceobject.ship.ExplorerShip;
import spaceobject.ship.ExtractionShip;
import spaceobject.ship.Ship;

import java.util.Random;

import minerals.Mineral;

import org.newdawn.slick.SlickException;


/**
 * Contains everything related to generating a world.
 * @author Kristof Bruyninckx
 * @author Wouter Bruyninckx
 * @author Pieter Verlinden
 */

public class WorldGenerator {
	
	// constants:
	
	public final static int MIN_STAR_STAR_DISTANCE = 400;
	public final static int MIN_PLANET_PLANET_DISTANCE = 150;
	public final static int MIN_STAR_PLANET_DISTANCE = 200;
	public final static int WORLD_OFFSET = 50;
	
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

	public void setPlanets(Collection<Planet> planets) {
		this.planets = planets;
	}

	public void setStars(Collection<Star> stars) {
		this.stars = stars;
	}

	public void setShips(Collection<Ship> ships) {
		this.ships = ships;
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
	
	
	// Constructors and other code:
	
	/**
	 * Constructs a world with a given size 
	 * @param 	height
	 * 			The height of this planet.
	 * @param 	width
	 * 			The width of this planet.
	 * @param 	planetDensity
	 * 			The number of planets per square with a size of maximum planet radius x maximum planet radius.
	 * @param 	starDensity
	 * 			The number of stars per square with a size of maximum planet radius x maximum planet radius. 
	 * @param 	mineralDensity
	 * 			The number of minerals per planet available in this world. capped between 1 and 5.
	 * @throws SlickException 
	 */
	public WorldGenerator(int width, int height, double planetDensity, double starDensity, double mineralDensity) throws SlickException{
		this.height = height;
		this.width = width;
		generateWorld(planetDensity,starDensity,mineralDensity);
		setImage();
	}
	
	private void generateWorld(double planetDensity, double starDensity, double mineralDensity){
		generateStars(starDensity);
		generatePlanets(planetDensity);
		addMinerals(mineralDensity);
		addShips();
		/* Debug information.
		for(Planet plt : this.getPlanets()){
			System.out.println(plt.getTemperature());
		}
		*/
	}
	
	private void addShips() {
		Collection<Ship> newShips = new ArrayList<Ship>();
		newShips.add(new ExplorerShip(this.getWidth()/2 , this.getHeight()/2));
		this.setShips(newShips);
		for (Ship shp : newShips){
			ArrayList<Planet> temp = (ArrayList<Planet>) getWorld().getPlanets();
			temp.get(0).addShip(shp); // temporary until player can pick his starting planet.
			shp.setX(temp.get(0).getX()+2);
			shp.setY(temp.get(0).getY()+2);
		}
	}


	private void generateStars(double starDensity){
		int starCount = (int) (((starDensity * getWidth() * getHeight()) / (Star.MAX_RADIUS * Star.MAX_RADIUS)) + 1); // number of stars (minimum 1!).
		Collection<Star> newStars = new ArrayList<Star>();
		
		Random randomGenerator = new Random();
		for (int i=0; i<starCount; i++){
			int rX, rY, rR;
			Star newStar;
			do { // find suitable location
				rX = randomGenerator.nextInt(getWidth() - 2 * Star.MAX_RADIUS - 2 * WORLD_OFFSET) + Star.MAX_RADIUS + WORLD_OFFSET;
				rY = randomGenerator.nextInt(getHeight() - 2 * Star.MAX_RADIUS - 2 * WORLD_OFFSET) + Star.MAX_RADIUS + WORLD_OFFSET;
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
			do { // find suitable location
				rX = randomGenerator.nextInt(getWidth() - 2 * Planet.MAX_RADIUS - 2 * WORLD_OFFSET ) + Planet.MAX_RADIUS + WORLD_OFFSET;
				rY = randomGenerator.nextInt(getHeight() - 2 * Planet.MAX_RADIUS - 2 * WORLD_OFFSET) + Planet.MAX_RADIUS + WORLD_OFFSET;
				rR = randomGenerator.nextInt(Planet.MAX_RADIUS - Planet.MIN_RADIUS) + Planet.MIN_RADIUS;
				newPlanet = new Planet(rX,rY,rR);
			} while (!isSpawnablePlanet(newPlanets, newPlanet));
			newPlanets.add(newPlanet);
			double temp = 0;
			// add temperatures to new planet
			for(Star str : this.getStars()){
				temp += (str.getRadius()/(Math.pow((str.getDistanceBetween(newPlanet.getX(), newPlanet.getY()) - str.getRadius() - newPlanet.getRadius()),2))) * Planet.TEMPERATURE_MULTIPLIER;
			}
			newPlanet.setTemperature(temp);
			// 60% chance on oxygen and not on planets which exceeds the hot planet threshold.
			if (randomGenerator.nextInt(10) < 6 && newPlanet.getTemperature() < Planet.HOT_PLANET_THRESHOLD){
				newPlanet.toggleOxygen();
			}
			if (randomGenerator.nextInt(10) < 8 && newPlanet.getTemperature() < Planet.HOT_PLANET_THRESHOLD)
				newPlanet.toggleWater(); // 80 percent chance on water on planet
			
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
		int mineralCount = (int) (planets.size() * mineralDensity);
		
		// compute total rarity
		
		int totalRarity = 0;
		
		for (Mineral mineral: Mineral.values()){
			totalRarity += mineral.getRarity();
		}
		
		// construct mineral pool
		
		Collection<Mineral> mineralPool = new ArrayList<Mineral>();
		
		for (Mineral mineral: Mineral.values()){
			int timesInPool = (int) (mineralCount*mineral.getRarity()/totalRarity);
			if (timesInPool == 0)
				timesInPool = 1; // make sure every mineral is added at least once into the pool.
			for (int i=0; i< timesInPool; i++)
				mineralPool.add(mineral);
		}
		
		// divide mineral pool over planets
		
		for (Mineral mineral : mineralPool){
			ArrayList<Planet> possiblePlanets = new ArrayList<Planet>();
			for (Planet planet : getPlanets()){
				// Planets must satisfy the constraints and must not already contain the mineral.
				if (mineral.HasSatisfiedConstraints(planet) && !(planet.getMinerals().contains(mineral))) 
					possiblePlanets.add(planet);
			}
			
			// if there is no suitable planet remove the mineral from the pool and start next iteration.
			
			if (possiblePlanets.isEmpty()){
				continue;
			}
			
			// pick random planet and add current mineral to that planet.
			
			Random randomGenerator = new Random();
			int randomIndex = randomGenerator.nextInt(possiblePlanets.size());
			possiblePlanets.get(randomIndex).addMineral(mineral);
		}
	}
	
	/*
	 * Stars can only spawn if there is a certain distance between the closest other star.
	 */
	private boolean isSpawnableStar(Collection<Star> stars, Star newStar){
		boolean isSpawnable = true;
		for (Star str: stars){
			if (str.getDistanceBetween(newStar.getX(), newStar.getY()) - str.getRadius() - newStar.getRadius() < MIN_STAR_STAR_DISTANCE)
				return false;
		}
		return isSpawnable;
	}

	private void setImage() throws SlickException{
		for(Star str : this.getStars()){
			str.setImage(ImageFactory.getImage(str));
		}
		for(Planet plt : this.getPlanets()){
			plt.setImage(ImageFactory.getImage(plt));
		}
		for(Ship shp : this.getShips()){
			shp.setImage(ImageFactory.getImage(shp));
		}
	}
	public World getWorld() {
		return new World(this.width, this.height,this.planets, this.stars, this.ships);
	}
	
	

}
