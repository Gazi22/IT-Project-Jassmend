package jassmendPackage;

import javafx.application.Application;
import javafx.stage.Stage;


public class JassmendMain extends Application {
	// Add final to NUM_PLAYERS
	public static final int NUM_PLAYERS = 4;
	JassmendModel model;
	JassmendView view;
	JassmendController controller;
	
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	// Create and initialize the MVC components
    	model = new JassmendModel();
    	view = new JassmendView(primaryStage, model);
    	controller = new JassmendController(model, view); 
    }
}