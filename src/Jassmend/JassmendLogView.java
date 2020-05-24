package Jassmend;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class JassmendLogView {
	private JasmendController jasmendController;
	static Scene scene;
	public TextArea areaMessages = new TextArea();
	GridPane gridLogView = new GridPane();



	
	public JassmendLogView(JasmendController jasmendController) {
		areaMessages.setPrefHeight(450);
		areaMessages.setEditable(false);
		areaMessages.setText("");
		this.jasmendController = jasmendController;

		HBox logBox = new HBox(areaMessages);

		gridLogView.add(logBox,0,0);

		scene = new Scene(gridLogView, 700, 275);






	}

	public static Scene getScene(){

		return scene;
	}

}
