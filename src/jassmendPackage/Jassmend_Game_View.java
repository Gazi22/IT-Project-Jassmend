package jassmendPackage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

public class Jassmend_Game_View {
	
	protected static Stage stage;
	//private Model 
	
	Insets insets = new Insets(10);
	//Player areas
	
	//Player bottom
	Label userNamePl1 = new Label("Player 1");
	Label scorePl1 = new Label("Score:");
	VBox player1Info = new VBox();
	HBox player1Cards = new HBox();
	
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
	
	
	//Hallo
	
	
	
	public Jassmend_Game_View (Stage stage) {
		
		this.stage = stage;
		
	OptionsMenu.getItems().addAll(ResumeItem);
	HelpMenu.getItems().addAll(RulesItem);
		
		
	meba.getMenus().addAll(OptionsMenu, HelpMenu);	
		
	
	player1Info.getChildren().add(userNamePl1);
	player1Info.getChildren().add(scorePl1);
	
	HBox player1Box = new HBox(player1Info);
	player1Box.setAlignment(Pos.CENTER);
	
	player2Info.getChildren().add(userNamePl2);
	player2Info.getChildren().add(scorePl2);
	
	
	player3Info.getChildren().add(userNamePl3);
	player3Info.getChildren().add(scorePl3);
	
	
	player4Info.getChildren().add(userNamePl4);
	player4Info.getChildren().add(scorePl4);
	
	
	
	BorderPane outerPane = new BorderPane();
	
	BorderPane middlePane = new BorderPane();
	
	BorderPane innerPane = new BorderPane();
	
	middlePane.setBottom(player1Box);
	middlePane.setTop(player3Info);
	middlePane.setLeft(player2Info);
	middlePane.setRight(player4Info);
	middlePane.setCenter(innerPane);
	
	outerPane.setCenter(middlePane);
	outerPane.setTop(meba);
	
		
	Scene GameScene = new Scene(outerPane);
	stage.setScene(GameScene);
	stage.setTitle("Jassmend");
	stage.show();
	
	
	}
	
	protected static void start() {
		
		stage.show();
	}
	
	

}
