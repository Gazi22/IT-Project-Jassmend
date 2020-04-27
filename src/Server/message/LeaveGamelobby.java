package Server.message;

import Server.Gamelobby;
import Server.Client;

/**
 * Remove a user as a member of a gamelobby
 */
public class LeaveGamelobby extends Message {
	private String token;
	private String name;
	private String username;

	public LeaveGamelobby(String[] data) {
		super(data);
		this.token = data[1];
		this.name = data[2];
		this.username = data[3];
	}

	/**
	 * The owner of a gamelobby can remove anyone. A user can remove themselves.
	 * Otherwise, no one can remove anyone.
	 */
	@Override
	public void process(Client client) {
		boolean result = false;
		if (client.getToken().equals(token)) {
			Gamelobby gamelobby = Gamelobby.exists(name);
			if (gamelobby.getOwner().equals(client.getName())
					|| client.getName().equals(username)) {
				gamelobby.removeUser(username);
				result = true;
			}
		}
		client.send(new Result(result));
		System.out.println(username+"left"+name);
	}
}
