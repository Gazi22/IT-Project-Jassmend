package client_v0;


import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class CreateAccountView {
	Label lblHeader = new Label("Create a new account FOR FREE!");
	Label lblUsername = new Label("Username : ");
	Label lblPassword = new Label("Password : ");
	static TextField txtPassword = new TextField();
	static TextField txtUsername = new TextField();
	Button btnSubmit = new Button("Submit");


	private static Scene sceneAccView;
	private ClientController clientController;

	public CreateAccountView(ClientController clientController)
	{
		this.clientController = clientController;
		//Width should increase when increasing windows size!
		GridPane gridAccountView = new GridPane();
     	gridAccountView.setAlignment(Pos.CENTER);
		gridAccountView.setPadding(new Insets(40, 40, 40, 40));
		gridAccountView.setHgap(10);
		gridAccountView.setVgap(10);

		       

		 
		 gridAccountView.add(lblHeader, 0,0,2,1);
		 GridPane.setHalignment(lblHeader, HPos.CENTER);
		 GridPane.setMargin(lblHeader, new Insets(20, 0,20,0));

		 
	     gridAccountView.add(lblUsername, 0,1);

         
		 txtUsername.setPrefHeight(40);
	
	
	     gridAccountView.add(txtUsername, 1,1);

         
		 
		 gridAccountView.add(lblPassword, 0, 3);

		      
		 
		 txtPassword.setPrefHeight(40);
		 gridAccountView.add(txtPassword, 1, 3);

		        
		 
		 btnSubmit.setPrefHeight(40);
		 btnSubmit.setDefaultButton(true);
		 btnSubmit.setPrefWidth(100);
		 gridAccountView.add(btnSubmit, 0, 4, 2, 1);
		 GridPane.setHalignment(btnSubmit, HPos.CENTER);
		 GridPane.setMargin(btnSubmit, new Insets(20, 0,20,0));

	//SOME ALERT ERROR HANDLING missing?
		 btnSubmit.setOnAction(event -> {
	            // Assume success always!
	            clientController.registerUser(txtUsername.getText(), txtPassword.getText());
	           this.clientController.getLoginView().newStageCreateAccount.close();
	        });
		 
		 
	        	
		    
		 sceneAccView = new Scene(gridAccountView, 500, 275);
		 
		 
		 
	}
	
	
	
	public static Scene getSceneAccView () {
		
		return sceneAccView;
		
	}
}
