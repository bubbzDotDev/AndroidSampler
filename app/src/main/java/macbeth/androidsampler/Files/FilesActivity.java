package macbeth.androidsampler.Files;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import macbeth.androidsampler.R;

public class FilesActivity extends AppCompatActivity {

    private EditText param1;
    private EditText param2;
    private EditText param3;
    private TextView fileContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        param1 = findViewById(R.id.file_param1);
        param2 = findViewById(R.id.file_param2);
        param3 = findViewById(R.id.file_param3);
        fileContents = findViewById(R.id.file_contents);
    }

    /**
     * Create a Data object out of the data in the 3 text fields, convert it to JSON,
     * and then save the JSON text to a file.
     * @param view
     */
    public void saveFile(View view) {
        try {
            Data data = new Data(param1.getText().toString(),
                    param2.getText().toString(),
                    param3.getText().toString());

            Gson gson = new Gson();
            String json = gson.toJson(data);

            // openFileOutput is the Android function to get a file for writing from the phone.  Wrapped the stream
            // into a BufferedWriter for ease of use.
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("ParamFile.txt", Context.MODE_PRIVATE)));
            writer.write(json);
            writer.close();
            fileContents.setText("Wrote data to ParamFile.txt");
        }
        catch (IOException ioe) {
            fileContents.setText("Unable to save to ParamFile.txt");
            Log.d("files",ioe.toString());
        }
    }

    /**
     * Read the JSON data from a file, convert it to a Data object, and (using the toString) display
     * the contents of the data object to the phone screen.
     * @param view
     */
    public void loadFile(View view) {
        try {
            // openFileInput is the Android function to get a file for reading from the phone.  Wrapped the stream
            // into a BufferedRead for ease of use.
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("ParamFile.txt")));
            String json = reader.readLine();
            reader.close();

            Gson gson = new Gson();
            Data data = gson.fromJson(json, Data.class);
            fileContents.setText(data.toString());
        }
        catch (IOException ioe) {
            fileContents.setText("Unable to load from ParamFile.txt");
            Log.d("files",ioe.toString());

        }
    }
}
