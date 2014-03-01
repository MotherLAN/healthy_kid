package com.example.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    protected void updateTextView(String toThis) {
        TextView mytextView = (TextView) findViewById(R.id.textView);
        mytextView.setText(toThis);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String bar = scanResult.getContents();
            ArrayList nut;
            try {
                nut = new NutritionAPIRequester(bar).getNutrition();
                String s = "";
                for (int i = 0; i < nut.size(); i++)
                    s += NutritionAPIRequester.NUTRIENTS[i] + ": " + nut.get(i);
                //TextView tv = (TextView) view;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Toast.makeText(MainActivity.this, nut.toString(), Toast.LENGTH_SHORT).show();
    }
    // else continue with any other code you need in the method

    public void selfDestruct(View v) {
        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
        integrator.initiateScan();
    }

    public void nuke(View v) {
        updateTextView("abcd");
        //    Intent i = new Intent(MainActivity.this,Maptivity.class);
        //   startActivity(i);
    }

    private class PlaceholderFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }
    }

}
