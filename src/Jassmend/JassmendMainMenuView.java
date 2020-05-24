package Jassmend;

import java.util.Arrays;
import java.util.Optional;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JassmendMainMenuView {

	// Author: Gazmend Shefiu
	private static Scene scene;
	private JasmendController jasmendController;
	private JasmendModel jasmendModel;
	private JassmendGameView view;
	private Slider volumeSlider = new Slider();
	private Media musicFile;
	private MediaPlayer musicPlayer;
	public JassmendLoginView logView;
	public String finalGamelobby;
	public String userName;
	
	// Author: Gazmend Shefiu
	Label lblUserName = new Label ();
	Button createGameLobby = new Button("Create Lobby");
	Button joinGameLobby = new Button("Join Lobby");
	HBox hBoxMusicButton = new HBox();
	HBox hBoxLobby = new HBox();
	HBox hBoxBoard = new HBox();
	HBox hBoxLogout = new HBox();
	Button showLobby = new Button("Show Jass Board");
	Button playMusic = new Button("Play Music");
	Button pauseMusic = new Button("Pause Music");
	Button stopMusic = new Button("Stop Music");
	Button logOut = new Button("Logout");
	Insets insets = new Insets(5, 5, 5, 5);
	GridPane gridMainMenuView = new GridPane();
	
	

	// Author: Gazmend Shefiu
	public JassmendMainMenuView(JasmendController jasmendController, JassmendGameView view, JassmendLoginView logView) {
		
		this.jasmendController = jasmendController;
		this.view = view;
		this.logView = logView;
		
		// Label Username || Author: Gazmend Shefiu
		
		lblUserName.getStyleClass().add("outline");
		
		
		double sliderWidth = 300;
		double sliderHeight = 75;
		
		musicFile = new Media(getClass().getResource("Halo 2 Theme song.wav").toString());
		musicPlayer = new MediaPlayer(musicFile);
		
		
		volumeSlider.setMin(0);
		volumeSlider.setMax(100);
		volumeSlider.setValue(20);
		volumeSlider.setMinWidth(sliderWidth);
		volumeSlider.setMinHeight(sliderHeight);
		volumeSlider.setShowTickLabels(true);
		volumeSlider.setShowTickMarks(true);
		volumeSlider.setMajorTickUnit(10);
		volumeSlider.setBlockIncrement(5);
		
		// Inspired By: Akatsuki Project || Author: Gazmend Shefiu
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                musicPlayer.setVolume(newValue.doubleValue() / 100);
            }
        });
		
		
		// Author: Gazmend Shefiu
		hBoxBoard.getChildren().add(showLobby);
				
		hBoxMusicButton.getChildren().addAll(playMusic, pauseMusic, stopMusic);
		hBoxMusicButton.setSpacing(10);
		
		hBoxLobby.getChildren().addAll(createGameLobby, joinGameLobby);
		hBoxLobby.setSpacing(10);
		
		hBoxLogout.getChildren().add(logOut);
		
		lblUserName.setAlignment(Pos.CENTER);
		hBoxBoard.setAlignment(Pos.CENTER);
		hBoxLobby.setAlignment(Pos.CENTER);
		hBoxMusicButton.setAlignment(Pos.CENTER);
		hBoxLogout.setAlignment(Pos.CENTER);
		
		showLobby.setMinWidth(50);
		hBoxLobby.setMinWidth(50);
		hBoxLogout.setMinWidth(50);
		
		gridMainMenuView = new GridPane();
		gridMainMenuView.setAlignment(Pos.CENTER);
		gridMainMenuView.setHgap(5);
		gridMainMenuView.setVgap(5);
		gridMainMenuView.add(lblUserName, 0, 1);
		gridMainMenuView.add(hBoxBoard, 0, 3);
		gridMainMenuView.add(hBoxLobby, 0, 5);
		gridMainMenuView.add(hBoxMusicButton, 0, 7);
		gridMainMenuView.add(volumeSlider, 0, 9);
		gridMainMenuView.add(hBoxLogout, 0, 11);
		gridMainMenuView.setMinSize(700, 400);

		scene = new Scene(gridMainMenuView, 700, 400);
		scene.getStylesheets().add(getClass().getResource("MainMenuJass.css").toExternalForm());
		
		// Author: Gazmend Shefiu
		showLobby.setOnAction(e ->{

			this.jasmendController.getViewManager().primaryStage.setTitle("Jassmend");
			this.jasmendController.getViewManager().primaryStage.setScene(JassmendGameView.getScene());
			this.jasmendController.getViewManager().primaryStage.setMaximized(true);
			this.jasmendController.getViewManager().primaryStage.setResizable(true);
			jasmendController.btnTrumpfTrue();
		});
		
		
		createGameLobby.setOnAction(e2 -> {
			createGameLobby();
			
		});
		
		joinGameLobby.setOnAction(e3 -> {
			joinGameLobby();
			
		});
		
		// Author: Florian Jäger
	     logOut.setOnAction(e -> {
	    	 jasmendController.getGamelobbyList();
	    	 PauseTransition pause = new PauseTransition(Duration.seconds(1));
	         pause.setOnFinished(e5 -> {

	        	 int lastMessageIndex = jasmendController.getAreaMessages().getText().split("\n").length-1;
	             String [] lastMessage = jasmendController.getAreaMessages().getText().split("\n")[lastMessageIndex].split("\\|");
	    		 String [] gameLobbyList = Arrays.copyOfRange(lastMessage, 2, lastMessage.length);
	             for (String str:gameLobbyList) {
	             jasmendController.leaveGamelobby(str);
	             }
			 });

			 pause.play();

	             jasmendController.logout();
	             jasmendController.waiterino(250);
	             jasmendController.logoutUser(jasmendController.getUsername());
	         this.jasmendController.getViewManager().primaryStage.setScene(JassmendLoginView.getScene());


	     });
			
		
		playMusic.setOnAction(e3 -> {
			musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			musicPlayer.play();
		});
		
		pauseMusic.setOnAction(e4 -> {
			musicPlayer.pause();
		});
		stopMusic.setOnAction(e5 -> {
			musicPlayer.stop();
		});

		
	
	}
	// Author: Florian Jäger & Gazmend Shefiu
	public void joinGameLobby() {
		//Based on http://tutorials.jenkov.com/javafx/listview.html
        Stage stage = new Stage();

        Button btnJoinGamelobby = new Button("Join selected gamelobby");
        btnJoinGamelobby.setId("btnJoinGamelobby");
     
        stage.setTitle("Gamelobby List");

        ListView listView = new ListView();

        jasmendController.getGamelobbyList();
        //Not the best way to do it but it does the trick
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            int lastMessageIndex = jasmendController.getAreaMessages().getText().split("\n").length-1;
            String [] lastMessage = jasmendController.getAreaMessages().getText().split("\n")[lastMessageIndex].split("\\|");
						String [] gameLobbyList = Arrays.copyOfRange(lastMessage, 2, lastMessage.length);
            for (String str:gameLobbyList) {
                listView.getItems().add(str);
            }
            btnJoinGamelobby.setOnAction(e2 -> {
                ObservableList selectedIndices = listView.getSelectionModel().getSelectedIndices();
                String gamelobby = "";
                for(Object o : selectedIndices){
                    gamelobby = (String)listView.getItems().get((int)o);
                    jasmendController.joinGamelobby(gamelobby);

                 
                    this.jasmendController.getViewManager().primaryStage.setScene(JassmendGameView.getScene());
                    this.jasmendController.getViewManager().primaryStage.setMaximized(true);
                    this.jasmendController.getViewManager().primaryStage.setResizable(true);
                    jasmendController.btnTrumpfTrue();
                    
                }
                //Don't do this at home kids !
                PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
                finalGamelobby = gamelobby;
                view.setGamelobby(finalGamelobby);
                pause2.setOnFinished(e3 -> {
                   if(jasmendController.readLastMessage("PlayerIDs")==true){
                        jasmendController.joinSuccessfull(finalGamelobby);
						jasmendController.joinedGamelobbyMode();

						for (int x=0; x < view.playerIDs.length; x++) {
							if (jasmendController.getPlayerIDs(x)!=null){
								view.playerIDs[x]= jasmendController.getPlayerIDs(x);
								System.out.println("Arraypostition"+ x+" "+view.playerIDs[x]);
							}
							else{
								System.out.println("Arraypostition"+ x+" "+"is empty");
							}

							}


                    }

                });
                pause2.play();

				stage.close();
				if (jasmendController.isFull()){
					jasmendController.gamelobbyIsFull(finalGamelobby,"GamelobbyIsFull");

				}
            });
        });
        pause.play();

        VBox vBox = new VBox(listView, btnJoinGamelobby);
        vBox.setStyle("-fx-background-color: BEIGE;");
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        Scene scene = new Scene(vBox, 275, 150);
        scene.getStylesheets().add(getClass().getResource("CreateJoinLobby.css").toExternalForm());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    
	}
	
	// Author: Florian Jäger & Gazmend Shefiu
	public void createGameLobby() {
		//Based on http://tutorials.jenkov.com/javafx/listview.html
		Stage stage = new Stage();
		
		Button btnCreateGamelobby = new Button("Create gamelobby:");
		stage.setTitle("Gamelobby List");
		
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
    	    	
    			jasmendController.createGamelobby(newGamelobby);
    	    	 stage.close();
    			
                jasmendController.showAlert("New gamelobby","The gamelobby " + newGamelobby + " has been created");
                joinGameLobby();
                            
    		});
		
	
		VBox vBox = new VBox(btnCreateGamelobby);
		vBox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(vBox, 275, 150);
		scene.getStylesheets().add(getClass().getResource("CreateJoinLobby.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
	    stage.show();
	}

	
	public static Scene getScene() {
		return scene;
	}


	public String getFinalGamelobby(){
		return finalGamelobby;
	}

	public Label getLblUserName(){
		return lblUserName;
	}
}
