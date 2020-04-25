package client_v0;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameView {
	  private static Scene scene;
	    private ClientController clientController;

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
	
	//Player bottom
	Label userNamePl4 = new Label("Player 4");
	Label scorePl4 = new Label("Score:");
	VBox player4Info = new VBox();
	HBox player4Cards = new HBox();
	
	
	// Menu Bar
	
	MenuBar meba = new MenuBar();
	
	Menu OptionsMenu = new Menu("Options");
	Menu HelpMenu = new Menu("Help");
	
	MenuItem ResumeItem = new MenuItem("Quit Game");
	
	MenuItem RulesItem = new MenuItem("How to play?");
	public Object areaMessages;
	
	
	//Hallo
	

	
	public GameView (ClientController clientController) {
		

		 this.clientController = clientController;
	   
	OptionsMenu.getItems().addAll(ResumeItem);
	HelpMenu.getItems().addAll(RulesItem);
		
		
	meba.getMenus().addAll(OptionsMenu, HelpMenu);	
		
	
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
	
		
	scene = new Scene(outerPane);


	 player1Btn.setOnAction(event -> {
     	System.out.println("Turn finished, it is now player 2 turn");
     	
     });;
	
	
	}
	

	

public static Scene getScene () {
		
		return scene;
		
	}



}
