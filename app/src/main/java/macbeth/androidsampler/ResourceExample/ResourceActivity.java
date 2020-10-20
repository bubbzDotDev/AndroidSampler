package macbeth.androidsampler.ResourceExample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import macbeth.androidsampler.R;

public class ResourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

        TextView tvPoem = findViewById(R.id.tv_poem);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.poem)));
            StringBuilder sb = new StringBuilder();
            boolean done = false;
            do {
                String text = reader.readLine();
                if (text != null) {
                    sb.append(text);
                    sb.append("\n");
                } else {
                    done = true;
                }
            } while (!done);
            tvPoem.setText(sb.toString());
        } catch (IOException ioe) {
            tvPoem.setText("Unable to read poem.txt");
        }

    }
}