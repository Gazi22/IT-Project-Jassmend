package client_v0;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenuView {

	private static Scene scene;
	private ClientController clientController;
	private GameView view;
	public LoginView logView;
	private ClientModel clientmodel;
	private Slider volumeSlider;
	private Media musicFile;
	private MediaPlayer musicPlayer;
	private MediaView mediaView;
	final ProgressBar pb;
	
	
	Label lblUserName = new Label ("Welcome KEK!");
	Button showGameLobbyScreen = new Button("Show Gamelobbies");
	HBox hBoxButton = new HBox();
	HBox hBoxSlider = new HBox();
	Button showLobby = new Button("Show Lobby");
	Button playMusic = new Button("Play Music");
	Button pauseMusic = new Button("Pause Music");
	Button stopMusic = new Button("Stop Music");
	Insets insets = new Insets(5, 5, 5, 5);
	GridPane gridMainMenuView;
	public String finalGamelobby;
	StackPane volumePane;

	
	public MainMenuView (ClientController clientController, GameView view) {
		
		this.clientController = clientController;
		this.view = view;
		
		double sliderWidth = 400;
		double sliderHeight = 75;
		
		pb = new ProgressBar(0);
		pb.setMinWidth(sliderWidth);
	    pb.setMinHeight(sliderHeight);
	
	    hBoxSlider.setSpacing(5);
	    hBoxSlider.setAlignment(Pos.CENTER);

		volumeSlider = new Slider();
		volumeSlider.setMin(0);
		volumeSlider.setMax(100);
		volumeSlider.setValue(50);
		volumeSlider.setMinWidth(sliderWidth);
		volumeSlider.setMinHeight(sliderHeight);
		volumeSlider.setShowTickLabels(true);
		volumeSlider.setShowTickMarks(true);
		volumeSlider.setMajorTickUnit(50);
		volumeSlider.setMinorTickCount(5);
		volumeSlider.setBlockIncrement(10);
		
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
                pb.setProgress(new_val.doubleValue() / 100);
              
                
            }
        });
		
		volumePane = new StackPane();
       
		hBoxButton.getChildren().addAll(playMusic, pauseMusic, stopMusic);
		volumePane.getChildren().addAll(pb, volumeSlider);
		hBoxSlider.getChildren().addAll(volumePane);
		
		gridMainMenuView = new GridPane();
		gridMainMenuView.setAlignment(Pos.CENTER);
		gridMainMenuView.setHgap(10);
		gridMainMenuView.setVgap(10);
		gridMainMenuView.setPadding(new Insets(25, 25, 25, 25));
		gridMainMenuView.add(lblUserName, 0, 4);
		gridMainMenuView.add(showLobby, 0, 5);
		gridMainMenuView.add(showGameLobbyScreen, 0, 6);
		gridMainMenuView.add(hBoxButton, 0, 7);
		gridMainMenuView.add(hBoxSlider, 0, 8);
		gridMainMenuView.setMinSize(400, 400);
		lblUserName.getStyleClass().add("outline");
		
		
		/*
		 * Loading Screen Animation = https://stackoverflow.com/questions/45326525/how-to-show-a-loading-animation-in-javafx-application
		new Thread(() -> {
		    Platform.runLater(()-> messageField.getStyleClass().add("smallLoading"));

		    submitImpl();

		    Platform.runLater(()-> messageField.getStyleClass().remove("smallLoading"));
		}).start();
		
		
		String path = new File("src/client_v0/Halo 2 Theme song.wav").getAbsolutePath();
		musicFile = new Media(new File(path).toURI().toString());
		musicPlayer = new MediaPlayer(musicFile);
		mediaView.setMediaPlayer(musicPlayer);
		DoubleProperty width = mediaView.fitWidthProperty();
		DoubleProperty height = mediaView.fitHeightProperty();
		width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
		hieght.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
		volumeSlider.setValue(musicPlayer.getVolume() * 100);
		volumeSlider.valueProperty().addListener(New InvalidationListener());
		musicPlayer.setVolume(volumeSlider.getValue() / 100);
		
		musicFile = new Media("Halo 2 Theme.mp3");
		mediaPlayer = new MediaPlayer(musicFile);
		mediaPlayer.setVolume(0.1);
		mediaPlayer.setAutoPlay(true);
		*/
		

		
		scene = new Scene(gridMainMenuView, 400, 400);
		scene.getStylesheets().add(getClass().getResource("MainMenuJass.css").toExternalForm());
		
		showLobby.setOnAction(e ->{

			this.clientController.getViewManager().primaryStage.setTitle("Jassmend");
			this.clientController.getViewManager().primaryStage.setScene(GameView.getScene());
			this.clientController.getViewManager().primaryStage.setMaximized(true);
		});
		
		
		showGameLobbyScreen.setOnAction(e2 -> {
			showgamelobbyScreen();
			
		});
			/*
		});
		playMusic.setOnAction(e3 -> {
			musicPlayer.play();
		});
		pauseMusic.setOnAction(e4 -> {
			musicPlayer.pause();
		});
		stopMusic.setOnAction(e5 -> {
			musicPlayer.stop();
		});

		*/
	
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
	            int lastMessageIndex = view.msgArea.getText().split("\n").length-1;
	            String [] lastMessage = view.msgArea.getText().split("\n")[lastMessageIndex].split("\\|");
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
	                view.setGamelobby(finalGamelobby);
	                pause2.setOnFinished(e3 -> {
	                    int lastMessageIndex2 = view.msgArea.getText().split("\n").length-1;
	                    String lastMessage2 = view.msgArea.getText().split("\n")[lastMessageIndex2];

	                    if (lastMessage2.startsWith("PlayerIDs")){
	                        clientController.joinSuccessfull(finalGamelobby);
							clientController.joinedGamelobbyMode();

							for (int x=0; x < view.playerIDs.length; x++) {
								if (clientController.getPlayerIDs(x)!=null){
									view.playerIDs[x]= clientController.getPlayerIDs(x);
									System.out.println("Arraypostition"+ x+" "+view.playerIDs[x]);
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
	    			
	                clientController.showAlert("New gamelobby","The gamelobby " + newGamelobby + " has been created");
				showgamelobbyScreen();
	                            
	    		});



	        VBox vBox = new VBox(listView, btnJoinGamelobby,btnCreateGamelobby);
	        vBox.setStyle("-fx-background-color: BEIGE;");
	        Scene scene = new Scene(vBox, 250, 150);
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
