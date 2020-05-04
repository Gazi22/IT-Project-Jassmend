package jassmendView;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea; 
import javafx.scene.control.TextField; 
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import static jassmendPackage.Player.HAND_SIZE;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import jassmendPackage.Player;
import jassmendPackage.Card;
import jassmendPackage.CardView;

import jassmendMain.JassmendMain;
import jassmendModel.JassmendModel;

public class JassmendGameView {

	private Player player1;
	
	public Scene gameScene;
	private JassmendModel model;
	
	public Stage primaryStage;
	//private Model 
	Insets insets = new Insets(10);
	// Player areas

	// Player bottom
	Label userNamePl1 = new Label("Player 1");
	Label scorePl1 = new Label("Score:");
	VBox player1Info = new VBox();
	HBox player1Cards = new HBox();

	// Player top
	Label userNamePl2 = new Label("Player 2");
	Label scorePl2 = new Label("Score:");
	VBox player2Info = new VBox();
	HBox player2Cards = new HBox();

	// Player left
	Label userNamePl3 = new Label("Player 3");
	Label scorePl3 = new Label("Score:");
	VBox player3Info = new VBox();
	HBox player3Cards = new HBox();

	// Player bottom
	Label userNamePl4 = new Label("Player 4");
	Label scorePl4 = new Label("Score:");
	VBox player4Info = new VBox();
	HBox player4Cards = new HBox();

	// Menu Bar
    MenuBar meba = new MenuBar();
    Menu optionsMenu = new Menu("Options");
    Menu helpMenu = new Menu("Help");
    MenuItem resumeItem = new MenuItem("Quit Game");
    MenuItem rulesItem = new MenuItem("How to play?");
	
	// Chat  
	TextField txt1 = new TextField (); 
	Button txtSend = new Button ("Send"); 
	TextArea msgArea = new TextArea (); 
	Label lblchat = new Label("Chat");
	HBox chatbox1 = new HBox (); 
	VBox chatbox2 = new VBox (); 
	VBox chatbox3 = new VBox (); 
	
	
	/** TEST */
    public static ArrayList<Button> cardButtons = new ArrayList<>();
    private boolean displayed = false;
	
	
    public JassmendGameView(Stage primaryStage, JassmendModel model) {

		this.primaryStage = primaryStage;
		this.model = model;
		
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

		
		
		player1Info.getChildren().add(player1Cards);
		player1Info.getChildren().add(userNamePl1);
		player1Info.getChildren().add(scorePl1);

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
		
		msgArea.setPrefHeight(600);
		msgArea.setPrefWidth(300);
		 
		chatbox1.getChildren().add(txtSend); 
		chatbox1.getChildren().add(txt1); 
		 
		chatbox2.getChildren().add(lblchat);
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

		gameScene = new Scene(outerPane);
		primaryStage.setMinHeight(600);
		primaryStage.setMinWidth(800);
		primaryStage.setScene(gameScene);
		primaryStage.setTitle("Jassmend");
		primaryStage.show();
		
		
		for (int i = 0; i < 9; i++) {
            Button btnCard = new CardView();
            player1Cards.getChildren().add(btnCard);
            player1Cards.setSpacing(2);}
		}
            
            
       public void addCardsToHand() {
       	for (int i = 0; i < Player.HAND_SIZE; i++) {
       		Card card = null;
       		if (player1.getCards().size() > i) card = player1.getCards().get(i);
       		CardView cl = (CardView) player1Cards.getChildren().get(i);
       		cl.setCard(card);
		
		
		
		
		
		
		
		

		// Disallow to resize window due to the images
		//primaryStage.setResizable(false);
	}

	
    }
}

