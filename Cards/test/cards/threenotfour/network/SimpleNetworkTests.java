package cards.threenotfour.network;

import org.junit.Test;

import cards.threenotfour.player.NetworkPlayer;

public class SimpleNetworkTests {

	@Test
	public void simpleReceiveTest() {
		NetworkMessageReceiver receiver = new NetworkMessageReceiver();

		receiver.run();

	}

	@Test
	public void simpleSendTest() {
		NetworkPlayer p1 = new NetworkPlayer();
		p1.displayCurrentCards();

	}

}