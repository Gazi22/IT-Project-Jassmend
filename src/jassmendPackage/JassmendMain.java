package jassmendPackage;

<<<<<<< HEAD
import javafx.application.Application;
import javafx.stage.Stage;

=======
import jassmendPackage.JassmendController;
import jassmendPackage.JassmendModel;
import jassmendPackage.Jassmend_Game_View;
import javafx.application.Application;
import javafx.stage.Stage;

public class JassmendMain extends Application {
	
	Jassmend_Game_View View;

	public static void main(String[] args) {
		launch(args);
>>>>>>> Dave1

public class JassmendMain extends Application {
	// Add final to NUM_PLAYERS
	public static final int NUM_PLAYERS = 4;
	JassmendModel model;
	JassmendView view;
	JassmendController controller;
	
    public static void main(String[] args) {
        launch();
    }

<<<<<<< HEAD
    @Override
    public void start(Stage primaryStage) throws Exception {
    	// Create and initialize the MVC components
    	model = new JassmendModel();
    	view = new JassmendView(primaryStage, model);
    	controller = new JassmendController(model, view); 
    }
}
=======
	@Override
	public void start(Stage myPrimaryStage) throws Exception {
		View  = new Jassmend_Game_View(myPrimaryStage);
		Jassmend_Game_View.start();
		
	}
	
}
>>>>>>> Dave1
