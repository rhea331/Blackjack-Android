package ryanheath.blackjack;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.graphics.Bitmap;
import android.content.res.AssetManager;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class BlackjackActivity extends AppCompatActivity {

    Button hit, stand, bet;
    SurfaceView surface;
    SurfaceHolder holder;
    Game game;
    Bitmap back;
    Bitmap background;
    ArrayList<Bitmap> cardImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack);
        surface = findViewById(R.id.surfaceView);
        holder = surface.getHolder();
        game = new Game();
        setImages();
        hit = findViewById(R.id.hit);
        hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(game.getGameState()==GameStateEnum.INITIALIZING) {
                    game.hit();
                    draw();
                }
            }
        });

        stand = findViewById(R.id.stand);
        stand.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(game.getGameState() == GameStateEnum.INITIALIZING) {
                    game.stand();
                    draw();
                }
            }
        });

        bet = findViewById(R.id.bet);
        bet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(game.getGameState() == GameStateEnum.DEFAULT) {
                    game.initialize(100);
                    draw();
                }
            }
        });
    }

    public void setImages(){
        AssetManager am = this.getAssets();
        Bitmap bitmap = null;
        try{
            bitmap = BitmapFactory.decodeStream(am.open("back.bmp"));
        }catch(IOException e){e.printStackTrace();}
        try{
            background = BitmapFactory.decodeStream(am.open("background.jpg"));
        }catch(IOException e){e.printStackTrace();}
        back = Bitmap.createScaledBitmap(bitmap, 142, 192, false);
        for(int i = 1; i < 14; i++){
            for (int j = 1; j < 5; j++){
                String imageLocation = "" + i + j + ".bmp";
                    try{
                    bitmap = BitmapFactory.decodeStream(am.open(imageLocation));
                    cardImages.add(Bitmap.createScaledBitmap(bitmap, 142, 192, false));
                }catch(IOException e){e.printStackTrace();}

            }
        }
    }

    public void draw(){
        Hand playerh = game.returnPlayerHand();
        Hand dealerh = game.returnDealerHand();
        Canvas canvas = holder.lockCanvas();
        background = Bitmap.createScaledBitmap(background, canvas.getWidth(), canvas.getHeight(), false);
        canvas.drawBitmap(background, 0, 0, null);
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(32f);

        canvas.drawText("Total Money: "+game.getTotalMoney(), 0, h/2, paint);
        GameStateEnum gs = game.getGameState();
        canvas.drawText(gs.returnMessage(), w/2-70, h/2, paint);
        if (gs != GameStateEnum.DEFAULT && gs != GameStateEnum.INITIALIZING){
            canvas.drawText("Dealer total: "+dealerh.getTotalValue(), w/2-70, h/3-120, paint);
            if(gs != GameStateEnum.PLAYING){
                game.setGameState(GameStateEnum.DEFAULT);
            }
        }

        canvas.drawText("Total: "+playerh.getTotalValue(), w/2-70, h-20, paint);
        int i = 71 + (playerh.size()-1)* 96; //displacement
        for (Card card: playerh.getCards()){
            canvas.drawBitmap(cardImages.get(card.getImageLocation()), w/2 - i, 2*(h/3)-96 , null);
            i -=192;
        }
        i = 71 + (dealerh.size()-1)* 96; //displacement
        for (Card card: dealerh.getCards()){
            if (card.flipped){
                canvas.drawBitmap(back, w/2-i, h/3-96, null);
            }else {
                canvas.drawBitmap(cardImages.get(card.getImageLocation()), w / 2 - i, h / 3 - 96, null);
            }
            i-=192;
        }
        holder.unlockCanvasAndPost(canvas);
    }
}
