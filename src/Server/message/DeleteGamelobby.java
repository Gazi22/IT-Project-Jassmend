package Server.message;

import Server.Gamelobby;
import Server.Client;

//Chat Server from Bradley Richards

public class DeleteGamelobby extends Message {
	private String token;
	private String name;
	
	public DeleteGamelobby(String[] data) {
		super(data);
		this.token = data[1];
		this.name = data[2];
	}

	/**
	 * Only the owner of a gamelobby can delete it
	 */
	@Override
	public void process(Client client) {
		boolean result = false;
		if (client.getToken().equals(token)) {
			Gamelobby gamelobby = Gamelobby.exists(name);
			if (gamelobby != null && gamelobby.getOwner().equals(client.getName())) {
				Gamelobby.remove(gamelobby);
				result = true;
			}
		}
		client.send(new Result(result));
	}


}
