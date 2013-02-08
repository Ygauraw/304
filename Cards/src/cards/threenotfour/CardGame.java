package cards.threenotfour;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;



public class CardGame {

	private static List<Card> deck;
	private static int bid;
	private static int trumpPlayer;
	private static Card trumpCard;
	private static Player[] players;
	private static Scanner scanner;
	
	public static void main(String args[]) {
		int numHands = 4;
		int cardsPerHand = 4;
		
		trumpPlayer = 0;
		players = new Player[4];
		for(int i=0; i<players.length; i++){
			players[i] = new Player();
		}
		scanner = new Scanner(new BufferedInputStream(System.in));

		int team1=0, team2=0;
		while(team1 < 10 && team2 < 10){
			deck = Card.newDeck();
			Collections.shuffle(deck);

			for (int i=0; i < numHands; i++){
				players[i].dealtFirstRound(deal(deck, cardsPerHand));
				System.out.println("Hand of p" + i + ": " + players[i].getHand());
			}
			
			getBid();
			System.out.println("Bid is " + bid);
			System.out.println("Trump card is: " + trumpCard);

			for (int i=0; i < numHands; i++){
				players[i].dealtSecondRound(deal(deck, cardsPerHand));
				System.out.println("Hand of p" + i + ": " + players[i].getHand());
			}
			team1 += 5;  //Temporary
			trumpPlayer = (trumpPlayer + 1) % 4;
		}
	}
	
	private static void getBid(){
		System.out.println("Player " + trumpPlayer + ", please enter your bid!");
		int enteredBid = scanner.nextInt();
		while(((enteredBid % 10) != 0) || (enteredBid < 160) || (enteredBid > 300)){
			System.out.println("Please enter valid bid (A multiple of 10 between 160 and 300)");
			enteredBid = scanner.nextInt();
		}
		bid = enteredBid;
		
		System.out.println("Player " + trumpPlayer + ", please enter index of trump card!");
		int enteredIndex = scanner.nextInt();
		while((enteredIndex < 0) || (enteredIndex > 3)){
			System.out.println("Please enter valid index (An integer between 0 and 3 inclusive)");
			enteredIndex = scanner.nextInt();
		}
		trumpCard = players[trumpPlayer].getHand().remove(enteredIndex);
	}

	public static ArrayList<Card> deal(List<Card> deck, int n) {
		int deckSize = deck.size();
		List<Card> handView = deck.subList(deckSize-n, deckSize);
		ArrayList<Card> hand = new ArrayList<Card>(handView);
		handView.clear();
		return hand;
	}
}
