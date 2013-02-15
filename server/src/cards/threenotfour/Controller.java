package cards.threenotfour;

import java.io.IOException;
import java.net.Socket;

import cards.threenotfour.game.Game;
import cards.threenotfour.game.Player;
import cards.threenotfour.network.MessageReceiver;

public class Controller {

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