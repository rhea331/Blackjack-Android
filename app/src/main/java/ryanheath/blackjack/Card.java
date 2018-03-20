/*
 Ryan Heath
 */
/*
 * ===========================================================
 * Card.java: Used for cards. Can set and get face and suit of 
 * card, along with x and y coordinates for drawing. Can also
 * flip card.
 * Card images from  Welopez, http://jbfilesarchive.com/phpBB3/viewtopic.php?t=1003
 * ===========================================================
 */

package ryanheath.blackjack;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

public class Card {
	protected FaceEnum face; //the face of the card, from Ace to King
	protected SuitEnum suit; //the suit of the card, from Spades, Hearts, Diamond or Clubs
	protected boolean flipped = false; //if card is flipped

	/** Constructor to create a card using FaceEnum
	 * @param face   the face of the card
	 * @param suit   the suit of the card
	 * @param flipped   if the card is flipped or not
	 */
	public Card(FaceEnum face, SuitEnum suit, boolean flipped){
		this.face = face;
		this.suit = suit;
		this.flipped = flipped;
	}
	
	/** Gets face of card
	 * @return face     the face
	 */
	public FaceEnum getFace(){
		return face;
	}

	/** Gets suit of card
	 * @return suit    the suit
	 */
	public SuitEnum getSuit(){
		return suit;
	}
	
	/** Gets string showing face and suit of card
	 * @return a string of face and suit
	 */
	public String getString(){
		return (face + " " + suit);
	}

	/** Sets if card is flipped or not
	 * @param flip    boolean if card is flipped or not
	 */
	public void flip(boolean flip){
		if (flip) flipped = true;
		else{flipped = false;}
	}

	/** Returns location of card in card array
	 * @return locationNo  the location of the card
	 */
	public int getImageLocation(){
		int locationNo = (face.returnValue()-1) * 4;
		switch(suit){
			case HEARTS:
				locationNo +=1;
				break;
			case DIAMONDS:
				locationNo +=2;
				break;
			case CLUBS:
				locationNo+=3;
		}
		return locationNo;
	}
}
