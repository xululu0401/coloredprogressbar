package com.xululu.customcoloredprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xululu.coloredprogressbar.ColoredProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ColoredProgressBar pb = findViewById(R.id.color_pb);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int prog = 0;
                while (prog <= 100){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pb.setProgress(prog++);
                }

            }
        }).start();
    }
}
