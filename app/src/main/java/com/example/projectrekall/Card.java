package com.example.projectrekall;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class Card extends Fragment {

    private Card instance;

    public Button cardButton;
    public ImageView cardFront;
    public ImageView card;

    protected boolean isFlipped = false;
    public boolean isEnable = false;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_card, container, false);
        instance = this;
        cardFront = (ImageView) view.findViewById(R.id.cardFront);
        card = (ImageView) view.findViewById(R.id.card);
        cardButton = (Button) view.findViewById(R.id.cardButton);
        cardButton.setOnClickListener(

                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (isFlipped || !isEnable) return;
                        ;
                        InGame.instance.onCardClicked(instance);
                    }
                }
        );

        return view;
    }

    public void setCardBackground(int ImageUrl) {
        cardFront.setImageResource(ImageUrl);
    }

    public void flip(View v) {
        if (isFlipped) {
            isFlipped = false;
            Animation fOutRB = AnimationUtils.loadAnimation(v.getContext(), R.anim.fadeout_rotate_b);
            Animation fInRB = AnimationUtils.loadAnimation(v.getContext(), R.anim.fadein_rotate_b);

            card.startAnimation(fInRB);
            cardFront.startAnimation(fOutRB);
            cardFront.setVisibility(View.INVISIBLE);
        } else {
            isFlipped = true;
            Animation fOutR = AnimationUtils.loadAnimation(v.getContext(), R.anim.fadeout_rotate);
            Animation fInR = AnimationUtils.loadAnimation(v.getContext(), R.anim.fadein_rotate);

            fInR.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation arg0) {
                }

                @Override
                public void onAnimationRepeat(Animation arg0) {
                }

                @Override
                public void onAnimationEnd(Animation arg0) {

                    InGame.instance.CheckFlip();

                }
            });

            card.startAnimation(fOutR);
            cardFront.startAnimation(fInR);
            cardFront.setVisibility(View.VISIBLE);
        }
    }

    public int getTagID() {
        int result = 1;
        try {
            result = Integer.parseInt(instance.getTag().toString());
        } catch (Exception ex) {
        }
        return result;
    }
}
