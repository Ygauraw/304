package cards.threenotfour.player;

import java.net.InetAddress;

import org.json.simple.JSONObject;

import cards.threenotfour.Player;
import cards.threenotfour.network.NetworkMessageSender;
import cards.threenotfour.network.Test;

public class NetworkPlayer extends Player {

	private final int port_number = Test.SERVER_PORT;
	private InetAddress ip_address;

	private static final NetworkMessageSender messageSender = new NetworkMessageSender();

	public NetworkPlayer(InetAddress address) {
		ip_address = address;
	}

	public void displayCurrentCards() {
		JSONObject object = new JSONObject();
		object.put("req", "new_game");
		sendMessage(object.toJSONString());
	}

	private void sendMessage(String message) {
		System.out.println("Sending : " + message);
		messageSender.sendMeesage(message, ip_address, port_number);

	}
}
