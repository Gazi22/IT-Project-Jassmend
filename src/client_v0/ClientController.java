package client_v0;

import jassmendModel.Card;
import jassmendModel.Player;
import jassmendView.CardView;
import jassmendView.PlayerPane;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;

//Author: Florian J�ger
public class ClientController {
    private  ClientModel clientModel;
    private Socket socket;
    private ChatView view;
    private LoginView loginView;
    private MainMenuView menuView;
    private ClientViewManager viewManager;
    private GameView gameView;
    private ClientTrumpfView trumpfView;
    private int gamelobbyFlag=0;
    private String[] playerIDs= new String[4];
    private String gamelobbyName;
    private String [] myCurrentPlayerHand=new String[9];
    ArrayList<Card> handCards = new ArrayList<>();
    public BufferedReader socketIn;
    public  OutputStreamWriter socketOut;
    private String[] playerTurns = new String[4];
    CardView cardView;
    ArrayList<Card> cardsPlayed = new ArrayList<>();
    int btnToActivate;
    int cardPlayedNr;
    int sticheTeam1=0;
    int sticheTeam2=0;
    int roundcounter =0;
    int pointsTeam1=0;
    int pointsTeam2=0;
    int firstPlayer=0;
    int sticheCounter=0;
    String trumpf ="";
    Image image5 = new Image(getClass().getResourceAsStream("/trumpf/images/Ecke.png"));
    Image image6 = new Image(getClass().getResourceAsStream("/trumpf/images/Herz.png"));
    Image image7 = new Image(getClass().getResourceAsStream("/trumpf/images/Kreuz.png"));
    Image image8 = new Image(getClass().getResourceAsStream("/trumpf/images/Schaufel.png"));



 // Author: Florian J�ger
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
 // Author: Florian J�ger
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
 // Author: Florian J�ger
    public boolean connect(){
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
                                if (msg.startsWith("MessageText")) {
                                   String [] arrMsgText = msg.split("\\|");
                                   msg=arrMsgText[1]+": "+arrMsgText[3];
                                   appendMessageGameView(msg);

                                }
                                else if(msg.startsWith("MessageGameText")) {

                                    String[] arrMsgText = msg.split("\\|");

                                    if(arrMsgText[3].equals("PlayerIDs")){
                                    msg = arrMsgText[3] +"|"+ arrMsgText[4]+"|"+ arrMsgText[5]+"|"+  arrMsgText[6]+"|"+  arrMsgText[7];

                                        playerIDs[0]=arrMsgText[4];
                                        playerIDs[1]=arrMsgText[5];
                                        playerIDs[2]=arrMsgText[6];
                                        playerIDs[3]=arrMsgText[7];
                                        gamelobbyName=arrMsgText[2];
                                    }
                                    else if(arrMsgText[3].equals("GamelobbyFull")) {
                                        if (gamelobbyFlag == 0) {

                                            System.out.println("The gamelobby is full, the game will now start");
                                            //show gameView

                                            Platform.runLater(new Runnable() {
                                                @Override public void run() {
                                                    setTextlblUsername();
                                                    setTextlblRoundCount();
                                                    getViewManager().primaryStage.setTitle("Jassmend");
                                                    getViewManager().primaryStage.setScene(GameView.getScene());
                                                    getViewManager().primaryStage.setMaximized(true);
                                                }
                                                });

                                            getGameView();
                                            comparePlayerIDs();
                                            setGameConfig();

                                            if(clientModel.getClientTurnPlayerID()==1) {
                                                showAlert("Game starts now", "Choose the Trumpf!");
                                            }

                                            else showAlert("Game starts now", "Wait for the Trumpf to be chosen.");
                                            buttonsTrue();

                                            if(clientModel.getClientPlayerID()==1){
                                                btnTrumpfFalse();
                                            }
                                            else btnTrumpfTrue();


                                            switch(clientModel.getClientPlayerID()){
                                                case 1:dealCards(menuView.getFinalGamelobby());
                                                    break;
                                                case 2:waiterino(500);
                                                    dealCards(menuView.getFinalGamelobby());
                                                    break;
                                                case 3:waiterino(1000);
                                                    dealCards(menuView.getFinalGamelobby());
                                                    break;
                                                case 4:waiterino(1500);
                                                    dealCards(menuView.getFinalGamelobby());
                                                    break;
                                            }



                                            gamelobbyFlag = 1;



                                            System.out.println("PlayerIDClient: "+clientModel.getClientPlayerID()+"playername: "+clientModel.getUser());
                                        }
                                    }
                                    else if (arrMsgText[3].equals("PlayerHand")){
                                        if(clientModel.getUser().equals(arrMsgText[1])) {
                                            msg = arrMsgText[3] + "|" + arrMsgText[4] + "|" + arrMsgText[5] + "|" + arrMsgText[6] + "|" + arrMsgText[7] + "|" + arrMsgText[8] + "|" + arrMsgText[9] + "|" + arrMsgText[10] + "|" + arrMsgText[11] + "|" + arrMsgText[12];
                                            for (int x = 4; x < 13; x++) {
                                                setMyCurrentPlayerHand(x-4,arrMsgText[x]);}

                                            for (int y = 0; y < 9; y++) {
                                                handCards.add(stringToCard(myCurrentPlayerHand[y]));

                                                }
                                            System.out.println(handCards.toString());
                                            applyCardImages();

                                        }
                                    }





                                    else if (arrMsgText[3].equals("TurnInfo")){
                                        for(int x=0;x<4;x++){
                                            playerTurns[x]=arrMsgText[x+4];
                                        }

                                        for (int y=0;y<4;y++){

                                            if(playerTurns[y].equals("1")) {
                                                if (clientModel.getClientTurnPlayerID()==(y+1)) {
                                                     buttonsFalse();}
                                                    //showAlert("Gameinfo","It is your turn!");}

                                                
                                                else {buttonsTrue();}
                                               // showAlert("Gameinfo","It is Player "+(y+1)+" turn!");}
                                        }
                                        }

                                        }
                                    //Auf Server implementieren etc
                                    else if (arrMsgText[3].equals("EvaluateRound")) {
                                        waiterino(500);
                                        pointsTeam1=Integer.parseInt(arrMsgText[5]);
                                        pointsTeam2=Integer.parseInt(arrMsgText[7]);

                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                gameView.getLblScoreT1().setText(Integer.toString(pointsTeam1));
                                                gameView.getLblScoreT2().setText(Integer.toString(pointsTeam2));
                                            }
                                            });
                                    }

                                    else if(arrMsgText[3].equals("Trumpf")){
                                        trumpf=arrMsgText[4];


                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {


                                        switch(trumpf){
                                            case "Ecke":
                                                getLblTrumpf().setGraphic(new ImageView(image5));
                                                btnTrumpfTrue();
                                                break;

                                            case "Schaufel":
                                                getLblTrumpf().setGraphic(new ImageView(image8));
                                                btnTrumpfTrue();
                                                break;

                                            case "Herz":
                                                getLblTrumpf().setGraphic(new ImageView(image6));
                                                btnTrumpfTrue();
                                                break;

                                            case "Kreuz":
                                                getLblTrumpf().setGraphic(new ImageView(image7));
                                                btnTrumpfTrue();
                                                break;
                                        }
                                            }
                                        });

                                        if(clientModel.getClientTurnPlayerID()==1) {
                                            buttonsFalse();
                                        }

                                    }



                                    else if (arrMsgText[3].equals("Stich")) {

                                        sticheCounter=Integer.parseInt(arrMsgText[4]);
                                        String turnPlayer1 = arrMsgText[5];

                                        int y =0;
                                        int z= 0;

                                        for(int v = 0; v< playerIDs.length; v++) {
                                            if (playerIDs[v].equals(clientModel.getUser())) {
                                                z = v;
                                            }
                                        }







                                        for(int x = 0; x< playerIDs.length; x++){
                                            if(playerIDs[x].equals(turnPlayer1)){
                                                y=x;



                                            }
                                        }

                                        switch(y){
                                            case 0:
                                                if(z==0){clientModel.setClientTurnPlayerID(1);}
                                                if(z==1){clientModel.setClientTurnPlayerID(2);}
                                                if(z==2){clientModel.setClientTurnPlayerID(3);}
                                                if(z==3){clientModel.setClientTurnPlayerID(4);}
                                               break;
                                            case 1:
                                                if(z==0){clientModel.setClientTurnPlayerID(4);}
                                                if(z==1){clientModel.setClientTurnPlayerID(1);}
                                                if(z==2){clientModel.setClientTurnPlayerID(2);}
                                                if(z==3){clientModel.setClientTurnPlayerID(3);}
                                                break;
                                            case 2:
                                                if(z==0){clientModel.setClientTurnPlayerID(3);}
                                                if(z==1){clientModel.setClientTurnPlayerID(4);}
                                                if(z==2){clientModel.setClientTurnPlayerID(1);}
                                                if(z==3){clientModel.setClientTurnPlayerID(2);}
                                                break;
                                            case 3:
                                                if(z==0){clientModel.setClientTurnPlayerID(2);}
                                                if(z==1){clientModel.setClientTurnPlayerID(3);}
                                                if(z==2){clientModel.setClientTurnPlayerID(4);}
                                                if(z==3){clientModel.setClientTurnPlayerID(1);}
                                                break;
                                        }


                                        if (clientModel.getClientTurnPlayerID()==1){
                                            setFirstPlayer(clientModel.getClientPlayerID());}


                                        if(!arrMsgText[7].equals("null")){
                                            sticheTeam1++;
                                        }
                                        else sticheTeam2++;


                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                gameView.getLblSticheT1().setText(Integer.toString(sticheTeam1));
                                                gameView.getLblSticheT2().setText(Integer.toString(sticheTeam2));
                                            }
                                            });

                                        waiterino(250);

                                        for (int r=0;r<4;r++){

                                            if(playerTurns[r].equals("1")) {
                                                if (clientModel.getClientTurnPlayerID()==(r+1)) {
                                                    buttonsFalse();}



                                                else {buttonsTrue();}

                                            }
                                        }
                                        //CLEAR CARDSHOLDER?


                                        if (sticheCounter==9){
                                            if(clientModel.getClientPlayerID()==1) {
                                                getEvaluation(menuView.getFinalGamelobby());
                                            }
                                            gameView.getPlayerPane(1).clearCardsHolder();
                                            sticheTeam2=0;
                                            sticheTeam1=0;



                                            int p= 0;

                                            for(int v = 0; v< playerIDs.length; v++) {
                                                if (playerIDs[v].equals(clientModel.getUser())) {
                                                    p = v;
                                                }
                                            }

                                            if(roundcounter>0){
                                                for(int q = 0; q<roundcounter;q++){
                                                        p++;
                                                     if(p==4){p=0;}
                                                }
                                            }

                                            if(p==0){clientModel.setClientTurnPlayerID(1);}




                                            if(clientModel.getClientTurnPlayerID()==1){
                                                btnTrumpfFalse();
                                            }



                                            waiterino(500);
                                            switch(clientModel.getClientPlayerID()){
                                                case 1:dealCards(menuView.getFinalGamelobby());
                                                    break;
                                                case 2:waiterino(500);
                                                    dealCards(menuView.getFinalGamelobby());
                                                    break;
                                                case 3:waiterino(1000);
                                                    dealCards(menuView.getFinalGamelobby());
                                                    break;
                                                case 4:waiterino(1500);
                                                    dealCards(menuView.getFinalGamelobby());
                                                    break;
                                            }

                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {

                                                    gameView.getLblSticheT1().setText(Integer.toString(sticheTeam1));
                                                    gameView.getLblSticheT2().setText(Integer.toString(sticheTeam2));
                                                }
                                            });

                                            waiterino(250);
                                        }

                                    }

                                    else if (arrMsgText[3].equals("CardsPlayed")) {
                                        setFirstPlayer(Integer.parseInt(arrMsgText[10]));
                                        roundcounter=Integer.parseInt(arrMsgText[4]);
                                        System.out.println("Server sent the card: "+arrMsgText);
                                        if(clientModel.getClientTurnPlayerID()==1){turnFinished(menuView.getFinalGamelobby());}
                                        if(cardsPlayed.size()==4){clearCardsPlayed();}
                                        int cardsPlayedCounter=Integer.parseInt(arrMsgText[5]);
                                        cardsPlayed.add(stringToCard(arrMsgText[cardsPlayedCounter+5]));









                                        if (arrMsgText[5].equals("1"))

                                        {cardPlayedNr=1;
                                            //client ID ersetzen durch clientModel.getclientTurnPlayerID()!=1){
                                                if (clientModel.getClientTurnPlayerID()==1){
                                                    btnToActivate= 1;
                                                }
                                                if (clientModel.getClientTurnPlayerID()==2){
                                                    btnToActivate= 4;
                                                }
                                                if (clientModel.getClientTurnPlayerID()==3){
                                                    btnToActivate= 3;//then btn3
                                                }
                                                if (clientModel.getClientTurnPlayerID()==4){
                                                    btnToActivate= 2;//then btn2
                                                }
                                                gameView.showPlayedCards();


                                        }
                                        else if (arrMsgText[5].equals("2")){
                                            cardPlayedNr=2;
                                                if (clientModel.getClientTurnPlayerID()==2){
                                                    btnToActivate= 1;
                                                 }
                                                if (clientModel.getClientTurnPlayerID()==1){
                                                    btnToActivate= 2;//then btn2
                                                }
                                                if (clientModel.getClientTurnPlayerID()==3){
                                                    btnToActivate= 4;//then btn3
                                                }
                                                if (clientModel.getClientTurnPlayerID()==4){
                                                    btnToActivate= 3; //then btn4
                                                }
                                                gameView.showPlayedCards();


                                        }
                                        else if (arrMsgText[5].equals("3")){
                                            cardPlayedNr=3;
                                                if (clientModel.getClientTurnPlayerID()==3){
                                                    btnToActivate=1;
                                                }
                                                if (clientModel.getClientTurnPlayerID()==1){
                                                    btnToActivate= 3;//then btn3
                                                }
                                                if (clientModel.getClientTurnPlayerID()==2){
                                                    btnToActivate= 2;//then btn4
                                                }
                                                if (clientModel.getClientTurnPlayerID()==4){
                                                    btnToActivate= 4;//then btn2
                                                }
                                                gameView.showPlayedCards();


                                        }
                                        else if (arrMsgText[5].equals("4")){
                                            cardPlayedNr=4;
                                                if (clientModel.getClientTurnPlayerID()==4){
                                                    btnToActivate=1;
                                                }
                                                if (clientModel.getClientTurnPlayerID()==1){
                                                    btnToActivate= 4;//then btn4
                                                }
                                                if (clientModel.getClientTurnPlayerID()==2){
                                                    btnToActivate= 3;//then btn3
                                                }
                                                if (clientModel.getClientTurnPlayerID()==3){
                                                    btnToActivate= 2;//then btn2
                                                }
                                                gameView.showPlayedCards();


                                        }



                                    if (cardPlayedNr == 4) {
                                        waiterino(1000);

                                        if(clientModel.getClientTurnPlayerID()==1) {
                                            getStiche(menuView.getFinalGamelobby());
                                        }

                                       // waiterino(5000);
                                        gameView.clearFieldButtons();
                                    }


                                    }



                                        appendMessageGameView(msg);

                                }

                                else
                                    //for testing purposes
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
 // Author: Florian J�ger
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
 // Author: Florian J�ger
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
 // Author: Florian J�ger
    private  void sendToServer(String message){
        try {
            socketOut.write(message + "\n");
            socketOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 // Author: Florian J�ger
    public void registerUser(String username, String password){
        String concatString = "CreateLogin|"+username+"|"+password;
        sendToServer(concatString);
    }
    public void gamelobbyIsFull(String gamelobby,String message){
        String concatString = message+"|"+clientModel.gethash()+"|"+gamelobby+"|"+clientModel.getUser();
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

    public String getUsername(){
        return clientModel.getUser();
    }
 // Author: Florian J�ger
    public void getGamelobbyList(){
        //No additional checks done, since button is disabled until confirmed login
        String concatString = "ListGamelobbys|"+clientModel.gethash();
        sendToServer(concatString);
    }
 // Author: Florian J�ger
    public void comparePlayerIDs() {
        for (int x = 0; x < playerIDs.length; x++) {
            if (playerIDs[x].equals(clientModel.getUser())) {
                clientModel.setClientPlayerID(x + 1);
                clientModel.setClientTurnPlayerID(x+1);
            }
        }
    }
 // Author: Florian J�ger
    public void setMyCurrentPlayerHand(int i,String cardString){

            myCurrentPlayerHand[i]=cardString;
        }

 // Author: Florian J�ger
    public void setGameConfig(){
        for(int i  = 0; i < 4;i++){
              clientModel.getPlayer(i).setPlayerName(getPlayerIDs(i));

            }
        }

 // Author: Florian J�ger
    public void getStiche(String gamelobby){
        String concatString = "GetStiche|"+clientModel.gethash()+"|"+gamelobby+"|"+clientModel.getUser();
        sendToServer(concatString);
    }
    public void getEvaluation(String gamelobby){
        String concatString = "Evaluation|"+clientModel.gethash()+"|"+gamelobby+"|"+clientModel.getUser();
        sendToServer(concatString);
    }

    public void getGamelobbyUsers(String gamelobby){
        //No additional checks done, since button is disabled until confirmed login
        String concatString = "ListGamelobbyUsers|"+clientModel.gethash()+"|"+gamelobby;
        sendToServer(concatString);
    }

    public void startGame(String gamelobby){
        String concatString = "StartGame|"+clientModel.gethash()+"|"+gamelobby+"|"+clientModel.getUser();
        sendToServer(concatString);
    }

    public void joinGamelobby(String gamelobby){
        String concatString = "JoinGamelobby|"+clientModel.gethash()+"|"+gamelobby+"|"+clientModel.getUser();
        sendToServer(concatString);
    }
    public void dealCards(String gamelobby){
        String concatString = "DealCards|"+clientModel.gethash()+"|"+gamelobby+"|"+clientModel.getUser();
        sendToServer(concatString);
    }

    public void turnFinished(String gamelobby){
        String concatString = "TurnManager|"+clientModel.gethash()+"|"+gamelobby+"|"+clientModel.getUser();
        sendToServer(concatString);
    }

    public void sendCardPlayed(String card,String gamelobby,String firstPlayer){
        String concatString = "CardPlayed|"+clientModel.gethash()+"|"+gamelobby+"|"+clientModel.getUser()+"|"+card+"|"+firstPlayer;
        sendToServer(concatString);

    }
    public void sendTrumpf(String trumpf,String gamelobby){
        String concatString = "Trumpf|"+clientModel.gethash()+"|"+gamelobby+"|"+clientModel.getUser()+"|"+trumpf;
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

    public String getPlayerNames(int i){
        String[] playerNames=new String[4];
         for(int x=0;x<4;x++){
           playerNames[x]=clientModel.getPlayer(x).getPlayerName();
        }
         return playerNames[i];
    }
 // Author: Florian J�ger
    public  void logout(){

        sendToServer("Logout");
    }

    public  void logoutUser(String username){
        String concatString = "LogoutUser|"+username;
        sendToServer(concatString);

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
         //added new fx thread
         Platform.runLater(new Runnable() {
             @Override
             public void run() {
                 Alert alert = new Alert(AlertType.INFORMATION);
                 alert.setTitle(alertTitle);
                 alert.setContentText(alertMessage);

                 alert.showAndWait();
             }
         });
    }

     void showInputDialog(String inputTitle,String inputContent) {
    	 TextInputDialog txtInput = new TextInputDialog();
    	 txtInput.setTitle(inputTitle);
    	 txtInput.setContentText(inputContent);
    	Optional<String> result = txtInput.showAndWait();


    }



  // Author: Florian J�ger
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

  // Author: Florian J�ger
     public ClientController(ClientModel clientModel){
         this.clientModel = clientModel;


     }
     public void addMainMenuView (MainMenuView menuView) {
    	 this.menuView = menuView;
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
     public void addTrumpfView(ClientTrumpfView trumpfView){
        this.trumpfView = trumpfView;
    }
     public void setViewManager(ClientViewManager viewManager) {
         this.viewManager = viewManager;
     }
     public ClientViewManager getViewManager() {
         return viewManager;
     }


    public String getPlayerIDs(int i) {
        for (int x = 0; x < playerIDs.length; x++) {
            return playerIDs[i];
        }
        return null;
    }



 // Author: Florian J�ger
    public void joinedGamelobbyMode(){
        gameView.btnSend.setDisable(false);
        gameView.txt1.setDisable(false);
    }

    public void buttonsFalse() {
        for (int i = 0; i < 9; i++) {
           gameView.getPlayerPane(1).getCardBox().getChildren().get(i).setDisable(false);
            }
    }


    public void buttonsTrue() {
        for (int i = 0; i < 9; i++) {
            gameView.getPlayerPane(1).getCardBox().getChildren().get(i).setDisable(true);
        }
    }

    public void btnTrumpfFalse(){
      gameView.getBtnTrumpf().setDisable(false);
    }

    public void btnTrumpfTrue(){
        gameView.getBtnTrumpf().setDisable(true);
    }



 // Author: Florian J�ger
    public boolean isFull() {

        boolean full = true;
        for (int i=0; i<playerIDs.length; i++) {
            if (playerIDs[i].equals("null")) {
                full = false;
                break;
            }
        }
        return full;
    }
 // Author: Florian J�ger
//Player p = clientModel.getPlayer(clientModel.getClientPlayerID());!!!!
    private void applyCardImages(){
        Player p = clientModel.getPlayer(1);
        p.discardHand();
        for (int j = 0; j < Player.HAND_SIZE; j++) {
            Card card = handCards.get(j);
            p.addCard(card);
        }
        PlayerPane pp = gameView.getPlayerPane(1);
        pp.updatePlayerDisplay();

    }
 // Author: Florian J�ger
    public String switchNumbersToWords(String number){
        switch(number){
            case "6": return "Sechs";
            case "7": return "Sieben";
            case "8": return "Acht";
            case "9": return "Neun";
            case "10": return "Zehn";
            default:
                return number;

        }
    }

 // Author: Florian J�ger
//https://stackoverflow.com/questions/1080904/how-can-i-lookup-a-java-enum-from-its-string-value/1080914
//https://stackoverflow.com/questions/9276639/java-how-to-split-a-string-by-a-number-of-characters
    public Card stringToCard(String stringOfCard){
        if(stringOfCard.startsWith("Kreuz")){
            String suitString;
            String rankString;

            suitString=(stringOfCard.substring(0,5));
            rankString=switchNumbersToWords(stringOfCard.substring(5));

            Card card=new Card(Card.Suit.valueOf(suitString),Card.Rank.valueOf(rankString));
            return card;

        }
        else if(stringOfCard.startsWith("Herz")){
            String suitString;
            String rankString;

            suitString=(stringOfCard.substring(0,4));
            rankString=switchNumbersToWords(stringOfCard.substring(4));

            Card card=new Card(Card.Suit.valueOf(suitString),Card.Rank.valueOf(rankString));
            return card;

        }
        else if(stringOfCard.startsWith("Ecke")){
            String suitString;
            String rankString;

            suitString=(stringOfCard.substring(0,4));
            rankString=switchNumbersToWords(stringOfCard.substring(4));

            Card card=new Card(Card.Suit.valueOf(suitString),Card.Rank.valueOf(rankString));
            return card;

        }
        else if(stringOfCard.startsWith("Schaufel")){
            String suitString;
            String rankString;

            suitString=(stringOfCard.substring(0,8));
            rankString=switchNumbersToWords(stringOfCard.substring(8));

            Card card=new Card(Card.Suit.valueOf(suitString),Card.Rank.valueOf(rankString));
            return card;

        }

        else return null;
    }

    public int getBtnToActivate() {
        return btnToActivate;
    }

    public Card getCardsPlayed(int i){
        return cardsPlayed.get(i);
    }

    public int getSizeCardsPlayed(){
        return cardsPlayed.size();
    }

    public void clearCardsPlayed(){
        cardsPlayed.clear();
    }
    public int getCardPlayedNr() {
        return cardPlayedNr;
    }
 // Author: Florian J�ger
    //https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
    public static void waiterino(int ms){
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
 // Author: Florian J�ger
    public String getFinalGamelobby(){
       return menuView.getFinalGamelobby();
    }
 // Author: Florian J�ger
    public void getGameView() {
        //added new fx thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getViewManager().primaryStage.setScene(GameView.getScene());
            }
        });
    }


 // Author: Florian J�ger
    public void setTextlblRoundCount(){
        gameView.getLblRound().setText("Round: "+roundcounter);
    }


 // Author: Florian J�ger
    public void setTextlblUsername() {

        int y =0;
        int playerID= 0;

        for(int v = 0; v< playerIDs.length; v++) {
            if (playerIDs[v].equals(clientModel.getUser())) {
                playerID = v;
            }
        }


        switch(playerID){
            case 0:
                gameView.getUserNamePl1().setText(playerIDs[0]);
                gameView.getUserNamePl2().setText(playerIDs[1]);
                gameView.getUserNamePl3().setText(playerIDs[2]);
                gameView.getUserNamePl4().setText(playerIDs[3]);
                gameView.getLblPl1().setText(playerIDs[0]);
                gameView.getLblPl2().setText(playerIDs[1]);
                gameView.getLblPl3().setText(playerIDs[2]);
                gameView.getLblPl4().setText(playerIDs[3]);

                break;
            case 1:
                gameView.getUserNamePl1().setText(playerIDs[1]);
                gameView.getUserNamePl2().setText(playerIDs[2]);
                gameView.getUserNamePl3().setText(playerIDs[3]);
                gameView.getUserNamePl4().setText(playerIDs[0]);
                gameView.getLblPl1().setText(playerIDs[1]);
                gameView.getLblPl2().setText(playerIDs[2]);
                gameView.getLblPl3().setText(playerIDs[3]);
                gameView.getLblPl4().setText(playerIDs[0]);
                break;
            case 2:
                gameView.getUserNamePl1().setText(playerIDs[2]);
                gameView.getUserNamePl2().setText(playerIDs[3]);
                gameView.getUserNamePl3().setText(playerIDs[0]);
                gameView.getUserNamePl4().setText(playerIDs[1]);
                gameView.getLblPl1().setText(playerIDs[2]);
                gameView.getLblPl2().setText(playerIDs[3]);
                gameView.getLblPl3().setText(playerIDs[0]);
                gameView.getLblPl4().setText(playerIDs[1]);
                break;
            case 3:
                gameView.getUserNamePl1().setText(playerIDs[3]);
                gameView.getUserNamePl2().setText(playerIDs[0]);
                gameView.getUserNamePl3().setText(playerIDs[1]);
                gameView.getUserNamePl4().setText(playerIDs[2]);
                gameView.getLblPl1().setText(playerIDs[3]);
                gameView.getLblPl2().setText(playerIDs[0]);
                gameView.getLblPl3().setText(playerIDs[1]);
                gameView.getLblPl4().setText(playerIDs[2]);
                break;
        }

    }
 // Author: Florian J�ger
    public Label getLblTrumpf(){
        return gameView.getLblTrumpf();
    }

    public void setTextlblUsernameMenu() {
        menuView.getLblUserName().setText("Welcome "+ getUsername()+" to JASSMEND!");
    }

    public String getFirstPlayer(){
        return Integer.toString(firstPlayer);
    }

    public void setFirstPlayer(int player){firstPlayer=player;}


    public int getPointsTeam1() {
        return pointsTeam1;
    }

    public int getPointsTeam2() {
        return pointsTeam2;
    }

    public int getSticheTeam1() {
        return sticheTeam1;
    }

    public void setSticheTeam1(int sticheTeam1) {
        this.sticheTeam1 = sticheTeam1;
    }

    public int getSticheTeam2() {
        return sticheTeam2;
    }

    public void setSticheTeam2(int sticheTeam2) {
        this.sticheTeam2 = sticheTeam2;
    }

    public int getRoundcounter() {
        return roundcounter;
    }

    public void setRoundcounter(int roundcounter) {
        this.roundcounter = roundcounter;
    }
    
    public void setSocket(Socket socket) {
    	
    	this.socket = socket;
    }
    
    public Socket getSocket() {
    	
    	return socket;
    }

 // Author: Florian J�ger
    public boolean readLastMessage(String lookFor) {
        int lastMessageIndex2 = gameView.msgArea.getText().split("\n").length - 1;
        String lastMessage2 = gameView.msgArea.getText().split("\n")[lastMessageIndex2];

        if (lastMessage2.startsWith(lookFor)) {
            return true;
        }
        else return false;
    }



}


