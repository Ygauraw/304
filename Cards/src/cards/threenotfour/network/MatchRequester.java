package cards.threenotfour.network;

import java.io.IOException;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cards.threenotfour.constants.JSONConstant;

/**
 * This class interfaces with the server at the start to get either
 * 
 * 1. The host of the game 2. Or a list of other players.
 * 
 * @author sg3809
 * 
 */
public class MatchRequester {

	private final Connection serverConnection;
	private final JSONParser parser;

	private String host_ip;

	public MatchRequester() throws UnknownHostException, IOException {
		serverConnection = new ServerConnection();
		parser = new JSONParser();
		host_ip = "";
	}

	/**
	 * Sends messages to the match-maker to get a game.
	 * 
	 * @return True iff we are the host, else false
	 * @throws ParseException
	 * @throws IOException
	 */
	public boolean getGame() throws ParseException, IOException {

		// Initially send a {"req":"new_game"} message a reply.
		JSONObject object = new JSONObject();
		object.put(JSONConstant.REQUEST, JSONConstant.NEW_GAME);
		serverConnection.sendMessage(object.toJSONString());

		// Then wait for a reply
		while (true) {
			String reply = serverConnection.receiveMessage();
			JSONObject jsonReply = (JSONObject) parser.parse(reply);

			int id = processMessage(jsonReply);
			if (id != 0) {
				closeConnection();
				return id == -1;
			}
		}
	}

	/**
	 * Processes the messages received and takes appropriate actions.
	 * 
	 * @param jsonMessage
	 * @return -1 to start as a host; 0 to continue receiving messages, 1 to
	 *         start as a peer.
	 */
	private int processMessage(JSONObject jsonMessage) {
		if (jsonMessage.get(JSONConstant.STATUS).equals(JSONConstant.OK)) {
			String getReplyMessage = (String) jsonMessage.get(JSONConstant.REQUEST);

			if (getReplyMessage == null) {
				// The server just sent a ok message. Just sit still.
			} else if (getReplyMessage.equals(JSONConstant.START_AS_HOST)) {
				// Start the game as a host
				serverConnection.sendOk();
				return -1;
			} else if (getReplyMessage.equals(JSONConstant.START)) {
				// Start the game as a peer.
				host_ip = (String) jsonMessage.get(JSONConstant.HOST);
				serverConnection.sendOk();
				return 1;
			}
		}
		return 0;
	}

	/**
	 * Frees all the socket resources.
	 * 
	 * @throws IOException
	 */
	private void closeConnection() throws IOException {
		serverConnection.close();
	}

	public String getHost_ip() {
		return host_ip;
	}

}
