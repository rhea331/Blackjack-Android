/*
 	Ryan Heath
/*
 Ryan Heath
 */
/*
 * ===========================================================
 * Deck.java: Contains the deck. Does simple functions like
 * drawing, shuffling and adding cards. Can also get size and
 * return cards for debugging.
 * ===========================================================
 */

package ryanheath.blackjack;

import java.util.*;

public class Deck {
	
	protected LinkedList<Card> deck; //The LinkedList containing the cards
	
	/** constructor to create deck with 52 cards
	 * 
	 */	
	public Deck(){
		deck = new LinkedList<Card>(); //sets up deck
		for (FaceEnum f: FaceEnum.values()){
			for (SuitEnum s: SuitEnum.values()){
				deck.add(new Card(f, s, false)); //adds cards to deck
			}
		}
	}
	/** Returns the current cards in the deck
	 * @return deck  The list containing the cards
	 */
	public LinkedList<Card> getDeck(){
		return deck;
	}
	
	/** Shuffles the cards in the deck
	 */
	public void shuffle(){
		Collections.shuffle(deck);
	}
	
	/** Removes and returns a card from the deck
	 * @return topcard The topcard from the deck
	 */
	public Card drawCard(){
		if (size()==0){
			return null;
			}
		Card topcard = deck.removeFirst();
		return topcard;
	}
	
	/** Adds a list of cards to the deck
	 * @param cards A LinkedList containing the cards to be added
	 */
	public void addCards(LinkedList <Card> cards){
		deck.addAll(cards);
	}
	
	/** Returns the size of the deck
	 * @return the size of the deck
	 */
	public int size(){
		return deck.size();
	}
	
	/** Prints out the cards in the deck for debugging purposes
	 */
	public void printDeck(){
    	for(Card c: deck){
    		System.out.println(c.getString());
    	}
	}
}
