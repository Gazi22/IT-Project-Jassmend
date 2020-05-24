package Server.message;

import Server.Client;

/**
 * Add a user as a member of a gamelobby.
 */

//Inspired by Chat Server from Bradley Richards
//Author Florian Jäger
public class GamelobbyIsFull extends Message {
	private String token;
	private String name;
	private String username;



	public GamelobbyIsFull(String[] data) {
		super(data);
		this.token = data[1];
		this.name = data[2];
		this.username = data[3];
	}



	/**
	 * The owner of a gamelobby can add anyone. A user can add themselves to any
	 * public gamelobby. Otherwise, no one can add anyone.
	 */
	@Override
	public void process(Client client) {
		String [] gameInfo = new String[4];
		gameInfo[1]=this.token;
		gameInfo[2]=this.name;
		gameInfo[3]="GamelobbyFull";

		SendGameMessage msgGame=new SendGameMessage(gameInfo);

		msgGame.process(client);



	}
}
