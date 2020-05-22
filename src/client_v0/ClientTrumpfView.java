package client_v0;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ClientTrumpfView {

	ClientController clientController;
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
	
	
	
	public ClientTrumpfView(ClientController clientController) {
		this.clientController = clientController;
		
		Image image1 = new Image(getClass().getResourceAsStream("/trumpf/images/Ecke_Jass_trumpf.jpg"));
	    lblEcke.setGraphic(new ImageView(image1));	
	    Image image2 = new Image(getClass().getResourceAsStream("/trumpf/images/Herz_Jass_trumpf.jpg"));
	    lblHerz.setGraphic(new ImageView(image2));	
	    Image image3 = new Image(getClass().getResourceAsStream("/trumpf/images/Kreuz_Jass_trumpf.jpg"));
	    lblKreuz.setGraphic(new ImageView(image3));	
	    Image image4 = new Image(getClass().getResourceAsStream("/trumpf/images/Schaufel_Jass_trumpf.jpg"));
	    lblSchaufel.setGraphic(new ImageView(image4));

		Image image5 = new Image(getClass().getResourceAsStream("/trumpf/images/Ecke.png"));
		Image image6 = new Image(getClass().getResourceAsStream("/trumpf/images/Herz.png"));
		Image image7 = new Image(getClass().getResourceAsStream("/trumpf/images/Kreuz.png"));
		Image image8 = new Image(getClass().getResourceAsStream("/trumpf/images/Schaufel.png"));




		HBox trumpfBox = new HBox();
		trumpfBox.setSpacing(5);
		trumpfBox.getChildren().addAll(lblEcke, lblHerz, lblKreuz, lblSchaufel);
		
		
		
		
		HBox slcBox = new HBox();
		slcBox.getChildren().addAll(btnEcke, btnHerz,  btnKreuz, btnSchaufel);
		slcBox.setPadding(new Insets(5, 7, 10, 14));
		slcBox.setSpacing(46);
		
		
	
	btnEcke.setOnAction(e1->{
		clientController.sendTrumpf("Ecke",clientController.getFinalGamelobby());
		clientController.getLblTrumpf().setGraphic(new ImageView(image5));
		});
	btnSchaufel.setOnAction(e1->{
		clientController.sendTrumpf("Schaufel",clientController.getFinalGamelobby());
		clientController.getLblTrumpf().setGraphic(new ImageView(image8));
		});
	btnKreuz.setOnAction(e1->{
		clientController.sendTrumpf("Kreuz",clientController.getFinalGamelobby());
		clientController.getLblTrumpf().setGraphic(new ImageView(image7));
		});
	btnHerz.setOnAction(e1->{
		clientController.sendTrumpf("Herz",clientController.getFinalGamelobby());
		clientController.getLblTrumpf().setGraphic(new ImageView(image6));
		});
	
	
	BorderPane trumpfLayout = new BorderPane();
	
	
	trumpfLayout.setCenter(trumpfBox);
	trumpfLayout.setBottom(slcBox);
	
	
	
	
	
	
	
	secondScene = new Scene(trumpfLayout);
	secondScene.getStylesheets().add(getClass().getResource("trumpfView.css").toExternalForm());
	trumpfWindow = new Stage();
	trumpfWindow.setTitle("Select the Trumpf suit");
	trumpfWindow.setMaxHeight(450);
	trumpfWindow.setMaxWidth(455);
	trumpfWindow.setResizable(false);
	trumpfWindow.setScene(secondScene);
	
	trumpfWindow.show();

	
	
	

}
public Scene getSceneTrumpfView () {
		
		return secondScene;
		
	}
	
}