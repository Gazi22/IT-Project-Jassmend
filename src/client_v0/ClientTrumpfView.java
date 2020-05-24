package client_v0;

// Author: Davide Seabra
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
	Button btnEcke = new Button("E");
	Button btnSchaufel = new Button("S");;
	Button btnHerz = new Button("H");
	Button btnKreuz = new Button("K");
	Image image1 = new Image(getClass().getResourceAsStream("/trumpf/images/Ecke_Jass_trumpf.jpg"));
	Image image2 = new Image(getClass().getResourceAsStream("/trumpf/images/Herz_Jass_trumpf.jpg"));
	Image image3 = new Image(getClass().getResourceAsStream("/trumpf/images/Kreuz_Jass_trumpf.jpg"));
	Image image4 = new Image(getClass().getResourceAsStream("/trumpf/images/Schaufel_Jass_trumpf.jpg"));



	public ClientTrumpfView(ClientController clientController) {
		this.clientController = clientController;
		

	    lblEcke.setGraphic(new ImageView(image1));	

	    lblHerz.setGraphic(new ImageView(image2));	

	    lblKreuz.setGraphic(new ImageView(image3));	

	    lblSchaufel.setGraphic(new ImageView(image4));






		HBox trumpfBox = new HBox();
		trumpfBox.setSpacing(5);
		trumpfBox.getChildren().addAll(lblEcke, lblHerz, lblKreuz, lblSchaufel);
		
		
		
		
		HBox slcBox = new HBox();
		slcBox.getChildren().addAll(btnEcke, btnHerz,  btnKreuz, btnSchaufel);
		slcBox.setPadding(new Insets(5, 10, 10, 33));
		slcBox.setSpacing(74);
		
		btnEcke.setId("btnEcke");
		btnSchaufel.setId("btnSchaufel");
		btnHerz.setId("btnHerz");
		btnKreuz.setId("btnKreuz");
		
		
	
	btnEcke.setOnAction(e1->{
		clientController.sendTrumpf("Ecke",clientController.getFinalGamelobby());
		trumpfWindow.close();
		});
	btnSchaufel.setOnAction(e1->{
		clientController.sendTrumpf("Schaufel",clientController.getFinalGamelobby());
		trumpfWindow.close();
		});
	btnKreuz.setOnAction(e1->{
		clientController.sendTrumpf("Kreuz",clientController.getFinalGamelobby());
		trumpfWindow.close();
		});
	btnHerz.setOnAction(e1->{
		clientController.sendTrumpf("Herz",clientController.getFinalGamelobby());
		trumpfWindow.close();
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