package ryanheath.blackjack;

public enum GameStateEnum {
	DEFAULT(""),
	INITIALIZING(""),
	PLAYING(""),
	PLAYERWIN("YOU WIN"),
	DEALERWIN("DEALER WINS"),
	STANDOFF("STAND-OFF");
	
	private String message;
	
	GameStateEnum(String message){
		this.message = message;
	}
	
	String returnMessage(){
		return message;
	}
}
