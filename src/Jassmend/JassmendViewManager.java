package Jassmend;

import java.io.IOException;
import java.util.Arrays;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

//Author: Florian J�ger
public class JassmendViewManager {
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Stage primaryStage;
    private JassmendAccountView jassmendAccountView;
    private JassmendGameView view;
    private JassmendLoginView jassmendLoginView;
    private JassmendMainMenuView menuView;
    private JasmendModel jasmendModel;
    private JasmendController jasmendController;
    private JassmendLogView jassmendLogView;

    public JassmendViewManager(Stage primaryStage, JassmendGameView view, JassmendMainMenuView menuView, JassmendLoginView jassmendLoginView, JassmendAccountView jassmendAccountView, JasmendController jasmendController, JassmendLogView jassmendLogView) {
 // Author: Florian J�ger
        
    	this.primaryStage = primaryStage;
        this.jassmendAccountView = jassmendAccountView;
        this.view = view;
        this.jassmendLoginView = jassmendLoginView;
        this.menuView = menuView;
        this.jasmendController = jasmendController;
        this.jassmendLogView = jassmendLogView;
        
     // https://noblecodemonkeys.com/properly-exiting-a-javafx-application/
        //primaryStage.setOnCloseRequest(e->{
        //closeWindow();
        //});
        primaryStage.setScene(JassmendLoginView.getScene());
    }
    
    
 // Author: Florian J�ger
    public void setScene(Scene scene) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                primaryStage.setScene(scene);
            }
               });
        
    }
// Author: Gazmend Shefiu & Davide Seabra & Florian J�ger
 // https://noblecodemonkeys.com/properly-exiting-a-javafx-application/
    public void closeWindow () {
    	if (jasmendController.getSocket()!= null) {
    	try {

          	Platform.exit();

       		jasmendController.getGamelobbyList();
          	 PauseTransition pause = new PauseTransition(Duration.seconds(1));
               pause.setOnFinished(e5 -> {

              	 int lastMessageIndex = view.msgArea.getText().split("\n").length-1;
                   String [] lastMessage = view.msgArea.getText().split("\n")[lastMessageIndex].split("\\|");
          		 String [] gameLobbyList = Arrays.copyOfRange(lastMessage, 2, lastMessage.length);
                   for (String str:gameLobbyList) {
                   jasmendController.leaveGamelobby(str);
                   }



           });
       pause.play();

        jasmendController.logout();
        jasmendController.waiterino(250);
        jasmendController.logoutUser(jasmendController.getUsername());

        jasmendController.getSocket().close();

        System.exit(0);

    	} catch (IOException e){
    		System.out.println("Error: " + e);
    	}
    	
    }
    	
    }
   

    public JassmendAccountView getJassmendAccountView() {
        return jassmendAccountView;
    }
    
    public JassmendGameView getView() {
        return view;
    }

    public JassmendLoginView getJassmendLoginView() {
        return jassmendLoginView;
    }
    
    public JassmendMainMenuView getMenuView() {
    	return menuView;
    }


   
}
