import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import spaceobject.*;
import spaceobject.ship.*;


public class ImageFactory {
	//Celestial Bodies
	private static final String LIVABLE_PLANET_1 = "img/planet-1.png";
	private static final String YELLOW_STAR = "img/planet-2.png";
	private static final String DRY_PLANET = "img/planet-3.png";
	private static final String COLD_PLANET = "img/planet-4.png";
	private static final String NOT_LIVABLE_PLANET_1 = "img/planet-5.png";
	private static final String LIVABLE_PLANET_2 = "img/planet-6.png";
	private static final String	HOT_PLANET = "img/planet-7.png";
	private static final String ICE_PLANET = "img/planet-8.png";
	private static final String LIVABLE_PLANET_3 = "img/planet-12.png";
	private static final String NOT_LIVABLE_PLANET_2 = "img/planet-13.png";
	private static final String RARE_PLANET = "img/planet15.png";
	private static final String BACKGROUND = "img/spacebackground.png";
	
	//Ships
	private static final String EXPLORER_SHIP = "img/Explorer.png";
	private static final String EXTRACTION_SHIP	= "img/Extrationer.png";
	private static final String BATTLE_SHIP	= "img/Fighter.png";
	private static final String DEFAULT_SHIP = "img/Aircraft Sprites/MiG-51.PNG";
	
	public static Image getBackground() throws SlickException{
		return new Image(BACKGROUND);
	}
	
	public static Image getImage(Object object) throws SlickException{
		if(object instanceof Planet)
			return getPlanetImage((Planet) object);
		if(object instanceof Star)
			return getStarImage((Star)object);
		if(object instanceof Ship)
			return getShipImage((Ship) object);
		else
			return null;
	}

	private static Image getShipImage(Ship object) throws SlickException {
		if(object instanceof ExplorerShip){
			return new Image(EXPLORER_SHIP);
		}else if(object instanceof ExtractionShip){
			return new Image(EXTRACTION_SHIP);
		}else if(object instanceof BattleShip){
			return new Image(BATTLE_SHIP);
		}else{
			return new Image(DEFAULT_SHIP);
		}
	}

	private static Image getStarImage(Star object) throws SlickException {
		return new Image(YELLOW_STAR);
	}

	private static Image getPlanetImage(Planet object) throws SlickException {
		Random rnd = new Random();
		if(object.isLivable()){
			int newCase = rnd.nextInt(3);
			switch(newCase){
			case 0: return new Image(LIVABLE_PLANET_1);
			case 1: return new Image(LIVABLE_PLANET_2);
			case 2: return new Image(LIVABLE_PLANET_3);
			}
		}else if(object.getTemperature() < Planet.COLD_PLANET_TRESHOLD){
			int newCase = rnd.nextInt(2);
			switch(newCase){
			case 0: return new Image(COLD_PLANET);
			case 1: return new Image(ICE_PLANET);
			}
		}else if(object.getTemperature() > Planet.HOT_PLANET_TRESHOLD){
			int newCase = rnd.nextInt(2);
			switch(newCase){
			case 0: return new Image(DRY_PLANET);
			case 1: return new Image(HOT_PLANET);
			}
		}else{
			int newCase = rnd.nextInt(2);
			switch(newCase){
			case 0: return new Image(NOT_LIVABLE_PLANET_1);
			case 1: return new Image(NOT_LIVABLE_PLANET_2);
			}
		}
		return null;
	}
}
