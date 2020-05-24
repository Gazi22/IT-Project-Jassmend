package Server.message;

import Server.Client;
import Server.Gamelobby;

//Inspired by Chat Server from Bradley Richards
//Author Florian Jäger

/**
 * Add a user as a member of a gamelobby.
 */
public class TurnManager extends Message {
	private String token;
	private String name;
	private String username;
	String roundCounter;



	public TurnManager(String[] data) {
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
	String turnMessage="";
	if (client.getToken().equals(token)) {
		Gamelobby gamelobby = Gamelobby.exists(name);
		//increases turncounter by 1
		gamelobby.increaseTurnCounter();


		int mod =gamelobby.getTurnCounter()%4;
		switch(mod){
			case 0:
				turnMessage="1"+"|"+"0"+"|"+"0"+"|"+"0";
				break;
			case 1:
				turnMessage="0"+"|"+"1"+"|"+"0"+"|"+"0";
				break;
			case 2:
				turnMessage="0"+"|"+"0"+"|"+"1"+"|"+"0";
				break;
			case 3:
				turnMessage="0"+"|"+"0"+"|"+"0"+"|"+"1";
				break;

			default:
				turnMessage="Error";
				break;
		}


		if (gamelobby.getTurnCounter()%36==0) {
			gamelobby.increaseRoundCounter();
		}
		roundCounter = Integer.toString(gamelobby.getRoundCounter());

	}




		String [] gameInfo = new String[4];
		gameInfo[1]=this.token;
		gameInfo[2]=this.name;


		gameInfo[3]="TurnInfo"+"|"+turnMessage+"|"+roundCounter;

		SendGameMessage msgGame=new SendGameMessage(gameInfo);

		msgGame.process(client);



	}
}
