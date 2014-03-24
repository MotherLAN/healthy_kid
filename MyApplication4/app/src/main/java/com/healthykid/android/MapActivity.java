package com.healthykid.android;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Copied from GPS project
 */
public class MapActivity extends Activity {
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 10; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    protected LocationManager locationManager;
    protected Button retrieveLocationButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        retrieveLocationButton = (Button) findViewById(R.id.retrieve_location_button);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES, MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener());

        retrieveLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = showCurrentLatLng();
                if (latLng.latitude < 90) {
                    GoogleMap googleMap = ((MapFragment) getFragmentManager().
                            findFragmentById(R.id.map)).getMap();
                    if (googleMap != null) {
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 13);
                        googleMap.animateCamera(cameraUpdate);
                        googleMap.addMarker(new MarkerOptions().position(latLng)
                                .title(getPositionString(R.string.map_current_location, latLng)));
                    }
                }
            }
        });
    }

    protected LatLng showCurrentLatLng() {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double latitude = Double.MAX_VALUE, longitude = Double.MAX_VALUE;

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Toast.makeText(MapActivity.this, getPositionString(R.string.map_current_location,
                    latitude, longitude), Toast.LENGTH_LONG).show();
        }
        return new LatLng(latitude, longitude);
    }

    private String getPositionString(int locationID, double latitude, double longitude) {
        return String.format(getString(locationID) + "\n" + getString(R.string.map_longitude) +
                        ": \t%03.4f\n" + getString(R.string.map_latitude) + ": \t%03.4f",
                latitude, longitude);
    }

    private String getPositionString(int locationID, LatLng latLng) {
        return getPositionString(locationID, latLng.latitude, latLng.longitude);
    }

    private String getPositionString(int locationID, Location location) {
        return getPositionString(locationID, location.getLatitude(), location.getLongitude());
    }

    @SuppressWarnings("NonStaticInnerClassInSecureContext")
    private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Toast.makeText(MapActivity.this, getPositionString(R.string.map_new_location,
                    location), Toast.LENGTH_LONG).show();
        }

        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(MapActivity.this, "Provider status changed",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(MapActivity.this,
                    "Provider disabled by the user. GPS turned off",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(MapActivity.this,
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }
    }
}
