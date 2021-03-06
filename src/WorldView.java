import java.text.DecimalFormat;
import java.util.Collection;

import minerals.Mineral;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.lessvoid.nifty.*;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick2d.NiftyOverlayBasicGameState;
import spaceobject.CelestialBody;
import spaceobject.Planet;
import spaceobject.SpaceObject;
import spaceobject.Star;
import spaceobject.ship.Ship;
import spaceobject.ship.ShipState;


public class WorldView extends NiftyOverlayBasicGameState implements ScreenController {
	private static final double OFFSET_FACTOR = 0.3;
	private static final int X_AXIS_INDEXATION = 10;
	private static final int PLANET_FLAG_RADIUS = 10;
	private int offsetX = 0;
	private int offsetY = 0;
	private World world; //The world that contains all object data and methods
	private WorldGenerator generator;
	private Image background;
	private int greatestScreenSize;
	private float backgroundScale;
	private StateBasedGame sbg;
	private SpaceObject selectedObject;
	private Screen screen;
	private Nifty nifty;
	private TextField selectionStats;
	@Override
	/**
	 * Instantiates the world generator which returns a world with random elements and the background of the world. 
	 */
	public void initGameAndGUI(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.generator = new WorldGenerator(5000,5000,0.01,0.005,2);
		this.world = generator.getWorld();
		this.background = ImageFactory.getBackground();
		this.sbg = sbg;
		
		
		if(world.getHeight() > world.getWidth()){
			this.greatestScreenSize = world.getHeight();
		}else{
			this.greatestScreenSize = world.getWidth();
		} 
		this.backgroundScale = (float)greatestScreenSize / (float)background.getHeight();

		this.initNifty(gc, sbg);
		
		this.screen = this.nifty.getCurrentScreen();
		//selectionStats = screen.findNiftyControl("Selection_Info", TextField.class);
		
	}
	protected void prepareNifty(Nifty nifty, StateBasedGame sbg) {
		nifty.loadControlFile("nifty-default-controls.xml"); //How the buttons look and stuff

		nifty.addXml("xml/MainHUD.xml"); //Load the interface data from the xml file, using this class as the screencontroller
		nifty.gotoScreen("hud");
		nifty.registerScreenController(this);
		this.nifty = nifty;
		

	}

	@Override
	public void renderGame(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.translate(offsetX, offsetY);
		background.draw(0,0,backgroundScale);
		drawPlanets();
		drawStars();
		drawShips();
		drawShipPlanetFlags(g);
		checkMouseOver(gc.getInput(), g);	
		if(selectedObject != null){
			g.drawOval(selectedObject.getX() - selectedObject.getRadius(), selectedObject.getY() - selectedObject.getRadius(), selectedObject.getRadius()*2, selectedObject.getRadius()*2);
		}
		g.resetTransform(); //Make sure that the user interface stays on the screen and does not translate with the world while scrolling around.
		//SlickCallable.enterSafeBlock();
		//nifty.render(false);
		//SlickCallable.leaveSafeBlock();

	}

	@Override
	public void updateGame(GameContainer gc, StateBasedGame sbg, int arg2)throws SlickException {
		if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			handleMouseClick(gc);
		}
		for(Ship shp : world.getShips()){
			shp.operate(arg2);
		}
		// key input actions:
		trackCameraMovement(gc,sbg, arg2);
		
		
	}


	private void handleMouseClick(GameContainer gc) {
		if(selectedObject instanceof Ship){
			Planet selectedBody = null;
			for(Planet plt : world.getPlanets()){
				if(mouseOnSpaceObject(plt,gc.getInput().getMouseX(),gc.getInput().getMouseY())){
					selectedBody = plt;
					break;
				}
			}
			if(selectedBody != null && ((Ship) selectedObject).getCurrentPlanet() != selectedBody){
				((Ship)selectedObject).setDestinationPlanet(selectedBody);
				double dX = selectedBody.getX() - selectedObject.getX();
				double dY = selectedBody.getY() - selectedObject.getY();
				double angle = Math.atan(dY/dX);
				if(dX < 0){
					angle += Math.PI;
				}
				((Ship)selectedObject).setAngle(angle);
				((Ship)selectedObject).setState(ShipState.TRAVELING);
				return;
			}
		}
		else {
			for (Ship shp: world.getShips()){
				if (shp.getCurrentPlanet() != null){
					double distance = Math.sqrt(Math.pow(gc.getInput().getMouseX() - offsetX - getPlanetFlagPos(shp)[0],2) + Math.pow(gc.getInput().getMouseY() - offsetY- getPlanetFlagPos(shp)[1], 2));
					if (distance <= PLANET_FLAG_RADIUS){
						selectedObject = shp;
						return;
					}
				}
			}
		}
		for(SpaceObject obj : world.getSpaceObjects()){
			if(mouseOnSpaceObject(obj,gc.getInput().getMouseX(),gc.getInput().getMouseY())){
				if(obj instanceof Ship && ((Ship) obj).getState() != ShipState.TRAVELING){
					continue;
				}
				else
					selectedObject = obj;
				return;//if a selectedobject is found return else if non is found set to null
			}
		}
		selectedObject = null;
		return;
	}

	//__________________________ DRAW FUNCTIONS _____________________________
	/**
	 * General function for drawing the planet in the world on the screen.
	 */
	private void drawPlanets(){
		for(Planet plt : world.getPlanets()){
			float scale = ((float)2*plt.getRadius() / (float)plt.getImage().getHeight());
			plt.getImage().draw(plt.getX() - plt.getRadius(), plt.getY() - plt.getRadius(), scale);
		}
	}
	/**
	 * General function for drawing the stars in the world on the screen.
	 */
	private void drawStars(){
		for(Star str : world.getStars()){
			float scale = ((float)2*str.getRadius() / (float)str.getImage().getHeight());
			str.getImage().draw(str.getX() - str.getRadius(), str.getY() - str.getRadius(), scale);
		}
	}
	/**
	 * General function for drawing the stars in the world on the screen.
	 */
	private void drawShips() {
		for(Ship shp : world.getShips()){
			if (shp.getState() == ShipState.TRAVELING || selectedObject == shp){ // only draw traveling or selected ships
				float scale = ((float)2*shp.getRadius() / (float)shp.getImage().getHeight());
				shp.getImage().setRotation((float) Math.toDegrees(shp.getAngle() + Math.PI/2));
				shp.getImage().draw(shp.getX() - shp.getRadius(), shp.getY() - shp.getRadius(),scale);
			}
		}
	}
	
	private void drawShipPlanetFlags(Graphics g){
		for (Ship shp : world.getShips()){
			int[] coordinates;
			int shipCount;
			if (shp.getCurrentPlanet() != null){
				coordinates = getPlanetFlagPos(shp);
				g.setColor(Color.green);
				g.fillOval(coordinates[0] - PLANET_FLAG_RADIUS, coordinates[1] - PLANET_FLAG_RADIUS, PLANET_FLAG_RADIUS*2, PLANET_FLAG_RADIUS*2);
				g.setColor(Color.white);
				shipCount = shp.getCurrentPlanet().getShips().size();
				g.drawString("" + shipCount, coordinates[0] - PLANET_FLAG_RADIUS, coordinates[1] - PLANET_FLAG_RADIUS);
			}
		}
	}
	
	/**<pre> ship.getCurrentPlanet() != null;
	 */
	private int[] getPlanetFlagPos(Ship shp){
		int[] coordinates = new int[2];
		coordinates[0] = (int) (shp.getCurrentPlanet().getX() + Math.cos(3*Math.PI/4) * shp.getCurrentPlanet().getRadius());
		coordinates[1] = (int) (shp.getCurrentPlanet().getY() - Math.sin(3*Math.PI/4) * shp.getCurrentPlanet().getRadius());
		return coordinates;
		
	}

	/**
	 * Checks whether the mouse is hovering over a spaceobject in the world and draws the corresponding
	 * information about the spaceobject.
	 * @param input
	 * @param g
	 */
	private void checkMouseOver(Input input, Graphics g) {
		int mX = input.getMouseX();
		int mY = input.getMouseY();
		for(CelestialBody cby : world.getCelestialBodies()){
			if(mouseOnSpaceObject(cby,mX,mY)){
				g.drawOval(cby.getX() - ((SpaceObject)cby).getRadius(), cby.getY() - ((SpaceObject)cby).getRadius(), 2*((SpaceObject)cby).getRadius(), 2*((SpaceObject)cby).getRadius());
				drawCelestialBodyStats((SpaceObject)cby, g);
				return;
			}
		}

	}

	/**
	 * Function which draws the stats of a spaceobject e.g. it's temperature, whether oxygen is 
	 * present and the present minerals on the planet.
	 * @param obj
	 * @param g
	 */
	private void drawCelestialBodyStats(SpaceObject obj, Graphics g){
		DecimalFormat fm = new DecimalFormat("#.##");
		if(obj instanceof Planet){
			float cX = obj.getX() + obj.getRadius();
			float cY = obj.getY() - obj.getRadius();
			int yIndent = g.getFont().getLineHeight();
			String hasOxygen;
			String hasWater;
			String isLivable;
			if(((Planet) obj).hasOxygen()){
				hasOxygen = "yes";
			}else{
				hasOxygen = "no";
			}
			if(((Planet) obj).hasWater()){
				hasWater = "yes";
			}else{
				hasWater = "no";
			}
			if(((Planet) obj).isLivable()){
				isLivable = "yes";
			}else{
				isLivable = "no";
			}
			//Draw temperature
			String temp = fm.format(((Planet) obj).getTemperature() - CelestialBody.KELVIN_CONSTANT) + "�C";
			g.drawString(temp,cX,cY);
			//Draw oxygen yes/no
			g.drawString("Oxygen: " + hasOxygen, cX, cY + yIndent);
			yIndent += g.getFont().getLineHeight();
			//Draw water yes/no
			g.drawString("Water: " + hasWater, cX, cY + yIndent);
			yIndent += g.getFont().getLineHeight();
			//Draw isLivable yes/no
			g.setColor(Color.green);
			g.drawString("Livable: " + isLivable, cX, cY + yIndent);
			yIndent += g.getFont().getLineHeight();
			//Draw Minerals
			g.setColor(Color.yellow);
			g.drawString("Minerals:", cX, cY + yIndent);
			yIndent += g.getFont().getLineHeight();
			cX += X_AXIS_INDEXATION;	// X axis indentation on screen
			for(Mineral mrl : ((Planet) obj).getMinerals()){
				g.drawString(mrl.getName(), cX, cY + yIndent);
				yIndent += g.getFont().getLineHeight();
			}
		}else{
			String temp = fm.format(((Star) obj).getTemperature() - CelestialBody.KELVIN_CONSTANT) + "�C";
			g.drawString(temp,obj.getX() + obj.getRadius() , obj.getY() - obj.getRadius());
		}
	}
	


	//__________________ HELP FUNCTIONS __________________


	/**
	 * Tracks the camera movement. If the "Up-arrow-key" is pressed the world moves down, if the
	 * "Down-arrow-key" is pressed the world moves up, etc.
	 * @param gc
	 * @param sbg
	 * @param arg2
	 */
	private void trackCameraMovement(GameContainer gc, StateBasedGame sbg, int arg2){
		if(gc.getInput().isKeyDown(Input.KEY_RIGHT)){
			this.offsetX += -GalacticEmpire.DEFAULT_FPS * OFFSET_FACTOR; 
		}
		if(gc.getInput().isKeyDown(Input.KEY_LEFT)){
			this.offsetX += GalacticEmpire.DEFAULT_FPS * OFFSET_FACTOR; 
		}
		if(gc.getInput().isKeyDown(Input.KEY_UP)){
			this.offsetY += GalacticEmpire.DEFAULT_FPS * OFFSET_FACTOR; 
		}
		if(gc.getInput().isKeyDown(Input.KEY_DOWN)){
			this.offsetY += -GalacticEmpire.DEFAULT_FPS * OFFSET_FACTOR; 
		}
		if(offsetX < -(this.world.getWidth() - gc.getWidth())){
			offsetX = -(this.world.getWidth() - gc.getWidth());
		}
		if(offsetY < -(this.world.getHeight() - gc.getHeight())){
			offsetY = -(this.world.getHeight() - gc.getHeight());
		}
		if(offsetX > 0){
			offsetX = 0;
		}
		if(offsetY > 0){
			offsetY = 0;
		}
	}




	/**
	 * Function which checks if the mouse is hovering over a given spaceobject.
	 * @param obj
	 * @param mouseX
	 * @param mouseY
	 * @return true if the distance between the object and the mouse is smaller than its radius.
	 */
	private boolean mouseOnSpaceObject(SpaceObject obj, int mouseX, int mouseY){
		if(obj.getDistanceBetween(mouseX - offsetX, mouseY - offsetY) < ((SpaceObject) obj).getRadius()){
			return true;
		}else{
			return false;
		}

	}

	/*____________________________________________ Nifty GUI controller __________________________________________________________ */

	@Override
	public int getID() {
		return 1;
	}
	
	/**
	 * Returns the information values in plain text representation of the currently selected entity.
	 * @return
	 */
	public String getSelectionStats() {
		return this.selectedObject.getStats().toString();
	}
	
	
	
	/*
	@Override
	public void bind(Nifty nifty, Screen screen) {

	}

	@Override
	public void onEndScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartScreen() {
		// TODO Auto-generated method stub

	}
	*/
	
	/*
	 * Enter the planet that is in the currently active selection. This method will change the state to the on-planet view where the player can build stuff
	 * and check on all planet activity.
	 */
	public void EnterSelectedPlanet() {
		//sbg.enterState(0); TODO: Fix this bug
		System.out.println("Succesful button click!");
	}
	@Override
	protected void enterState(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void leaveState(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void bind(Nifty nifty, Screen screen) {

		
	}
	@Override
	public void onEndScreen() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStartScreen() {
		// TODO Auto-generated method stub
		
	}
}

/**
 * Currently not in use. Was used to try and get another screencontroller to handle input, instead of WorldView.
 * @author Wouter
 *
 */
class ScreenControllerExample implements ScreenController {
	public void bind(Nifty nifty, Screen screen) {}
	public void onEndScreen() {}
	public void onStartScreen() {}

	public void EnterSelectedPlanet() {
		System.out.println("hi!!");
	}

	@NiftyEventSubscriber(id="SelGrpBt")
	public void onSelGrpBtClicked(final String id, final ButtonClickedEvent event) {
		System.out.println("Clicou");
	}
}





