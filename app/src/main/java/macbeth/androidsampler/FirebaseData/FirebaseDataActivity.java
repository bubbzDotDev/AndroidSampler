package macbeth.androidsampler.FirebaseData;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;

import macbeth.androidsampler.R;

public class FirebaseDataActivity extends AppCompatActivity implements FirebaseObserver {

    private FirebaseHelper firebaseHelper;
    private EditText evNameData;
    private EditText evNameToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_data);
        setTitle("Firebase Data");
        evNameData = findViewById(R.id.editText2);
        evNameToAdd = findViewById(R.id.editText);
        firebaseHelper = new FirebaseHelper("names", this);
    }

    @Override
    public void firebaseCallback(DataSnapshot data) {
        evNameData.append(data.getValue(String.class)+" ");
    }

    public void submitName(View view) {
        firebaseHelper.writeFirebase(evNameToAdd.getText().toString());
    }
}
