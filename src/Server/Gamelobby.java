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
import Jassmend.JasmendModel;
import jassmendModelClasses.Card;
import jassmendModelClasses.Player;

//Chat Server from Bradley Richards
//Author Florian Jäger



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
	private JasmendModel jasmendModel;
	private final String name;
	private final String owner; // username of an account
	private final boolean isPublic;
	private final ArrayList<String> users = new ArrayList<>();
	private Instant lastMessage;
	private final int maxPlayers = 4;
	private String[] playerIDs = new String[maxPlayers];
	private ArrayList<Card> playerHand = new ArrayList<>(9);
	private ArrayList<String> cardsDealt = new ArrayList<>(36);
	private Deck deck;
	private int turnCounter = 0;
	private String[] cardsInRound = new String[4];
	private int roundCounter = 0;
	private ArrayList<String> cardsTotalString = new ArrayList<>();
	private ArrayList<Card> sticheTeam1 = new ArrayList<>();
	private ArrayList<Card> sticheTeam2 = new ArrayList<>();
	private int cardCounter = 0;
	private String trumpf="Herz";
	private String [] team1Members=new String[2];
	private String [] team2Members=new String[2];
	private String firstCardInTurn;
	private String userPlayingCard;
	private ArrayList<String> cardsWithNames = new ArrayList<>();
	private String stichWinner="";
	private int stichCounter=0;
	private int pointsTeam1;
	private int pointsTeam2;

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
			for (Iterator<Gamelobby> i = gamelobbys.iterator(); i.hasNext(); ) {
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
				if (gamelobby.name.equals(name)) {
					return gamelobby;
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
			for (Iterator<Gamelobby> i = gamelobbys.iterator(); i.hasNext(); ) {
				Gamelobby gamelobby = i.next();
				logger.fine("Cleanup gamelobbys: removing gamelobby " + gamelobby.getName());
				i.remove();

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

	//Added Deck
	public Gamelobby(String name, boolean isPublic, String owner) {
		this.name = name;
		this.isPublic = isPublic;
		this.owner = owner;
		this.lastMessage = Instant.now();
		deck = new Deck();
	}

	@Override // Sendable
	public String getName() {
		return name;
	}

	/**
	 * Send a message to every user of this gamelobby who is logged on. Users may
	 * remain in a gamelobby after logout or disconnection, so we remove inactive
	 * users when we find them.
	 * <p>
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
	//Author Florian Jäger
	public boolean isFull() {
		boolean full = true;
		for (int i = 0; i < playerIDs.length; i++) {
			if (playerIDs[i] == (null)) {
				full = false;
				break;
			}
		}
		return full;
	}

	public boolean isPublic() {
		return isPublic;
	}


	//Changed by Florian Jäger
	public void addUser(String username) {
		if (!users.contains(username)) {
			users.add(username);
		}

		if (playerIDs[0] == null) {
			playerIDs[0] = username;
			System.out.println(username + " ist Player 1");
		} else if (playerIDs[1] == null) {
			playerIDs[1] = username;
			System.out.println(username + " ist Player 2");
		} else if (playerIDs[2] == null) {
			playerIDs[2] = username;
			System.out.println(username + " ist Player 3");
		} else if (playerIDs[3] == null) {
			playerIDs[3] = username;
			System.out.println(username + " ist Player 4");
		}


	}


	//Added from Pokerproject from Bradley Richards, changed by Florian Jäger
	public void deal() {


		Deck deck = getDeck();
		for (int j = 0; j < Player.HAND_SIZE; j++) {
			Card card = deck.dealCard();
			playerHand.add(card);
			cardsDealt.add(card.toString());

		}
	}


	public Card getPlayerHand(int i) {
		return playerHand.get(i);

	}

	public int getTurnCounter() {
		return turnCounter;
	}

	public void increaseTurnCounter() {
		turnCounter++;
	}

	public String getPlayerIDs(int i) {
		for (int x = 0; x < playerIDs.length; x++) {
			return playerIDs[i];
		}
		return null;
	}

	public void clearPlayerHand() {
		playerHand.clear();
	}


	//Author Bradley Richards, changed by Florian Jäger
	public void removeUser(String username) {
		users.remove(username);
		for (int x = 0; x < playerIDs.length; x++) {
			if (playerIDs[x].equals(username)) {
				playerIDs[x] = null;
				System.out.println(username + " left the gamelobbys!" + playerIDs[x] + "is null");
			}
		}
	}

	public ArrayList<String> getUsers() {
		return users; // Arguably, we should return only a copy
	}



	//Getters and Setters------------------------------------------------------------------------
	public Deck getDeck() {
		return deck;
	}

	public void addCard(Card card) {
		if (playerHand.size() < 9) playerHand.add(card);
	}

	public void setCardsInRound(int x, String card) {
		cardsInRound[x-1] = card;
	}

	public String getCardsInRound(int i) {
		return cardsInRound[i];
	}

	public int getRoundCounter() {
		return roundCounter;
	}

	public void increaseRoundCounter() {
		roundCounter++;
	}

	public void resetRoundCounter(){
		roundCounter=0;
	}
	public int getCardCounter() {
		return cardCounter;
	}
	public void decreaseCardCounter() {
		cardCounter--;
	}
	public void increaseCardCounter() {
		cardCounter++;
	}

	public void resetCardCounter() {
		cardCounter = 0;
	}

	public void setSticheTeam1(Card card) { sticheTeam1.add(card);
	}
	public void setSticheTeam2(Card card) {
		sticheTeam2.add(card);
	}

	public Card getSticheTeam1(int i){
		return sticheTeam1.get(i);
	}
	public Card getSticheTeam2(int i){
		return sticheTeam2.get(i);
	}

	public int getSizeSticheTeam1(){
		return sticheTeam1.size();
	}
	public int getSizeSticheTeam2(){
		return sticheTeam2.size();
	}
	public int getSizeCardsInRound(){
		return cardsInRound.length;
	}

	public void addToCardsTotal(String card){
		cardsTotalString.add(card);}

	public String getCardsTotal(int i){
		return cardsTotalString.get(i);
	}

	public void setTrumpf(String trumpf){
		this.trumpf=trumpf;
	}

	public String getTrumpf(){
		return trumpf;
	}


	public void addToTeam1(int i,String username){
		team1Members[i]=username;

 	}
	public void addToTeam2(int i,String username){
		team2Members[i]=username;

	}

	public String getTeam1Members(int i){
		return team1Members[i];
	}

	public String getTeam2Members(int i){
		return team2Members[i];
	}

	public String getUserPlayingCard() {
		return userPlayingCard;
	}
	public void setUserPlayingCard(String username){
		this.userPlayingCard=username;
	}

	public void addCardsWithNames(String card){
		cardsWithNames.add(card);

			}


	public String getCardsWithNames(int i){
		return cardsWithNames.get(i);
	}

	public void clearCardsWithNames(){
		cardsWithNames.clear();
	}

	public void clearTotalCards(){
		cardsTotalString.clear();
	}

	public String getFirstCardInTurn(){
		return firstCardInTurn;
	}

	public void setFirstCardInTurn(String card){
		firstCardInTurn=card;
	}

	public void clearCardsDealt(){
		cardsDealt.clear();
	}
	public String getCardsDealt(int i){
		return cardsDealt.get(i);
	}
	public void setCardsDealtNull(int i){cardsDealt.set(i,null);}

	public void clearSticheTeams(){
		sticheTeam1.clear();
		sticheTeam2.clear();
	}

	public void setStichWinner(String username){
		stichWinner=username;
	}
	public String getStichWinner(){
		return stichWinner;
	}

	public int getStichCounter() {
		return stichCounter;
	}

	public void increaseStichCounter() {
		stichCounter++;
	}

	public void resetStichCounter(){
		stichCounter=0;
	}

	public int getPointsTeam1() {
		return pointsTeam1;
	}

	public void setPointsTeam1(int pointsTeam1) {
		this.pointsTeam1 = pointsTeam1;
	}

	public int getPointsTeam2() {
		return pointsTeam2;
	}

	public void setPointsTeam2(int pointsTeam2) {
		this.pointsTeam2 = pointsTeam2;
	}
}
