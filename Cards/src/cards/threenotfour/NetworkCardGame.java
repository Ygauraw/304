package cards.threenotfour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkCardGame {

	private final Player[] players;

	// From Cardgame..
	private List<Card> deck;
	private int bid;
	private int trumpPlayer;
	private Card trumpCard;

	public NetworkCardGame(Player[] p) {
		players = p;
		trumpPlayer = 0;
	}

	public void start() {
		int team1 = 0;
		int team2 = 0;

		int numHands = 4;
		int cardsPerHand = 4;

		while (team1 < 10 && team2 < 10) {
			deck = Card.newDeck();
			Collections.shuffle(deck);

			for (int i = 0; i < numHands; i++) {
				players[i].dealtFirstRound(deal(deck, cardsPerHand));
				sayToPlayer(players[i], "Hand of p" + i + ": " + players[i].getHand());
			}

			getBid();
			sayToAll("Bid is " + bid);

			// System.out.println("Trump card is: " + trumpCard);

			for (int i = 0; i < numHands; i++) {
				players[i].dealtSecondRound(deal(deck, cardsPerHand));
				sayToPlayer(players[i], "Hand of p" + i + ": " + players[i].getHand());
			}
			team1 += 5; // Temporary
			trumpPlayer = (trumpPlayer + 1) % 4;
		}

	}

	private void getBid() {

		Player tPlayer = players[trumpPlayer];

		sayToPlayer(tPlayer, "Player " + trumpPlayer + ", please enter your bid!");
		int enteredBid = getFromPlayer(tPlayer);

		while (enteredBid % 10 != 0 || enteredBid < 160 || enteredBid > 300) {
			sayToPlayer(tPlayer, "Please enter valid bid (A multiple of 10 between 160 and 300)");
			enteredBid = getFromPlayer(tPlayer);
		}
		bid = enteredBid;

		sayToPlayer(tPlayer, "Player " + trumpPlayer + ", please enter index of trump card!");
		int enteredIndex = getFromPlayer(tPlayer);

		while (enteredIndex < 0 || enteredIndex > 3) {
			sayToPlayer(tPlayer, "Please enter valid index (An integer between 0 and 3 inclusive)");
			enteredIndex = getFromPlayer(tPlayer);
		}
		trumpCard = players[trumpPlayer].getHand().remove(enteredIndex);
	}

	/**
	 * Asks the player for an input
	 * 
	 * @param networkPlayer
	 * @return
	 */
	private int getFromPlayer(Player networkPlayer) {
		// TODO: Send a message asking the scanner to get a message and send it.
		return 0;

	}

	public ArrayList<Card> deal(List<Card> deck, int n) {
		int deckSize = deck.size();
		List<Card> handView = deck.subList(deckSize - n, deckSize);
		ArrayList<Card> hand = new ArrayList<Card>(handView);
		handView.clear();
		return hand;
	}

	private void sayToAll(String message) {
		for (int i = 1; i < players.length; i++) {
			players[i].sendMessage(message);

		}
	}

	private void sayToPlayer(Player player, String message) {
		player.sendMessage(message);
	}

}
