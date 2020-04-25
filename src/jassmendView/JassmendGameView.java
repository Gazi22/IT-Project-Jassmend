package jassmendView;

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
import jassmendModel.JassmendModel;

public class JassmendGameView {
	
	public Scene loginScene, gameScene;
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
	HBox chatbox1 = new HBox (); 
	VBox chatbox2 = new VBox (); 
	VBox chatbox3 = new VBox (); 
	
	public JassmendGameView(Stage primaryStage, JassmendModel model) {

		this.primaryStage = primaryStage;
		this.model = model;
		
		optionsMenu.getItems().addAll(resumeItem);
		helpMenu.getItems().addAll(rulesItem);

		meba.getMenus().addAll(optionsMenu, helpMenu);

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

		// Disallow to resize window due to the images
		//primaryStage.setResizable(false);
	}

	

}
