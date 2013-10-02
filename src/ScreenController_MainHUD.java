import org.newdawn.slick.state.BasicGameState;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

class ScreenController_MainHUD implements ScreenController {

	private Nifty nifty;
	private Screen screen;
	private BasicGameState worldview;
	public ScreenController_MainHUD() {
		
	}
	
	@Override
	public void bind(Nifty nifty, Screen screen) {
		nifty.registerScreenController(this);
		nifty.addControls();
		this.nifty = nifty;
		this.screen = screen;
	}

	@Override
	public void onEndScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartScreen() {
		// TODO Auto-generated method stub
		
	}
	
	public void EnterSelectedPlanet() {
		System.err.println("hi");
	}
	
}


