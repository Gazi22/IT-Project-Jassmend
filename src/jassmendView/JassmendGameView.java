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



import jassmendMain.JassmendMain;
import jassmendModel.Card;
import jassmendModel.JassmendModel;
import jassmendModel.Player;

public class JassmendGameView {

	public Scene gameScene;
	private JassmendModel model;
	
	public Stage primaryStage;
	//private Model 
	Insets insets = new Insets(10);
	
	
	Button btnDeal = new Button("Deal");
	// Player areas

	// Player bottom
	Label userNamePl1 = new Label("Player 1");
	Label scorePl1 = new Label("Score:");
	HBox player1Box = new HBox();
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
    TitledPane chatArea = new TitledPane();
	TextField txt1 = new TextField (); 
	Button btnSend = new Button ("Send"); 
	TextArea msgArea = new TextArea (); 
	Label lblchat = new Label("Chat");
	HBox chatbox1 = new HBox (); 
	VBox chatbox2 = new VBox (); 
	VBox chatbox3 = new VBox (); 
	
	// Play Area
	HBox playedCardPl1 = new HBox();
	VBox playedCardPl2 = new VBox();
	HBox playedCardPl3 = new HBox();
	VBox playedCardPl4 = new VBox();
	
	
	
	

	
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
		
		//____________________________________________________________________
		
		HBox controlBox = new HBox();
		controlBox.getChildren().addAll(btnDeal);
		controlBox.setAlignment(Pos.CENTER);
		
		
		//_____________________________________________________________________

		
		//Table Leaderboard
		
        TableView table = new TableView();
		
		table.setEditable(false);
		
		
		TableColumn playerNameCol = new TableColumn("Player");
		TableColumn scoreCol = new TableColumn("Score");
		
		playerNameCol.setCellValueFactory(c -> Player.getPlayerName());
		
		
		table.getColumns().addAll(playerNameCol, scoreCol);
		
		//______________________________________________________________________
		
		
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
        
        
		middlePane.setBottom(player1Box);
		middlePane.setTop(player3Box);
		
		middlePane.setLeft(player2Info);
		middlePane.setRight(player4Info);
		middlePane.setCenter(innerPane);

		outerPane.setCenter(middlePane);
		outerPane.setTop(meba);
		outerPane.setRight(chatArea); 
		outerPane.setBottom(controlBox);
		outerPane.setLeft(table);

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
	
	
	public PlayerPane getPlayerPane(int i) {
		return (PlayerPane) player1Box.getChildren().get(0);
	}
}


