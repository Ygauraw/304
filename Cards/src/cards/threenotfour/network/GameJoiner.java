package cards.threenotfour.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import cards.threenotfour.constants.NetworkConstants;

public class GameJoiner {

	private final Connection connection;

	public GameJoiner(String ip) throws UnknownHostException, IOException {
		connection = new Connection(InetAddress.getByName(ip), NetworkConstants.CLIENT_PORT);
	}

	public void startTask() {
		connection.sendOk();
	}

	public Connection getConnection() {
		return connection;
	}

}
