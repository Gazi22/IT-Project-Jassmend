package Server.message;

import Server.Client;

public class MessageGameText extends Message {

	public MessageGameText(String name, String target, String message) {
		super(new String[] {"MessageGameText", name, target, message});
	}
	
	/**
	 * This message type does no processing at all
	 */
	@Override
	public void process(Client client) {
	}
}
