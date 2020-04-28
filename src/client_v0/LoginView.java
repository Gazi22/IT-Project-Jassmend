package client_v0;


import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginView {

    private static Scene scene;
    private ClientController clientController;
    private GameView view;
    public Stage newStageCreateAccount;

    Label lblUserName = new Label("User Name:");
    TextField txtUser = new TextField();
    Label lblPassword = new Label("Password:");
    TextField txtPassword = new TextField();
    Label lblIP = new Label();
    TextField txtIPAddress = new TextField();
    Label lblPort = new Label();
    TextField txtPort = new TextField();
    Button btnSignIn = new Button("Sign in");
    Button btnGameView = new Button("GameView");
    Button btnCreateAccount = new Button("Create new account");
    Button btnConnect = new Button("connect");

    HBox hboxBtn = new HBox(10);


    public LoginView(ClientController clientController, GameView view)
    {
        this.clientController = clientController;
        this.view  = view;
        GridPane gridLoginView = new GridPane();

        btnSignIn.setDisable(true);
        btnCreateAccount.setDisable(true);
        gridLoginView.setAlignment(Pos.CENTER);
        gridLoginView.setHgap(10);
        gridLoginView.setVgap(10);
        gridLoginView.setPadding(new Insets(25, 25, 25, 25));



        gridLoginView.add(lblUserName, 0, 4);


        gridLoginView.add(txtUser, 1, 4);

        gridLoginView.add(lblPassword, 0, 5);

        gridLoginView.add(txtPassword, 1, 5);

        gridLoginView.add(lblIP, 0, 0);

        gridLoginView.add(txtIPAddress, 1, 0);

        gridLoginView.add(lblPort, 0, 1);

        gridLoginView.add(txtPort, 1, 1);


        lblIP.setText("IP-Address:");


        lblPort.setText("Port:");





        hboxBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hboxBtn.getChildren().add(btnSignIn);
        hboxBtn.getChildren().add(btnGameView);
        hboxBtn.getChildren().addAll(btnCreateAccount, btnConnect);
        gridLoginView.add(hboxBtn, 1, 8);


        gridLoginView.setMinSize(400, 400);


        //Handling Login button


        //Handling Create Account button
        btnCreateAccount.setOnAction(e -> {
            newStageCreateAccount = new Stage();
            newStageCreateAccount.setScene(CreateAccountView.getSceneAccView());
            newStageCreateAccount.show();
        });

        btnSignIn.setOnAction(event -> {
            // Assume success always!
            String user = txtUser.getText();
            String password = txtPassword.getText();
            clientController.loginUser(user, password);
            //Not the best way to do it but it does the trick
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event2 -> {
                int lastMessageIndex = view.msgArea.getText().split("\n").length-1;
                String lastMessage = view.msgArea.getText().split("\n")[lastMessageIndex];
                if (lastMessage.matches("Received: Result\\|true\\|.*")) {
                    //Get Login hash and store in model
                    int hashIndex = lastMessage.split("\\|").length-1;
                    String hash = lastMessage.split("\\|")[hashIndex];
                    clientController.loginSuccesfull(user, hash);
                   
                    this.clientController.getViewManager().primaryStage.setScene(GameView.getScene()); 
                }
                else {
                	
                }
            });
            pause.play();
        });;


        
        btnGameView.setOnAction(event -> {
        	this.clientController.getViewManager().primaryStage.setScene(GameView.getScene()); 
        	
        });;
        
        
        btnConnect.setOnAction((event2) -> {
            // Button was clicked, do something...
            String inputIP = txtIPAddress.getText();
            String inputPort = txtPort.getText();
            if(!inputIP.equals("") && !inputPort.equals("")){
                if (clientController.connect(inputIP, inputPort)){
                    connectedMode();
                }
            } else {
                if (clientController.connect()){
                    connectedMode();
                }
            }

        });

        scene = new Scene(gridLoginView, 500, 275);
    }

    public void connectedMode(){
        //If connection to server was successfully established, disable all related fields and enable
        //chat functionality.
        btnSignIn.setDisable(false);
        txtIPAddress.setDisable(true);
        txtPort.setDisable(true);
        btnCreateAccount.setDisable(false);
        btnConnect.setDisable(true);
    }


    public void loggedInMode(){
        btnSignIn.setDisable(true);


    }



public static Scene getScene () {
		
		return scene;
		
	}

    public void setText_txtIPAddress(String text){
        txtIPAddress.setText(text);
    }

    public void setText_txtPort(String text){
        txtPort.setText(text);
    }


}
