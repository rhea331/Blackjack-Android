package ryanheath.blackjack;


import java.util.LinkedList;
import java.util.Arrays;

/**
 * Created by The Orangutan on 4/01/2018.
 */

public class Game {

    Deck deck;
    Hand dealerHand;
    Hand playerHand;
    GameStateEnum GameState;
    int TotalMoney=1000;
    int CurrentBet;




    public Game(){
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
        GameState = GameStateEnum.DEFAULT;

    }

    public void hit(){
        GameState = GameStateEnum.PLAYING;
        playerHand.addCard(deck.drawCard());
        int total = playerHand.getTotalValue();
        if (total > 21){
            dealerHand.revealSecond();
            GameState = GameStateEnum.DEALERWIN;
        }else{
            GameState = GameStateEnum.INITIALIZING;
        }
    }

    public void stand(){
        GameState=GameStateEnum.PLAYING;
        dealerHand.revealSecond();
        while(dealerHand.getTotalValue() < 17){
            dealerHand.addCard(deck.drawCard());
        }
        int dealervalue = dealerHand.getTotalValue();
        if (dealervalue > 21){
            GameState = GameStateEnum.PLAYERWIN;
            TotalMoney+=CurrentBet+CurrentBet;
        }else{
            if(dealervalue > playerHand.getTotalValue()){
                GameState = GameStateEnum.DEALERWIN;
            }else if(dealervalue == playerHand.getTotalValue()){
                GameState = GameStateEnum.STANDOFF;
                TotalMoney+=CurrentBet;
            }else{
                GameState = GameStateEnum.PLAYERWIN;
                TotalMoney+=CurrentBet+CurrentBet;
            }
        }
    }

    public void initialize(int bet){
        GameState = GameStateEnum.INITIALIZING;

        if (deck.size() < 15){
            deck = new Deck();
        }
        deck.shuffle();
        dealerHand.clearHand();
        playerHand.clearHand();
        dealerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        if(playerHand.getTotalValue() == 21){
            dealerHand.addCard(deck.drawCard());
            if(dealerHand.getTotalValue() == 21){
                GameState=GameStateEnum.STANDOFF;
            }else{
                GameState = GameStateEnum.PLAYERWIN;
                TotalMoney+=(CurrentBet* 0.5);
            }
            return;
        }
        Card flippedCard = deck.drawCard();
        flippedCard.flip(true);
        dealerHand.addCard(flippedCard);
        TotalMoney-=bet;
        CurrentBet = bet;
    }

    public Hand returnDealerHand(){
        return dealerHand;
    }

    public Hand returnPlayerHand(){
        return playerHand;
    }

    public GameStateEnum getGameState(){
        return GameState;
    }

    public void setGameState(GameStateEnum gamestate){
        this.GameState = gamestate;
    }

    public int getTotalMoney(){
        return TotalMoney;
    }

    public int getCurrentBet(){
        return CurrentBet;
    }
}
