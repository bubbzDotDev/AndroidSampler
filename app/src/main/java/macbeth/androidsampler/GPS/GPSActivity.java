package macbeth.androidsampler.GPS;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import macbeth.androidsampler.R;

public class GPSActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private TextView tvLat;
    private TextView tvLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        setTitle("GPS");
        tvLat = findViewById(R.id.tv_lat);
        tvLong = findViewById(R.id.tv_long);

        // Get the Location Manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // If permissions are not granted for location, then we will ask for them
        // Assuming that permissions work, then we will showLocation and register for updaates
        requestPermissions();
        showLocation();
        registerLocationListener();
    }

    private void registerLocationListener() {
        try {
            // Request updates. When they occur, then update the location fields
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    tvLat.setText(String.valueOf(location.getLatitude()));
                    tvLong.setText(String.valueOf(location.getLongitude()));
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }
        catch (SecurityException se) {
            displayPermissionError();        }
        }

    private void showLocation() {
        try {
            // Get the initial location
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            tvLat.setText(String.valueOf(location.getLatitude()));
            tvLong.setText(String.valueOf(location.getLongitude()));
        }
        catch (SecurityException se) {
            displayPermissionError();
        }
    }

    private void displayPermissionError() {
        tvLat.setText("No permissions");
        tvLong.setText("No permissions");
    }

    private void requestPermissions() {
        // If we don't have ACCESS_FINE_LOCATION permission, then request it
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // When we get the permission result back, lets assume we got it and redisplay location
        // and re-register listener (because they probably failed before).
        showLocation();
        registerLocationListener();
    }
}
