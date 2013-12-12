package spaceobject.ship;

import java.util.ArrayList;
import java.util.Collection;

public class BattleShip extends Ship{

	public BattleShip(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	public Collection<String> getStats() {
		ArrayList<String> stats = new ArrayList<String>();
		stats.add("Information:");
		return stats;
	}
}
