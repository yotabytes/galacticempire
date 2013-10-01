package spaceobject.ship;

import java.util.HashMap;

import minerals.Mineral;

import spaceobject.SpaceObject;

public abstract class Ship extends SpaceObject{

	
	// variabeles and constants:
	
	private int fuel;
	private int storage;
	private HashMap<Mineral,Double> cargo;
	
	// getters and setters:
	
	public int getFuel() {
		return fuel;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public int getStorage() {
		return storage;
	}

	public void setStorage(int storage) {
		this.storage = storage;
	}

	public HashMap<Mineral, Double> getCargo() {
		return cargo;
	}

	public void setCargo(HashMap<Mineral, Double> cargo) {
		this.cargo = cargo;
	}
	
	// Constructors and code:
	
	public Ship(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	
}
