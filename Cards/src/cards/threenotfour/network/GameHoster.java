package cards.threenotfour.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cards.threenotfour.constants.NetworkConstants;
import cards.threenotfour.player.NetworkPlayer;

public class GameHoster {

	private final NetworkPlayer[] players;

	public GameHoster() {
		players = new NetworkPlayer[3];
	}

	public void startTask() {

		System.out.println("Starting a server socket at port: " + NetworkConstants.CLIENT_PORT);

		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(NetworkConstants.CLIENT_PORT);

			Socket clientSocket = null;
			int i = 0;
			while (i < 3) {
				clientSocket = serverSocket.accept();

				if (clientSocket != null) {
					players[i] = new NetworkPlayer(clientSocket);
					++i;
				}
			}

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
