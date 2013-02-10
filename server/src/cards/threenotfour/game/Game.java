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
	private ArrayList<Player> players;
	private boolean is_full;
	private int number_of_players;

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
		System.out.println("Added player with address " + player);

		is_full = players.size() >= number_of_players;
	}

	public boolean isFull() {
		return is_full;
	}

	public void startGame() {

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
