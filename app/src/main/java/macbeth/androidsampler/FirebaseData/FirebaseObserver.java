package macbeth.androidsampler.FirebaseData;

import com.google.firebase.database.DataSnapshot;

public interface FirebaseObserver {
    public void firebaseCallback(DataSnapshot data);
}
