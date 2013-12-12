import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/*
 * The main HUD in the WorldView is controlled by this ScreenController. Methods that result from interaction with the HUD should
 * be implemented here, and interface with the WorldView public methods.
 */
class ScreenController_MainHUD implements ScreenController {

	
	public ScreenController_MainHUD() {
		
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
	
	public void EnterSelectedPlanet() {
		System.out.println("hi");
	}
	
}


