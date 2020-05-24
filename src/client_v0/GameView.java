package client_v0;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import jassmendView.CardView;
import jassmendView.PlayerPane;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.Desktop;


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

	Button btnTrumpf = new Button("Trumpf");
	//Button btnDeal = new Button("Deal");
	// Player areas
	Insets insets = new Insets(10);
	
	//Player bottom
	Label userNamePl1 = new Label("");
	VBox player1Info =new VBox();
	Label scorePl1 = new Label("Score:");
	VBox player1Box = new VBox();
	VBox player1InfoBox=new VBox();
	HBox player1Cards = new HBox();
	Button player1Btn = new Button("Test");
	HBox player1BottomBox=new HBox();
	
	//Player top
	Label userNamePl2 = new Label("");
	Label scorePl2 = new Label("Score:");
	VBox player2Info = new VBox();
	HBox player2Cards = new HBox();
		
	//Player left
	Label userNamePl3 = new Label("");
	Label scorePl3 = new Label("Score:");
	VBox player3Info = new VBox();
	HBox player3Cards = new HBox();
	
	//Player right
	Label userNamePl4 = new Label("");
	Label scorePl4 = new Label("Score:");
	VBox player4Info = new VBox();
	HBox player4Cards = new HBox();
	
	
	// Menu Bar
	
	MenuBar meba = new MenuBar();
	
	Menu OptionsMenu = new Menu("Options");
	Menu HelpMenu = new Menu("Help");
	Menu returnToMenu = new Menu("Return To Menu");
	Menu helpMenu = new Menu("Help");
	
	MenuItem ResumeItem = new MenuItem("Quit Game");
	MenuItem LogoutItem = new MenuItem("Logout");
	MenuItem RulesItem = new MenuItem("How to play?");

	MenuItem clickToReturn = new MenuItem("Click to return");
    
	
	

	// Chat
	TitledPane chatArea = new TitledPane();
	TextField txt1 = new TextField ();
	Label lblchat = new Label("Chat");
	Button btnSend = new Button ("Send");
	TextArea msgArea = new TextArea ();
	HBox chatbox1 = new HBox ();
	VBox chatbox2 = new VBox ();
	VBox chatbox3 = new VBox ();

	// Leaderboard
	Label lblLeaderboard = new Label("Leaderboard");
	Label lblRound = new Label("Round:");
	Label lblTeam1 = new Label("Team1");
	Label lblSticheT1 = new Label("SticheT1");
	Label lblScoreT1 = new Label("ScoreT1");
	Label lblTeam2 = new Label("Team2");
	Label lblSticheT2 = new Label("SticheT2");
	Label lblScoreT2 = new Label("ScoreT2");
	Label lblStiche = new Label("Stiche ");
	Label lblScore = new Label("Score");



	HBox lblLeaderboardBox = new HBox();
	HBox rssBox = new HBox();
	HBox team1Box = new HBox();

	HBox team2Box = new HBox();

	VBox lbBox = new VBox();

	// Team table


	Label lblTeam1Pl = new Label("Team1:");

	Label lblPl1 = new Label("Player1");
	Label lblPl3 = new Label("Player3");

	Label lblTeam2Pl = new Label("Team2:");

	Label lblPl2 = new Label("Player2");
	Label lblPl4 = new Label("Player4");

	VBox plTeam1Box = new VBox();
	HBox team1PlBox = new HBox();

	VBox plTeam2Box = new VBox();
	HBox team2PlBox = new HBox();



	VBox teamBox = new VBox();


	//PlayArea
	HBox playedCardPl1 = new HBox();
	VBox playedCardPl2 = new VBox();
	HBox playedCardPl3 = new HBox();
	VBox playedCardPl4 = new VBox();


// Label for displaying the trumpf suit

	Label lblTrumpf = new Label();


	public GameView (ClientController clientController, ClientModel model, MainMenuView mainMenuView) {




	this.clientController = clientController;
	this.mainMenuView = mainMenuView;
	PlayerPane pp = new PlayerPane();
	pp.setPlayer(model.getPlayer(1));
	OptionsMenu.getItems().addAll(ResumeItem,LogoutItem);
	HelpMenu.getItems().addAll(RulesItem);
	returnToMenu.getItems().addAll(clickToReturn);

	// Author: Davide Seabra
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
			this.clientController.getViewManager().primaryStage.setMaximized(false);
			this.clientController.getViewManager().primaryStage.setScene(MainMenuView.getScene());
			this.clientController.getViewManager().primaryStage.setResizable(false);
			
		});
		
		ResumeItem.setOnAction(e3 -> {
				this.clientController.getViewManager().primaryStage.close();
		});
		

		meba.getMenus().addAll(OptionsMenu, HelpMenu, returnToMenu);
	
	// ___________________________________________________________________

		// ___________________________________________________________________


		player1Box.getChildren().add(pp);
		player1Box.setAlignment(Pos.CENTER);
		player1Info.getChildren().add(userNamePl1);
		player1InfoBox.getChildren().addAll(player1Box,player1Info);
		player1BottomBox.getChildren().add(player1InfoBox);
		player1BottomBox.setAlignment(Pos.CENTER);
		player2Info.getChildren().add(userNamePl2);
	
		
		player2Info.setAlignment(Pos.CENTER);

		player3Info.getChildren().add(userNamePl3);


		HBox player3Box = new HBox(player3Info);
		player3Box.setAlignment(Pos.CENTER);

		player3Box.setVisible(true);

		player4Info.getChildren().add(userNamePl4);


		player4Info.setAlignment(Pos.CENTER);
		
		userNamePl2.getStyleClass().add("outline");
		scorePl2.getStyleClass().add("outline");
		userNamePl2.setId("userNamePl2");
		scorePl2.setId("scorePlayer2");
		
		userNamePl3.getStyleClass().add("outline");
		scorePl3.getStyleClass().add("outline");
		userNamePl3.setId("userNamePl3");
		scorePl3.setId("scorePlayer3");
		
		userNamePl4.getStyleClass().add("outline");
		scorePl4.getStyleClass().add("outline");
		userNamePl4.setId("userNamePl4");
		scorePl4.setId("scorePlayer4");
		
		
		// __________________________________________________________________


		chatArea.setText("Chat");

		
		
		txt1.setPromptText("Type here your message");
		HBox.setHgrow(txt1, Priority.ALWAYS);

		
		msgArea.setEditable(false);
		msgArea.setPrefHeight(600);
		msgArea.setPrefWidth(300);
		msgArea.setId("ChatArea");
		
		btnSend.setId("btnSend");
		
		chatbox1.getChildren().add(btnSend);
		chatbox1.getChildren().add(txt1);

		chatbox2.getChildren().add(lblchat);
		chatbox2.getChildren().add(msgArea);

		chatbox3.getChildren().add(chatbox2);
		chatbox3.getChildren().add(chatbox1);

		chatArea.setContent(chatbox3);

		//-----------------------------------------------------------------------

		HBox controlBox = new HBox();
		controlBox.getChildren().addAll(btnTrumpf);
		btnTrumpf.setTooltip(new Tooltip ("Select the Trumpf suit for this round!"));
		controlBox.setAlignment(Pos.CENTER);


		//----------------------------------------------------------------------------------------------------------
		//Table Leaderboard


		lblLeaderboardBox.getChildren().add(lblLeaderboard);
		lblLeaderboardBox.setAlignment(Pos.CENTER);
		lblLeaderboardBox.setPadding(new Insets(20,0,0,0));
		lblLeaderboard.setId("Leaderboard");
		


		lblRound.setId("lblRound");
		lblStiche.setId("lblStiche");
		lblScore.setId("lblScore");
		rssBox.getChildren().addAll(lblRound, lblStiche, lblScore);
		rssBox.setAlignment(Pos.CENTER);
		rssBox.setPadding(new Insets(0,20,0,0));
		rssBox.setSpacing(12);


		team1Box.getChildren().addAll(lblTeam1, lblSticheT1, lblScoreT1);
		lblTeam1.setId("lblTeam1");
		lblSticheT1.setId("lblSticheT1");
		lblScoreT1.setId("lblScoreT1");
		team1Box.setAlignment(Pos.CENTER);
		team1Box.setSpacing(12);


		team2Box.getChildren().addAll(lblTeam2, lblSticheT2, lblScoreT2);
		lblTeam2.setId("lblTeam2");
		lblSticheT2.setId("lblSticheT2");
		lblScoreT2.setId("lblScoreT2");
		team2Box.setAlignment(Pos.CENTER);
		team2Box.setSpacing(12);


		lbBox.getChildren().addAll(lblLeaderboardBox, rssBox, team1Box, team2Box);
		lbBox.setSpacing(40);

		plTeam1Box.getChildren().addAll(lblTeam1Pl, lblPl1, lblPl3);
		lblTeam1Pl.setId("lblTeam1Pl");
		lblPl1.setId("lblPl1");
		lblPl3.setId("lblPl3");
		plTeam1Box.setSpacing(7);
		plTeam2Box.getChildren().addAll(lblTeam2Pl, lblPl2, lblPl4);
		lblTeam2Pl.setId("lblTeam2Pl");
		lblPl2.setId("lblPl2");
		lblPl4.setId("lblPl4");
		plTeam2Box.setSpacing(7);


		team1PlBox.getChildren().add(plTeam1Box);
		team1PlBox.setAlignment(Pos. CENTER);
		team2PlBox.getChildren().add(plTeam2Box);
		team2PlBox.setAlignment(Pos. CENTER);

		teamBox.getChildren().addAll(team1PlBox,team2PlBox);
		teamBox.setSpacing(15);

		VBox mpLeftBox = new VBox();
		mpLeftBox.setMinWidth(300);
		mpLeftBox.setMaxHeight(700);
		mpLeftBox.setId("mpLeftBox");

		mpLeftBox.getChildren().addAll(lbBox, teamBox);
		mpLeftBox.setSpacing(90);




		//----------------------------------------------------------------------------------------------------------

			Button btnCard = new CardView();
			playedCardPl1.getChildren().add(btnCard);
			btnCard.getStyleClass().add("btnCard");
			playedCardPl1.setAlignment(Pos.CENTER);
			playedCardPl1.setPadding(new Insets(0, 0, 30, 0));
			Button btnCard2 = new CardView();

			playedCardPl2.getChildren().add(btnCard2);
			btnCard.getStyleClass().add("btnCard");
			playedCardPl2.setAlignment(Pos.CENTER);
			playedCardPl2.setPadding(new Insets(0, 0, 0, 30));
			Button btnCard3 = new CardView();

			playedCardPl3.getChildren().add(btnCard3);
			btnCard.getStyleClass().add("btnCard");
			playedCardPl3.setAlignment(Pos.CENTER);
			playedCardPl3.setPadding(new Insets(30, 0, 0, 0));
			Button btnCard4 = new CardView();

			playedCardPl4.getChildren().add(btnCard4);
			btnCard.getStyleClass().add("btnCard");
			playedCardPl4.setAlignment(Pos.CENTER);
			playedCardPl4.setPadding(new Insets(0, 30, 0, 0));




		//-----------------------------------------------------------------------------------------------------


		BorderPane outerPane = new BorderPane();
		outerPane.setVisible(true);
		BorderPane middlePane = new BorderPane();
		middlePane.setVisible(true);
		BorderPane innerPane = new BorderPane();
		innerPane.setVisible(true);

		middlePane.setBottom(player1BottomBox);
		middlePane.setTop(player3Box);
		middlePane.setLeft(player2Info);
		middlePane.setRight(player4Info);
		middlePane.setCenter(innerPane);

		middlePane.setId("middlePane");

		outerPane.setCenter(middlePane);
		outerPane.setTop(meba);
		outerPane.setRight(chatArea);
		outerPane.setLeft(mpLeftBox);
		outerPane.setBottom(controlBox);

		innerPane.setBottom(playedCardPl1);
		innerPane.setLeft(playedCardPl2);
		innerPane.setTop(playedCardPl3);
		innerPane.setRight(playedCardPl4);
		innerPane.setCenter(lblTrumpf);

		scene = new Scene(outerPane);
		scene.getStylesheets().add(getClass().getResource("Jass.css").toExternalForm());



		btnSend.setOnAction((event -> {
			clientController.sendMessage(txt1.getText());
		}));

		player1Btn.setOnAction(e9 -> {
			System.out.println("Turn finished, it is now player 2 turn");

		});
		
		txt1.setOnKeyPressed(e11 -> {
			if(e11.getCode() == KeyCode.ENTER) {
				clientController.sendMessage(txt1.getText());
			}
		});



		//Play the first Card into the first fieldbutton
			getHandButton(0).setOnAction(e10 -> {
				if(pp.getCardsHolder(0)!=null) {
					clientController.sendCardPlayed(pp.getCardsHolder(0).toString(), finalGamelobby, clientController.getFirstPlayer());
					clientController.waiterino(200);
					if(clientController.readLastMessage("Result|false")==true){
						
					}



				clientController.waiterino(250);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						for (int x = 0; x < clientController.getSizeCardsPlayed(); x++) {
							if (pp.getCardsHolder(0).toString().equals(clientController.getCardsPlayed(x).toString())) {
								getHandButton(0).setGraphic(null);
								pp.setCardsHolderNull(0);

							}
						}
					}
				});
				}
			});
		getHandButton(1).setOnAction(e11 -> {

			if(pp.getCardsHolder(1)!=null) {
				clientController.sendCardPlayed(pp.getCardsHolder(1).toString(), finalGamelobby, clientController.getFirstPlayer());

			clientController.waiterino(250);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
			for(int x = 0;x<clientController.getSizeCardsPlayed();x++){
				if(pp.getCardsHolder(1).toString().equals(clientController.getCardsPlayed(x).toString())){
					getHandButton(1).setGraphic(null);
					pp.setCardsHolderNull(1);
				}
			}
				}
			});
			}

		});
		getHandButton(2).setOnAction(e12 -> {

			if(pp.getCardsHolder(2)!=null) {
				clientController.sendCardPlayed(pp.getCardsHolder(2).toString(), finalGamelobby, clientController.getFirstPlayer());

			clientController.waiterino(250);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
			for(int x = 0;x<clientController.getSizeCardsPlayed();x++){
				if(pp.getCardsHolder(2).toString().equals(clientController.getCardsPlayed(x).toString())){
					getHandButton(2).setGraphic(null);
					pp.setCardsHolderNull(2);
				}
			}
				}
			});
			}
		});
		getHandButton(3).setOnAction(e13 -> {

			if(pp.getCardsHolder(3)!=null) {
				clientController.sendCardPlayed(pp.getCardsHolder(3).toString(), finalGamelobby, clientController.getFirstPlayer());

			clientController.waiterino(250);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
			for(int x = 0;x<clientController.getSizeCardsPlayed();x++){
				if(pp.getCardsHolder(3).toString().equals(clientController.getCardsPlayed(x).toString())){
					getHandButton(3).setGraphic(null);
					pp.setCardsHolderNull(3);
				}
			}
				}
			});
			}
		});
		getHandButton(4).setOnAction(e14 -> {

			if(pp.getCardsHolder(4)!=null) {
				clientController.sendCardPlayed(pp.getCardsHolder(4).toString(), finalGamelobby, clientController.getFirstPlayer());

			clientController.waiterino(250);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
			for(int x = 0;x<clientController.getSizeCardsPlayed();x++){
				if(pp.getCardsHolder(4).toString().equals(clientController.getCardsPlayed(x).toString())){
					getHandButton(4).setGraphic(null);
					pp.setCardsHolderNull(4);
				}
			}
				}
			});
			}
		});
		getHandButton(5).setOnAction(e15 -> {

			if(pp.getCardsHolder(5)!=null) {
				clientController.sendCardPlayed(pp.getCardsHolder(5).toString(), finalGamelobby, clientController.getFirstPlayer());

			clientController.waiterino(250);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
			for(int x = 0;x<clientController.getSizeCardsPlayed();x++){
				if(pp.getCardsHolder(5).toString().equals(clientController.getCardsPlayed(x).toString())){
					getHandButton(5).setGraphic(null);
					pp.setCardsHolderNull(5);
				}
			}
				}
			});
			}
		});
		getHandButton(6).setOnAction(e16 -> {

			if(pp.getCardsHolder(6)!=null) {
				clientController.sendCardPlayed(pp.getCardsHolder(6).toString(), finalGamelobby, clientController.getFirstPlayer());

			clientController.waiterino(250);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
			for(int x = 0;x<clientController.getSizeCardsPlayed();x++){
				if(pp.getCardsHolder(6).toString().equals(clientController.getCardsPlayed(x).toString())){
					getHandButton(6).setGraphic(null);
					pp.setCardsHolderNull(6);
				}
			}
				}
			});
			}
		});
		getHandButton(7).setOnAction(e17 -> {

			if(pp.getCardsHolder(7)!=null) {
				clientController.sendCardPlayed(pp.getCardsHolder(7).toString(), finalGamelobby, clientController.getFirstPlayer());

			clientController.waiterino(250);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
			for(int x = 0;x<clientController.getSizeCardsPlayed();x++){
				if(pp.getCardsHolder(7).toString().equals(clientController.getCardsPlayed(x).toString())){
					getHandButton(7).setGraphic(null);
					pp.setCardsHolderNull(7);
				}
			}
				}
			});
			}
		});
		getHandButton(8).setOnAction(e18 -> {

			if(pp.getCardsHolder(8)!=null) {
				clientController.sendCardPlayed(pp.getCardsHolder(8).toString(), finalGamelobby, clientController.getFirstPlayer());

			clientController.waiterino(250);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
			for(int x = 0;x<clientController.getSizeCardsPlayed();x++){
				if(pp.getCardsHolder(8).toString().equals(clientController.getCardsPlayed(x).toString())){
					getHandButton(8).setGraphic(null);
					pp.setCardsHolderNull(8);
				}
			}
				}
			});
			}
		});

		getBtnTrumpf().setOnAction(e ->{
			new ClientTrumpfView(clientController);

		});








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

         clientController.logoutUser(clientController.getUsername());
         clientController.waiterino(250);
         clientController.logout();
         this.clientController.getViewManager().primaryStage.setMaximized(false);
         this.clientController.getViewManager().primaryStage.setScene(LoginView.getScene());
     });

     pause.play();

     });




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



public void setGamelobby(String gamelobby){
		this.finalGamelobby=gamelobby;
}


	public Button getBtnTrumpf() {
		return btnTrumpf;
	}



	public Label getUserNamePl1(){
		return userNamePl1;
	}
	public Label getUserNamePl2(){
		return userNamePl2;
	}
	public Label getUserNamePl3(){
		return userNamePl3;
	}
	public Label getUserNamePl4(){
		return userNamePl4;
	}

	public Label getLblTrumpf(){
		return lblTrumpf;
	}



	public Label getLblSticheT1() {
		return lblSticheT1;
	}



	public Label getLblScoreT1() {
		return lblScoreT1;
	}



	public Label getLblSticheT2() {
		return lblSticheT2;
	}


	public Label getLblScoreT2() {
		return lblScoreT2;
	}


	public Label getLblPl1() {
		return lblPl1;
	}



	public Label getLblPl3() {
		return lblPl3;
	}



	public Label getLblPl2() {
		return lblPl2;
	}



	public Label getLblPl4() {
		return lblPl4;
	}

	public Label getLblRound() {
		return lblRound;
	}
}


