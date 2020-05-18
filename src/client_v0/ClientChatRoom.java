package client_v0;



import javafx.application.Application;
import javafx.stage.Stage;

public class ClientChatRoom extends Application {
    ClientViewManager clientView;
    LoginView loginView;
    CreateAccountView createAccountView;
    GameView view;
    MainMenuView viewMenu;
   

    public static void main(String[] args) {
    	 	
    		launch();
    		
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jassmend");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);
        primaryStage.setResizable(false);
        ClientModel clientModel = new ClientModel();

        ClientController clientController = new ClientController(clientModel);
        view = new GameView(clientController,clientModel);
        clientController.addGameView(view);
        loginView = new LoginView(clientController, view);
        clientController.addLoginView(loginView);
        viewMenu = new MainMenuView(clientController, view);
        clientController.addMainMenuView(viewMenu);
        createAccountView = new CreateAccountView(clientController);

       
      

        clientView = new ClientViewManager(primaryStage, view, viewMenu, loginView, createAccountView);
       
        clientController.setViewManager(clientView);
        primaryStage.show();
    }
}
