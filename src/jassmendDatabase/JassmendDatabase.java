package jassmendDatabase;

import java.sql.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import client_v0.*;


import java.util.Date;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;


public class JassmendDatabase {
	
	// Author: Gazmend Shefiu || Code from Akatsuki Project
	final String sqlUrl = "jdbc:mysql://eu-cdbr-west-03.cleardb.net/heroku_ffd397e5d72f806?reconnect=true";
	final String sqlUser = "b3ab4e3875375e";
	final String sqlPw = "10ff5735";
	public final String insertQuery = "INSERT INTO `UserInformation`(`username`, `userpassword`)"
		    + "VALUES (?, ?)";
	// https://stackoverflow.com/questions/46582390/how-to-prevent-duplication-java-sql
	public final String checkQuery = "SELECT * FROM userinformation WHERE Username = ?";
	private String userName;
	private String userPassword;
	private JasmendController jasmendController;
	private Date dateLastRequest;
	private JassmendAccountView createAccView;
	private Connection myConnection = null;
	private static JassmendDatabase databaseDriver;
	private ResultSet rs;
	private PreparedStatement stmt = null;
	private PreparedStatement stmt2 = null;
	private boolean accCreationOk;
	private String userNameLogin;
	private String userPasswordLogin;
	private String localIP;
	private String errorCode;
	
	public static JassmendDatabase getInstance() {
	        if(databaseDriver == null){
	        	databaseDriver = new JassmendDatabase();
	        }
	        return  databaseDriver;
	    }
	 
	// Author: Gazmend Shefiu || Code from Akatsuki Project
    public boolean connectDB() {   

        try {
            System.out.println("Connecting");
            myConnection = DriverManager.getConnection(sqlUrl, sqlUser, sqlPw);
            System.out.println("Connected to the DB!");
            this.dateLastRequest = new Date();
            return true;
        } catch (SQLException e) {

            System.out.println(e.toString());
            //errorCode = (e.toString());
            infoBox("Database Connection Error", "Could not connect to database, please ensure you're connected to the internet");
            return false;
        }


    }
    
 // Code provided by Mr. Bradley Richards || Author: Gazmend Shefiu
    public void clearResource() { 
        try {
            System.out.println("Resources are being freed");
            if (rs != null) try {
                if (!rs.isClosed()) rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                if (!stmt.isClosed()) stmt.close();
            } catch (Exception e) {
            }
            if (myConnection != null) try {
                if (!myConnection.isClosed()) myConnection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Author: Gazmend Shefiu || Code from Akatsuki Project
    public boolean checkRegistratedAccDB(String userName) {
    
    		this.userName = userName;
    		
    	try {
    		connectDB();
    		stmt2 = myConnection.prepareStatement(checkQuery);
    		stmt2.setString(1, "Username");
    		rs = stmt2.executeQuery();
    		while(rs.next()) {
    			String userNameFetch = rs.getString("Username");

                return !userNameFetch.equals(userName);
   
    		}
    		
    		
    	} catch (Exception e) {
    		
    	
    	} finally {
    		clearResource();
    	}
    	return true;
    }
	
    // Author: Gazmend Shefiu || Code from Akatsuki Project
    public void registerAccDB(String userName, String userPassword) { 

    	this.userName = userName;
    	this.userPassword = userPassword;
    	
        try {
        		connectDB();
        		System.out.println("Sending data to db");
        		
            	stmt = myConnection.prepareStatement(insertQuery);
            	String generatedSecuredPasswordHash = generateStrongPasswordHash(userPassword);
            	stmt.setString(1, userName);
            	stmt.setString(2, generatedSecuredPasswordHash);
            	stmt.executeUpdate();
            	System.out.println("Success! Account created!");
            	accCreationOk = true;
					 
  
        } catch (Exception e) {
        	System.out.println("Error creating acc : " + e);
            infoBox("Error","Username already exists, please choose another!");
            accCreationOk = false;
            
        } finally {
         clearResource();
        }
        
    }
    	// Author: Gazmend Shefiu || Code from Akatsuki Project
    
        public void checkLogin(String userNameLogin, String userPasswordLogin) {

            try {
                this.userNameLogin = userNameLogin;
                this.userPasswordLogin = userPasswordLogin;

                connectDB();

                stmt = myConnection.prepareStatement(checkQuery);

                stmt.setString(1, userNameLogin);
                //stmt.setString(2, accPw);

                // compareEntryDB();
                rs = stmt.executeQuery();
                while (rs.next()) {

                    String user = rs.getString("Username");
                    String pass = rs.getString("Userpassword");

    //
                    if (user.equals(userNameLogin) && validatePassword(userPasswordLogin, pass)) {


                        stmt = myConnection.prepareStatement(checkQuery);
                        stmt.setString(1, getIpAdress());
                        stmt.setString(2, userNameLogin);
                        stmt.executeUpdate();

                        System.out.println("Acc logged in! Connected!");
                    }
                }


            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Login failed! Check credentials" );

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } finally {

                clearResource();
            }
        }
     // Author: Gazmend Shefiu || Code from Akatsuki Project
        public String getIpAdress(){    
            try {
                localIP = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e) {
                e.printStackTrace();
            }

        return localIP;
    }
    
     // Author: Gazmend Shefiu || Code from Akatsuki Project
    private static String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
        
        
    }
 // Author: Gazmend Shefiu || Code from Akatsuki Project
    private static byte[] getSalt() throws NoSuchAlgorithmException /** https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/ */
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
 // Author: Gazmend Shefiu || Code from Akatsuki Project
    private static String toHex(byte[] array)    /** https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/ */
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
 // Author: Gazmend Shefiu || Code from Akatsuki Project
    private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    // Author: Gazmend Shefiu || Code from Akatsuki Project
    /** https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/ */
    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
	
    	// Author: Gazmend Shefiu
		//https://stackoverflow.com/questions/7080205/popup-message-boxes
		public static void infoBox(String infoMessage, String titleBar)
	    {
	        /* By specifying a null headerMessage String, we cause the dialog to
	           not have a header */
	        infoBox(infoMessage, titleBar, null);
	    }

	    public static void infoBox(String infoMessage, String titleBar, String headerMessage)
	    {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle(titleBar);
	        alert.setHeaderText(headerMessage);
	        alert.setContentText(infoMessage);
	        alert.showAndWait();
	    }

	 // Author: Gazmend Shefiu || Code from Akatsuki Project
	    public String getLocalIp() {
	        return localIP;
	    }

	    public void setLocalIp(String localIp) {
	        this.localIP = localIp;
	    }


	    public static JassmendDatabase getDataBaseDriver() {
	        return databaseDriver;
	    }

	    public static void setDataBaseDriver(JassmendDatabase databaseDriver) {
	    	JassmendDatabase.databaseDriver = databaseDriver;
	    }


	    public String getpWord() {
	        return userPassword;
	    }

	    public void setpWord(String userPassword) {
	        this.userPassword = userPassword;
	    }

	    public String getuName() {
	        return userName;
	    }

	    public void setuName(String userName) {
	        this.userName = userName;
	    }


	    public Connection getCn() {
	        return myConnection;
	    }

	    public void setCn(Connection myConnection) {
	        this.myConnection = myConnection;
	    }

	    public Statement getStmt() {
	        return stmt;
	    }

	    public void setStmt(PreparedStatement stmt) {
	        this.stmt = stmt;
	    }

	    public ResultSet getRs() {
	        return rs;
	    }

	    public void setRs(ResultSet rs) {
	        this.rs = rs;
	    }

	    public Date getDateLastRequested() {
	        return dateLastRequest;
	    }

	    public void setDateLastRequested(Date dateLastRequested) {
	        this.dateLastRequest = dateLastRequested;
	    }

	    public String getErrorCode() {
	        return errorCode;
	    }

	    public void setErrorCode(String errorCode) {
	        this.errorCode = errorCode;
	    }

	    public String getAccUser() {
	        return userNameLogin;
	    }

	    public void setAccUser(String userNameLogin) {
	        this.userNameLogin = userNameLogin;
	    }

	    public String getAccPw() {
	        return userPasswordLogin;
	    }

	    public void setAccPw(String accPw) {
	        this.userPasswordLogin = accPw;
	    }

	    public boolean isAccCreationOk() {
	        return accCreationOk;
	    }

	    public void setAccCreationOk(boolean accCreationOk) {
	        this.accCreationOk = accCreationOk;
	    }
	

}