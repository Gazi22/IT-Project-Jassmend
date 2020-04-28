package client_v0;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import java.net.ConnectException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Optional;

public class ClientController {
    private  ClientModel clientModel;
    private Socket socket;
    private ChatView view;
    private LoginView loginView;
    private ClientViewManager viewManager;
    private GameView gameView;

    public BufferedReader socketIn;
    public  OutputStreamWriter socketOut;
    
    
    
    public boolean connect (String ipaddress, String port){
        if (validateIpAddress(ipaddress)&&validatePortNumber(port)){
            try {
                // Get IP address and port from user or use default settings

                String connecting = "Connecting to "+ipaddress+" via port:"+port;
                clientModel.log_info(connecting);
                appendMessageGameView(connecting);

                socket = new Socket(ipaddress, Integer.parseInt(port));
                String established = "Connection established !";
                clientModel.log_info(established);
                appendMessageGameView(established);

                clientModel.setIpAddress(ipaddress);
                clientModel.setPort(Integer.parseInt(port));

                loginView.setText_txtIPAddress(ipaddress);
                loginView.setText_txtPort(port);
                try {
                    socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    socketOut = new OutputStreamWriter(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            } catch (Exception e) {
                clientModel.log_error(e.toString());
                return false;
            }
        } else {
            String incorrectFormat = "Formatting of IP-Address and/or Port is incorrect !";

            clientModel.log_warning(incorrectFormat);
            appendMessageGameView(incorrectFormat);

            return false;
        }


    }

    public void appendMessage(String message){
        view.areaMessages.appendText(message+"\n");
    }
    
    public void appendMessageGameView(String message){
        gameView.msgArea.appendText(message+"\n");
    }
    
    public void setText_IPField(String text){
        loginView.txtIPAddress.setText(text);
    }

    public void setText_PortField(String text){
        loginView.txtPort.setText(text);
    }
    
    public boolean connect (){
        try {
            // Get IP address and port from user or use default settings
            String ipaddress;
            int port;

            ipaddress = clientModel.defaultiIPAddress;
            port = clientModel.defaultPortNumber;

            String connecting = "Connecting to "+ipaddress+" via port:"+port;
            clientModel.log_info(connecting);
            appendMessageGameView(connecting);

            socket = new Socket(ipaddress, port);
            String established = "Connection established !";
            clientModel.log_info(established);
            appendMessageGameView(established);

            loginView.setText_txtIPAddress(ipaddress);
            loginView.setText_txtPort(String.valueOf(port));
            try {
                socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                socketOut = new OutputStreamWriter(socket.getOutputStream());

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            String msg;
                            try {
                                msg = socketIn.readLine();
                                appendMessageGameView("Received: " + msg);
                            } catch (IOException e) {
                                break;
                            }
                            if (msg == null) break; // In case the server closes the socket
                        }
                    }
                };
                Thread t = new Thread(r);
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
            
        } catch (Exception e) {
            clientModel.log_error(e.toString());
            return false;
        }
        
    }

    private  boolean validateIpAddress(String ipAddress) {
        boolean formatOK = false;
        // Check for validity (not complete, but not bad)
        String [] ipPieces = ipAddress.split("\\."); // Must escape (see
        // documentation)
        // Must have 4 parts
        if (ipPieces.length == 4) {
            // Each part must be an integer 0 to 255
            formatOK = true; // set to false on the first error
            int byteValue = -1;
            for (String s : ipPieces) {
                byteValue = Integer.parseInt(s); // may throw
                // NumberFormatException
                if (byteValue < 0 | byteValue > 255) formatOK = false;
            }
        }
        return formatOK;
    }

    private  boolean validatePortNumber(String portText) {
        boolean formatOK = false;
        try {
            int portNumber = Integer.parseInt(portText);
            if (portNumber >= 1024 & portNumber <= 65535) {
                formatOK = true;
            }
        } catch (NumberFormatException e) {
        }
        return formatOK;
    }

    private  void sendToServer(String message){
        try {
            socketOut.write(message + "\n");
            socketOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerUser(String username, String password){
        String concatString = "CreateLogin|"+username+"|"+password;
        sendToServer(concatString);
    }
    public void loginUser(String username, String password){
       
        String concatString = "Login|"+username+"|"+password;
        sendToServer(concatString);
    }
    public  void loginSuccesfull(String username, String hash){
        clientModel.setUser(username);
        clientModel.setHash(hash);
    }
    public void getGamelobbyList(){
        //No additional checks done, since button is disabled until confirmed login
        String concatString = "ListGamelobbys|"+clientModel.gethash();
        sendToServer(concatString);
    }
    
    public void getGamelobbyUsers(String gamelobby){
        //No additional checks done, since button is disabled until confirmed login
        String concatString = "ListGamelobbyUsers|"+clientModel.gethash()+"|"+gamelobby;
        sendToServer(concatString);
    }
    
    
    public void joinGamelobby(String gamelobby){
        String concatString = "JoinGamelobby|"+clientModel.gethash()+"|"+gamelobby+"|"+clientModel.getUser();
        sendToServer(concatString);
    }
    
    public void leaveGamelobby (String gamelobby) {
    	String concatString = "LeaveGamelobby|"+clientModel.gethash()+"|"+gamelobby+"|"+clientModel.getUser();
        sendToServer(concatString);
    }
    
    public  void createGamelobby(String newGamelobby){
        String concatString = "CreateGamelobby|"+clientModel.gethash()+"|"+newGamelobby+"|"+"true";
        sendToServer(concatString);
    }
    
    public  void deleteAccount(){
        String concatString = "DeleteLogin|"+clientModel.gethash();
        sendToServer(concatString);
    }
    
    public  void changePassword(String newPassword){
        String concatString = "ChangePassword|"+clientModel.gethash()+"|"+newPassword;
        sendToServer(concatString);
    }
    
    public  void ping(){
        String concatString = "Ping|"+clientModel.gethash();
        sendToServer(concatString);
    }
    
    
    
    public  void logout(){
        
        sendToServer("Logout");
    }
    
    public void sendMessage(String message){
        // Destination is currently selected gamelobby
        String concatString = "SendMessage|"+clientModel.gethash()+"|"+clientModel.getCurrentGamelobby()+"|"+message;
        sendToServer(concatString);
    }
    public void joinSuccessfull(String gamelobby){
        clientModel.setCurrentgamelobby(gamelobby);
    }


    public LoginView getLoginView() {
        return loginView;
    }
    
    //Alerts for user feedback
     void showAlert(String alertTitle,String alertMessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(alertTitle);
        alert.setContentText(alertMessage);
 
        alert.showAndWait();
    }
    
     void showInputDialog(String inputTitle,String inputContent) {
    	 TextInputDialog txtInput = new TextInputDialog();
    	 txtInput.setTitle(inputTitle);
    	 txtInput.setContentText(inputContent);
    	Optional<String> result = txtInput.showAndWait();
    	 
    	
    }
    
         
     
        
    //Source: https://tagmycode.com/snippet/5207/yes-no-cancel-dialog-in-javafx#.XiML3MhKjD4
     void showAlertYesNo(String alertTitle,String alertMessage) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(alertTitle);
    alert.setContentText(alertMessage);
    ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
    ButtonType noButton = new ButtonType("No", ButtonData.NO);
    ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);
    alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.OK) {
            } else if (type == ButtonType.NO) {
            } else {
            }
    });
}
     
     
     public ClientController(ClientModel clientModel){
         this.clientModel = clientModel;
     }
     public  void addChatView(ChatView chatView){
         this.view = chatView;
     }
     public void addLoginView(LoginView loginView){
         this.loginView = loginView;
     }
     public void addGameView(GameView gameView){
         this.gameView = gameView;
     }
     public void setViewManager(ClientViewManager viewManager) {
         this.viewManager = viewManager;
     }
     public ClientViewManager getViewManager() {
         return viewManager;
     }
     
}
