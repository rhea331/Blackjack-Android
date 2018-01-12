package ryanheath.blackjack;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private SurfaceHolder holder;
    private Resources res;
    private int mCanvasWidth, mCanvasHeight;
    Context mContext;

    Deck deck;
    Hand playerHand,dealerHand;

    Bitmap back, background;
    ArrayList<Bitmap> cardImages = new ArrayList<>();

    GameStateEnum gameState = GameStateEnum.DEFAULT;

    int totalMoney = 1000;
    int currentBet = 0;

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);
        holder = getHolder();
        mContext = context;
        holder.addCallback(this);
        res = context.getResources();
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
        back = BitmapFactory.decodeResource(res, R.drawable.back);
        background = BitmapFactory.decodeResource(res, R.drawable.background);
        setImages();
    }

    public void doDraw(){
        Canvas canvas = holder.lockCanvas();

        int w = canvas.getWidth();
        int h = canvas.getHeight();
        int cardwidth = back.getScaledWidth(canvas);
        int cardheight = back.getScaledHeight(canvas); //scaling purposes

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(cardwidth/4);

        canvas.drawBitmap(background, 0, 0, null);

        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Total Money: "+totalMoney, 0, h/2, paint);

        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(gameState.returnMessage(), w/2, h/2, paint);

        LinkedList<Card> Hand = playerHand.getCards();
        int i = cardwidth/2 + (Hand.size() -1) * ((3*cardwidth)/4);
        for (Card card : Hand){
            canvas.drawBitmap(cardImages.get(card.getImageLocation()), w/2-i, 2*(h/3) - cardheight/2, null);
            i-=1.5*cardwidth;
        }

        Hand = dealerHand.getCards();
        i = cardwidth/2 + (Hand.size() -1) * ((3*cardwidth)/4);
        for (Card card : Hand){
            if(card.flipped){
                canvas.drawBitmap(back, w/2-i, (h/4) - cardheight/2, null);
            }else {
                canvas.drawBitmap(cardImages.get(card.getImageLocation()), w / 2 - i, (h / 4) - cardheight / 2, null);
            }
            i-=1.5*cardwidth;
        }
        canvas.drawText("Player Total: "+playerHand.getTotalValue(), w/2, 2*(h/3) + cardheight, paint);
        if (gameState != GameStateEnum.DEFAULT && gameState != GameStateEnum.INITIALIZING){
            canvas.drawText("Dealer Total: "+dealerHand.getTotalValue(), w/2, cardheight/3, paint);
        }

        holder.unlockCanvasAndPost(canvas);

    }

    public void initialize(int bet) {
        gameState = GameStateEnum.INITIALIZING;
        if (deck.size() < 15) {
            deck = new Deck();
        }
        deck.shuffle();
        dealerHand.clearHand();
        playerHand.clearHand();
        dealerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        if (playerHand.getTotalValue() == 21) {
            dealerHand.addCard(deck.drawCard());
            if (dealerHand.getTotalValue() == 21) {
                gameState = GameStateEnum.STANDOFF;
            } else {
                totalMoney +=(bet * 0.5);
                gameState = GameStateEnum.PLAYERWIN;
            }
            doDraw();
            return;
        }
        Card flippedCard = deck.drawCard();
        flippedCard.flip(true);
        dealerHand.addCard(flippedCard);
        totalMoney-=bet;
        currentBet = bet;
        doDraw();
    }

    public void hit(){
        gameState = GameStateEnum.PLAYING;
        playerHand.addCard(deck.drawCard());
        int total = playerHand.getTotalValue();
        if(total > 21){
            dealerHand.revealSecond();
            currentBet = 0;
            gameState = GameStateEnum.DEALERWIN;
        }else{
            gameState = GameStateEnum.INITIALIZING;
        }
        doDraw();
    }


    public void stand(){
        gameState = GameStateEnum.PLAYING;
        dealerHand.revealSecond();
        while(dealerHand.getTotalValue() < 17){
            dealerHand.addCard(deck.drawCard());
        }
        int dealerValue = dealerHand.getTotalValue();
        if (dealerValue> 21){
            totalMoney += (currentBet * 2);
            gameState = GameStateEnum.PLAYERWIN;
        }else{
            if(dealerValue > playerHand.getTotalValue()){
                gameState = GameStateEnum.DEALERWIN;
            }else if(dealerValue == playerHand.getTotalValue()){
                totalMoney += currentBet;
                gameState = GameStateEnum.STANDOFF;
            }else{
                totalMoney += (currentBet * 2);
                gameState = GameStateEnum.PLAYERWIN;
            }
        }
        doDraw();
    }

    public void setImages(){
        for(FaceEnum f: FaceEnum.values()){
            for(SuitEnum s: SuitEnum.values()){
                String location = (""+f+s).toLowerCase();
                int id = res.getIdentifier(location, "drawable", mContext.getPackageName());
                cardImages.add(BitmapFactory.decodeResource(res, id));
            }
        }
    }

    public void setSurfaceSize(int width, int height){
        mCanvasWidth = width;
        mCanvasHeight = height;
        background = Bitmap.createScaledBitmap(background, mCanvasWidth, mCanvasHeight, false);
    }

    public GameStateEnum getGameState(){
        return gameState;
    }

    public int returnTotalMoney(){
        return totalMoney;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
        setSurfaceSize(w, h);
        doDraw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
