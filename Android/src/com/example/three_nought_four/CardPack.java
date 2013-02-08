package com.example.three_nought_four;

import java.util.Random;

public class CardPack {
	
	public static void main(String[]args){
		System.out.println("Kavindu");
	    System.out.println(shuffle());
	}
	
	public static int[] shuffle(){
		Random rgen = new Random();  // Random number generator
		int[] cards = new int[32]; // Since 304 only requires 32 cards 
	
		//--- Initialize the array to the ints 0-51
		for (int i=0; i<cards.length; i++) {
		    cards[i] = i;
		}
	
		//--- Shuffle by exchanging each element randomly
		for (int i=0; i<cards.length; i++) {
		    int randomPosition = rgen.nextInt(cards.length);
		    int temp = cards[i];
		    cards[i] = cards[randomPosition];
		    cards[randomPosition] = temp;
		}
		
		return cards;
	}
}
