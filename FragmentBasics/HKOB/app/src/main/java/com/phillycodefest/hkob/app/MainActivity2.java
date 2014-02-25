package com.phillycodefest.hkob.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //setup button listener
        Button startButton = (Button) findViewById(android.R.id.activityclass);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startGame();
            }
        });
    }



    private void startGame() {
        Intent launchGame = new Intent(MainActivity2.this, Simple.class);
        startActivity(launchGame);
    }

    }

