package cards.threenotfour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cards.threenotfour.constants.JSONConstant;

public class NetworkCardGame {

	private final Player[] players;

	// From Cardgame..
	private List<Card> deck;
	private int bid;
	private int trumpPlayer;
	private int roundLeader;
	private Card trumpCard;
	private final BufferedReader playerinput = new BufferedReader(new InputStreamReader(System.in));

	public NetworkCardGame(Player[] p) {
		players = p;
		roundLeader = 0;
	}

	public void start() {
		int team1 = 0;
		int team2 = 0;

		int numHands = 4;
		int cardsPerHand = 4;
		int numRounds = 8;
		
		while(team1 < 10 && team2 < 10) {
			deck = Card.newDeck();
			Collections.shuffle(deck);

			dealFirstRound(numHands, cardsPerHand);

			getBid();
			if(trumpPlayer==roundLeader){
				sayToAll("Bid by player " + trumpPlayer + " is "+ bid);
			}else{
				sayToAll("Player " + roundLeader + " called partner. Bid by player "+ trumpPlayer + " is "+ bid);
			}
			
			int opposition;
			if((opposition = requestOppositeAgreement()) != -1){
				players[trumpPlayer].getHand().add(trumpCard);
				getOverBid(opposition);
				sayToAll("OverBid by player " + trumpPlayer + " is "+ bid);
			}
			
			dealSecondRound(numHands, cardsPerHand);
			
			
			for(int i=0; i<numRounds; i++){
				Card[]currentRound = new Card[4];
				for(int j=0; j<numHands; j++){
					Player currPlayer = players[((roundLeader + j) % 4)];
					sayToPlayer(currPlayer, "Your hand: " + currPlayer.getHand());
					if(currPlayer.getIndex()==trumpPlayer && trumpCard!=null){
						sayToPlayer(currPlayer, "Trump card: " + trumpCard);
					}
					sayToPlayer(currPlayer, "Please select a card...");
					int card = getFromPlayer(currPlayer, "card");
					if(card >= 0 && card < currPlayer.getHand().size()){
						Card chosen = currPlayer.getHand().get(card);
						if(i==0 && j==0 && roundLeader==trumpPlayer){
							if(chosen.suit().equals(trumpCard.suit())){
								sayToPlayer(currPlayer, "Your initial card cannot be of same suit as trump");
								j--;
							}else{
								currentRound[currPlayer.getIndex()] = currPlayer.getHand().remove(card);
							}
						}else if(j==0 || isValid(currPlayer, chosen, currentRound[roundLeader])){
							currentRound[currPlayer.getIndex()] = currPlayer.getHand().remove(card);
						} else {
							j--;
						}
					} else if(card == 8 && currPlayer.getIndex()==trumpPlayer && trumpCard!=null){
						currentRound[currPlayer.getIndex()] = trumpCard;
						trumpCard = null;
					} else {
						j--;
					}
				}
				int team1Val = currentRound[0].rank().getRankpoints() + currentRound[2].rank().getRankpoints();
				int team2Val = currentRound[1].rank().getRankpoints() + currentRound[3].rank().getRankpoints();
				System.out.println("Team 1 val: " + team1Val);
				System.out.println("Team 2 val: " + team2Val);
			}
			team1 += 1; // Temporary
			roundLeader = (roundLeader + 1) % 4;
		}

	}
	
	private int requestOppositeAgreement(){
		Player opp1 = players[(roundLeader+1) % 4];
		Player opp2 = players[(roundLeader+3) % 4];
		sayToPlayer(opp1, "Do you want to overbid?");
		int rep1 = getFromPlayer(opp1, "overbid");
		sayToPlayer(opp2, "Do you want to overbid?");
		int rep2 = getFromPlayer(opp2, "overbid");
		if(rep1==0){
			if(rep2==0){
				return -1;
			} else {
				return opp2.getIndex();
			}
		} else {
			return opp1.getIndex();
		}
	}
	
	private void dealFirstRound(int numHands, int cardsPerHand){
		for(int i = 0; i < numHands; i++) {
			players[i].dealtFirstRound(deal(deck, cardsPerHand));
			sayToPlayer(players[i], "Hand of p" + i + ": " + players[i].getHand());
		}
	}
	
	private void dealSecondRound(int numHands, int cardsPerHand){
		for(int i = 0; i < numHands; i++) {
			players[i].dealtSecondRound(deal(deck, cardsPerHand));
			sayToPlayer(players[i], "Hand of p" + i + ": " + players[i].getHand());
		}
	}
	
	private boolean isValid(Player curr, Card chosen, Card first){
		if(chosen.suit().equals(first.suit())){
			return true;
		}else{
			ArrayList<Card> hand = curr.getHand();
			for(int i=0; i<hand.size(); i++){
				if(hand.get(i).suit().equals(first.suit())){
					return false;
				}
			}
			return true;
		}
	}

	private void getBid() {
		trumpPlayer = roundLeader;
		Player tPlayer = players[trumpPlayer];

		sayToPlayer(tPlayer, "Player " + roundLeader + ", please enter your bid!");
		int enteredBid = getFromPlayer(tPlayer, "bid");

		while (enteredBid % 10 != 0 || enteredBid < 150 || enteredBid > 300) {
			sayToPlayer(tPlayer, "Please enter valid bid (A multiple of 10 between 160 and 300)");
			enteredBid = getFromPlayer(tPlayer, "bid");
		}
		if(enteredBid == 150){ //Ask partner to bid
			trumpPlayer = ((roundLeader+2)%4);
			tPlayer = players[trumpPlayer];
			sayToPlayer(tPlayer, "Player " + trumpPlayer + ", your partner asked you to bid!");
			enteredBid = getFromPlayer(tPlayer, "partnerbid");

			while (enteredBid % 10 != 0 || enteredBid < 160 || enteredBid > 300) {
				sayToPlayer(tPlayer, "Please enter valid bid (A multiple of 10 between 160 and 300)");
				enteredBid = getFromPlayer(tPlayer, "partnerbid");
			}
		}
		bid = enteredBid;

		sayToPlayer(tPlayer, "Player " + trumpPlayer + ", please enter index of trump card!");
		int enteredIndex = getFromPlayer(tPlayer, "trump");

		while (enteredIndex < 0 || enteredIndex > 3) {
			sayToPlayer(tPlayer, "Please enter valid index (An integer between 0 and 3 inclusive)");
			enteredIndex = getFromPlayer(tPlayer, "trump");
		}
		trumpCard = players[trumpPlayer].getHand().remove(enteredIndex);
	}
	
	private void getOverBid(int overBidder) {
		trumpPlayer = overBidder;
		Player tPlayer = players[trumpPlayer];
		int lowestBid = bid;
		
		sayToPlayer(tPlayer, "Player " + roundLeader + ", please enter your bid!");
		int enteredBid = getFromPlayer(tPlayer, "secondarybid");

		while (enteredBid % 10 != 0 || enteredBid < (lowestBid + 10) || enteredBid > 300) {
			sayToPlayer(tPlayer, "Please enter valid bid (A multiple of 10 between " + lowestBid+10 + " and 300)");
			enteredBid = getFromPlayer(tPlayer, "bid");
		}
		bid = enteredBid;

		sayToPlayer(tPlayer, "Player " + trumpPlayer + ", please enter index of trump card!");
		int enteredIndex = getFromPlayer(tPlayer, "trump");

		while (enteredIndex < 0 || enteredIndex > 3) {
			sayToPlayer(tPlayer, "Please enter valid index (An integer between 0 and 3 inclusive)");
			enteredIndex = getFromPlayer(tPlayer, "trump");
		}
		trumpCard = players[trumpPlayer].getHand().remove(enteredIndex);
	}
	
	/**
	 * Asks the player for an input
	 * 
	 * @param networkPlayer
	 * @return
	 * @throws ParseException 
	 */
	private int getFromPlayer(Player networkPlayer, String type){
		if(networkPlayer.getIndex() == 0){
			System.out.println("Please enter a " + type +  "...");
			try {
				return Integer.parseInt(playerinput.readLine());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(JSONConstant.REQUEST, type);
			String message = (jsonObject.toString());
			networkPlayer.sendMessage(message);
			while(true){
				String reply;
				if((reply = networkPlayer.receiveMessage()) != ""){
					try{
						System.out.println("In try!");
						System.out.println("Reply is: " + reply);
						JSONParser parser = new JSONParser();
						JSONObject object = (JSONObject) parser.parse(reply);
		
						String status = (String) object.get(JSONConstant.STATUS);
						if (status.equals(type)) {
							int returnedVal = Integer.parseInt((String) object.get(JSONConstant.CONTENT));
							System.out.println(type + " received: " + returnedVal);
							return returnedVal;
						}
					}catch(ParseException e){
						
					}
				}
			}
		}
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
