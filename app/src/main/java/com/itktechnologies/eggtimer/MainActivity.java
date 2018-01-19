package com.itktechnologies.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView txtTimer;
    Button cmdGo;
    CountDownTimer counter;
    boolean isActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        txtTimer = (TextView)findViewById(R.id.txtTimer);
        cmdGo = (Button)findViewById(R.id.cmdGo);

        timerSeekBar.setMax(120);
        timerSeekBar.setProgress(0);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                setTextOfTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void cmdGo (View v) {

        if ( isActive == false ) {
            isActive = true;

            cmdGo.setText("Stop!");
            counter = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100,1000) {
                public void onTick(long milisecondsUntilDone) {
                    int secs = (int)milisecondsUntilDone/1000;

                    setTextOfTimer(secs);
                    timerSeekBar.setProgress(secs);
                }

                public void onFinish() {
                    performFinish(true);
                }

            }.start();

        } else {
            counter.cancel();
            performFinish(false);
        }
    }

    public void setTextOfTimer(int i) {

        int mins = (int) i / 60;
        int secs = i - mins * 60;

        txtTimer.setText(Integer.toString(mins) + ":" + String.format("%02d", secs));

    }

    public void performFinish(boolean playSound) {

        timerSeekBar.setProgress(0);
        cmdGo.setText("Go!");
        isActive = false;

        if (playSound == true) {
            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
            mediaPlayer.start();
        }

    }
}
