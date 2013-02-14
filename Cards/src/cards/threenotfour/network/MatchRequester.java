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
			if (jsonReply.get(JSONConstant.STATUS).equals(JSONConstant.OK)) {
				String getReplyMessage = (String) jsonReply.get(JSONConstant.REQUEST);

				if (getReplyMessage == null) {
					// The server just sent a ok message. Just sit still.
				} else if (getReplyMessage != null && getReplyMessage.equals(JSONConstant.START)) {
					System.out.println("I have been asked to start a game!!");
					System.out.println(reply);
					break;
				}
			}

		}

	}
}
