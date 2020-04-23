package macbeth.androidsampler.ActivityResult;

import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import macbeth.androidsampler.R;

public class Activity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        Button button = findViewById(R.id.button_get);
        button.setOnClickListener((view)-> {
            Intent i = new Intent(this, Activity2.class);
            startActivityForResult(i, 0);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView tv = findViewById(R.id.result);
        tv.setText(data.getStringExtra("NAME"));
    }
}
