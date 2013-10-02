package spaceobject.ship;

public enum ShipState {
	
	TRAVELING("Traveling"),ON_PLANET("On planet"),FUELING("Fueling"),REPAIRING("Repairing"),EXTRACTING("Extracting");
	
	private ShipState(String name){
		this.name = name;
	}
	String name;
	
	public String getState(){
		return name;
	}
}
