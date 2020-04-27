package Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.logging.Logger;

import Server.message.Message;

/**
 * This class represents a gamelobby, which may be either public or private, and
 * has a list of accounts that are members. A gamelobby is "owned" by the user
 * who created it. If that user account is deleted, the gamelobby can be claimed
 * by the next person to create an account with the same name.
 * 
 * To avoid problems with duplicates, we use a TreeSet for the membership list.
 * We store usernames rather than account object, to make loading/saving data
 * simpler
 * 
 * At the class level, we maintain a list of all existing gamelobbys.
 */
public class Gamelobby implements Comparable<Gamelobby>, Sendable, Serializable {
	private static final long serialVersionUID = 1;

	private static Logger logger = Logger.getLogger("");

	private static final TreeSet<Gamelobby> gamelobbys = new TreeSet<>();

	private final String name;
	private final String owner; // username of an account
	private final boolean isPublic;
	private final ArrayList<String> users = new ArrayList<>();
	private Instant lastMessage;
	private final int maxPlayers =4;
	private String [] playerIDs = new String[maxPlayers];
	
	
	/**
	 * Add a new gamelobby to our list of gamelobbys
	 */
	public static void add(Gamelobby gamelobby) {
		gamelobbys.add(gamelobby);
	}

	/**
	 * Remove a gamelobby from our list of valid gamelobbys
	 */
	public static void remove(Gamelobby gamelobby) {
		synchronized (gamelobbys) {
			for (Iterator<Gamelobby> i = gamelobbys.iterator(); i.hasNext();) {
				if (gamelobby == i.next()) i.remove();
			}
		}
	}
	
	/**
	 * List gamelobby names
	 */
	public static ArrayList<String> listPublicNames() {
		ArrayList<String> names = new ArrayList<>();
		synchronized (gamelobbys) {
			for (Gamelobby c : gamelobbys) if (c.isPublic) names.add(c.name);
		}
		return names;
	}
	
	/**
	 * Find and return an existing gamelobby
	 */
	public static Gamelobby exists(String name) {
		synchronized (gamelobbys) {
			for (Gamelobby gamelobby : gamelobbys) {
				if (gamelobby.name.equals(name)){
					if(!gamelobby.isFull()) {
						return gamelobby;
				}
					
			}
		}
		}
		return null;
	}

	/**
	 * Clean up old gamelobbys -- called by cleanup thread
	 */
	public static void cleanupgamelobbys() {
		synchronized (gamelobbys) {
			logger.fine("Cleanup gamelobbys: " + gamelobbys.size() + " gamelobbys registered");
			Instant expiry = Instant.now().minusSeconds(3 * 86400); // 3 days
			for (Iterator<Gamelobby> i = gamelobbys.iterator(); i.hasNext();) {
				Gamelobby gamelobby = i.next();
				if (gamelobby.lastMessage.isBefore(expiry)) {
					logger.fine("Cleanup gamelobbys: removing gamelobby " + gamelobby.getName());
					i.remove();
				}
			}
			logger.fine("Cleanup gamelobbys: " + gamelobbys.size() + " gamelobbys registered");
		}
	}

	/**
	 * Save gamelobbys to disk -- called by cleanup thread
	 */
	public static void savegamelobbys() {
		File gamelobbyFile = new File(Server.getHome() + "gamelobby.sav");
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(gamelobbyFile))) {
			synchronized (gamelobbys) {
				out.writeInt(gamelobbys.size());
				for (Gamelobby gamelobby : gamelobbys) {
					out.writeObject(gamelobby);
				}
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			logger.severe("Unable to save gamelobbys: " + e.getMessage());
		}
	}

	/**
	 * Read gamelobbys at program start. No synchronization needed, since no threads
	 * are running
	 */
	public static void readgamelobbys() {
		File gamelobbyFile = new File(Server.getHome() + "gamelobby.sav");
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(gamelobbyFile))) {
			int num = in.readInt();
			for (int i = 0; i < num; i++) {
				Gamelobby gamelobby = (Gamelobby) in.readObject();
				gamelobbys.add(gamelobby);
				logger.fine("Loaded gamelobby " + gamelobby.getName());
			}
		} catch (Exception e) {
			logger.severe("Unable to read gamelobbys: " + e.getMessage());
		}
	}

	public Gamelobby(String name, boolean isPublic, String owner) {
		this.name = name;
		this.isPublic = isPublic;
		this.owner = owner;
		this.lastMessage = Instant.now();
	}

	@Override // Sendable
	public String getName() {
		return name;
	}

	/**
	 * Send a message to every user of this gamelobby who is logged on. Users may
	 * remain in a gamelobby after logout or disconnection, so we remove inactive
	 * users when we find them.
	 * 
	 * Note: If multiple clients are logged in with the same name, only one will
	 * receive a message. An alternative would be to iterate through the
	 * clients-list and match names, but this would require synchronization on the
	 * clients list, and our I/O may be slow. One would also need to put the cleanup
	 * of invalid usernames into the Client cleanup code, rather than doing it here.
	 */
	@Override // Sendable
	public void send(Message msg) {
		Iterator<String> i = users.iterator();
		while (i.hasNext()) {
			String username = i.next();
			Client user = Client.exists(username);
			if (user == null) i.remove();
			else // User exists
				user.send(msg);
		}
		this.lastMessage = Instant.now();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass()) return false;
		Gamelobby oc = (Gamelobby) o;
		return oc.name.equals(this.name);
	}

	@Override
	public int compareTo(Gamelobby c) {
		return name.compareTo(c.name);
	}

	public String getOwner() {
		return owner;
	}
	
	//check array full
	public boolean isFull() {
		for (int x=0; x < playerIDs.length; x++)
	           if (playerIDs[x] == null) {
	        	   return false;
	           }
		return true;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void addUser(String username) {
		if (!users.contains(username)) 
		{users.add(username);}
		
		if(playerIDs[0]==null) {
			playerIDs[0]=username;
			System.out.println(username+" ist Player 1");
		}
		
		else {
			if(playerIDs[1]==null) {
				playerIDs[1]=username;
				System.out.println(username+" ist Player 2");}
			else {
				if(playerIDs[2]==null) {
					playerIDs[2]=username;
					System.out.println(username+" ist Player 3");
			}
									
					else playerIDs[3]=username;
				System.out.println(username+" ist Player 4");}
			}
		}
		
		
	

	public void removeUser(String username) {
		users.remove(username);
		System.out.println(username + " left the gamelobbys!");
		for (int x=0; x < playerIDs.length; x++)
	           if (playerIDs[x] == username) {
	        	   playerIDs[x]= null;
	           }
		
	}
	
	public ArrayList<String> getUsers() {
		return users; // Arguably, we should return only a copy
	}
}
