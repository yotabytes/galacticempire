package spaceobject.ship;

import java.util.ArrayList;
import java.util.Collection;

import minerals.Mineral;

public class TradeShip extends Ship {

	public TradeShip(int x, int y) {
		super(x, y);
	}
	
	public Collection<String> getStats() {
		ArrayList<String> stats = new ArrayList<String>();
		stats.add("Information:");
		return stats;
	}

}
