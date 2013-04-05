package cards.threenotfour.player;

import java.io.IOException;
import java.net.Socket;

import cards.threenotfour.Player;
import cards.threenotfour.network.Connection;

public class NetworkPlayer extends Player {

	private Connection messageSender;

	public NetworkPlayer(Socket socket, int index) throws IOException {
		super(index);
		messageSender = new Connection(socket);
		System.out.println("Socket local address is: " + socket.getLocalAddress());
	}

	@Override
	public void sendMessage(String message) {
		System.out.println("Inet address before sending is: " + messageSender.getSocket().getInetAddress());
		messageSender.sendMessage(message);
	}
	
	public String receiveMessage(){
		return messageSender.receiveMessage();
	}
}
