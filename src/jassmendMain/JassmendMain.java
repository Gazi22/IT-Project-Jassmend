package jassmendMain;


import jassmendController.JassmendController;
import jassmendModel.JassmendModel;
import jassmendView.JassmendGameView;
import javafx.application.Application;
import javafx.stage.Stage;

public class JassmendMain extends Application {
	
	final public static int NUM_PLAYERS = 2;
	JassmendModel model;
	JassmendGameView view;
	JassmendController controller;

	public static void main(String[] args) {
		launch(args);
	}

	

	@Override
	public void start(Stage myPrimaryStage) throws Exception {
		model = new JassmendModel();
		view  = new JassmendGameView(myPrimaryStage, model);
		controller = new JassmendController(model,view);
		
		JassmendGameView.start();
	}
	}
	



