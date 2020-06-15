package com.example.projectrekall;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.projectrekall.InGame;

import static com.example.projectrekall.InGame.timeLeftAfterPause;

public class PauseMenu extends Fragment {


    public static View vInstance;
    public static ImageView pauseBack;
    public static ImageButton pauseButton;
    public static long timeLeftAfterPause;
    public static TextView pTitle;
    public static TextView rButton;
    public static TextView mButton;
    public static TextView retryButtonP;
    public static long timeGetterPause;
    public static int pauseCount;


    public PauseMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_pause_menu, container, false);

        vInstance = view;
        pTitle = view.findViewById(R.id.pauseTitle);
        pauseButton = view.findViewById(R.id.pauseButton);
        pauseBack = view.findViewById(R.id.pauseBack);
        rButton = view.findViewById(R.id.resumeButton);
        mButton = view.findViewById(R.id.mainMenuButton2);
        retryButtonP = view.findViewById(R.id.menuButtonOver);

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InGame.mTimer.cancel();
                timeLeftAfterPause = InGame.timeLeftGetter;
                pauseBack.setVisibility(View.VISIBLE);
                pTitle.setVisibility(View.VISIBLE);
                rButton.setVisibility(view.VISIBLE);
                mButton.setVisibility(view.VISIBLE);
                pauseCount++;

            }
        });

        retryButtonP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), InGame.class);
                startActivity(i);

            }
        });

        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseBack.setVisibility(View.GONE);
                pTitle.setVisibility(View.GONE);
                rButton.setVisibility(view.GONE);
                mButton.setVisibility(view.GONE);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MainMenu.class);
                startActivity(i);

            }
        });
        return view;
    }

    public void updateTimer(){

        int min = (int) timeLeftAfterPause / 60000;
        int sec = (int) timeLeftAfterPause % 60000 / 1000;
        int millisec = (int) (timeLeftAfterPause/ 100) % 10;
        timeGetterPause = timeLeftAfterPause;
        String timerLeftText = "";

        if (min < 10) timerLeftText += "0";
        timerLeftText += min;
        timerLeftText +=":";
        if (sec < 10) timerLeftText += "0";
        timerLeftText += sec;
        timerLeftText +=":";
        timerLeftText += millisec;
        InGame.timerString.setText(timerLeftText);
    }

    public void stopTimer(){
        InGame.mTimer.cancel();
        Animation fI = AnimationUtils.loadAnimation(InGame.instance, R.anim.fadein_readyset);
        InGame.bigText.startAnimation(fI);
        InGame.bigText.setText("GAME OVER");
        InGame.bigText.setTextSize(60);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                //start activity after finish game
                startActivity(new Intent(getActivity(), GameOver.class));
            }

        }, 2000L);
    }



}
