package Jassmend;

import jassmendModelClasses.Card;
import jassmendModelClasses.Player;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

//Author: Florian Jäger
//some things from Chat project 2019 (Florian Jäger)
public class JasmendController {
    private JasmendModel jasmendModel;
    private Socket socket;
    private JassmendLoginView jassmendLoginView;
    private JassmendMainMenuView menuView;
    private JassmendViewManager viewManager;
    private JassmendLogView jassmendLogView;
    private JassmendGameView jassmendGameView;
    private JassmendTrumpfView trumpfView;
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



 // Author: Florian Jäger
    public boolean connect (String ipaddress, String port){
        if (validateIpAddress(ipaddress)&&validatePortNumber(port)){
            try {
                // Get IP address and port from user or use default settings

                String connecting = "Connecting to "+ipaddress+" via port:"+port;
                jasmendModel.log_info(connecting);
                appendMessage(connecting);

                socket = new Socket(ipaddress, Integer.parseInt(port));
                String established = "Connection established !";
                jasmendModel.log_info(established);
                appendMessage(established);

                jasmendModel.setIpAddress(ipaddress);
                jasmendModel.setPort(Integer.parseInt(port));

                jassmendLoginView.setText_txtIPAddress(ipaddress);
                jassmendLoginView.setText_txtPort(port);
                try {
                    socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    socketOut = new OutputStreamWriter(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            } catch (Exception e) {
                jasmendModel.log_error(e.toString());
                return false;
            }
        } else {
            String incorrectFormat = "Formatting of IP-Address and/or Port is incorrect !";

            jasmendModel.log_warning(incorrectFormat);
            appendMessage(incorrectFormat);

            return false;
        }


    }
 // Author: Florian Jäger
 //append Servermessages
    public void appendMessage(String message){
        jassmendLogView.areaMessages.appendText(message+"\n");
    }

//append messages for the chat
    public void appendMessageGameView(String message){
        jassmendGameView.msgArea.appendText(message+"\n");
    }


    
    public void setText_IPField(String text){
        jassmendLoginView.txtIPAddress.setText(text);
    }

    public void setText_PortField(String text){
        jassmendLoginView.txtPort.setText(text);
    }
 // Author: Florian Jäger
    public boolean connect(){
        try {
            // Get IP address and port from user or use default settings
            String ipaddress;
            int port;

            ipaddress = jasmendModel.defaultiIPAddress;
            port = jasmendModel.defaultPortNumber;

            String connecting = "Connecting to "+ipaddress+" via port:"+port;
            jasmendModel.log_info(connecting);
            appendMessage(connecting);

            socket = new Socket(ipaddress, port);
            String established = "Connection established !";
            jasmendModel.log_info(established);
            appendMessage(established);

            jassmendLoginView.setText_txtIPAddress(ipaddress);
            jassmendLoginView.setText_txtPort(String.valueOf(port));
            try {
                socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                socketOut = new OutputStreamWriter(socket.getOutputStream());



         //Splitted in different kinds of messages

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            String msg;
                            try {
                                //messages for the chatting
                                msg = socketIn.readLine();
                                if (msg.startsWith("MessageText")) {
                                   String [] arrMsgText = msg.split("\\|");
                                   msg=arrMsgText[1]+": "+arrMsgText[3];
                                   appendMessageGameView(msg);

                                }
                                //all messages related to the gamecontrolling
                                else if(msg.startsWith("MessageGameText")) {

                                    String[] arrMsgText = msg.split("\\|");

                                    //PLayer arraylist with names
                                    if(arrMsgText[3].equals("PlayerIDs")){
                                    msg = arrMsgText[3] +"|"+ arrMsgText[4]+"|"+ arrMsgText[5]+"|"+  arrMsgText[6]+"|"+  arrMsgText[7];

                                        playerIDs[0]=arrMsgText[4];
                                        playerIDs[1]=arrMsgText[5];
                                        playerIDs[2]=arrMsgText[6];
                                        playerIDs[3]=arrMsgText[7];
                                        gamelobbyName=arrMsgText[2];
                                    }
                                    //As soon as all players have joined the lobby->
                                    else if(arrMsgText[3].equals("GamelobbyFull")) {
                                        if (gamelobbyFlag == 0) {

                                            System.out.println("The gamelobby is full, the game will now start");
                                            //show gameView

                                            Platform.runLater(new Runnable() {
                                                @Override public void run() {
                                                    setTextlblUsername();
                                                    setTextlblRoundCount();
                                                    getViewManager().primaryStage.setTitle("Jassmend");
                                                    getViewManager().primaryStage.setScene(JassmendGameView.getScene());
                                                    getViewManager().primaryStage.setMaximized(true);
                                                }
                                                });

                                            getGameView();
                                            comparePlayerIDs();
                                            setGameConfig();

                                            if(jasmendModel.getClientTurnPlayerID()==1) {
                                                showAlert("Game starts now", "Choose the Trumpf!");
                                            }

                                            else showAlert("Game starts now", "Wait for the Trumpf to be chosen.");
                                            buttonsTrue();

                                            if(jasmendModel.getClientPlayerID()==1){
                                                btnTrumpfFalse();
                                            }
                                            else btnTrumpfTrue();


                                            switch(jasmendModel.getClientPlayerID()){
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



                                            System.out.println("PlayerIDClient: "+ jasmendModel.getClientPlayerID()+"playername: "+ jasmendModel.getUser());
                                        }
                                    }

                                    //Player hands from the server
                                    else if (arrMsgText[3].equals("PlayerHand")){
                                        if(jasmendModel.getUser().equals(arrMsgText[1])) {
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




                                    //Turninfo, who's turn is it
                                    else if (arrMsgText[3].equals("TurnInfo")) {
                                        roundcounter = Integer.parseInt(arrMsgText[8]);
                                        for (int x = 0; x < 4; x++) {
                                            playerTurns[x] = arrMsgText[x + 4];
                                        }

                                        for (int y = 0; y < 4; y++) {

                                            if (playerTurns[y].equals("1")) {
                                                if (jasmendModel.getClientTurnPlayerID() == (y + 1)) {
                                                    buttonsFalse();
                                                    appendMessageGameView("It is your turn");
                                                } else {
                                                    buttonsTrue();
                                                    appendMessageGameView("It is Player " + (y + 1) + " turn!");

                                                }

                                            }

                                        }
                                    }
                                    //Evaluation of the points at the end of the round
                                    else if (arrMsgText[3].equals("EvaluateRound")) {
                                        waiterino(500);
                                        pointsTeam1=Integer.parseInt(arrMsgText[5]);
                                        pointsTeam2=Integer.parseInt(arrMsgText[7]);

                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                jassmendGameView.getLblScoreT1().setText(Integer.toString(pointsTeam1));
                                                jassmendGameView.getLblScoreT2().setText(Integer.toString(pointsTeam2));
                                            }
                                            });
                                    }

                                    //Trumpf set images for each client
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

                                        if(jasmendModel.getClientTurnPlayerID()==1) {
                                            buttonsFalse();
                                        }

                                    }


                                    //Stiche for the designated team, calculation of the next player's turn
                                    else if (arrMsgText[3].equals("Stich")) {

                                        sticheCounter=Integer.parseInt(arrMsgText[4]);
                                        String turnPlayer1 = arrMsgText[5];

                                        int y =0;
                                        int z= 0;

                                        for(int v = 0; v< playerIDs.length; v++) {
                                            if (playerIDs[v].equals(jasmendModel.getUser())) {
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
                                                if(z==0){
                                                    jasmendModel.setClientTurnPlayerID(1);}
                                                if(z==1){
                                                    jasmendModel.setClientTurnPlayerID(2);}
                                                if(z==2){
                                                    jasmendModel.setClientTurnPlayerID(3);}
                                                if(z==3){
                                                    jasmendModel.setClientTurnPlayerID(4);}
                                               break;
                                            case 1:
                                                if(z==0){
                                                    jasmendModel.setClientTurnPlayerID(4);}
                                                if(z==1){
                                                    jasmendModel.setClientTurnPlayerID(1);}
                                                if(z==2){
                                                    jasmendModel.setClientTurnPlayerID(2);}
                                                if(z==3){
                                                    jasmendModel.setClientTurnPlayerID(3);}
                                                break;
                                            case 2:
                                                if(z==0){
                                                    jasmendModel.setClientTurnPlayerID(3);}
                                                if(z==1){
                                                    jasmendModel.setClientTurnPlayerID(4);}
                                                if(z==2){
                                                    jasmendModel.setClientTurnPlayerID(1);}
                                                if(z==3){
                                                    jasmendModel.setClientTurnPlayerID(2);}
                                                break;
                                            case 3:
                                                if(z==0){
                                                    jasmendModel.setClientTurnPlayerID(2);}
                                                if(z==1){
                                                    jasmendModel.setClientTurnPlayerID(3);}
                                                if(z==2){
                                                    jasmendModel.setClientTurnPlayerID(4);}
                                                if(z==3){
                                                    jasmendModel.setClientTurnPlayerID(1);}
                                                break;
                                        }


                                        if (jasmendModel.getClientTurnPlayerID()==1){
                                            setFirstPlayer(jasmendModel.getClientPlayerID());}


                                        if(!arrMsgText[7].equals("null")){
                                            sticheTeam1++;
                                        }
                                        else sticheTeam2++;


                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                jassmendGameView.getLblSticheT1().setText(Integer.toString(sticheTeam1));
                                                jassmendGameView.getLblSticheT2().setText(Integer.toString(sticheTeam2));
                                            }
                                            });

                                        waiterino(250);

                                        for (int r=0;r<4;r++){

                                            if(playerTurns[r].equals("1")) {
                                                if (jasmendModel.getClientTurnPlayerID()==(r+1)) {
                                                    buttonsFalse();}



                                                else {buttonsTrue();}

                                            }
                                        }


                                        //End of round
                                        if (sticheCounter==9){
                                            if(jasmendModel.getClientPlayerID()==1) {
                                                getEvaluation(menuView.getFinalGamelobby());
                                            }

                                            //When more than 1 round is implemented
                                            //gameView.getPlayerPane(1).clearCardsHolder();
                                            sticheTeam2=0;
                                            sticheTeam1=0;
                                            String winner="";
                                            if(Integer.parseInt(jassmendGameView.getLblScoreT1().getText())>Integer.parseInt(jassmendGameView.getLblScoreT2().getText())){
                                                  winner ="Team 1";
                                            }
                                            else winner="Team 2";


                                            showAlert("Game finished","The winner is: "+winner);



                                        }



                                    //Only relevant for the future development of the project - more rounds than 1
                                 /*           int p= 0;

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
                                            if(p==1){clientModel.setClientTurnPlayerID(2);}
                                            if(p==2){clientModel.setClientTurnPlayerID(3);}
                                            if(p==3){clientModel.setClientTurnPlayerID(4);}




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
                                            **/



                                    }

                                    //apply cards played
                                    else if (arrMsgText[3].equals("CardsPlayed")) {
                                        setFirstPlayer(Integer.parseInt(arrMsgText[10]));

                                        System.out.println("Server sent the card: "+arrMsgText);
                                        if(jasmendModel.getClientTurnPlayerID()==1){turnFinished(menuView.getFinalGamelobby());}
                                        if(cardsPlayed.size()==4){clearCardsPlayed();}
                                        int cardsPlayedCounter=Integer.parseInt(arrMsgText[5]);
                                        cardsPlayed.add(stringToCard(arrMsgText[cardsPlayedCounter+5]));









                                        if (arrMsgText[5].equals("1"))

                                        {cardPlayedNr=1;
                                            //client ID ersetzen durch clientModel.getclientTurnPlayerID()!=1){
                                                if (jasmendModel.getClientTurnPlayerID()==1){
                                                    btnToActivate= 1;
                                                }
                                                if (jasmendModel.getClientTurnPlayerID()==2){
                                                    btnToActivate= 4;
                                                }
                                                if (jasmendModel.getClientTurnPlayerID()==3){
                                                    btnToActivate= 3;//then btn3
                                                }
                                                if (jasmendModel.getClientTurnPlayerID()==4){
                                                    btnToActivate= 2;//then btn2
                                                }
                                                jassmendGameView.showPlayedCards();


                                        }
                                        else if (arrMsgText[5].equals("2")){
                                            cardPlayedNr=2;
                                                if (jasmendModel.getClientTurnPlayerID()==2){
                                                    btnToActivate= 1;
                                                 }
                                                if (jasmendModel.getClientTurnPlayerID()==1){
                                                    btnToActivate= 2;//then btn2
                                                }
                                                if (jasmendModel.getClientTurnPlayerID()==3){
                                                    btnToActivate= 4;//then btn3
                                                }
                                                if (jasmendModel.getClientTurnPlayerID()==4){
                                                    btnToActivate= 3; //then btn4
                                                }
                                                jassmendGameView.showPlayedCards();


                                        }
                                        else if (arrMsgText[5].equals("3")){
                                            cardPlayedNr=3;
                                                if (jasmendModel.getClientTurnPlayerID()==3){
                                                    btnToActivate=1;
                                                }
                                                if (jasmendModel.getClientTurnPlayerID()==1){
                                                    btnToActivate= 3;//then btn3
                                                }
                                                if (jasmendModel.getClientTurnPlayerID()==2){
                                                    btnToActivate= 2;//then btn4
                                                }
                                                if (jasmendModel.getClientTurnPlayerID()==4){
                                                    btnToActivate= 4;//then btn2
                                                }
                                                jassmendGameView.showPlayedCards();


                                        }
                                        else if (arrMsgText[5].equals("4")){
                                            cardPlayedNr=4;
                                                if (jasmendModel.getClientTurnPlayerID()==4){
                                                    btnToActivate=1;
                                                }
                                                if (jasmendModel.getClientTurnPlayerID()==1){
                                                    btnToActivate= 4;//then btn4
                                                }
                                                if (jasmendModel.getClientTurnPlayerID()==2){
                                                    btnToActivate= 3;//then btn3
                                                }
                                                if (jasmendModel.getClientTurnPlayerID()==3){
                                                    btnToActivate= 2;//then btn2
                                                }
                                                jassmendGameView.showPlayedCards();


                                        }



                                    if (cardPlayedNr == 4) {
                                        waiterino(1000);

                                        if(jasmendModel.getClientTurnPlayerID()==1) {
                                            getStiche(menuView.getFinalGamelobby());
                                        }

                                       // waiterino(5000);
                                        jassmendGameView.clearFieldButtons();
                                    }


                                    }



                                    appendMessage(msg);

                                }
                                appendMessage(msg);

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
            jasmendModel.log_error(e.toString());
            return false;
        }

    }
 // Author Bradley Richards
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
    // Author Bradley Richards
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

 // Author: Florian Jäger // Messages to the Server
    public void registerUser(String username, String password){
        String concatString = "CreateLogin|"+username+"|"+password;
        sendToServer(concatString);
    }
    public void gamelobbyIsFull(String gamelobby,String message){
        String concatString = message+"|"+ jasmendModel.gethash()+"|"+gamelobby+"|"+ jasmendModel.getUser();
        sendToServer(concatString);
    }
    public void loginUser(String username, String password){
        String concatString = "Login|"+username+"|"+password;
        sendToServer(concatString);
    }
    public  void loginSuccesfull(String username, String hash){
        jasmendModel.setUser(username);
        jasmendModel.setHash(hash);
    }

    public String getUsername(){
        return jasmendModel.getUser();
    }
 // Author: Florian Jäger
    public void getGamelobbyList(){
        //No additional checks done, since button is disabled until confirmed login
        String concatString = "ListGamelobbys|"+ jasmendModel.gethash();
        sendToServer(concatString);
    }
 // Author: Florian Jäger
    public void comparePlayerIDs() {
        for (int x = 0; x < playerIDs.length; x++) {
            if (playerIDs[x].equals(jasmendModel.getUser())) {
                jasmendModel.setClientPlayerID(x + 1);
                jasmendModel.setClientTurnPlayerID(x+1);
            }
        }
    }
 // Author: Florian Jäger
    public void setMyCurrentPlayerHand(int i,String cardString){

            myCurrentPlayerHand[i]=cardString;
        }

 // Author: Florian Jäger
    public void setGameConfig(){
        for(int i  = 0; i < 4;i++){
              jasmendModel.getPlayer(i).setPlayerName(getPlayerIDs(i));

            }
        }

 // Author: Florian Jäger
    public void getStiche(String gamelobby){
        String concatString = "GetStiche|"+ jasmendModel.gethash()+"|"+gamelobby+"|"+ jasmendModel.getUser();
        sendToServer(concatString);
    }
    public void getEvaluation(String gamelobby){
        String concatString = "Evaluation|"+ jasmendModel.gethash()+"|"+gamelobby+"|"+ jasmendModel.getUser();
        sendToServer(concatString);
    }

    public void getGamelobbyUsers(String gamelobby){
        //No additional checks done, since button is disabled until confirmed login
        String concatString = "ListGamelobbyUsers|"+ jasmendModel.gethash()+"|"+gamelobby;
        sendToServer(concatString);
    }

    public void startGame(String gamelobby){
        String concatString = "StartGame|"+ jasmendModel.gethash()+"|"+gamelobby+"|"+ jasmendModel.getUser();
        sendToServer(concatString);
    }

    public void joinGamelobby(String gamelobby){
        String concatString = "JoinGamelobby|"+ jasmendModel.gethash()+"|"+gamelobby+"|"+ jasmendModel.getUser();
        sendToServer(concatString);
    }
    public void dealCards(String gamelobby){
        String concatString = "DealCards|"+ jasmendModel.gethash()+"|"+gamelobby+"|"+ jasmendModel.getUser();
        sendToServer(concatString);
    }

    public void turnFinished(String gamelobby){
        String concatString = "TurnManager|"+ jasmendModel.gethash()+"|"+gamelobby+"|"+ jasmendModel.getUser();
        sendToServer(concatString);
    }

    public void sendCardPlayed(String card,String gamelobby,String firstPlayer){
        String concatString = "CardPlayed|"+ jasmendModel.gethash()+"|"+gamelobby+"|"+ jasmendModel.getUser()+"|"+card+"|"+firstPlayer;
        sendToServer(concatString);

    }
    public void sendTrumpf(String trumpf,String gamelobby){
        String concatString = "Trumpf|"+ jasmendModel.gethash()+"|"+gamelobby+"|"+ jasmendModel.getUser()+"|"+trumpf;
        sendToServer(concatString);

    }

    public void leaveGamelobby (String gamelobby) {
    	String concatString = "LeaveGamelobby|"+ jasmendModel.gethash()+"|"+gamelobby+"|"+ jasmendModel.getUser();
        sendToServer(concatString);
    }

    public  void createGamelobby(String newGamelobby){
        String concatString = "CreateGamelobby|"+ jasmendModel.gethash()+"|"+newGamelobby+"|"+"true";
        sendToServer(concatString);
    }

    public  void deleteAccount(){
        String concatString = "DeleteLogin|"+ jasmendModel.gethash();
        sendToServer(concatString);
    }

    public  void changePassword(String newPassword){
        String concatString = "ChangePassword|"+ jasmendModel.gethash()+"|"+newPassword;
        sendToServer(concatString);
    }

    public  void ping(){
        String concatString = "Ping|"+ jasmendModel.gethash();
        sendToServer(concatString);
    }


    public String getPlayerNames(int i){
        String[] playerNames=new String[4];
         for(int x=0;x<4;x++){
           playerNames[x]= jasmendModel.getPlayer(x).getPlayerName();
        }
         return playerNames[i];
    }

 // Author: Florian Jäger
    public  void logout(){

        sendToServer("Logout");
    }

    public  void logoutUser(String username){
        String concatString = "LogoutUser|"+username;
        sendToServer(concatString);

    }

    public void sendMessage(String message){
        // Destination is currently selected gamelobby
        String concatString = "SendMessage|"+ jasmendModel.gethash()+"|"+ jasmendModel.getCurrentGamelobby()+"|"+message;
        sendToServer(concatString);
    }
    public void joinSuccessfull(String gamelobby){
        jasmendModel.setCurrentgamelobby(gamelobby);
    }



    public JassmendLoginView getJassmendLoginView() {
        return jassmendLoginView;
    }

    //https://www.geeksforgeeks.org/javafx-alert-with-examples/
    //With help from third party (Fabian Jäger)
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




  // Author: Florian Jäger
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

  // Author: Florian Jäger
     public JasmendController(JasmendModel jasmendModel){
         this.jasmendModel = jasmendModel;
     }


     public void addMainMenuView (JassmendMainMenuView menuView) {
    	 this.menuView = menuView;
     }
     public  void addChatView(JassmendLogView jassmendLogView){
         this.jassmendLogView = jassmendLogView;
     }
     public void addLoginView(JassmendLoginView jassmendLoginView){
         this.jassmendLoginView = jassmendLoginView;
     }
     public void addGameView(JassmendGameView jassmendGameView){
         this.jassmendGameView = jassmendGameView;
     }
     public void addTrumpfView(JassmendTrumpfView trumpfView){
        this.trumpfView = trumpfView;
    }
     public void setViewManager(JassmendViewManager viewManager) {
         this.viewManager = viewManager;
     }
     public JassmendViewManager getViewManager() {
         return viewManager;
     }


    public String getPlayerIDs(int i) {
        for (int x = 0; x < playerIDs.length; x++) {
            return playerIDs[i];
        }
        return null;
    }



 // Author: Florian Jäger
    public void joinedGamelobbyMode(){
        jassmendGameView.btnSend.setDisable(false);
        jassmendGameView.txt1.setDisable(false);
    }

    public void buttonsFalse() {
        for (int i = 0; i < 9; i++) {
           jassmendGameView.getPlayerPane(1).getCardBox().getChildren().get(i).setDisable(false);
            }
    }


    public void buttonsTrue() {
        for (int i = 0; i < 9; i++) {
            jassmendGameView.getPlayerPane(1).getCardBox().getChildren().get(i).setDisable(true);
        }
    }

    public void btnTrumpfFalse(){
      jassmendGameView.getBtnTrumpf().setDisable(false);
    }

    public void btnTrumpfTrue(){
        jassmendGameView.getBtnTrumpf().setDisable(true);
    }



 // Author: Florian Jäger
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
 // Author: Florian Jäger
//Player p = clientModel.getPlayer(clientModel.getClientPlayerID());!!!!
    private void applyCardImages(){
        Player p = jasmendModel.getPlayer(1);
        p.discardHand();
        for (int j = 0; j < Player.HAND_SIZE; j++) {
            Card card = handCards.get(j);
            p.addCard(card);
        }
        PlayerPane pp = jassmendGameView.getPlayerPane(1);
        pp.updatePlayerDisplay();

    }
 // Author: Florian Jäger
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

 // Author: Florian Jäger
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
 // Author: Florian Jäger
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
 // Author: Florian Jäger
    public String getFinalGamelobby(){
       return menuView.getFinalGamelobby();
    }
 // Author: Florian Jäger
    public void getGameView() {
        //added new fx thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getViewManager().primaryStage.setScene(JassmendGameView.getScene());
            }
        });
    }


 // Author: Florian Jäger
    public void setTextlblRoundCount(){
        jassmendGameView.getLblRound().setText("Round: "+roundcounter);
    }


 // Author: Florian Jäger
    //Update lbls on gameview
    public void setTextlblUsername() {

        int y =0;
        int playerID= 0;

        for(int v = 0; v< playerIDs.length; v++) {
            if (playerIDs[v].equals(jasmendModel.getUser())) {
                playerID = v;
            }
        }


        switch(playerID){
            case 0:
                jassmendGameView.getUserNamePl1().setText(playerIDs[0]);
                jassmendGameView.getUserNamePl2().setText(playerIDs[1]);
                jassmendGameView.getUserNamePl3().setText(playerIDs[2]);
                jassmendGameView.getUserNamePl4().setText(playerIDs[3]);
                jassmendGameView.getLblPl1().setText(playerIDs[0]);
                jassmendGameView.getLblPl2().setText(playerIDs[1]);
                jassmendGameView.getLblPl3().setText(playerIDs[2]);
                jassmendGameView.getLblPl4().setText(playerIDs[3]);

                break;
            case 1:
                jassmendGameView.getUserNamePl1().setText(playerIDs[1]);
                jassmendGameView.getUserNamePl2().setText(playerIDs[2]);
                jassmendGameView.getUserNamePl3().setText(playerIDs[3]);
                jassmendGameView.getUserNamePl4().setText(playerIDs[0]);
                jassmendGameView.getLblPl1().setText(playerIDs[0]);
                jassmendGameView.getLblPl2().setText(playerIDs[1]);
                jassmendGameView.getLblPl3().setText(playerIDs[2]);
                jassmendGameView.getLblPl4().setText(playerIDs[3]);
                break;
            case 2:
                jassmendGameView.getUserNamePl1().setText(playerIDs[2]);
                jassmendGameView.getUserNamePl2().setText(playerIDs[3]);
                jassmendGameView.getUserNamePl3().setText(playerIDs[0]);
                jassmendGameView.getUserNamePl4().setText(playerIDs[1]);
                jassmendGameView.getLblPl1().setText(playerIDs[0]);
                jassmendGameView.getLblPl2().setText(playerIDs[1]);
                jassmendGameView.getLblPl3().setText(playerIDs[2]);
                jassmendGameView.getLblPl4().setText(playerIDs[3]);
                break;
            case 3:
                jassmendGameView.getUserNamePl1().setText(playerIDs[3]);
                jassmendGameView.getUserNamePl2().setText(playerIDs[0]);
                jassmendGameView.getUserNamePl3().setText(playerIDs[1]);
                jassmendGameView.getUserNamePl4().setText(playerIDs[2]);
                jassmendGameView.getLblPl1().setText(playerIDs[0]);
                jassmendGameView.getLblPl2().setText(playerIDs[1]);
                jassmendGameView.getLblPl3().setText(playerIDs[2]);
                jassmendGameView.getLblPl4().setText(playerIDs[3]);
                break;
        }

    }
 // Author: Florian Jäger
    //GETTERS AND SETTERS---------------------------------------------------------------------------------------------
    public Label getLblTrumpf(){
        return jassmendGameView.getLblTrumpf();
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
//-------------------------------------------------------------------------------------------------------------------
 // Author: Florian Jäger
    //Read last message from text area
    public boolean readLastMessage(String lookFor) {
        int lastMessageIndex2 = jassmendLogView.areaMessages.getText().split("\n").length - 1;
        String lastMessage2 = jassmendLogView.areaMessages.getText().split("\n")[lastMessageIndex2];

        if (lastMessage2.startsWith(lookFor)) {
            return true;
        }
        else return false;
    }



    public TextArea getAreaMessages(){
        return jassmendLogView.areaMessages;
    }

}


