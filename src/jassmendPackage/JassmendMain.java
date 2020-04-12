package jassmendPackage;

import jassmendPackage.JassmendController;
import jassmendPackage.JassmendModel;
import jassmendPackage.Jassmend_Game_View;
import javafx.application.Application;
import javafx.stage.Stage;

public class JassmendMain extends Application {
	
	Jassmend_Game_View View;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage myPrimaryStage) throws Exception {
		View  = new Jassmend_Game_View(myPrimaryStage);
		Jassmend_Game_View.start();
		
	}
	
}
