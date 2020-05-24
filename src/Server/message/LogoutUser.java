package Server.message;

import Server.Account;
import Server.Client;

/**
 * Login to an existing account. If successful, return an authentication token
 * to the client.
 */
public class LogoutUser extends Message {
	private String username;


	public LogoutUser(String[] data) {
		super(data);
		this.username = data[1];
		}

	@Override
	public void process(Client client) {
		Message reply;
		// Find existing login matching the username
		Account account = Account.exists(username);
		if (account != null ) {
			account.removeUsersLoggedIn(username);
			reply = new Result(true);
		} else {
			reply = new Result(false);
		}
		client.send(reply);
	}
}
