package Jassmend;

import Server.Deck;

import jassmendModelClasses.Player;

import java.util.ArrayList;
import java.util.logging.Logger;
import  java.net.InetAddress;
import java.net.UnknownHostException;
//Author: Florian J�ger
public class JasmendModel {
	
	 InetAddress localHost;
	 String localIP;
     int clientPlayerID;
     int clientTurnPlayerID;
     //https://crunchify.com/how-to-get-server-ip-address-and-hostname-in-java/
{
     try {
    	 localHost = InetAddress.getLocalHost();
    	 localIP = localHost.getHostAddress();
         } 
     
     catch (UnknownHostException e) {
    
    	 e.printStackTrace();
     }
     // Author: Florian J�ger
     //end https://crunchify.com/how-to-get-server-ip-address-and-hostname-in-java/
     
}
	String defaultiIPAddress = localIP;
    int defaultPortNumber = 8080;

    private String ipAddress;
    private int port;
    private String user;
    private String hash;
    private String currentGamelobby;

    private Logger logger = Logger.getLogger("");
 // Author: Florian J�ger
    public void setIpAddress(String ip){
        this.ipAddress = ip;
    }
 // Author: Florian J�ger
    public String getIpAddress(){
        if (this.ipAddress != null){
            return this.ipAddress;
        } else {
            return "Not available";
        }
    }

    public void setPort(int port){
        this.port = port;
    }

    public int getPort(){
        if (this.port != 0){
            return this.port;
        } else {
            return 0;
        }
    }
    public void log_info(String message){
        logger.info(message);
    }

    public void log_error(String message){
        logger.severe(message);
    }

    public void log_warning(String message){
        logger.warning(message);
    }

    public void setUser(String username){
        this.user = username;
    }

    public void setClientPlayerID(int id){this.clientPlayerID=id;}

    public int getClientPlayerID(){
        return this.clientPlayerID;
    }

    public void setClientTurnPlayerID(int id){this.clientTurnPlayerID=id;}

    public int getClientTurnPlayerID(){
        return this.clientTurnPlayerID;
    }

    public String getUser(){
        return this.user;
    }

    public void setHash(String hash){
        this.hash = hash;
    }

    public String gethash(){
        return this.hash;
    }

    public String getCurrentGamelobby() {
        return currentGamelobby;
    }
    
    public void setCurrentgamelobby(String currentGamelobby) {
        this.currentGamelobby = currentGamelobby;
    }

    private final ArrayList<Player> players = new ArrayList<>();
    private Deck deck;


 // Author: Florian J�ger
    public JasmendModel() {
        for (int i = 0; i < JassmendMain.NUM_PLAYERS; i++) {
            players.add(new Player("Player " + i, 0));
        }


    }


    public Player getPlayer(int i) {
        return players.get(i);
    }

    public Deck getDeck() {
        return deck;
    }
}