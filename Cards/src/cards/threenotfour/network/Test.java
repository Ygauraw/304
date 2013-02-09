package cards.threenotfour.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cards.threenotfour.player.NetworkPlayer;

public class Test {

	public static void main(String[] args) throws UnknownHostException {

		// NetworkMessageReceiver receiver = new NetworkMessageReceiver();
		// new Thread(receiver).start();

		InetAddress server = InetAddress.getByName("192.168.1.2");
		NetworkPlayer p1 = new NetworkPlayer(server);
		p1.displayCurrentCards();
	}
}
