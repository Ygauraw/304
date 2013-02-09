package cards.threenotfour.game;

import java.net.InetAddress;

public class Player {

	private InetAddress ip_address;

	public Player(InetAddress address) {
		ip_address = address;
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
		return ip_address.toString();
	}
}
