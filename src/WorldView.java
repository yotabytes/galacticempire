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
	private int offsetX = 0;
	private int offsetY = 0;
	private World world; //The world this view is connected to
	private WorldGenerator generator;
	private Image background;
	private Image star;
	private Image planet;
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.generator = new WorldGenerator(2000,2000,0.01,0.005,3);
		this.world = generator.getWorld();
		this.background = new Image("img/spacebackground.png");
		this.star = new Image("img/planet-2.png");
		this.planet = new Image("img/planet-1.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.translate(offsetX, offsetY);
		int size;
		if(world.getHeight() > world.getWidth()){
			size = world.getHeight();
		}else{
			size = world.getWidth();
		}
		float bgScale = (float)size / (float)background.getHeight();
		background.draw(0,0,bgScale);
		for(Star str : world.getStars()){
			float scale = ((float)2*str.getRadius() / (float)star.getHeight());
			star.draw(str.getX(), str.getY(), scale);
		}
		for(Planet plt : world.getPlanets()){
			float scale = ((float)2*plt.getRadius() / (float)planet.getHeight());
			planet.draw(plt.getX(), plt.getY(), scale);
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2)throws SlickException {
		// key input actions:
		trackCameraMovement(gc,sbg, arg2);
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

	@Override
	public int getID() {
		return 1;
	}

}
