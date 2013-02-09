package cards.threenotfour;

import java.net.InetAddress;

import cards.threenotfour.game.Game;
import cards.threenotfour.game.Player;
import cards.threenotfour.network.MessageReceiver;

public class Controller {

	public static final int CLIENT_PORT = 59423;
	public static final int SERVER_PORT = 59422;

	public static final String REQUEST = "req";
	public static final String NEW_GAME = "new_game";

	private static Game game;

	public static void main(String[] args) {
		new Thread(new MessageReceiver()).start();
	}

	public static void addPlayer(InetAddress address) {
		if (game == null) {
			game = new Game();
		}
		Player player = new Player(address);
		game.addPlayer(player);
	}

}