package macbeth.androidsampler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import macbeth.androidsampler.ArcGisMapDisplay.ArcGisMapDisplay;
import macbeth.androidsampler.FirebaseData.FirebaseDataActivity;
import macbeth.androidsampler.FirebaseLogin.FirebaseLogin;
import macbeth.androidsampler.GoogleLogin.GoogleLogin;
import macbeth.androidsampler.JSONRecyclerView.JSONRecyclerView;
import macbeth.androidsampler.Music.MusicActivity;
import macbeth.androidsampler.Notifications.Notifications;
import macbeth.androidsampler.StorageSharedPreferences.StorageSharedPreferencesActivity;

public class AndroidSamplerMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_sampler_main);
    }

    public void runJSONRecyclerView(View view) {
        Intent intent = new Intent(this, JSONRecyclerView.class);
        startActivity(intent);
    }

    public void runArcGisMapDisplay(View view) {
        Intent intent = new Intent(this, ArcGisMapDisplay.class);
        startActivity(intent);
    }

    public void runNotifications(View view) {
        Intent intent = new Intent(this, Notifications.class);
        startActivity(intent);
    }

    public void runFirebaseLogin(View view) {
        Intent intent = new Intent(this, FirebaseLogin.class);
        startActivity(intent);
    }

    public void runGoogleLogin(View view) {
        Intent intent = new Intent(this, GoogleLogin.class);
        startActivity(intent);
    }

    public void runFirebaseData(View view) {
        Intent intent = new Intent(this, FirebaseDataActivity.class);
        startActivity(intent);
    }

    public void runMusic(View view) {
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }

    public void runStorageSharedPreferences(View view) {
        Intent intent = new Intent(this, StorageSharedPreferencesActivity.class);
        startActivity(intent);
    }
}
