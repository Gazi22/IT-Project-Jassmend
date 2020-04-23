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
	private int playerID= 0;
	private int numPlayers=0;

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
	if (playerID<5){	
		if (client.getToken().equals(token)) {
			Gamelobby gamelobby = Gamelobby.exists(name);
			if (gamelobby.getOwner().equals(client.getName())
					|| client.getName().equals(username) && gamelobby.isPublic()) {
				gamelobby.addUser(username);
				playerID=playerID++;
				result = true;
				System.out.println("Player "+playerID+" joined the gamelobby "+name);
			}
		}
		
	}
	client.send(new Result(result));
		
	}
}
