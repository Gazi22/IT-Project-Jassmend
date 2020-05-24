package Jassmend;



import javafx.application.Application;
import javafx.stage.Stage;

	//Author: Florian J�ger
public class JassmendMain extends Application {
    JassmendViewManager clientView;
    JassmendLoginView jassmendLoginView;
    JassmendAccountView jassmendAccountView;
    JassmendGameView view;
    JassmendMainMenuView viewMenu;
    JassmendLogView jassmendLogView;
    final public static int NUM_PLAYERS = 4;

    public static void main(String[] args) {
    	 	
    		launch();
    		
    }
    // Author: Florian J�ger
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jassmend");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);
        primaryStage.setResizable(false);
        JasmendModel jasmendModel = new JasmendModel();

        JasmendController jasmendController = new JasmendController(jasmendModel);
        view = new JassmendGameView(jasmendController, jasmendModel, viewMenu);
        jasmendController.addGameView(view);

        jassmendLogView = new JassmendLogView(jasmendController);
        jasmendController.addChatView(jassmendLogView);

        jassmendLoginView = new JassmendLoginView(jasmendController, view, jassmendLogView);
        jasmendController.addLoginView(jassmendLoginView);

        jassmendAccountView = new JassmendAccountView(jasmendController);

        viewMenu = new JassmendMainMenuView(jasmendController, view, jassmendLoginView);
        jasmendController.addMainMenuView(viewMenu);





      

        clientView = new JassmendViewManager(primaryStage, view, viewMenu, jassmendLoginView, jassmendAccountView, jasmendController, jassmendLogView);
       
        jasmendController.setViewManager(clientView);
        primaryStage.show();
    }
}
