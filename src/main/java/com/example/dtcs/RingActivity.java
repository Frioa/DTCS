package com.example.dtcs;

/**
 * Created by q9163 on 21/02/2018.
 */
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RingActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);

        mediaPlayer = MediaPlayer.create(this, R.raw.beep);
        mediaPlayer.start();
    }

    public void stop(View view){
        mediaPlayer.stop();
        finish();
    }
}