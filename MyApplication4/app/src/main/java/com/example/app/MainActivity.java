package com.example.app;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends ActionBarActivity {
    HashMap<Nutrient, String> nutrients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        displayNutritionalInfo();
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

    @Override
    protected void onPause() {
        super.onPause();

    }

    protected void updateTextView(String toThis) {
        TextView mytextView = (TextView) findViewById(R.id.textView);
        if (mytextView != null)
            mytextView.setText(toThis);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String barcode = scanResult.getContents();
            if (barcode == null) {
                Log.w(getClass().getName(), "Barcode not scanned");
                return;
            }
            Log.d("barcode UPC", barcode);
            nutrients = NutritionAPIRequester.getNutrition(barcode, this);
            displayNutritionalInfo();
        }

        // Toast.makeText(MainActivity.this, nut.toString(), Toast.LENGTH_SHORT).show();
    }

    public void scanBarcode(View v) {
        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
        integrator.initiateScan();
    }

    public void displayNutritionalInfo() {
        if (nutrients == null) {
            updateTextView(getString(R.string.main_textview_default));
            Log.w(getClass().getName(), "No nutritional info available");
            return;
        }
        String s = "";
        for (Nutrient nutrient : Nutrient.values()) {
            String val = nutrients.get(nutrient);
            if (val.length() > 0 && !val.equals("0"))
                s += getString(nutrient.display) + ": " + val + nutrient.unit + "\n";
        }
        Log.d("nutrition size:", "" + nutrients.size());

        updateTextView(s);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        displayNutritionalInfo();
    }

    public void nuke(View v) {
        updateTextView(getString(R.string.main_textview_updated));
        //    Intent i = new Intent(MainActivity.this,Maptivity.class);
        //   startActivity(i);
    }

    public static class PlaceholderFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }
    }
}
