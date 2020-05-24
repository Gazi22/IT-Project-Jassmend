package client_v0;



import javafx.application.Application;
import javafx.stage.Stage;

	//Author: Florian J�ger
public class ClientChatRoom extends Application {
    ClientViewManager clientView;
    LoginView loginView;
    CreateAccountView createAccountView;
    GameView view;
    MainMenuView viewMenu;
    ChatView chatView;
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
        ClientModel clientModel = new ClientModel();

        ClientController clientController = new ClientController(clientModel);
        view = new GameView(clientController,clientModel, viewMenu);
        clientController.addGameView(view);

        chatView = new ChatView(clientController);
        clientController.addChatView(chatView);

        loginView = new LoginView(clientController, view,chatView);
        clientController.addLoginView(loginView);

        createAccountView = new CreateAccountView(clientController);

        viewMenu = new MainMenuView(clientController, view, loginView);
        clientController.addMainMenuView(viewMenu);





      

        clientView = new ClientViewManager(primaryStage, view, viewMenu, loginView, createAccountView, clientController,chatView);
       
        clientController.setViewManager(clientView);
        primaryStage.show();
    }
}
