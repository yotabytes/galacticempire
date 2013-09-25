package minerals;

import spaceobject.Planet;

public class Iron extends Mineral {
	public Iron() {
		super("iron", 0.7);
	}

	@Override
	public boolean HasSatisfiedConstraints(Planet planet) {
		return true; // Iron can spawn anywhere
	}

}
