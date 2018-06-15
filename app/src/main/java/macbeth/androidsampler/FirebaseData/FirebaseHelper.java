package macbeth.androidsampler.FirebaseData;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {

    public final static String log = "FirebaseHelper";
    private DatabaseReference databaseRef;
    private FirebaseObserver observer;

    public FirebaseHelper(String database, FirebaseObserver observer) {
        databaseRef = FirebaseDatabase.getInstance().getReference(database);
        this.observer = observer;
        registerListener();
    }

    public void writeFirebase(Object obj) {
        new FirebaseWriterTask().execute(obj);
    }

    private void registerListener() {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (observer != null) {
                    observer.firebaseCallback(dataSnapshot);
                    Log.i(FirebaseHelper.log,"Received Firebase Data");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseRef.addChildEventListener(childEventListener);
    }

    private class FirebaseWriterTask extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... objects) {
            String key = databaseRef.push().getKey();
            databaseRef.child(key).setValue(objects[0]);
            return null;
        }
    }
}
