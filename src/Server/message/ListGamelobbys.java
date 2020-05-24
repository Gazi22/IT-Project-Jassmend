package Server.message;

import java.util.ArrayList;

import Server.Gamelobby;
import Server.Client;

//Chat Server from Bradley Richards

public class ListGamelobbys extends Message {
	private String token;
	
	public ListGamelobbys(String[] data) {
		super(data);
		this.token = data[1];
	}

	@Override
	public void process(Client client) {
		if (client.getToken().equals(token)) {
			ArrayList<String> names = Gamelobby.listPublicNames();
			client.send(new Result(true, names));
		} else {
			client.send(new Result(false));
		}
	}
}
