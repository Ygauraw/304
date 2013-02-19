package cards.threenotfour.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cards.threenotfour.constants.JSONConstant;
import cards.threenotfour.constants.NetworkConstants;
import cards.threenotfour.log.Log;

public class GameJoiner {

	private final Connection connection;
	private final JSONParser parser = new JSONParser();

	public GameJoiner(String ip) throws UnknownHostException, IOException {
		connection = new Connection(InetAddress.getByName(ip), NetworkConstants.CLIENT_PORT);
	}

	public void startTask() {
		connection.sendOk();
		while (true) {
			String message = connection.receiveMessage();

			if (processMessage(message)) {
				break;
			}
		}
	}

	private boolean processMessage(String message) {

		try {
			JSONObject messageObject = (JSONObject) parser.parse(message);

			String request = (String) messageObject.get(JSONConstant.REQUEST);
			if (request != null) {
				String content = (String) messageObject.get(JSONConstant.CONTENT);
				if (request.equals(JSONConstant.YOUR_CARDS)) {
					System.out.println("Your cards are: ");
					System.out.println(content);
				} else if (request.equals(JSONConstant.TABLE_CARDS)) {
					System.out.println("The cards in the table are: ");
					System.out.println(content);

				}
			}

		} catch (ParseException e) {
			Log.e(e.getMessage());
		}

		return false;
	}

	public Connection getConnection() {
		return connection;
	}

}
