package client_v0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameView {
	  private static Scene scene;
	    private ClientController clientController;
	    
	    
	//Client Server Communication
	int playerID;    
	    
	    
	//private Model 
	
	Insets insets = new Insets(10);
	//Player areas
	
	//Player bottom
	Label userNamePl1 = new Label("Player 1");
	Label scorePl1 = new Label("Score:");
	VBox player1Info = new VBox();
	HBox player1Cards = new HBox();
	Button player1Btn = new Button("Test");
	
	//Player top
	Label userNamePl2 = new Label("Player 2");
	Label scorePl2 = new Label("Score:");
	VBox player2Info = new VBox();
	HBox player2Cards = new HBox();
		
	//Player left
	Label userNamePl3 = new Label("Player 3");
	Label scorePl3 = new Label("Score:");
	VBox player3Info = new VBox();
	HBox player3Cards = new HBox();
	
	//Player right
	Label userNamePl4 = new Label("Player 4");
	Label scorePl4 = new Label("Score:");
	VBox player4Info = new VBox();
	HBox player4Cards = new HBox();
	
	
	// Menu Bar
	
	MenuBar meba = new MenuBar();
	
	Menu OptionsMenu = new Menu("Options");
	Menu HelpMenu = new Menu("Help");
	Menu LobbyMenu = new Menu("Gamelobby");
	
	MenuItem ResumeItem = new MenuItem("Quit Game");
	MenuItem LogoutItem = new MenuItem("Logout");
	
	MenuItem RulesItem = new MenuItem("How to play?");
	
	MenuItem gamelobbyItem = new MenuItem("Show Gamelobbys");
	
	public Object areaMessages;
	
	
	// Chat
	
	TextField txt1 = new TextField ();
	Button btnSend = new Button ("Send");
	TextArea msgArea = new TextArea ();
	HBox chatbox1 = new HBox ();
	VBox chatbox2 = new VBox ();
	VBox chatbox3 = new VBox ();
	

	
	public GameView (ClientController clientController) {
		

	this.clientController = clientController;
	   
	OptionsMenu.getItems().addAll(ResumeItem,LogoutItem);
	HelpMenu.getItems().addAll(RulesItem);
	LobbyMenu.getItems().addAll(gamelobbyItem);
		
		
	meba.getMenus().addAll(OptionsMenu, HelpMenu, LobbyMenu);	
	
	// ___________________________________________________________________
	
	player1Info.getChildren().add(userNamePl1);
	player1Info.getChildren().add(scorePl1);
	player1Info.getChildren().add(player1Btn);
	
	HBox player1Box = new HBox(player1Info);
	player1Box.setAlignment(Pos.CENTER);
	
	player2Info.getChildren().add(userNamePl2);
	player2Info.getChildren().add(scorePl2);
	
	player2Info.setAlignment(Pos.CENTER);
	
	
	player3Info.getChildren().add(userNamePl3);
	player3Info.getChildren().add(scorePl3);
	
	HBox player3Box = new HBox(player3Info);
	player3Box.setAlignment(Pos.CENTER);
	
	player3Box.setVisible(true);
	
	
	player4Info.getChildren().add(userNamePl4);
	player4Info.getChildren().add(scorePl4);
	
	player4Info.setAlignment(Pos.CENTER);
	
	// __________________________________________________________________
	
	
	txt1.setPromptText("Type here your message");
	HBox.setHgrow(txt1, Priority.ALWAYS);
	
	chatbox1.getChildren().add(btnSend);
	chatbox1.getChildren().add(txt1);
	
	chatbox2.getChildren().add(msgArea);
	
	chatbox3.getChildren().add(chatbox2);
	chatbox3.getChildren().add(chatbox1);
	
	
	
	
	
	
	BorderPane outerPane = new BorderPane();
	
	outerPane.setVisible(true);
	
	BorderPane middlePane = new BorderPane();
	
	middlePane.setVisible(true);
	
	BorderPane innerPane = new BorderPane();
	
	innerPane.setVisible(true);
	
	middlePane.setBottom(player1Box);
	middlePane.setTop(player3Box);
	middlePane.setLeft(player2Info);
	middlePane.setRight(player4Info);
	middlePane.setCenter(innerPane);
	
	outerPane.setCenter(middlePane);
	outerPane.setTop(meba);
	outerPane.setRight(chatbox3);
	
		
	scene = new Scene(outerPane);

	
	
	
	btnSend.setOnAction((event -> {
		 clientController.sendMessage(txt1.getText());
	}));

	 player1Btn.setOnAction(event -> {
     	System.out.println("Turn finished, it is now player 2 turn");
     	
     });;
	
     
     //Handlungsbedarf transition
     LogoutItem.setOnAction(e -> {
    	 clientController.getGamelobbyList();
    	 PauseTransition pause = new PauseTransition(Duration.seconds(1));
         pause.setOnFinished(e5 -> {
        	              
        	 int lastMessageIndex = msgArea.getText().split("\n").length-1;
             String [] lastMessage = msgArea.getText().split("\n")[lastMessageIndex].split("\\|");
    						String [] gameLobbyList = Arrays.copyOfRange(lastMessage, 2, lastMessage.length);
             for (String str:gameLobbyList) {
             clientController.leaveGamelobby(str);
             }
        	
            	 
         clientController.logout();
         this.clientController.getViewManager().primaryStage.setScene(LoginView.getScene());
     });
     
     pause.play();
     
     });
     gamelobbyItem.setOnAction(e4 -> {
			showgamelobbyScreen();
		});
	
	}
	

	
	
	
	
	
	
	//Open Gamelobby View
	public void showgamelobbyScreen() {
        //Based on http://tutorials.jenkov.com/javafx/listview.html
        Stage stage = new Stage();
        
               
        Button btnCreateGamelobby = new Button("Create gamelobby:");
        Button btnJoinGamelobby = new Button("Join selected gamelobby");
     
        stage.setTitle("Gamelobby List");

        ListView listView = new ListView();

        clientController.getGamelobbyList();
        //Not the best way to do it but it does the trick
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            int lastMessageIndex = msgArea.getText().split("\n").length-1;
            String [] lastMessage = msgArea.getText().split("\n")[lastMessageIndex].split("\\|");
						String [] gameLobbyList = Arrays.copyOfRange(lastMessage, 2, lastMessage.length);
            for (String str:gameLobbyList) {
                listView.getItems().add(str);
            }
            btnJoinGamelobby.setOnAction(e2 -> {
                ObservableList selectedIndices = listView.getSelectionModel().getSelectedIndices();
                String gamelobby = "";
                for(Object o : selectedIndices){
                    gamelobby = (String)listView.getItems().get((int)o);
                    clientController.joinGamelobby(gamelobby);
                    
                }
                //Don't do this at home kids !
                PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
                String finalGamelobby = gamelobby;
                pause2.setOnFinished(e3 -> {
                    int lastMessageIndex2 = msgArea.getText().split("\n").length-1;
                    String lastMessage2 = msgArea.getText().split("\n")[lastMessageIndex2];

                    if (lastMessage2.equals("Received: Result|true")){
                        clientController.joinSuccessfull(finalGamelobby);
                        joinedGamelobbyMode();
                        stage.close();
                        //Handling Create Account button
                                                                       
                    }
                });
                pause2.play();
            });
        });
        pause.play();

        btnCreateGamelobby.setOnAction(e4 -> {
            // Assume success always!
        					
    			TextInputDialog txtInput = new TextInputDialog();
    	    	 txtInput.setTitle("Create new gamelobby");
    	    	 txtInput.setContentText("Name of new gamelobby:");
					Optional<String> result = txtInput.showAndWait();
    	    	 String newGamelobby = "";
    	    	 if (result.isPresent()){
    	    	 	newGamelobby = result.get();
						 }
    	    	
    			clientController.createGamelobby(newGamelobby);
    	    	 stage.close();
    			
                clientController.showAlert("New gamelobby","The gamelobby"+newGamelobby+"has been created");
			showgamelobbyScreen();
                            
    		});
        
        
        VBox vBox = new VBox(listView, btnJoinGamelobby,btnCreateGamelobby);
        vBox.setStyle("-fx-background-color: BEIGE;");
        Scene scene = new Scene(vBox, 250, 150);
        stage.setScene(scene);
        stage.show();
    }
	
	
	
	
	
	public void joinedGamelobbyMode(){
        btnSend.setDisable(false);
        txt1.setDisable(false);
    }

public static Scene getScene () {
		
		return scene;
		
	}



}
