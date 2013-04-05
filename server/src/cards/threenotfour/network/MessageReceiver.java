package cards.threenotfour.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cards.threenotfour.Controller;
import cards.threenotfour.constants.JSONConstant;
import cards.threenotfour.constants.NetworkConstants;

/**
 * This guy just sits waiting for any data
 * 
 * @author sg3809
 * 
 */
public class MessageReceiver implements Runnable {

	@Override
	public void run() {

		System.out.println("Starting a server at port: " + NetworkConstants.SERVER_PORT);

		try {
			// Open a server port to allow clients to send message
			ServerSocket serverSocket = new ServerSocket(NetworkConstants.SERVER_PORT);
			Socket clientSocket = null;
			BufferedReader bufferedReader = null;
			System.out.println("Tyring to establish socket!");

			while (true) {
				System.out.println("In while!");
				// Accept connection from an incoming client
				clientSocket = serverSocket.accept();

				bufferedReader = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));

				String request = bufferedReader.readLine();

				// Send it to process the request
				processRequest(request, clientSocket);

			}

		} catch (IOException e) {
			System.out.println("Caught IO exception");
			e.printStackTrace();
		}

	}

	public void processRequest(String request, Socket clientSocket) {
		System.out.println("Processing received request");
		JSONParser parser = new JSONParser();

		try {
			JSONObject object = (JSONObject) parser.parse(request);

			String command = (String) object.get(JSONConstant.REQUEST);

			if (command != null && command.equals(JSONConstant.NEW_GAME)) {
				Controller.addPlayer(clientSocket);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
