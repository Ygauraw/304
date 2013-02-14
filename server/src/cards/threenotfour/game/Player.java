package cards.threenotfour.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cards.threenotfour.constants.JSONConstant;

public class Player {

	private final BufferedReader bufferedReader;
	private final PrintWriter printWriter;
	private final Socket socket;

	public Player(Socket socket) throws IOException {
		this.socket = socket;
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		printWriter = new PrintWriter(socket.getOutputStream(), true);
	}

	/**
	 * Just send a message to close the connection to the client and initiate
	 * connection to the host.
	 * 
	 * Need to pass the ip address of the client.
	 */
	public void startGame() {

	}

	/**
	 * Send a message asking the client to create a server socket port and wait
	 * for a response. If they respond fine. Then contact all the other clients
	 * with startGame
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public void startGameAsHost() throws IOException, ParseException {
		Hashtable<String, String> message = new Hashtable<String, String>();
		message.put(JSONConstant.STATUS, JSONConstant.OK);
		message.put(JSONConstant.REQUEST, JSONConstant.START_AS_HOST);

		JSONObject reply = new JSONObject(message);
		sendMessage(reply.toString());

		while (true) {
			String replyFromClient = bufferedReader.readLine();

			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(replyFromClient);

			String status = (String) object.get(JSONConstant.STATUS);

			if (status.equals(JSONConstant.OK)) {
				socket.close();
				return;
			}

		}
	}

	/**
	 * Takes a JSON encoded message and send it. This does not do any processing
	 * on the message
	 */
	private void sendMessage(String message) {
		System.out.println("Sending :" + message);
		printWriter.println(message);
	}

	/**
	 * Retuns the time it takes to send and receive a message. Used to find the
	 * best host for the game.
	 * 
	 * @return time taken to send and receive a packet.
	 */
	public int testConnection() {
		return 1;
	}

	@Override
	public String toString() {
		return socket.toString();
	}

	public void sendOk() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(JSONConstant.STATUS, JSONConstant.OK);
		sendMessage(jsonObject.toString());
	}
}
