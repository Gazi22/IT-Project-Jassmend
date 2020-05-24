package client_v0;


import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

//Author: Florian J�ger & Gazmend Shefiu
public class JassmendLoginView {

	
    private static Scene scene;
    private JasmendController jasmendController;
    private JassmendMainMenuView menuView;
    private JassmendGameView view;
    private JassmendLogView jassmendLogView;
    public Stage newStageCreateAccount;
    private JasmendModel jasmendModel;
    

    Label lblUserName = new Label("User Name:");
    TextField txtUser = new TextField();
    Label lblPassword = new Label("Password:");
    Label lblIP = new Label();
    TextField txtIPAddress = new TextField();
    Label lblPort = new Label();
    TextField txtPort = new TextField();
    Button btnSignIn = new Button("Sign in");
    public String getBtnString;
    Button btnCreateAccount = new Button("Create new account");
    Button btnConnect = new Button("Connect");
    CheckBox checkPw = new CheckBox("Show Password");
    
    TextField passwordTextField = new TextField();
    PasswordField passwordField = new PasswordField();

    HBox hboxBtn = new HBox(10);
 

    public JassmendLoginView(JasmendController jasmendController, JassmendGameView view, JassmendLogView jassmendLogView)
  //Author: Florian J�ger
    {
        this.jasmendController = jasmendController;
        this.view  = view;
        this.jassmendLogView = jassmendLogView;
    
        GridPane gridLoginView = new GridPane();

    	// Author: Gazmend Shefiu
        // https://stackoverflow.com/questions/17014012/how-to-unmask-a-javafx-passwordfield-or-properly-mask-a-textfield
        passwordTextField.setManaged(false);
        passwordTextField.setVisible(false);
        
        passwordTextField.managedProperty().bind(checkPw.selectedProperty());
        passwordTextField.visibleProperty().bind(checkPw.selectedProperty());

        passwordField.managedProperty().bind(checkPw.selectedProperty().not());
        passwordField.visibleProperty().bind(checkPw.selectedProperty().not());

        // Bind the textField and passwordField text values bidirectionally.
        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());
        
      //Author: Florian J�ger
        btnSignIn.setDisable(true);
        btnCreateAccount.setDisable(true);
        gridLoginView.setAlignment(Pos.CENTER);
        gridLoginView.setHgap(10);
        gridLoginView.setVgap(10);
        gridLoginView.setPadding(new Insets(25, 25, 25, 25));
        
        
        txtUser.setPromptText("Type here your Username");
        passwordTextField.setPromptText("Type here your Password");
        passwordField.setPromptText("Type here your Password");

      //Author: Gazmend Shefiu
        checkPw.getStyleClass().add("outline");
        lblUserName.getStyleClass().add("outline");
        lblPassword.getStyleClass().add("outline");
        lblPort.getStyleClass().add("outline");
        lblIP.getStyleClass().add("outline");
        
        
       
      //Author: Florian J�ger
		gridLoginView.add(lblUserName, 0, 4);

		gridLoginView.add(txtUser, 1, 4);

		gridLoginView.add(lblPassword, 0, 5);

		gridLoginView.add(passwordField, 1, 5);
		
		gridLoginView.add(passwordTextField, 1, 5);
		
		gridLoginView.add(checkPw, 1, 6);

		gridLoginView.add(lblIP, 0, 0);

		gridLoginView.add(txtIPAddress, 1, 0);

		gridLoginView.add(lblPort, 0, 1);

		gridLoginView.add(txtPort, 1, 1);

        lblIP.setText("IP-Address:");


        lblPort.setText("Port:");



        hboxBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hboxBtn.getChildren().add(btnSignIn);

        hboxBtn.getChildren().addAll(btnCreateAccount, btnConnect);
        gridLoginView.add(hboxBtn, 1, 8);


        gridLoginView.setMinSize(400, 400);

        
        
        
        

        //Author: Florian J�ger
        //Handling Create Account button
        btnCreateAccount.setOnAction(e -> {
            newStageCreateAccount = new Stage();
            newStageCreateAccount.setScene(JassmendAccountView.getSceneAccView());
            newStageCreateAccount.setMinWidth(700);
            newStageCreateAccount.setMinHeight(400);
            newStageCreateAccount.setResizable(false);
            newStageCreateAccount.show();
        });
        
      //Author: Florian J�ger & Gazmend Shefiu
      //Handling Login button
        btnSignIn.setOnAction(event -> {
            // Assume success always!
            String user = txtUser.getText();
            String password = passwordField.getText();
            jasmendController.loginUser(user, password);
            
            //Not the best way to do it but it does the trick
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event2 -> {
                int lastMessageIndex = jassmendLogView.areaMessages.getText().split("\n").length-1;
                String lastMessage = jassmendLogView.areaMessages.getText().split("\n")[lastMessageIndex];
                if (lastMessage.matches("Result\\|true\\|.*")) {

                    //Get Login hash and store in
                    int hashIndex = lastMessage.split("\\|").length-1;
                    String hash = lastMessage.split("\\|")[hashIndex];
                    jasmendController.loginSuccesfull(user, hash);

                    jasmendController.setTextlblUsernameMenu();
                    this.jasmendController.getViewManager().primaryStage.setScene(JassmendMainMenuView.getScene());
                }
                else {
                	
                }
            });
            pause.play();
        });
      //Author: Florian J�ger & Gazmend Shefiu
        passwordField.setOnKeyPressed(e11 -> {
        	if(e11.getCode() == KeyCode.ENTER) {
                // Assume success always!
                String user = txtUser.getText();
                String password = passwordField.getText();
                jasmendController.loginUser(user, password);
                
                //Not the best way to do it but it does the trick
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event2 -> {
                    int lastMessageIndex = jassmendLogView.areaMessages.getText().split("\n").length-1;
                    String lastMessage = jassmendLogView.areaMessages.getText().split("\n")[lastMessageIndex];
                    if (lastMessage.matches("Received: Result\\|true\\|.*")) {

                        //Get Login hash and store in
                        int hashIndex = lastMessage.split("\\|").length-1;
                        String hash = lastMessage.split("\\|")[hashIndex];
                        jasmendController.loginSuccesfull(user, hash);

                        jasmendController.setTextlblUsernameMenu();
                        this.jasmendController.getViewManager().primaryStage.setScene(JassmendMainMenuView.getScene());
                    }
                    else {
                    	
                    }
                });
                pause.play();
			}
        });

      //Author: Florian J�ger
        btnConnect.setOnAction((event2) -> {
            // Button was clicked, do something...
            String inputIP = txtIPAddress.getText();
            String inputPort = txtPort.getText();
            if(!inputIP.equals("") && !inputPort.equals("")){
                if (jasmendController.connect(inputIP, inputPort)){
                    connectedMode();
                }
            } else {
                if (jasmendController.connect()){
                    connectedMode();
                }
            }

        });

        scene = new Scene(gridLoginView, 700, 275);
        scene.getStylesheets().add(getClass().getResource("LogJass.css").toExternalForm());
        
    }
  //Author: Florian J�ger
    public void connectedMode(){
        //If connection to server was successfully established, disable all related fields and enable
        //chat functionality.
        btnSignIn.setDisable(false);
        txtIPAddress.setDisable(true);
        txtPort.setDisable(true);
        btnCreateAccount.setDisable(false);
        btnConnect.setDisable(true);
    }

  //Author: Florian J�ger
    public void loggedInMode(){
        btnSignIn.setDisable(true);


    }

  //Author: Florian J�ger
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
