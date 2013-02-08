package cards.threenotfour.network;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class will use something like JSON to transfer content to another player
 * playing via the Internet.
 * 
 * @author sg3809
 * 
 */
public class NetworkMessageSender {

	/**
	 * Implements message passing (i.e Async send)
	 * 
	 * @param message
	 * @return
	 */
	// For now just tries to send the message.
	public boolean sendMeesage(String message, int port_number) {
		try {
			// Create a new socket to connect to myself
			Socket socket = new Socket("localhost", port_number);

			// Get the output stream of the socket
			PrintStream out = new PrintStream(socket.getOutputStream());

			// Then put the message into the outputstream
			out.println(message);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
