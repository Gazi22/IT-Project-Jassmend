package client_v0;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ClientViewManager {
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Stage primaryStage;
    private CreateAccountView createAccountView;
    private GameView view;
    private LoginView loginView;
   
    

    public ClientViewManager(Stage primaryStage, GameView view, LoginView loginView, CreateAccountView createAccountView) {
        this.primaryStage = primaryStage;
        this.createAccountView = createAccountView;
        this.view = view;
        this.loginView = loginView;
       
        
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
   

    public CreateAccountView getCreateAccountView() {
        return createAccountView;
    }
    
    public GameView getView() {
        return view;
    }

    public LoginView getLoginView() {
        return loginView;
    }
    
   
}
