package jassmendView;

import java.awt.Checkbox;

import jassmendController.JassmendController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
	
	
	
	public TrumpfView() {
		
		
		Image image1 = new Image(getClass().getResourceAsStream("/trumpf/images/Ecke_Jass_trumpf.jpg"));
	    lblEcke.setGraphic(new ImageView(image1));	
	    Image image2 = new Image(getClass().getResourceAsStream("/trumpf/images/Herz_Jass_trumpf.jpg"));
	    lblHerz.setGraphic(new ImageView(image2));	
	    Image image3 = new Image(getClass().getResourceAsStream("/trumpf/images/Kreuz_Jass_trumpf.jpg"));
	    lblKreuz.setGraphic(new ImageView(image3));	
	    Image image4 = new Image(getClass().getResourceAsStream("/trumpf/images/Schaufe_Jass_trumpf.jpg"));
	    lblSchaufel.setGraphic(new ImageView(image4));	
		
		HBox trumpfBox = new HBox();
		trumpfBox.setSpacing(10);
		trumpfBox.getChildren().addAll(lblEcke, lblHerz, lblKreuz, lblSchaufel);
		
		
		
		
		HBox slcBox = new HBox();
		slcBox.getChildren().addAll(btnEcke, btnHerz, btnKreuz, btnSchaufel);
		slcBox.setSpacing(90);
	
	
	
	
	BorderPane trumpfLayout = new BorderPane();
	
	
	trumpfLayout.setCenter(trumpfBox);
	trumpfLayout.setBottom(slcBox);
	
	
	
	
	
	
	
	secondScene = new Scene(trumpfLayout);
	trumpfWindow = new Stage();
	trumpfWindow.setTitle("Select the Trumpf suit");
	trumpfWindow.setMinHeight(100);
	trumpfWindow.setMinWidth(100);
	trumpfWindow.setScene(secondScene);
	
	trumpfWindow.show();

	
	
	

}
public Scene getSceneTrumpfView () {
		
		return secondScene;
		
	}
	
}