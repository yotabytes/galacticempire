package minerals;

import spaceobject.Planet;

/*
 * A mineral as it is used to generate the world. Every mineral has a unique name, a rarity factor, and spawning constraint checkers
 */
public enum Mineral {
	
	// List of minerals with constraints, minerals that get added here will automatically be used in map generation.
	// The rarity factor can be any positive integer, higher integer means higher frequency.
	COAL("coal",150) {
		@Override
		public boolean HasSatisfiedConstraints(Planet planet) {
			return true; // Coal can spawn anywhere.
		}
	}, IRON("Iron",100) {
		@Override
		public boolean HasSatisfiedConstraints(Planet planet) {
			return true; // iron can spawn anywhere.
		}
	}, TIN("Tin", 150) {
		@Override
		public boolean HasSatisfiedConstraints(Planet planet) {
			return true;
		}
	}, COPPER("Copper", 150) {
		@Override
		public boolean HasSatisfiedConstraints(Planet planet) {
			return true;
		}
	}
	, QUARTS("Quartz", 100) {
		@Override
		public boolean HasSatisfiedConstraints(Planet planet) {
			return true;
		}
	}
	, ADAMANT("Adamant", 25) {
		@Override
		public boolean HasSatisfiedConstraints(Planet planet) {
			return !(planet.getTemperature() > 773); // Adamant can only spawn on planets with a temperature larger than 500.
		}
	}, GOLD("Gold", 25) {
		@Override
		public boolean HasSatisfiedConstraints(Planet planet) {
			return true; // gold has no constraints
		}
	}, SILVER("Silver", 50) {
		@Override
		public boolean HasSatisfiedConstraints(Planet planet) {
			return true; // silver has no constraints.
		}
	}, DIAMOND("Diamond", 15) {
		@Override
		public boolean HasSatisfiedConstraints(Planet planet) {
			return planet.getRadius() > Planet.MAX_RADIUS - (Planet.MAX_RADIUS - Planet.MIN_RADIUS) / 4; // pressure needed for forming diamond
		}
	}, FROSTIUM("Frostium", 15) {
		@Override
		public boolean HasSatisfiedConstraints(Planet planet) {
			return planet.getTemperature() < 100;
		}
	}, KYANITE("Kyanite", 15) {
		@Override
		public boolean HasSatisfiedConstraints(Planet planet) {
			return !planet.hasWater(); // only available on dry planets
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
