package minerals;

import spaceobject.Planet;

public class Adamant extends Mineral {

	public Adamant() {
		super("Adamant", 0.3);
	
	}

	@Override
	public boolean HasSatisfiedConstraints(Planet planet) {
		if (!planet.hasOxygen())
			return true;
		else
			return false;
	}

}
