package macbeth.androidsampler.GoogleLogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import macbeth.androidsampler.R;

/**
 *  Will login to Google: https://developers.google.com/identity/sign-in/android/sign-in
 *
 *  To get the default_web_client_id, need to goto https://console.firebase.google.com
 *  or https://console.developers.google.com/apis/credentials
 *  Need to put the web client id into strings.xml.  If you do this, then you don't
 *  need the Google Services Plugin in the gradle files.
 *
 */

public class GoogleLogin extends AppCompatActivity {
    private String TAG = "GoogleLogin";
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);
        setTitle("Google Login");
        updateProgressBar(false);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 0) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                updateUI(account);
                updateProgressBar(false);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                updateUI(null);
                updateProgressBar(false);
            }
        }
    }


    public void signIn(View view) {
        updateProgressBar(true);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 0);
    }

    public void signOut(View view) {
        updateProgressBar(true);
        // Firebase sign out

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        updateUI(null);
                        updateProgressBar(false);
                    }
                });
    }

    private void updateUI(GoogleSignInAccount user) {
        TextView tv = findViewById(R.id.loginStatus2);
        if (user == null) {
            tv.setText("Not signed in to Google.");
        }
        else {
            tv.setText("Logged into Google: "+user.getDisplayName());
        }

    }

    private void updateProgressBar(boolean on) {
        ProgressBar pb = findViewById(R.id.loginProgress2);
        if (on) {
            pb.setVisibility(ProgressBar.VISIBLE);
        }
        else {
            pb.setVisibility(ProgressBar.INVISIBLE);
        }
    }
}
