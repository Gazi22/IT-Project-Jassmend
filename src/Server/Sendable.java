package Server;

import Server.message.Message;

//Chat Server from Bradley Richards

/**
 * We can send messages to a class that implements this interface.
 */
public interface Sendable {	
	String getName(); // The name of the target being sent a message
	void send(Message msg); // The method to sent to this target
}
