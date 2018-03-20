package ryanheath.blackjack;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.graphics.Bitmap;
import android.widget.TextView;

public class BlackjackActivity extends AppCompatActivity {

    Button hit, stand, bet;

    Bitmap back;
    Bitmap background;

    GameView mGameView;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //force full screen landscape
        mGameView = findViewById(R.id.GameView);
        mTextView = findViewById(R.id.betText);

        //setting up hit, stand and bet buttons
        hit = findViewById(R.id.hit);
        hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGameView.getGameState() == GameStateEnum.INITIALIZING) {
                    mGameView.hit();
                }
            }
        });

        stand = findViewById(R.id.stand);
        stand.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (mGameView.getGameState() == GameStateEnum.INITIALIZING){
                    mGameView.stand();
                }

            }
        });

        bet = findViewById(R.id.bet);
        bet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int betNo = getIntFromTextView();
                if(betNo > 0 && betNo <= mGameView.returnTotalMoney()) {
                    if (mGameView.getGameState() != GameStateEnum.INITIALIZING && mGameView.getGameState() != GameStateEnum.PLAYING) {
                        mGameView.initialize(betNo);
                    }
                }
            }
        });
    }
    /**
     * Grabs the number in the bet TextView.
     */
    public int getIntFromTextView() {//get number from bet text view,
        if(TextUtils.isEmpty(mTextView.getText())){
            return -1; //no text, returns invalid number
        }else{
            return(Integer.parseInt(mTextView.getText().toString()));
        }
    }

}
