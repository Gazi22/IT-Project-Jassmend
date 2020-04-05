package client_v0;

import java.util.Optional;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ChatView {
	private ClientController clientController;
	private static Scene scene;
	LoginView loginView;
	TextArea areaMessages = new TextArea();
	private TextField input = new TextField();
	Button btnSend = new Button("Send");
	Button btnLogout = new Button("Logout");
	
	
	public ChatView(ClientController clientController){
		areaMessages.setPrefHeight(450);
		areaMessages.setEditable(false);
		input.setPrefWidth(450);
		input.setDisable(true);
		btnSend.setPrefWidth(50);
		btnSend.setDisable(true);
		
		//close view...
		this.clientController = clientController;
		
		Menu user = new Menu("User");
		Menu chatrooms = new Menu("Chatrooms");
		Menu file = new Menu("File");
		Menu extras = new Menu("Extras");
		MenuBar menuBar = new MenuBar();
		
		menuBar.getMenus().addAll(user,chatrooms,file,extras);
		
		MenuItem menuItemLogout = new MenuItem("Logout");
		
		MenuItem menuItemChatrooms = new MenuItem ("Chatrooms");
		
		//Window for password change
		MenuItem menuItemChangePassword = new MenuItem("Change your password");
		
		//ask the user whether he is sure
		MenuItem menuItemDeleteAccount= new MenuItem("Delete Account");
		
		user.getItems().add(menuItemLogout);
		user.getItems().add(menuItemChangePassword);
		user.getItems().add(menuItemDeleteAccount);
		chatrooms.getItems().add(menuItemChatrooms);

		menuItemLogout.setOnAction(e -> {
			clientController.logout();
            clientController.showAlert("Logout","You have been sucessfully logged out.");

            this.clientController.getViewManager().primaryStage.setScene(LoginView.getScene());
            
           
            //TODO: Set scene of primary stage like previously
        	
		});
		
		
		menuItemChangePassword.setOnAction(e2 -> {
						
			TextInputDialog txtInput = new TextInputDialog();
	    	 txtInput.setTitle("Change Password");
	    	 txtInput.setContentText("Your new password:");
	    	 
	    	Optional<String> newPassword = txtInput.showAndWait();
	    	
			clientController.changePassword(newPassword.get());
			
            clientController.showAlert("Change Password","Your password has been successfully changed.");
                        
		});
		
		menuItemDeleteAccount.setOnAction(e3 -> {
			
            clientController.showAlertYesNo("Delete Account?","You want to delete your account?");
            clientController.deleteAccount();
            this.clientController.getViewManager().primaryStage.setScene(LoginView.getScene());
            
            
               
        
          
		});
	
		menuItemChatrooms.setOnAction(e4 -> {
			showChatroomScreen();
		});
		
		btnSend.setOnAction((event -> {
			 clientController.sendMessage(input.getText());
		}));
		
		
		//not working?
		//Creating an image 
		Image image = new Image("https://upload.wikimedia.org/wikipedia/commons/d/d3/User_Circle.png"); 
		       
		//Setting the image view 
		ImageView menuIcon = new ImageView(image); 
		
		menuIcon.setFitHeight(20);
	    menuIcon.setFitWidth(20);
	    user.setGraphic(menuIcon);
	    
		HBox msgRoot = new HBox(input,btnSend);
		VBox root = new VBox(menuBar,areaMessages,msgRoot);
		
		root.setPrefSize(500, 275);
		
		
		
		 
		scene = new Scene(root, 500, 275);
	}
	
	public void showChatroomScreen() {
        //Based on http://tutorials.jenkov.com/javafx/listview.html
        Stage stage = new Stage();
        
               
        Button btnCreateChatroom = new Button("Create Chatroom:");
        Button btnJoinChatroom = new Button("Join Selected Chatroom");
        btnJoinChatroom.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        stage.setTitle("Chatroom List");

        ListView listView = new ListView();

        clientController.getChatroomList();
        //Not the best way to do it but it does the trick
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            int lastMessageIndex = areaMessages.getText().split("\n").length-1;
            String lastMessage = areaMessages.getText().split("\n")[lastMessageIndex];
            for (String str:lastMessage.split("\\|")) {
                listView.getItems().add(str);
            }
            btnJoinChatroom.setOnAction(e2 -> {
                ObservableList selectedIndices = listView.getSelectionModel().getSelectedIndices();
                String chatroom = "";
                for(Object o : selectedIndices){
                    chatroom = (String)listView.getItems().get((int)o);
                    clientController.joinChatroom(chatroom);
                }
                //Don't do this at home kids !
                PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
                String finalChatroom = chatroom;
                pause2.setOnFinished(e3 -> {
                    int lastMessageIndex2 = areaMessages.getText().split("\n").length-1;
                    String lastMessage2 = areaMessages.getText().split("\n")[lastMessageIndex2];

                    if (lastMessage2.equals("Received: Result|true")){
                        clientController.joinSuccessfull(finalChatroom);
                        joinedChatroomMode();
                        stage.close();
                    }
                });
                pause2.play();
            });
        });
        pause.play();

        btnCreateChatroom.setOnAction(e4 -> {
            // Assume success always!
        					
    			TextInputDialog txtInput = new TextInputDialog();
    	    	 txtInput.setTitle("Create new Chatroom");
    	    	 txtInput.setContentText("Name of new chatroom:");
    	    	 
    	    	Optional<String> newChatroom = txtInput.showAndWait();
    	    	
    			clientController.createChatroom(newChatroom);
    			
                clientController.showAlert("New Chatroom","The chatroom"+newChatroom+"has been created");
                            
    		});
        
        
        VBox vBox = new VBox(listView, btnJoinChatroom,btnCreateChatroom);
        vBox.setStyle("-fx-background-color: BEIGE;");
        Scene scene = new Scene(vBox, 250, 150);
        stage.setScene(scene);
        stage.show();
    }
	
	
	
	
	
	public void joinedChatroomMode(){
        btnSend.setDisable(false);
        input.setDisable(false);
    }
	
	public static Scene getScene () {
		
		return scene;
		
	}
}
