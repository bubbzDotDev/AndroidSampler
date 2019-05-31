package macbeth.androidsampler.StorageSharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import macbeth.androidsampler.R;

public class StorageSharedPreferencesActivity extends AppCompatActivity {

    private EditText etZipCode;
    private static String SP_ZIP_CODE = "macbeth.androidsampler.zipcode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_shared_preferences);
        setTitle("Storage Shared Preferences");
        etZipCode = findViewById(R.id.zipcode);
        readSharedPreferences();
    }

    @Override
    protected void onPause() {
        super.onPause();
        writeSharedPreferences();
    }

    private void writeSharedPreferences() {
        SharedPreferences sp = getSharedPreferences("contact_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SP_ZIP_CODE, etZipCode.getText().toString());
        editor.commit();
    }

    private void readSharedPreferences() {
        SharedPreferences sp = getSharedPreferences("contact_info", Context.MODE_PRIVATE);
        String zipCode = sp.getString(SP_ZIP_CODE,"");
        etZipCode.setText(zipCode);
        Toast.makeText(this, "Shared Preferences Loaded", Toast.LENGTH_SHORT).show();
    }

}
