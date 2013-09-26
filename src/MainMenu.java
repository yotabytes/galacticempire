import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MainMenu extends BasicGameState {

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		 	g.setColor(Color.white);
		    g.drawString("Galactic Empire", 150, 10);
		 
		    g.drawString("1. New Game", 150, 100);
		    g.drawString("2. Load Game", 150, 120);
		    g.drawString("3. Quit", 150, 140);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {
		if (gc.getInput().isKeyPressed(Input.KEY_1))
			sbg.enterState(1);
		if (gc.getInput().isKeyPressed(Input.KEY_3))
			gc.exit();
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
