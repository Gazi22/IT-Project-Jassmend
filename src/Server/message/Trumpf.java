package Server.message;

import Server.Client;
import Server.Gamelobby;

//Inspired by Chat Server from Bradley Richards
//Author Florian Jäger

/**
 * Add a user as a member of a gamelobby.
 */
public class Trumpf extends Message {
	private String token;
	private String name;
	private String username;
	private  String trumpf;




	public Trumpf(String[] data) {
		super(data);
		this.token = data[1];
		this.name = data[2];
		this.username = data[3];
		this.trumpf =data[4];
	}

	
	
	/**
	 * The owner of a gamelobby can add anyone. A user can add themselves to any
	 * public gamelobby. Otherwise, no one can add anyone.
	 */
	@Override
	public void process(Client client) {
		
		boolean result = false;

		if (client.getToken().equals(token)) {
			Gamelobby gamelobby = Gamelobby.exists(name);

			gamelobby.setTrumpf(trumpf);
			result=true;
		}



	String [] gameInfo = new String[4];
	gameInfo[1]=this.token;
	gameInfo[2]=this.name;
	gameInfo[3]="Trumpf"+"|"+trumpf;

	SendGameMessage msgGame=new SendGameMessage(gameInfo);
	if (result = true){
		msgGame.process(client);
		}
	else client.send(new Result(result));
		
}
}
