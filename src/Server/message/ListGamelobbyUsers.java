package Server.message;

import java.util.ArrayList;

import Server.Gamelobby;
import Server.Client;

public class ListGamelobbyUsers extends Message {
	private String token;
	private String name;

	public ListGamelobbyUsers(String[] data) {
		super(data);
		this.token = data[1];
		this.name = data[2];
	}

	/**
	 * Only allowed if the chatorom is public, or the client is a member of the
	 * chatroom
	 */
	@Override
	public void process(Client client) {
		boolean result = false;
		ArrayList<String> names = null;
		if (client.getToken().equals(token)) {
			Gamelobby chatroom = Gamelobby.exists(name);
			if (chatroom != null) {
				names = chatroom.getUsers();
				if (chatroom.isPublic() || names.contains(client.getName())) {
					result = true;
				}
			}
		}

		if (result) {
			client.send(new Result(true, names));
		} else {
			client.send(new Result(false));
		}
	}
}
