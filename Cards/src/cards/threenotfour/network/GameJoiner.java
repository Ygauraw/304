package cards.threenotfour.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	private final BufferedReader playerinput = new BufferedReader(new InputStreamReader(System.in));

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
		//System.out.println("Entered process message!");
		try {
			JSONObject messageObject = (JSONObject) parser.parse(message);

			String request = (String) messageObject.get(JSONConstant.REQUEST);
			if (request != null) {
				String content;
				if (request.equals(JSONConstant.YOUR_CARDS)) {
					content = (String) messageObject.get(JSONConstant.CONTENT);
					System.out.println("Your cards are: ");
					System.out.println(content);
				} else if (request.equals(JSONConstant.TABLE_CARDS)) {
					content = (String) messageObject.get(JSONConstant.CONTENT);
					System.out.println("The cards in the table are: ");
					System.out.println(content);
				} else if (!request.equals(null) && !request.equals("")){
					try {
						//System.out.println("Please enter a " + request +  "...");
						String input = playerinput.readLine();
						JSONObject jsonObject = new JSONObject();
						jsonObject.put(JSONConstant.STATUS, request);
						jsonObject.put(JSONConstant.CONTENT, input);
						String request_reply = (jsonObject.toString());
						connection.sendMessage(request_reply);
					} catch (NumberFormatException e) {
						System.out.println("Non number input!");
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("IOException");
						e.printStackTrace();
					}
				}
			}

		} catch (ParseException e) {
			
		}
		//System.out.println("Exiting process message!");
		return false;
	}

	public Connection getConnection() {
		return connection;
	}

}
