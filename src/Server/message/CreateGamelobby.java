package Server.message;

import Server.Account;
import Server.Gamelobby;
import Server.Client;

//Chat Server from Bradley Richards


public class CreateGamelobby extends Message {
	private String token;
	private String name;
	private boolean isPublic;
	
	public CreateGamelobby(String[] data) {
		super(data);
		this.token = data[1];
		this.name = data[2];
		this.isPublic = data[3].equalsIgnoreCase("true");
	}


	@Override
	public void process(Client client) {
		boolean result = false;
		if (client.getToken().equals(token)) {
			if (name.length() >= 3 && Account.exists(name) == null && Gamelobby.exists(name) == null) {
				Gamelobby gamelobby = new Gamelobby(name, isPublic, client.getName());
				Gamelobby.add(gamelobby);
				result = true;				
			}
		}
		client.send(new Result(result));
	}
}