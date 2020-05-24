package client_v0;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;



public class ChatView {
	private ClientController clientController;
	static Scene scene;
	public TextArea areaMessages = new TextArea();
	GridPane gridLogView = new GridPane();



	
	public ChatView(ClientController clientController) {
		areaMessages.setPrefHeight(450);
		areaMessages.setEditable(false);
		areaMessages.setText("");
		this.clientController = clientController;

		HBox logBox = new HBox(areaMessages);

		gridLogView.add(logBox,0,0);

		scene = new Scene(gridLogView, 700, 275);






	}

	public static Scene getScene(){

		return scene;
	}

}
