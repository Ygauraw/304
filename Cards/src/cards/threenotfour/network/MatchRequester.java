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

	public MatchRequester() throws UnknownHostException, IOException {
		serverConnection = new ServerConnection();
		parser = new JSONParser();
	}

	public void getGame() throws ParseException {

		// Initially send a {"req":"new_game"} message a reply.
		JSONObject object = new JSONObject();
		object.put(JSONConstant.REQUEST, JSONConstant.NEW_GAME);
		serverConnection.sendMessage(object.toJSONString());

		// Then wait for a reply
		while (true) {
			String reply = serverConnection.receiveMessage();
			JSONObject jsonReply = (JSONObject) parser.parse(reply);

			if (processMessage(jsonReply)) {
				break;
			}
		}

		try {
			closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean processMessage(JSONObject jsonMessage) {
		if (jsonMessage.get(JSONConstant.STATUS).equals(JSONConstant.OK)) {
			String getReplyMessage = (String) jsonMessage.get(JSONConstant.REQUEST);

			if (getReplyMessage == null) {
				// The server just sent a ok message. Just sit still.
			} else if (getReplyMessage.equals(JSONConstant.START_AS_HOST)) {
				// Start the game as a host
				createGame();
				serverConnection.sendOk();
				return true;
			} else if (getReplyMessage.equals(JSONConstant.START)) {
				// Start the game as a peer.
				serverConnection.sendOk();
			}
		}
		return false;
	}

	private void createGame() {
		System.out.println("I have been asked to start a game!!");
		GameHoster gameHoster = new GameHoster();

	}

	/**
	 * Frees all the socket resources.
	 * 
	 * @throws IOException
	 */
	private void closeConnection() throws IOException {
		serverConnection.close();
	}

}
