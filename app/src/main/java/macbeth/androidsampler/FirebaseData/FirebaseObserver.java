package macbeth.androidsampler.FirebaseData;

import com.google.firebase.database.DataSnapshot;

/**
 * To receive FireBase data from FirebaseHelper, a class needs to implement this interface.
 */
public interface FirebaseObserver {
    public void firebaseCallback(DataSnapshot data);
}
