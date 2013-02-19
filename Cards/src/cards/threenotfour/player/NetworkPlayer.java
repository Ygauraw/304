package cards.threenotfour.player;

import java.io.IOException;
import java.net.Socket;

import cards.threenotfour.Player;
import cards.threenotfour.network.Connection;

public class NetworkPlayer extends Player {

	private static Connection messageSender;

	public NetworkPlayer(Socket socket) throws IOException {
		messageSender = new Connection(socket);
	}

	@Override
	public void sendMessage(String message) {
		messageSender.sendMessage(message);
	}
}
