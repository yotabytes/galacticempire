import java.text.DecimalFormat;

import minerals.Mineral;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import spaceobject.Planet;
import spaceobject.SpaceObject;
import spaceobject.Star;


public class WorldView extends BasicGameState {
	private static final int DEFAULT_OFFSET = 5;
	private static final int X_AXIS_INDEXATION = 10;
	private int offsetX = 0;
	private int offsetY = 0;
	private World world; //The world this view is connected to
	private WorldGenerator generator;
	private Image background;
	private int greatestScreenSize;
	private float backgroundScale;
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.generator = new WorldGenerator(4000,4000,0.01,0.005,2);
		this.world = generator.getWorld();
		this.background = new Image("img/spacebackground.png");
		if(world.getHeight() > world.getWidth()){
			this.greatestScreenSize = world.getHeight();
		}else{
			this.greatestScreenSize = world.getWidth();
		} 
		this.backgroundScale = (float)greatestScreenSize / (float)background.getHeight();		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.translate(offsetX, offsetY);
		background.draw(0,0,backgroundScale);
		drawPlanets();
		drawStars();
		checkMouseOver(gc.getInput(), g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2)throws SlickException {
		// key input actions:
		trackCameraMovement(gc,sbg, arg2);
	}

	private void checkMouseOver(Input input, Graphics g) {
		int mX = input.getMouseX();
		int mY = input.getMouseY();
		for(SpaceObject obj : world.getSpaceObjects()){
			if(mouseOnSpaceObject(obj,mX,mY)){
				g.drawOval(obj.getX() - obj.getRadius(), obj.getY() - obj.getRadius(), 2*obj.getRadius(), 2*obj.getRadius());
				drawSpaceObjectStats(obj, g);
			}
		}

	}

	private void trackCameraMovement(GameContainer gc, StateBasedGame sbg, int arg2){
		if(gc.getInput().isKeyDown(Input.KEY_RIGHT)){
			this.offsetX += -DEFAULT_OFFSET; 
		}
		if(gc.getInput().isKeyDown(Input.KEY_LEFT)){
			this.offsetX += DEFAULT_OFFSET; 
		}
		if(gc.getInput().isKeyDown(Input.KEY_UP)){
			this.offsetY += DEFAULT_OFFSET; 
		}
		if(gc.getInput().isKeyDown(Input.KEY_DOWN)){
			this.offsetY += -DEFAULT_OFFSET; 
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

	private void drawPlanets(){
		for(Planet plt : world.getPlanets()){
			float scale = ((float)2*plt.getRadius() / (float)plt.getImage().getHeight());
			plt.getImage().draw(plt.getX() - plt.getRadius(), plt.getY() - plt.getRadius(), scale);
		}
	}
	private void drawStars(){
		for(Star str : world.getStars()){
			float scale = ((float)2*str.getRadius() / (float)str.getImage().getHeight());
			str.getImage().draw(str.getX() - str.getRadius(), str.getY() - str.getRadius(), scale);
		}
	}

	private boolean mouseOnSpaceObject(SpaceObject obj, int mouseX, int mouseY){
		if(obj.getDistanceBetween(mouseX - offsetX, mouseY - offsetY) < obj.getRadius()){
			return true;
		}else{
			return false;
		}
	}

	private void drawSpaceObjectStats(SpaceObject obj, Graphics g){
		DecimalFormat fm = new DecimalFormat("#.##");
		if(obj instanceof Planet){
			int cX = obj.getX() + obj.getRadius();
			int cY = obj.getY() - obj.getRadius();
			int yIndent = g.getFont().getLineHeight();
			String hasOxygen;
			if(((Planet) obj).hasOxygen()){
				hasOxygen = "yes";
			}else{
				hasOxygen = "no";
			}
			//Draw temperature
			String temp = fm.format(((Planet) obj).getTemperature() - SpaceObject.KELVIN_CONSTANT) + "°C";
			g.drawString(temp,cX,cY);
			//Draw oxygen
			g.drawString("Oxygen: " + hasOxygen, cX, cY + yIndent);
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
			String temp = fm.format(((Star) obj).getTemperature() - SpaceObject.KELVIN_CONSTANT) + "°C";
			g.drawString(temp,obj.getX() + obj.getRadius() , obj.getY() - obj.getRadius());
		}

	}
	@Override
	public int getID() {
		return 1;
	}

}
