package macbeth.androidsampler.StorageSharedPreferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import macbeth.androidsampler.R;

public class StorageSharedPreferencesActivity extends AppCompatActivity {

    private EditText etZipCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_shared_preferences);
        setTitle("Storage Shared Preferences");
        etZipCode = findViewById(R.id.zipcode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        readSharedPreferences();
    }

    @Override
    protected void onPause() {
        super.onPause();
        writeSharedPreferences();
    }

    private void writeSharedPreferences() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("macbeth.androidsampler.zipcode", etZipCode.getText().toString());
        editor.commit();
    }

    private void readSharedPreferences() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String zipCode = sp.getString("macbeth.androidsampler.zipcode","");
        etZipCode.setText(zipCode);
        Toast.makeText(this, "Shared Preferences Loaded", Toast.LENGTH_SHORT).show();
    }

}
