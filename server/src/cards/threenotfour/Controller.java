package cards.threenotfour;

import java.io.IOException;
import java.net.Socket;

import cards.threenotfour.game.Game;
import cards.threenotfour.game.Player;
import cards.threenotfour.network.MessageReceiver;

public class Controller {

	public static final int CLIENT_PORT = 59423;
	public static final int SERVER_PORT = 59422;

	public static final String REQUEST = "req";
	public static final String NEW_GAME = "new_game";
	public static final String STATUS = "status";
	public static final String WAIT = "wait";
	public static final String START_GAME = "start_game";
	public static final String START_AS_HOST = "start_game_host";
	public static final String OK = "ok";

	private static Game game;

	public static void main(String[] args) {
		new Thread(new MessageReceiver()).start();
	}

	public static void addPlayer(Socket scoket) {
		if (game == null) {
			game = new Game();
		}
		try {
			Player player = new Player(scoket);
			game.addPlayer(player);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		if (game.isFull()) {
			game.startGame();
			game = null;
		}
	}
}