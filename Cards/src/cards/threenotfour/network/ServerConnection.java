package cards.threenotfour.network;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * This class connects to the match-making server at the start of the game to
 * get a free game slot.
 * 
 * @author sg3809
 * 
 */
public class ServerConnection extends Connection {

	public static final String SERVER_IP = "ghimire.tk";
	public static final int SERVER_PORT = 59422;

	public ServerConnection() throws UnknownHostException, IOException {
		super(SERVER_IP, SERVER_PORT);
	}

}
