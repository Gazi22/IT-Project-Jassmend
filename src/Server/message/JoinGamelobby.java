package Server.message;

import Server.Gamelobby;
import Server.Client;

/**
 * Add a user as a member of a gamelobby.
 */
public class JoinGamelobby extends Message {
	private String token;
	private String name;
	private String username;
	


	public JoinGamelobby(String[] data) {
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
		
		boolean result = false;
		String [] ids = new String[4];
		if (client.getToken().equals(token)) {
			Gamelobby gamelobby = Gamelobby.exists(name);
			if(!gamelobby.isFull()) {
				if (gamelobby.getOwner().equals(client.getName()) || client.getName().equals(username) && gamelobby.isPublic()) {



						gamelobby.addUser(username);
						ids[0] = gamelobby.getPlayerIDs(0);
						ids[1] = gamelobby.getPlayerIDs(1);
						ids[2] = gamelobby.getPlayerIDs(2);
						ids[3] = gamelobby.getPlayerIDs(3);


						if (ids[3] != null) {
							//Add to Teams
							gamelobby.addToTeam1(0, ids[0]);
							gamelobby.addToTeam1(1, ids[2]);
							gamelobby.addToTeam2(0, ids[1]);
							gamelobby.addToTeam2(1, ids[3]);
							result = true;
						}
					}

			}
		}

	String [] gameInfo = new String[4];
	gameInfo[1]=this.token;
	gameInfo[2]=this.name;
	gameInfo[3]="PlayerIDs"+"|"+ids[0]+"|"+ids[1]+"|"+ids[2]+"|"+ids[3];

	SendGameMessage msgGame=new SendGameMessage(gameInfo);
	if (result = true){
		msgGame.process(client);
		}
	else client.send(new Result(result));
		
}
}
