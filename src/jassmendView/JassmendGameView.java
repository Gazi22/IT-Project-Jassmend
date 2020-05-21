package jassmendView;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea; 
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static jassmendModel.Player.HAND_SIZE;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import client_v0.CreateAccountView;
import jassmendMain.JassmendMain;
import jassmendModel.Card;
import jassmendModel.JassmendModel;
import jassmendModel.Player;

public class JassmendGameView {

	public Scene gameScene;
	public Stage TrumpfWindow;
	private JassmendModel model;
	
	public Stage primaryStage;
	//private Model 
	Insets insets = new Insets(10);
	
	Button btnTrumpf = new Button("Trumpf");
	Button btnDeal = new Button("Deal");
	// Player areas




	// Player bottom
	Label userNamePl1 = new Label("Player 1");
	HBox player1Box = new HBox();
	HBox player1Cards = new HBox();

	// Player top
	Label userNamePl2 = new Label("Player 2");
	VBox player2Info = new VBox();
	HBox player2Cards = new HBox();

	// Player left
	Label userNamePl3 = new Label("Player 3");
	VBox player3Info = new VBox();
	HBox player3Cards = new HBox();

	// Player bottom
	Label userNamePl4 = new Label("Player 4");
	VBox player4Info = new VBox();
	HBox player4Cards = new HBox();

	// Menu Bar
    MenuBar meba = new MenuBar();
    Menu optionsMenu = new Menu("Options");
    Menu helpMenu = new Menu("Help");
    MenuItem resumeItem = new MenuItem("Quit Game");
    MenuItem rulesItem = new MenuItem("How to play?");
	
	// Chat  
    TitledPane chatArea = new TitledPane();
	TextField txt1 = new TextField (); 
	Button btnSend = new Button ("Send"); 
	TextArea msgArea = new TextArea (); 
	Label lblchat = new Label("Chat with your fellow players");
	HBox chatbox1 = new HBox (); 
	VBox chatbox2 = new VBox (); 
	VBox chatbox3 = new VBox (); 
	
	// Leaderboard
	
	Label lblLeaderboard = new Label("Leaderboard");
	Label lblRound = new Label("Round:");
	Label lblTeam1 = new Label("Team1");
	Label lblPl1 = new Label("Player1");
	Label lblPl2 = new Label("Player2");
	Label lblSticheT1 = new Label("SticheT1");
	Label lblScoreT1 = new Label("ScoreT1");
	Label lblTeam2 = new Label("Team2");
	Label lblPl3 = new Label("Player3");
	Label lblPl4 = new Label("Player4");
	Label lblSticheT2 = new Label("SticheT2");
	Label lblScoreT2 = new Label("ScoreT2");
	Label lblStiche = new Label("Stiche ");
	Label lblScore = new Label("Score");
	
	
	HBox lblLeaderboardBox = new HBox();
	HBox rssBox = new HBox();
	HBox team1Box = new HBox();
	HBox pl1team1Box = new HBox();
	HBox pl2team1Box = new HBox();
	HBox team2Box = new HBox();
	HBox pl1team2Box = new HBox();
	HBox pl2team2Box = new HBox();
	VBox lbBox = new VBox();
	
	
	// Play Area
	HBox playedCardPl1 = new HBox();
	VBox playedCardPl2 = new VBox();
	HBox playedCardPl3 = new HBox();
	VBox playedCardPl4 = new VBox();
	
	
	
	// Label for displaying the trumpf suit
	
	Label trumpf = new Label();
	
	
	
	

	
	  public JassmendGameView(Stage primaryStage, JassmendModel model) {

		this.primaryStage = primaryStage;
		this.model = model;
		PlayerPane pp = new PlayerPane();
		pp.setPlayer(model.getPlayer(1));
		optionsMenu.getItems().addAll(resumeItem);
		helpMenu.getItems().addAll(rulesItem);
		

		
		// Hyperlink test helpMenu - Code From Reddit https://www.reddit.com/r/javahelp/comments/4bqcci/how_to_make_a_link_hyperlink_in_javafx/

		helpMenu.setOnAction( e -> {
			if(Desktop.isDesktopSupported())
				
			{
				try {
					Desktop.getDesktop().browse(new URI("https://www.swisslos.ch/de/jass/informationen/jass-regeln/jass-grundlagen.html"));
					
				}catch (IOException e1) {
					e1.printStackTrace();
					
				}catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
			
		meba.getMenus().addAll(optionsMenu, helpMenu);

		

		
		player1Box.getChildren().add(pp);
		player1Box.setAlignment(Pos.CENTER);

		player2Info.getChildren().add(userNamePl2);
	

		player2Info.setAlignment(Pos.CENTER);

		player3Info.getChildren().add(userNamePl3);
	

		HBox player3Box = new HBox(player3Info);
		player3Box.setAlignment(Pos.CENTER);

		player3Box.setVisible(true);

		player4Info.getChildren().add(userNamePl4);


		player4Info.setAlignment(Pos.CENTER);
		
		// __________________________________________________________________ 
		 
		
		chatArea.setText("Chat");
		
		btnSend.setId("btnSend");
		
		 
		txt1.setPromptText("Type here your message"); 
		HBox.setHgrow(txt1, Priority.ALWAYS); 
		
		msgArea.setPrefHeight(600);
		msgArea.setPrefWidth(300);
		msgArea.setId("ChatArea");

		
		chatbox1.getChildren().add(btnSend);
		chatbox1.getChildren().add(txt1);

		chatbox2.getChildren().add(lblchat);
		chatbox2.getChildren().add(msgArea);
		 
		chatbox3.getChildren().add(chatbox2); 
		chatbox3.getChildren().add(chatbox1); 
		
		chatArea.setContent(chatbox3);
		
		//____________________________________________________________________
		
		HBox controlBox = new HBox();
		controlBox.getChildren().addAll(btnDeal);
		controlBox.getChildren().addAll(btnTrumpf);	
		btnDeal.setTooltip(new Tooltip ("Start the game by getting your cards!"));
		btnTrumpf.setTooltip(new Tooltip ("Select the Trumpf suit for this round!"));
		controlBox.setAlignment(Pos.CENTER);
		
		
		
		//_____________________________________________________________________

		
		// Leaderboard
		
		
		lblLeaderboardBox.getChildren().add(lblLeaderboard);
		lblLeaderboardBox.setAlignment(Pos.CENTER);
		lblLeaderboard.setId("Leaderboard");
		
		rssBox.getChildren().addAll(lblRound, lblStiche, lblScore);
		rssBox.setAlignment(Pos.CENTER_LEFT);
		rssBox.setSpacing(15);
		
		team1Box.getChildren().addAll(lblTeam1, lblSticheT1, lblScoreT1);
		team1Box.setAlignment(Pos.CENTER_LEFT);
		team1Box.setSpacing(10);
		
		pl1team1Box.getChildren().add(lblPl1);
		pl2team1Box.getChildren().add(lblPl2);
		
		team2Box.getChildren().addAll(lblTeam2, lblSticheT2, lblScoreT2);
		team2Box.setAlignment(Pos.CENTER_LEFT);
		team2Box.setSpacing(10);
		
		pl1team2Box.getChildren().add(lblPl3);
		pl2team2Box.getChildren().add(lblPl4);
		
		lbBox.getChildren().addAll(lblLeaderboardBox, rssBox, team1Box,pl1team1Box, pl2team1Box, team2Box, pl1team2Box, pl2team2Box);
		lbBox.setSpacing(40);
		
		
        /**TableView table = new TableView();
		
		table.setEditable(false);
		
		//Runde
		//Team
		//Stiche
		//Score
		TableColumn roundCol = new TableColumn("Round");
		TableColumn teamCol = new TableColumn("Team");
		TableColumn stichCol = new TableColumn("Stiche");
		TableColumn scoreCol = new TableColumn("Score");
		
		
		
		table.getColumns().addAll(teamCol, roundCol, stichCol, scoreCol);
		*/
		//______________________________________________________________________
		
		
		
    		Button btnCard = new CardView();
    		playedCardPl1.getChildren().addAll(btnCard);
    		btnCard.getStyleClass().add("btnCard");
    		playedCardPl1.setAlignment(Pos.CENTER);
    		playedCardPl1.setHgrow(btnCard, Priority.ALWAYS);
    		
    		Button btnCard2 = new CardView();
    		playedCardPl2.getChildren().addAll(btnCard2);
    		btnCard.getStyleClass().add("btnCard");
    		playedCardPl2.setAlignment(Pos.CENTER);
    		playedCardPl2.setVgrow(btnCard2, Priority.ALWAYS);
    		
    		Button btnCard3 = new CardView();
    		playedCardPl3.getChildren().addAll(btnCard3);
    		btnCard.getStyleClass().add("btnCard");
    		playedCardPl3.setAlignment(Pos.CENTER);
    		playedCardPl3.setHgrow(btnCard3, Priority.ALWAYS);
    		
    		Button btnCard4 = new CardView();
    		playedCardPl4.getChildren().addAll(btnCard4);
    		btnCard.getStyleClass().add("btnCard");
    		playedCardPl4.setAlignment(Pos.CENTER);
    		playedCardPl4.setVgrow(btnCard4, Priority.ALWAYS);
    		
    		
    		
		
		
		
		//_________________________________________________________________________
		
		
		

		
		//_________________________________________________________________________
	
		
		BorderPane outerPane = new BorderPane();
        outerPane.setVisible(true);
        BorderPane middlePane = new BorderPane();
        middlePane.setVisible(true);
        BorderPane innerPane = new BorderPane();
        innerPane.setVisible(true);
        
        innerPane.setBottom(playedCardPl1);
        innerPane.setLeft(playedCardPl2);
        innerPane.setTop(playedCardPl3);
        innerPane.setRight(playedCardPl4);
        innerPane.setCenter(trumpf);
        
        
		middlePane.setBottom(player1Box);
		middlePane.setTop(player3Box);
		
		middlePane.setLeft(player2Info);
		middlePane.setRight(player4Info);
		middlePane.setCenter(innerPane);
		
		middlePane.setId("middlePane");

		outerPane.setCenter(middlePane);
		outerPane.setTop(meba);
		outerPane.setRight(chatArea); 
		outerPane.setBottom(controlBox);
		outerPane.setLeft(lbBox);

		
		gameScene = new Scene(outerPane);
		gameScene.getStylesheets().add(getClass().getResource("Jass.css").toExternalForm());
		primaryStage.setMinHeight(500);//500
		primaryStage.setMinWidth(1250);//1250
		primaryStage.setScene(gameScene);
		primaryStage.setTitle("Jassmend");
		primaryStage.setMaximized(true);
		primaryStage.show();
		
		
		
		
		
		
		

		  //System.out.println(player1Box.getChildren().get(1));
		
		// Disallow to resize window due to the images
		//primaryStage.setResizable(false);
	}

	public Button getbtnDeal() {
		
		return btnDeal;
	}
	
	public Button getBtnTrumpf() {
		return btnTrumpf;
	}


	
	
	public PlayerPane getPlayerPane(int i) {
		return (PlayerPane) player1Box.getChildren().get(0);
	}
}


