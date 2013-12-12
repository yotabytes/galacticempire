package spaceobject.ship;

import java.util.ArrayList;
import java.util.Collection;

public class ExtractionShip extends Ship {

	public ExtractionShip(int x, int y) {
		super(x, y);
	}
	
	public String getType() {
		return "Extraction";
	}
	public Collection<String> getStats() {
		ArrayList<String> stats = new ArrayList<String>();
		stats.add("Type: " + this.getType());
		stats.add("Information:");
		return stats;
	}

}
