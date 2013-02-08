package cards.threenotfour.network;

import cards.threenotfour.player.NetworkPlayer;

public class Test {

	public static void main(String[] args) {

		NetworkMessageReceiver receiver = new NetworkMessageReceiver();
		// new Thread(receiver).start();

		NetworkPlayer p1 = new NetworkPlayer();
		p1.displayCurrentCards();
	}
}
