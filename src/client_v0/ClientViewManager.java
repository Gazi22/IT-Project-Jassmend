package client_v0;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ClientViewManager {
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Stage primaryStage;
    private CreateAccountView createAccountView;
    private GameView view;
    private LoginView loginView;
    private MainMenuView menuView;
    private ClientModel clientModel;
    private ClientController clientController;
    

    public ClientViewManager(Stage primaryStage, GameView view, MainMenuView menuView, LoginView loginView, CreateAccountView createAccountView, ClientController clientController) {
        
    	this.primaryStage = primaryStage;
        this.createAccountView = createAccountView;
        this.view = view;
        this.loginView = loginView;
        this.menuView = menuView;
        this.clientController = clientController;
       
        
     // https://noblecodemonkeys.com/properly-exiting-a-javafx-application/

        closeWindow();
      		
        primaryStage.setScene(LoginView.getScene());
    }
    
    

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
    	if (clientController.getSocket()!= null) {
    	try {
    		
    	primaryStage.setOnCloseRequest(e->{

          	Platform.exit();
          	System.exit(0);
       		clientController.getGamelobbyList();
          	 PauseTransition pause = new PauseTransition(Duration.seconds(1));
               pause.setOnFinished(e5 -> {

              	 int lastMessageIndex = view.msgArea.getText().split("\n").length-1;
                   String [] lastMessage = view.msgArea.getText().split("\n")[lastMessageIndex].split("\\|");
          		 String [] gameLobbyList = Arrays.copyOfRange(lastMessage, 2, lastMessage.length);
                   for (String str:gameLobbyList) {
                   clientController.leaveGamelobby(str);
                   }

               clientController.logout();
               clientController.waiterino(250);
               clientController.logoutUser(clientController.getUsername());
              
           });

           pause.play();
          	});
    	
    	clientController.getSocket().close();
    	
    	} catch (IOException e){
    		System.out.println("Error: " + e);
    	}
    	
    }
    	
    }
   

    public CreateAccountView getCreateAccountView() {
        return createAccountView;
    }
    
    public GameView getView() {
        return view;
    }

    public LoginView getLoginView() {
        return loginView;
    }
    
    public MainMenuView getMenuView() {
    	return menuView;
    }


   
}
