package cards.threenotfour.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cards.threenotfour.Player;
import cards.threenotfour.constants.NetworkConstants;
import cards.threenotfour.log.Log;
import cards.threenotfour.player.NetworkPlayer;

public class GameHoster {

	private final Player[] players;

	public GameHoster() {
		players = new Player[4];
		players[0] = new Player();
	}

	public void startTask() {
		Log.d("Starting a server socket at port: " + NetworkConstants.CLIENT_PORT);

		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(NetworkConstants.CLIENT_PORT);

			Socket clientSocket = null;
			int i = 1;
			while (i < 4) {
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

	public Player[] getPlayers() {
		return players;
	}
}
