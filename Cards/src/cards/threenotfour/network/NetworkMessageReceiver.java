package cards.threenotfour.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import cards.threenotfour.Player;

public class NetworkMessageReceiver implements Runnable {

	// The player related to this connection
	private Player player;

	// public NetworkMessageReceiver(Player player) {
	// this.player = player;
	// }

	@Override
	public void run() {

		try {
			ServerSocket serverSocket = new ServerSocket(5800);

			Socket clientSocket = serverSocket.accept();

			// Get the inputstream of that socket to receive the data

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));

			while (true) {

				String line = reader.readLine();
				if (line != null) {
					System.out.println("RECEIVER ** " + line);
				}

			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
