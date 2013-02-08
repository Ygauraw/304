package cards.threenotfour;
import java.util.List;
import java.util.ArrayList;


public class Card {
	private Rank rank;
	private Suit suit;

	public enum Rank {SEVEN(0), EIGHT(0), NINE(20), TEN(10), 
		JACK(30), QUEEN(2), KING(3), ACE(11);

	private int Rankpoints;

	private Rank(int points){
		this.Rankpoints = points;  
	}

	public int getRankpoints() {
		return Rankpoints;
	}

	}

	public enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES;}

	private Card(Rank rank, Suit suit){
		this.rank = rank;
		this.suit = suit;
	}

	public Rank rank() { 
		return this.rank;
	}
	public Suit suit() {
		return this.suit;
	}

	public String toString() { 
		return rank + " of " + suit; 
	}
	
	private static final List<Card> protoDeck = new ArrayList<Card>();

	// Initialize prototype deck
	static {
	    for (Suit suit : Suit.values())
	        for (Rank rank : Rank.values())
	            protoDeck.add(new Card(rank, suit));
	}
	
	public static ArrayList<Card> newDeck() {
	    return new ArrayList<Card>(protoDeck); // Return copy of prototype deck
	}

}
