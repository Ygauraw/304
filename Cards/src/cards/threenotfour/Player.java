package cards.threenotfour;

import java.util.ArrayList;

public class Player {
	private ArrayList<Card> hand;
	private int index;

	public Player(int index) {
		hand = null;
		this.index = index;
	}
	
	public int getIndex(){
		return index;
	}

	public Card playCard(int index) {
		return hand.remove(index);
	}

	public ArrayList<Card> dealtFirstRound(ArrayList<Card> firstRound) {
		hand = firstRound;
		return hand;
	}

	public ArrayList<Card> dealtSecondRound(ArrayList<Card> secondRound) {
		hand.addAll(secondRound);
		return hand;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void sendMessage(String message) {
		System.out.println(message);
	}
	
	public String receiveMessage(){
		return "";
	}

	public void readInt() {
		// TODO Auto-generated method stub
		
	}
}
