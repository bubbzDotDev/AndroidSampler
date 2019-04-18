package macbeth.androidsampler.FirebaseData;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The purpose of this class is to provide support for reading and writing to Firebase
 * A FirebaseHelper object should be created for each database.  An observer object
 * is provided so that the initial read of data and any new data added can be notified to the
 * observer.  This class is not yet written to support updates to Firebase data.  To do this
 * you would need to add a new callback to FirebaseObserver and implement one of the
 * functions in the ChildEventListener below.
 *
 * writeFirebase - Write an object to the database in the background.  No notification is
 * given when the write is completed.
 *
 * https://firebase.google.com/docs/android/setup
 * https://firebase.google.com/docs/database/android/read-and-write
 *
 */
public class FirebaseHelper {

    private final static String log = "FirebaseHelper";
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
