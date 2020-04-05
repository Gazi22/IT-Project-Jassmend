package client_v0;

import java.util.logging.Logger;

public class ClientModel {
	
    String defaultiIPAddress = "147.86.8.31";
    int defaultPortNumber = 50001;

    private String ipAddress;
    private int port;

    private String user;
    private String hash;
    private String currentChatroom;

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

    public String getCurrentChatroom() {
        return currentChatroom;
    }
    
    public void setCurrentChatroom(String currentChatroom) {
        this.currentChatroom = currentChatroom;
    }
}