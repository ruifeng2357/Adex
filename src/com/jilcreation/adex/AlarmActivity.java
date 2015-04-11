package com.jilcreation.adex;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class AlarmActivity extends SuperActivity {
    protected MediaPlayer mediaPlayer;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        mediaPlayer = new MediaPlayer().create(this, R.raw.alarm);
        try {
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();

        TextView textMessage = (TextView) findViewById(R.id.textMessage);
        textMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    try {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    } catch (Exception ex) {}
                }

                Intent intent = new Intent(AlarmActivity.this, MainMenuActivity.class);
                intent.putExtra("SELINDEX", MainMenuActivity.LUCKY_DRAW);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getMainLayoutRes() {
        return R.id.rlParent;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception ex) {}
        }
    }
}
