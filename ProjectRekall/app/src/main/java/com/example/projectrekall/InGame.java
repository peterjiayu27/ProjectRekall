package com.example.projectrekall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import static com.example.projectrekall.Card.*;

public class InGame extends AppCompatActivity{

    Handler readyback = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Animation rDB = AnimationUtils.loadAnimation(instance, R.anim.ready_black);
            Animation fI = AnimationUtils.loadAnimation(instance, R.anim.fadein_readyset);
            bigText.startAnimation(fI);
            black.startAnimation(rDB);

        }
    };

    Handler setback = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Animation fI = AnimationUtils.loadAnimation(instance, R.anim.fadein_readyset);
            black.setVisibility(View.INVISIBLE);
            bigText.startAnimation(fI);
            bigText.setText("SET");
        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Animation fO = AnimationUtils.loadAnimation(instance, R.anim.fadeout);
            bigText.setText("GO!!!");
            bigText.startAnimation(fO);
        }
    };


    public static InGame instance;
    public int streakCount = 1;
    public Card[] cards;
    public static int[] fronts;

    private ImageView black;
    public static CountDownTimer mTimer;
    public static TextView timerString;
    private TextView level;
    private int timePressure;
    private int levelInt = 1;
    private static long timeLeft; //1 min 33 sec;
    public static long timeLeftGetter;
    private TextView score;
    private int scoreInt;
    private Boolean timerRunning;
    private int numOfCards = 18;
    private TextView streak;
    public static TextView bigText;
    public static String scoreStr;
    public static int timeLeftAfterPause;

    private int MatchedCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeLeft = 93000;
        instance = this;
        MatchedCount = 0;

        setContentView(R.layout.activity_in_game);
        score = findViewById(R.id.score);
        streak = findViewById(R.id.streak);
        level = findViewById(R.id.level);
        black = findViewById(R.id.black);
        bigText = findViewById(R.id.ready);
        Runnable ra = new Runnable(){
            @Override
            public void run(){

                long futureTime = System.currentTimeMillis() + 1000;

                while(System.currentTimeMillis() < futureTime){
                    synchronized (this){
                        try{
                            wait(futureTime-System.currentTimeMillis());
                        }catch(Exception e){ }
                    }
                }
                readyback.sendEmptyMessage(0);
            }
        };
        Runnable rb = new Runnable(){
            @Override
            public void run(){

                long futureTime = System.currentTimeMillis() + 2000;

                while(System.currentTimeMillis() < futureTime){
                    synchronized (this){
                        try{
                            wait(futureTime-System.currentTimeMillis());
                        }catch(Exception e){ }
                    }
                }
                setback.sendEmptyMessage(0);
            }
        };
        Thread myThreadA = new Thread(ra);
        myThreadA.start();
        Thread myThreadB = new Thread(rb);
        myThreadB.start();
        Prepare();
    }

    private void Prepare()
    {
        startTimer();
        MatchedCount = 0;

        cards = new Card[numOfCards];
        timerString = findViewById(R.id.time);



        Runnable rd = new Runnable(){
            @Override
            public void run(){

                long futureTime = System.currentTimeMillis() + 3000;

                while(System.currentTimeMillis() < futureTime){
                    synchronized (this){
                        try{
                            wait(futureTime-System.currentTimeMillis());
                        }catch(Exception e){ }
                    }
                }
                handler.sendEmptyMessage(0);
            }
        };
        Thread myThreadD = new Thread(rd);
        myThreadD.start();

        cards[0] = (Card)getSupportFragmentManager().findFragmentById(R.id.c1);
        cards[1] = (Card) getSupportFragmentManager().findFragmentById(R.id.c2);
        cards[2] = (Card)getSupportFragmentManager().findFragmentById(R.id.c3);
        cards[3] = (Card)getSupportFragmentManager().findFragmentById(R.id.c4);
        cards[4] = (Card)getSupportFragmentManager().findFragmentById(R.id.c5);
        cards[5] = (Card)getSupportFragmentManager().findFragmentById(R.id.c6);
        cards[6] = (Card)getSupportFragmentManager().findFragmentById(R.id.c7);
        cards[7] = (Card)getSupportFragmentManager().findFragmentById(R.id.c8);
        cards[8] = (Card)getSupportFragmentManager().findFragmentById(R.id.c9);
        cards[9] = (Card)getSupportFragmentManager().findFragmentById(R.id.c10);
        cards[10] = (Card)getSupportFragmentManager().findFragmentById(R.id.c11);
        cards[11] = (Card)getSupportFragmentManager().findFragmentById(R.id.c12);
        cards[12] = (Card)getSupportFragmentManager().findFragmentById(R.id.c13);
        cards[13] = (Card)getSupportFragmentManager().findFragmentById(R.id.c14);
        cards[14] = (Card)getSupportFragmentManager().findFragmentById(R.id.c15);
        cards[15] = (Card)getSupportFragmentManager().findFragmentById(R.id.c16);
        cards[16] = (Card)getSupportFragmentManager().findFragmentById(R.id.c17);
        cards[17] = (Card)getSupportFragmentManager().findFragmentById(R.id.c18);

        fronts = new int[numOfCards];
        fronts[0] = R.drawable.f1;
        fronts[1] = R.drawable.f2;
        fronts[2] = R.drawable.f3;
        fronts[3] = R.drawable.f4;
        fronts[4] = R.drawable.f5;
        fronts[5] = R.drawable.f6;
        fronts[6] = R.drawable.f7;
        fronts[7] = R.drawable.f8;
        fronts[8] = R.drawable.f9;
        fronts[9] = R.drawable.f1;
        fronts[10] = R.drawable.f2;
        fronts[11] = R.drawable.f3;
        fronts[12] = R.drawable.f4;
        fronts[13] = R.drawable.f5;
        fronts[14] = R.drawable.f6;
        fronts[15] = R.drawable.f7;
        fronts[16] = R.drawable.f8;
        fronts[17] = R.drawable.f9;


        shuffleFronts();

        for(int i = 0; i < numOfCards; i++){
            if(cards[i].isFlipped) cards[i].flip(cards[i].getView());
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //start activity after splash screen
                for(int i = 0; i < numOfCards; i++){
                    cards[i].setCardBackground(fronts[i]);
                    cards[i].isEnable = true;
                }
            }

        }, 2000L);
    }


    public void startTimer(){
        mTimer = new CountDownTimer(timeLeft, 1) {
            @Override
            public void onTick(long l) {
                timerRunning = true;
                timeLeft = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                stopTimer();
            }
        }.start();

    }




    public void stopTimer(){
        mTimer.cancel();
        timerRunning = false;
        Animation fI = AnimationUtils.loadAnimation(instance, R.anim.fadein_readyset);
        bigText.startAnimation(fI);
        bigText.setText("GAME OVER");
        bigText.setTextSize(60);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                //start activity after finish game
                startActivity(new Intent(InGame.this, GameOver.class));
                finish();
            }

        }, 2000L);
    }

    public void updateTimer(){
        int min = (int) timeLeft / 60000;
        int sec = (int) timeLeft % 60000 / 1000;
        int millisec = (int) (timeLeft / 100) % 10;
        timeLeftGetter = timeLeft;
        String timerLeftText = "";

        if (min < 10) timerLeftText += "0";
        timerLeftText += min;
        timerLeftText +=":";
        if (sec < 10) timerLeftText += "0";
        timerLeftText += sec;
        timerLeftText +=":";
        timerLeftText += millisec;
        timerString.setText(timerLeftText);
    }

    public void shuffleFronts(){
        Random rand = new Random();
        for(int i = 0; i < numOfCards; i++){
            int temp = fronts[i];

            int swapIndex = rand.nextInt(18);

            fronts[i] = fronts[swapIndex];

            fronts[swapIndex] = temp;
        }
    }


    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private static int FlipCount = 0;
    private static Card FirstFlip;
    private static Card SecondFlip;
    public void onCardClicked(Card card) {
        //System.out.println(" >> Tag Clicked: " + card.getTag().toString());
        if(FlipCount >= 2) return;

        if(FlipCount == 0) {
            FirstFlip = card;
        }
        else if(FlipCount == 1) {
            SecondFlip = card;
        }

        card.flip(card.getView());
        FlipCount++;
    }

    public void CheckFlip()
    {
        if(FlipCount == 2) {
            Boolean isMatch = false;

            if(fronts[FirstFlip.getTagID() - 1] == fronts[SecondFlip.getTagID() - 1]) isMatch = true;

            if(isMatch)  {
                //Match
                FirstFlip.isEnable = false;
                SecondFlip.isEnable = false;
                MatchedCount++;
                if(streakCount==1){
                    streakCount++;
                }else if(streakCount==12){
                    streakCount=12;
                }
                else{
                    streakCount += 2;
                }
                scoreInt += 1000 * streakCount;
                 scoreStr = "" + scoreInt;
                score.setText(scoreStr);
                changeStreakColor();
                String streakStr = "" + streakCount + "x";
                streak.setText(streakStr);
            } else {
                //Not Match
                FirstFlip.flip(FirstFlip.getView());
                SecondFlip.flip(SecondFlip.getView());
                streakCount = 1;
                String streakStr = "" + streakCount + "x";
                streak.setText(streakStr);
                changeStreakColor();
            }

            if(MatchedCount == numOfCards / 2)
            {
                //Next level
                levelInt++;
                String lvlString = "" + levelInt;
                level.setText(lvlString);
                timePressure+=5000;
                mTimer.cancel();
                timeLeft = 93000 - timePressure;
                Prepare();
                Animation fI = AnimationUtils.loadAnimation(instance, R.anim.fadein_readyset);
                Animation fO = AnimationUtils.loadAnimation(instance, R.anim.fadeout);
                bigText.setVisibility(View.VISIBLE);
                bigText.setText("TIME +");
                bigText.startAnimation(fI);
                bigText.startAnimation(fO);
            }
            FlipCount = 0;
        }


    }

    //for each score multiplier, the color of the multiplier changes
    public void changeStreakColor(){
        if (streakCount == 1){
            streak.setTextColor(Color.BLACK);
        }
        else if (streakCount == 2){
            streak.setTextColor(Color.YELLOW);
        }
        else if (streakCount == 4){
            streak.setTextColor(Color.GREEN);
        }
        else if (streakCount == 6){
            streak.setTextColor(Color.CYAN);
        }
        else if (streakCount == 8){
            streak.setTextColor(Color.BLUE);
        }
        else if (streakCount == 10){
            streak.setTextColor(Color.MAGENTA);
        }
        else if (streakCount == 12){
            streak.setTextColor(Color.RED);
        }

    }

}
