package cards.threenotfour.player;

import cards.threenotfour.Player;
import cards.threenotfour.network.NetworkMessageSender;

public class NetworkPlayer extends Player {

	private final int port_number = 59000;

	private NetworkMessageSender messageSender;

	// private NetworkMessageReceiver messageReceiver;

	public NetworkPlayer() {

		messageSender = new NetworkMessageSender();
		// messageReceiver = new NetworkMessageReceiver(this);

		// Need to implement a thread which will wait for incoming connections..
		// TODO Auto-generated constructor stub
	}

	public void displayCurrentCards() {
		String message = "These are the cards.. I need to send this via INTERNET!";
		messageSender.sendMeesage(message, port_number);

	}
}
