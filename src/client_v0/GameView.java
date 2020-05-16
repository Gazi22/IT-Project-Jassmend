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
import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class GameView {
	  private static Scene scene;
	private ClientController clientController;
	private MainMenuView mainMenuView;

	private CardView cardView;
	public Stage primaryStage;
	//Client Server Communication
	public String [] playerIDs=new String[4];
	String finalGamelobby="";

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
	Menu returnToMenu = new Menu("Return To Menu");
	Menu helpMenu = new Menu("Help");
	Menu soundMenu = new Menu("Sound Options");
	
	MenuItem ResumeItem = new MenuItem("Quit Game");
	MenuItem LogoutItem = new MenuItem("Logout");
	MenuItem RulesItem = new MenuItem("How to play?");

	MenuItem clickToReturn = new MenuItem("Click to return");
	CustomMenuItem customMenuItem = new CustomMenuItem(new Slider());
	MenuItem playSound = new MenuItem("Play Music");
	MenuItem pauseSound = new MenuItem("Pause Music");
	MenuItem stopSound = new MenuItem("Stop Music");
    
	
	
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
	OptionsMenu.getItems().addAll(ResumeItem,LogoutItem);
	HelpMenu.getItems().addAll(RulesItem);
	returnToMenu.getItems().addAll(clickToReturn);
	soundMenu.getItems().addAll(playSound, pauseSound, stopSound, customMenuItem);
	customMenuItem.setHideOnClick(false);


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
		
		returnToMenu.setOnAction(e2 -> {
			this.clientController.getViewManager().primaryStage.setScene(MainMenuView.getScene());
		});
		
		ResumeItem.setOnAction(e3 -> {
			this.clientController.getViewManager().primaryStage.close();
		});
		
		
		
		


		meba.getMenus().addAll(OptionsMenu, HelpMenu, returnToMenu, soundMenu);
	
	// ___________________________________________________________________

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

				clientController.sendCardPlayed(pp.getCardsHolder(0).toString(), finalGamelobby);
				clientController.waiterino(1000);
				clientController.turnFinished(finalGamelobby);

			});
		getHandButton(1).setOnAction(e11 -> {


			clientController.sendCardPlayed(pp.getCardsHolder(1).toString(), finalGamelobby);
			clientController.waiterino(1000);
			clientController.turnFinished(finalGamelobby);

		});
		getHandButton(2).setOnAction(e12 -> {

			clientController.sendCardPlayed(pp.getCardsHolder(2).toString(), finalGamelobby);
			clientController.waiterino(1000);
			clientController.turnFinished(finalGamelobby);

		});
		getHandButton(3).setOnAction(e13 -> {


			clientController.sendCardPlayed(pp.getCardsHolder(3).toString(), finalGamelobby);
			clientController.waiterino(1000);
			clientController.turnFinished(finalGamelobby);

		});
		getHandButton(4).setOnAction(e14 -> {


			clientController.sendCardPlayed(pp.getCardsHolder(4).toString(), finalGamelobby);
			clientController.waiterino(1000);
			clientController.turnFinished(finalGamelobby);

		});
		getHandButton(5).setOnAction(e15 -> {


			clientController.sendCardPlayed(pp.getCardsHolder(5).toString(), finalGamelobby);
			clientController.waiterino(1000);
			clientController.turnFinished(finalGamelobby);

		});
		getHandButton(6).setOnAction(e16 -> {


			clientController.sendCardPlayed(pp.getCardsHolder(6).toString(), finalGamelobby);
			clientController.waiterino(1000);
			clientController.turnFinished(finalGamelobby);

		});
		getHandButton(7).setOnAction(e17 -> {


			clientController.sendCardPlayed(pp.getCardsHolder(7).toString(), finalGamelobby);
			clientController.waiterino(1000);
			clientController.turnFinished(finalGamelobby);

		});
		getHandButton(8).setOnAction(e18 -> {


			clientController.sendCardPlayed(pp.getCardsHolder(8).toString(), finalGamelobby);
			clientController.waiterino(1000);
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




	}
	public Button getBtnDeal() {

		return btnDeal;
	}


	public PlayerPane getPlayerPane(int i) {
		return (PlayerPane) player1Box.getChildren().get(0);
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
				cV2.setCard(clientController.getCardsPlayed(clientController.getCardPlayedNr()-1));//
				}
		});
	}

	public void clearFieldButtons(){
		//added new fx thread
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

					//clientController.wait(5000);
					for (int x = 0; x < 4; x++) {
						CardView cV2 = (CardView) getFieldButton(x);
						cV2.setGraphic(null);//
					}

			}
			});
	}


public static Scene getScene () {

		return scene;

	}



public String[] getPlayerIDs() {
	return playerIDs;
}

public void addMainMenuView(MainMenuView mainMenuView){
		this.mainMenuView = mainMenuView;
	}


public void setGamelobby(String gamelobby){
		this.finalGamelobby=gamelobby;
}
}


