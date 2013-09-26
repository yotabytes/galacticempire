package minerals;

import spaceobject.Planet;

/*
 * A mineral as it is used to generate the world. Every mineral has a unique name, a rarity, and spawning constraint checkers
 */
public enum Mineral {
	
	// List of minerals with constraints, minerals that get added here will automatically be used in map generation.
	// total of rarities must be equal to 1.
	COAL("coal",0.3) {
		@Override
		public boolean HasSatisfiedConstraints(Planet planet) {
			return true; // Coal can spawn anywhere.
		}
	}, IRON("Iron",0.3) {
		@Override
		public boolean HasSatisfiedConstraints(Planet planet) {
			return true; // iron can spawn anywhere.
		}
	}, ADAMANT("adamant",0.2) {
		@Override
		public boolean HasSatisfiedConstraints(Planet planet) {
			return !(planet.getTemperature() > 500); // Adamant can only spawn on planets with a temperature larger than 500.
		}
	}, DIAMOND("diamond", 0.2) {
		@Override
		public boolean HasSatisfiedConstraints(Planet planet) {
			return planet.getTemperature() > 500;
		}
	};
	
	// variabeles and constants:
	
	protected final String name;
	protected final double rarity;

	// getters and setter:
	
	public String getName() {
		return name;
	}

	public double getRarity() {
		return rarity;
	}

	// constructors and other code:
	
	private Mineral(String name, double rarity) {
		this.name = name;
		this.rarity = rarity;
	}
	
	public abstract boolean HasSatisfiedConstraints(Planet planet);
}
