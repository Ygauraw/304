package cards.threenotfour.player;

import java.net.InetAddress;
import java.net.Socket;

import org.json.simple.JSONObject;

import cards.threenotfour.Player;
import cards.threenotfour.network.Connection;

public class NetworkPlayer extends Player {

	private final int port_number = 110;
	private InetAddress ip_address;

	private static final Connection messageSender = null; // new Connection();

	public NetworkPlayer(InetAddress address) {
		ip_address = address;
	}

	public NetworkPlayer(Socket socket) {

	}

	public void displayCurrentCards() {
		JSONObject object = new JSONObject();
		object.put("req", "new_game");
		sendMessage(object.toJSONString());
	}

	private void sendMessage(String message) {
		System.out.println("Sending : " + message);
		messageSender.sendMessage(message);

	}
}
