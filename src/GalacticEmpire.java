import java.awt.Toolkit;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class GalacticEmpire extends StateBasedGame {

	public GalacticEmpire(String title) {
		super(title);
		
	}
	
	
	
	public static void main(String Args[]) throws SlickException {
		AppGameContainer app = new AppGameContainer(new GalacticEmpire("Galactic Empire"));
		app.setDisplayMode((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.75), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.75), false);
		app.start();
	}



	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new MainMenu());
		this.addState(new WorldView());
	}


}
