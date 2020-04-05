package client_v0;



import javafx.application.Application;
import javafx.stage.Stage;

public class ClientChatRoom extends Application {
    ClientViewManager clientView;
    ChatView view;
    LoginView loginView;
    CreateAccountView createAccountView;

   

    public static void main(String[] args) {
    	 	
    		launch();
    		
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("G�nnjamin");
        ClientModel clientModel = new ClientModel();

        ClientController clientController = new ClientController(clientModel);
        view = new ChatView(clientController);
        clientController.addChatView(view);
        loginView = new LoginView(clientController, view);
        clientController.addLoginView(loginView);
        createAccountView = new CreateAccountView(clientController);

        clientView = new ClientViewManager(primaryStage, view, loginView, createAccountView);
       
        clientController.setViewManager(clientView);
        primaryStage.show();
    }
}
