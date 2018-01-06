/*
 Ryan Heath
 */
/*
 * ===========================================================
 * Hand.java: Used for a hand of a player. Can add or remove
 * cards from the hand, get the x and y coordinates for drawing,
 * Can reveal the second card or flip all cards face up.
 * Can determine total value of the hand with blackjack rules
 * Can clear hand to remove all cards from the hand
 * ===========================================================
 */

package ryanheath.blackjack;

import java.util.LinkedList;

public class Hand {
	protected LinkedList<Card> hand; //the cards in the hand

	/** Constructor to create hand
	 */
	public Hand(){
		hand = new LinkedList<Card>(); //makes new LinkedList to contain cards
	}
	
	/**Constructor to create hand if also given cards to add
	 * @param cards  the cards to be added
	*/
	public Hand(LinkedList<Card> cards){
		hand.addAll(cards);
	}
	
	/**Constructor to create hand starting with a card
	 * @param card  the card to be added
	 */
	public Hand(Card card){
		hand.add(card);
	}
	
	/** Add a card to the hand
	 * @param card  the card to be added
	 */
	public void addCard(Card card){
		hand.add(card);
	}
	
	/** Remove a card from the hand
	 * @param card   the card to be removed
	 */
	public void removeCard(Card card) throws ArrayIndexOutOfBoundsException {
		if (!hand.remove(card))
			throw new ArrayIndexOutOfBoundsException("card does not exist"); //TODO: get better exception
	}
	
	/** Remove a card from the hand, given face and suit
	 * @param face  the face of the card
	 * @param suit  the suit of the card
	 * @return card  the card that was removed.
	 */
	public Card removeCard(FaceEnum face, SuitEnum suit){
		for (Card card: hand){
			if (card.getFace() == face && card.getSuit() == suit){
				removeCard(card);
				return card;
			}
		}
		return null;
	}

	public LinkedList<Card> getCards(){
		return hand;
	}

	/** Returns size of hand
	 * @return int of size of hand
	 */
	public int size(){
		return hand.size();
	}

	/** Reveals second card in hand
	 */
	public void revealSecond(){
		hand.get(1).flip(false);
	}
	
	/** Gets total value of hand using blackjack rules
	 * @return totalValue  the value of the hand
	 */
	public int getTotalValue(){
		int totalAces = 0; //total number of aces
		int totalValue = 0; //total value of hand
		for (Card card: hand){
			if (card.getFace() == FaceEnum.ACE){//remember how many aces there are
				totalAces +=1;
			}
			totalValue+=card.getFace().returnBJValue(); //increases value of hand by card value
			while(totalValue > 21 && totalAces != 0){ //if it goes over 21 and there are aces, set ace value to 1
				totalValue -=10;
				totalAces-=1;
			}
		}
		return totalValue;
	}
	
	/** Flips all cards in the hand face up
	 */
	public void flipAll(){
		for (Card card: hand){
			card.flipped = false;
		}
	}
	
	/** Removes all cards in hand
	 * @return removedList the cards that were removed
	 */
	public LinkedList<Card> clearHand(){
		LinkedList<Card> removedlist = hand;
		hand = new LinkedList<Card>();
		return removedlist;
	}
	
}
