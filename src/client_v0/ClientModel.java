package client_v0;

import java.util.logging.Logger;
import  java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientModel {
	
	 InetAddress localHost;
	 String localIP;
          
     //https://crunchify.com/how-to-get-server-ip-address-and-hostname-in-java/
{
     try {
    	 localHost = InetAddress.getLocalHost();
    	 localIP = localHost.getHostAddress();
         } 
     
     catch (UnknownHostException e) {
    
    	 e.printStackTrace();
     }
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

    public void setIpAddress(String ip){
        this.ipAddress = ip;
    }

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
}