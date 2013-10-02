package spaceobject.ship;

import java.util.HashMap;

import minerals.Mineral;
import spaceobject.Planet;
import spaceobject.SpaceObject;

public abstract class Ship extends SpaceObject{


	// variabeles and constants:
	public static final int DEFAULT_SHIP_RADIUS = 15;
	protected ShipState state;
	protected Planet destinationPlanet;
	protected int fuel;
	protected double storage;
	protected double speed;
	protected double angle;
	protected HashMap<Mineral,Double> cargo;

	// getters and setters:

	public int getFuel() {
		return fuel;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public double getStorage() {
		return storage;
	}

	public void setStorage(double storage) {
		this.storage = storage;
	}

	public HashMap<Mineral, Double> getCargo() {
		return cargo;
	}

	public void setCargo(HashMap<Mineral, Double> cargo) {
		this.cargo = cargo;
	}

	public double getSpeed(){
		return this.speed;
	}

	public void setSpeed(double speed){
		this.speed = speed;
	}

	public double getAngle(){
		return this.angle;
	}

	public void setAngle(double angle){
		if (angle < 2*Math.PI && angle > (-2)*Math.PI)
			this.angle = angle;
		else
			this.angle = angle % (2*Math.PI);
	}
	
	
	// Constructors and code:

	

	/**
	 * @return the state
	 */
	public ShipState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(ShipState state) {
		this.state = state;
	}

	/**
	 * @return the destinationPlanet
	 */
	public Planet getDestinationPlanet() {
		return destinationPlanet;
	}

	/**
	 * @param destinationPlanet the destinationPlanet to set
	 */
	public void setDestinationPlanet(Planet destinationPlanet) {
		this.destinationPlanet = destinationPlanet;
	}

	public Ship(int x, int y) {
		super(x, y, DEFAULT_SHIP_RADIUS);
		this.speed = 0.2;
	}

	public void operate(double delta){
		if(this.state == ShipState.TRAVELING){
			double newPositionX = getX() + (delta * speed * Math.cos(getAngle()));
			double newPositionY = getY() + (delta * speed * Math.sin(getAngle()));
			setPosition((float)newPositionX,(float)newPositionY); 
		}
		if(this.destinationPlanet != null && this.overlap(destinationPlanet)){
			this.state = ShipState.ON_PLANET;
		}
	}

	private void setPosition(float x, float y){
		this.setX(x);
		this.setY(y);
	}
	
	public static final double EPSILON = 5;

	public boolean overlap(SpaceObject other){
			if (other == this)
				return true;
			if (this.getDistanceBetween(other.getX(), other.getY()) < other.getRadius())
				return true;
			return false;
	}
}
