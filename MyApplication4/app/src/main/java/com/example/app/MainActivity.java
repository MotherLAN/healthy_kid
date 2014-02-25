package com.example.app;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.sql.Connection;
import java.sql.DriverManager;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    PlaceholderFragment f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        f = new PlaceholderFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, f)
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateTextView(String toThis) {

      //  TextView mytextView = (TextView) findViewById(R.id.textView);
        //mytextView.setText(toThis);

        return;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
           IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
           if (scanResult != null) {
                    String bar = scanResult.getContents();
                        //id 1, Jhon, doe... shopping cart
               //Log.w("Anything", "Penis?");
               ArrayList nut;
               try {
                  nut = new NutritionAPIRequester(bar).getNutrition();
                  String s = "";
                   for (int i = 0; i < nut.size(); i++)
                       s += NutritionAPIRequester.NUTRIENTS[i] + ": " + nut.get(i);
                   //updateTextView(s);
                   TextView tv = (TextView) view
               } catch (IOException e) {
                   nut = null;
                   e.printStackTrace();
               }
//ip 144.118.225.221

        }

               //Toast.makeText(MainActivity.this, nut.toString(), Toast.LENGTH_SHORT).show();
           }
           // else continue with any other code you need in the method


    /**
     * A placeholder fragment containing a simple view.
     */
    public void selfDestruct(View v){

        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);

        integrator.initiateScan();
    }

   // public void Nuke(View v) {

    //    Intent i = new Intent(MainActivity.this,Maptivity.class);
    //    Log.w("Anything", "Falls fog the mutton?");
     //   startActivity(i);
  //  }
    public class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }


    }

}
