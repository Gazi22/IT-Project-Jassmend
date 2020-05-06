package Server.message;

import Server.Client;


import Server.Gamelobby;
import Server.Client;

/**
 * Add a user as a member of a gamelobby.
 */
public class StartGame extends Message {
	private String token;
	private String name;
	private String username;



	public StartGame(String[] data) {
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
		gameInfo[3]="StartGame";

		SendGameMessage msgGame=new SendGameMessage(gameInfo);

		msgGame.process(client);



	}
}
