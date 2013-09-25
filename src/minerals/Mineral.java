package minerals;

import spaceobject.Planet;

/*
 * A mineral as it is used to generate the world. Every mineral has a unique name, a rarity, and spawning constraint checkers
 */
public abstract class Mineral {
	protected String name;
	protected double rarity;

	public Mineral(String name, double rarity) {
		this.name = name;
		this.rarity = rarity;
	}
	
	public abstract boolean HasSatisfiedConstraints(Planet planet);

}
