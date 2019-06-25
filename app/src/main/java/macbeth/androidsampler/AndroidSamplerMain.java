package macbeth.androidsampler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import macbeth.androidsampler.ArcGisMapDisplay.ArcGisMapDisplay;
import macbeth.androidsampler.CalendarDisplay.CalendarDisplayActivity;
import macbeth.androidsampler.Files.FilesActivity;
import macbeth.androidsampler.FirebaseData.FirebaseDataActivity;
import macbeth.androidsampler.FirebaseLogin.FirebaseLogin;
import macbeth.androidsampler.Fragments.FragmentsActivity;
import macbeth.androidsampler.GPS.GPSActivity;
import macbeth.androidsampler.GoogleLogin.GoogleLogin;
import macbeth.androidsampler.JSONPost.JSONPostActivity;
import macbeth.androidsampler.JSONRecyclerView.JSONRecyclerView;
import macbeth.androidsampler.Menus.MenusActivity;
import macbeth.androidsampler.Music.MusicActivity;
import macbeth.androidsampler.Notifications.Notifications;
import macbeth.androidsampler.StorageSharedPreferences.StorageSharedPreferencesActivity;
import macbeth.androidsampler.Themes.ThemeChanger;

/**
 * The sampler app has a main activity with buttons to test out the different
 * capabilities of the Android SDK.  Each button will launch an activity
 * that exists in one of the sub folders.
 */
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

    public void runMenus(View view) {
        Intent intent = new Intent(this, MenusActivity.class);
        startActivity(intent);
    }

    public void runFragments(View view) {
        Intent intent = new Intent(this, FragmentsActivity.class);
        startActivity(intent);
    }

    public void runJSONPost(View view) {
        Intent intent = new Intent(this, JSONPostActivity.class);
        startActivity(intent);
    }

    public void runCalendarDisplay(View view) {
        Intent intent = new Intent(this, CalendarDisplayActivity.class);
        startActivity(intent);
    }

    public void runFiles(View view) {
        Intent intent = new Intent(this, FilesActivity.class);
        startActivity(intent);
    }

    public void runGPS(View view) {
        Intent intent = new Intent(this, GPSActivity.class);
        startActivity(intent);
    }

    public void runThemes(View view) {
        Intent intent = new Intent(this, ThemeChanger.class);
        startActivity(intent);
    }
}
