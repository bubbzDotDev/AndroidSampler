package macbeth.androidsampler.ActivityResult;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import macbeth.androidsampler.R;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Button button = findViewById(R.id.button);
        button.setOnClickListener((view) -> {
            EditText name = findViewById(R.id.input);
            Intent i = new Intent();
            i.putExtra("NAME", name.getText().toString());
            setResult(100, i);
            finish();
        });
    }


}
