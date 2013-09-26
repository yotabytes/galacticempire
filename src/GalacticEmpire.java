import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class GalacticEmpire extends StateBasedGame {

	public GalacticEmpire(String title) {
		super(title);
		
	}
	
	
	
	public static void main(String Args[]) throws SlickException {
		AppGameContainer app = new AppGameContainer(new GalacticEmpire("Galactic Empire"));
		app.setDisplayMode(1920, 1080, true);
		app.start();
	}



	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new MainMenu());
		this.addState(new WorldView());
	}


}
