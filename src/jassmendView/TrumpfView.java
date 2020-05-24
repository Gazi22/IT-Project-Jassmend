package jassmendView;

import java.awt.Checkbox;


import jassmendController.JassmendController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class TrumpfView {
	
	private Stage trumpfWindow;
	private Scene secondScene;
	Label lblEcke = new Label();
	Label lblSchaufel = new Label();
	Label lblHerz = new Label();
	Label lblKreuz = new Label();
	Button btnEcke = new Button("Ecke");
	Button btnSchaufel = new Button("Schaufel");;
	Button btnHerz = new Button("Herz");
	Button btnKreuz = new Button("Kreuz");
    Label lblspace = new Label("__");
	
	
	
	public TrumpfView() {
		
		
		Image image1 = new Image(getClass().getResourceAsStream("/trumpf/images/Ecke_Jass_trumpf.png"));
	    lblEcke.setGraphic(new ImageView(image1));	
	    Image image2 = new Image(getClass().getResourceAsStream("/trumpf/images/Herz_Jass_trumpf.png"));
	    lblHerz.setGraphic(new ImageView(image2));	
	    Image image3 = new Image(getClass().getResourceAsStream("/trumpf/images/Kreuz_Jass_trumpf.png"));
	    lblKreuz.setGraphic(new ImageView(image3));	
	    Image image4 = new Image(getClass().getResourceAsStream("/trumpf/images/Schaufel_Jass_trumpf.png"));
	    lblSchaufel.setGraphic(new ImageView(image4));	
		
		HBox trumpfBox = new HBox();
		trumpfBox.setSpacing(5);
		trumpfBox.getChildren().addAll(lblEcke, lblHerz, lblKreuz, lblSchaufel);
		
		
		
		
		HBox slcBox = new HBox();
		slcBox.getChildren().addAll(btnEcke, btnHerz,  btnKreuz, btnSchaufel);
	
		
		btnEcke.setId("btnEcke");
		btnSchaufel.setId("btnSchaufel");
		btnHerz.setId("btnHerz");
		btnKreuz.setId("btnKreuz");
	

	
	
	GridPane trumpfLayout = new GridPane();
	
	trumpfLayout.add(trumpfBox, 0, 0);
	trumpfLayout.add(slcBox, 0, 1);
	
	trumpfBox.setAlignment(Pos.CENTER);
	//trumpfLayout.setCenter(trumpfBox);
	slcBox.setAlignment(Pos.CENTER);
	//trumpfLayout.setBottom(slcBox);
	
	
	
	
	
	
	
	
	secondScene = new Scene(trumpfLayout, 600, 450);
	secondScene.getStylesheets().add(getClass().getResource("trumpfView.css").toExternalForm());
	trumpfWindow = new Stage();
	trumpfWindow.setTitle("Select the Trumpf suit");


	trumpfWindow.setScene(secondScene);
	


	
	
	

}
public Scene getSceneTrumpfView () {
		
		return secondScene;
		
	}
	
}