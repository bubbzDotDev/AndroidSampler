package macbeth.androidsampler.Themes;

import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import macbeth.androidsampler.R;

public class ThemeChanger extends AppCompatActivity {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Read theme from shared preferenecs and set before the setContentView function
        // If first time, use Theme_DeviceDefault
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        int theme = sp.getInt("theme", R.style.AppTheme);
        setTheme(theme);
        setContentView(R.layout.activity_theme_changer);
        setTitle("Themes");
    }

    private void applyTheme(int theme) {
        // Write theme to share preferenes
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("theme", theme);
        editor.commit();

        // Restart the activity
        recreate();
    }

    // These themes are defined in res / values / styles.xml

    public void setThemeBlack(View view) {
        applyTheme(R.style.AppTheme_DarkMode);
    }

    public void setThemeLight(View view) {
        applyTheme(R.style.AppTheme_LightMode);
    }

    public void setThemeRed(View view) {
        applyTheme(R.style.AppTheme_RedMode);
    }

}
