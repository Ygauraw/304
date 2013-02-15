package cards.threenotfour.game;

import java.util.ArrayList;

/**
 * A class that represents a game that is waiting for more players.
 * 
 * @author sg3809
 * 
 */
public class Game {

	private static final int DEFAULT_SIZE = 4;

	// Each game will have 4 players.
	private final ArrayList<Player> players;
	private boolean is_full;
	private final int number_of_players;

	public Game() {
		this(DEFAULT_SIZE);
	}

	public Game(int size) {
		players = new ArrayList<Player>(size);
		is_full = false;
		number_of_players = size;
	}

	public void addPlayer(Player player) {
		players.add(player);
		System.out.print("Added player with address " + player + ". ");
		System.out.println("Total number of players : " + players.size());

		player.sendOk();

		is_full = players.size() >= number_of_players;
	}

	public boolean isFull() {
		return is_full;
	}

	public void startGame() {
		try {
			// The host is the first client that created the game.
			Player host = players.get(0);
			String hostIP = host.getIPAddress();
			System.out.println("Here..... : " + hostIP);
			host.startGameAsHost();

			// Then send all other clients to connect to the host.
			for (int i = 1; i < 4; ++i) {
				players.get(i).startGame(hostIP);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(100);
		for (Player p : players) {
			stringBuilder.append(p + " | ");
		}
		return stringBuilder.toString();
	}

}
