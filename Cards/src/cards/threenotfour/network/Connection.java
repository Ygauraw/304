package cards.threenotfour.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

import cards.threenotfour.constants.JSONConstant;

/**
 * This class will use something like JSON to transfer content to another player
 * playing via the Internet.
 * 
 * @author sg3809
 * 
 */
public class Connection {

	private final Socket socket;
	private final BufferedReader reader;
	private final PrintWriter writer;

	public Connection(InetAddress address, int port) throws IOException {
		socket = new Socket(address, port);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintWriter(socket.getOutputStream(), true);
	}

	public Connection(Socket socket) throws IOException {
		this.socket = socket;
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintWriter(socket.getOutputStream(), true);
	}

	public Connection(String host, int port) throws UnknownHostException, IOException {
		this(InetAddress.getByName(host), port);
	}

	public synchronized String receiveMessage() {

		try {
			String message = reader.readLine();
			System.out.println("Received: " + message);
			return message;
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return "";
	}

	/**
	 * Implements message passing (i.e Async send)
	 * 
	 * @param message
	 * @return
	 */
	// For now just tries to send the message.
	public synchronized void sendMessage(String message) {

		System.out.println("Sending message" + message + " to :" + socket.getInetAddress());

		// Then put the message into the outputstream
		writer.println(message);
	}

	public void close() throws IOException {
		writer.close();
		reader.close();
		socket.close();
	}

	public void sendOk() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(JSONConstant.STATUS, JSONConstant.OK);
		sendMessage(jsonObject.toString());
	}

}
