package client_v0;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import jassmendModel.JassmendModel;
import jassmendModel.Player;
import jassmendView.CardView;
import jassmendView.PlayerPane;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class GameView {
	  private static Scene scene;
	    private ClientController clientController;
	     private String finalGamelobby;
	     	private CardView cardView;
			public Stage primaryStage;
	//Client Server Communication
	private String [] playerIDs=new String[4];

//temporary
	Button btnEndTurn = new Button("End Turn");

	Button btnDeal = new Button("Deal");
	// Player areas
	
	Insets insets = new Insets(10);
	//Player areas
	
	//Player bottom
	Label userNamePl1 = new Label("Player 1");
	Label scorePl1 = new Label("Score:");
	VBox player1Box = new VBox();
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
	Menu helpMenu = new Menu("Help");
	MenuItem gamelobbyItem = new MenuItem("Show Gamelobbys");
	
	public Object areaMessages;
	
	
	// Chat
	TitledPane chatArea = new TitledPane();
	TextField txt1 = new TextField ();
	Label lblchat = new Label("Chat");
	Button btnSend = new Button ("Send");
	TextArea msgArea = new TextArea ();
	HBox chatbox1 = new HBox ();
	VBox chatbox2 = new VBox ();
	VBox chatbox3 = new VBox ();


	//PlayArea
	HBox playedCardPl1 = new HBox();
	VBox playedCardPl2 = new VBox();
	HBox playedCardPl3 = new HBox();
	VBox playedCardPl4 = new VBox();


	public GameView (ClientController clientController, ClientModel model) {

		this.clientController = clientController;
		PlayerPane pp = new PlayerPane();
		pp.setPlayer(model.getPlayer(1));
		OptionsMenu.getItems().addAll(ResumeItem, LogoutItem);
		HelpMenu.getItems().addAll(RulesItem);
		LobbyMenu.getItems().addAll(gamelobbyItem);
		// Hyperlink test helpMenu - Code From Reddit https://www.reddit.com/r/javahelp/comments/4bqcci/how_to_make_a_link_hyperlink_in_javafx/

		helpMenu.setOnAction(e -> {
			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(new URI("https://www.swisslos.ch/de/jass/informationen/jass-regeln/jass-grundlagen.html"));

				} catch (IOException e1) {
					e1.printStackTrace();

				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});


		meba.getMenus().addAll(OptionsMenu, HelpMenu, LobbyMenu);

		// ___________________________________________________________________


		player1Box.getChildren().add(pp);
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


		chatArea.setText("Chat");

		txt1.setPromptText("Type here your message");
		HBox.setHgrow(txt1, Priority.ALWAYS);

		msgArea.setPrefHeight(600);
		msgArea.setPrefWidth(300);

		chatbox1.getChildren().add(btnSend);
		chatbox1.getChildren().add(txt1);

		chatbox2.getChildren().add(lblchat);
		chatbox2.getChildren().add(msgArea);

		chatbox3.getChildren().add(chatbox2);
		chatbox3.getChildren().add(chatbox1);

		chatArea.setContent(chatbox3);

		//-----------------------------------------------------------------------

		HBox controlBox = new HBox();
		controlBox.getChildren().addAll(btnDeal, btnEndTurn);
		controlBox.setAlignment(Pos.CENTER);


		//----------------------------------------------------------------------------------------------------------
		//Table Leaderboard

		TableView table = new TableView();
		table.setEditable(false);

		TableColumn playerNameCol = new TableColumn("Player");
		TableColumn scoreCol = new TableColumn("Score");

		playerNameCol.setCellValueFactory(c -> Player.getPlayerName());

		table.getColumns().addAll(playerNameCol, scoreCol);

		//----------------------------------------------------------------------------------------------------------
		for (int i = 0; i < 1; i++) {
			Button btnCard = new CardView();
			playedCardPl1.getChildren().add(btnCard);
			btnCard.getStyleClass().add("btnCard");
			playedCardPl1.setAlignment(Pos.CENTER);
			Button btnCard2 = new CardView();
			playedCardPl2.getChildren().add(btnCard2);
			btnCard.getStyleClass().add("btnCard");
			playedCardPl2.setAlignment(Pos.CENTER);
			Button btnCard3 = new CardView();
			playedCardPl3.getChildren().add(btnCard3);
			btnCard.getStyleClass().add("btnCard");
			playedCardPl3.setAlignment(Pos.CENTER);
			Button btnCard4 = new CardView();
			playedCardPl4.getChildren().add(btnCard4);
			btnCard.getStyleClass().add("btnCard");
			playedCardPl4.setAlignment(Pos.CENTER);

		}


		//-----------------------------------------------------------------------------------------------------


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
		outerPane.setRight(chatArea);
		outerPane.setLeft(table);
		outerPane.setBottom(controlBox);

		innerPane.setBottom(playedCardPl1);
		innerPane.setLeft(playedCardPl2);
		innerPane.setTop(playedCardPl3);
		innerPane.setRight(playedCardPl4);

		scene = new Scene(outerPane);
		scene.getStylesheets().add(getClass().getResource("Jass.css").toExternalForm());
		//	primaryStage.setMinHeight(500);//500
		//	primaryStage.setMinWidth(1250);//1250
		//	primaryStage.setScene(scene);
		//	primaryStage.setTitle("Jassmend");
		//primaryStage.setMaximized(true);


		btnSend.setOnAction((event -> {
			clientController.sendMessage(txt1.getText());
		}));

		player1Btn.setOnAction(e9 -> {
			System.out.println("Turn finished, it is now player 2 turn");

		});



		//Play the first Card into the first fieldbutton
			getHandButton(0).setOnAction(e10 -> {

				CardView cV1 = (CardView) getHandButton(0);
				cV1.setGraphic(null);
				CardView cV2 = (CardView) getFieldButton(0);
				cV2.setCard(pp.getCardsHolder(0));
				clientController.sendCardPlayed(pp.getCardsHolder(0).toString(), finalGamelobby);
				clientController.turnFinished(finalGamelobby);

			});



		btnDeal.setOnAction(e7 -> {
			clientController.dealCards(finalGamelobby);
				});


		btnEndTurn.setOnAction(e8->clientController.turnFinished(finalGamelobby));

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
	public Button getBtnDeal() {

		return btnDeal;
	}


	public PlayerPane getPlayerPane(int i) {
		return (PlayerPane) player1Box.getChildren().get(0);
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
                finalGamelobby = gamelobby;
                pause2.setOnFinished(e3 -> {
                    int lastMessageIndex2 = msgArea.getText().split("\n").length-1;
                    String lastMessage2 = msgArea.getText().split("\n")[lastMessageIndex2];

                    if (lastMessage2.startsWith("PlayerIDs")){
                        clientController.joinSuccessfull(finalGamelobby);
						clientController.joinedGamelobbyMode();

						for (int x=0; x < playerIDs.length; x++) {
							if (clientController.getPlayerIDs(x)!=null){
								playerIDs[x]= clientController.getPlayerIDs(x);
								System.out.println("Arraypostition"+ x+" "+playerIDs[x]);
							}
							else{
								System.out.println("Arraypostition"+ x+" "+"is empty");
							}

							}


                        stage.close();
						if (clientController.isFull()){
							clientController.gamelobbyIsFull(finalGamelobby,"GamelobbyIsFull");

						}
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



	public Button getHandButton(int i) {

		return  (Button) getPlayerPane(1).getCardBox().getChildren().get(i);

	}
	public Button getFieldButton(int i) {
if(i==1) {return (Button) playedCardPl2.getChildren().get(0);}
else if(i==2){return (Button) playedCardPl3.getChildren().get(0);}
else if(i==3){return (Button) playedCardPl4.getChildren().get(0);}
else return (Button) playedCardPl1.getChildren().get(0);
	}

	public void showPlayedCards(){
		//added new fx thread
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				int i = clientController.getBtnToActivate();

				CardView cV2 = (CardView) getFieldButton(i-1);
				cV2.setCard(clientController.getCardsPlayed(clientController.getCardPlayedNr()));//
			}
		});
	}


public static Scene getScene () {
		
		return scene;
		
	}




}


