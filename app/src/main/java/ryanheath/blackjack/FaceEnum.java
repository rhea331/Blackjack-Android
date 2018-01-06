package ryanheath.blackjack;
//possible TODO: set card values to default and add new function for blackjack values
public enum FaceEnum {
	ACE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5),
	SIX(6),
	SEVEN(7),
	EIGHT(8),
	NINE(9),
	TEN(10),
	JACK(11),
	QUEEN(12),
	KING(13);
	
	private int value;
	
	/** Sets value
	 * @param value    the value of the card
	 */
	FaceEnum(int value){
		this.value = value;
	}
	
	/** Returns value of card
	 * @return value   the value of the card
	 */
	int returnValue(){
		return value;
	}

	int returnBJValue() {
		if (value > 10) return 10;
		else if (value == 1) return 11;
		else return value;
	}
}
