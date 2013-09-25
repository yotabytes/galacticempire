import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import spaceobject.Star;


public class WorldView extends BasicGameState {
	
	private World world; //The world this view is connected to
	private WorldGenerator generator;
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.generator = new WorldGenerator(1920,1080,0.2,0.05,3);
		this.world = generator.getWorld();
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.white);
		for(Star str : world.getStars()){
			g.drawOval(str.getX(), str.getY(), str.getRadius(), str.getRadius());
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2)throws SlickException {
		
	}

	@Override
	public int getID() {
		return 1;
	}

}
