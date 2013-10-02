package spaceobject.ship;

import java.util.HashMap;

import minerals.Mineral;
import spaceobject.SpaceObject;

public abstract class Ship extends SpaceObject{


	// variabeles and constants:
	private ShipState state;
	private float destinationX;
	private float destinationY;
	private int fuel;
	private double storage;
	private double speed;
	private double angle;
	private HashMap<Mineral,Double> cargo;

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

	public float getDestinationX() {
		return destinationX;
	}

	public void setDestinationX(float destinationX) {
		this.destinationX = destinationX;
	}

	public float getDestinationY() {
		return destinationY;
	}

	public void setDestinationY(float destinationY) {
		this.destinationY = destinationY;
	}


	public Ship(int x, int y) {
		super(x, y);
	}

	public void operate(double delta){
		if(this.state == ShipState.TRAVELING){
			double newPositionX = getX() + (delta * speed * Math.cos(getAngle()));
			double newPositionY = getY() + (delta * speed * Math.sin(getAngle()));
			setPosition((float)newPositionX,(float)newPositionY); 
		}
		if(fuzzyEquals(getX(), getDestinationX()) && fuzzyEquals(getY(), getDestinationY())){
			this.state = ShipState.ON_PLANET;
			this.setDestinationX(0);
			this.setDestinationY(0);
		}
	}

	private void setPosition(float x, float y){
		this.setX(x);
		this.setY(y);
	}
	
	public static final double EPSILON = 5;

	public static boolean fuzzyEquals(double x, double y) {
	    if (Double.isNaN(x) || Double.isNaN(y))
	      return false;
	    return Math.abs(x - y) <= EPSILON || Double.valueOf(x).equals(Double.valueOf(y));
	  }
}
