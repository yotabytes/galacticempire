package minerals;

import spaceobject.Planet;

public class Coal extends Mineral {

	public Coal() {
		super("Coal", 0.8);
		
	}

	@Override
	public boolean HasSatisfiedConstraints(Planet planet) {
		return true; // Coal can spawn anywhere
	}

}
