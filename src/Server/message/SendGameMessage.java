package Server.message;

import Server.Client;
import Server.Gamelobby;
import Server.Sendable;

public class SendGameMessage extends Message {
	private String token;
	private String destination;
	private String message;

	public SendGameMessage(String[] data) {
		super(data);
		this.token = data[1];
		this.destination = data[2];
		this.message = data[3];
	}

	/**
	 * The destination may be either a chatroom or an individual user.
	 * Messages can be between 1 and 1024 characters long.
	 */
	@Override
	public void process(Client client) {
		boolean result = false;
		if (client.getToken().equals(token) && message.length() >= 1 && message.length() <= 1024) {
			Sendable target = null;
			// First check for an individual user
			Client user = Client.exists(destination);
			if (user != null && user.getToken() != null) target = user; // user exists and is logged in
			
			// If not an individual user, try a chatroom
			if (target == null) {
				Gamelobby chatroom = Gamelobby.exists(destination);
				if (chatroom != null) target = chatroom;
			}

			if (target != null) { // If we have a target for the message, send it
				target.send(new MessageGameText(client.getName(), target.getName(), message));
				result = true;
			}
		}

	}
}