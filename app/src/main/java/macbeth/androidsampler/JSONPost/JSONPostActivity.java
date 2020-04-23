package macbeth.androidsampler.JSONPost;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import macbeth.androidsampler.R;

public class JSONPostActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_post);
        setTitle("JSON Post");
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        textview = findViewById(R.id.textView9);
        textview.setMovementMethod(new ScrollingMovementMethod());
        disableProgressBar();
        new JSONPostLoader(this).execute(); // Load JSON data via a Post
    }

    public void displayDump(String jsonData) {
        textview.setText(jsonData);
    }

    public void enableProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void disableProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
