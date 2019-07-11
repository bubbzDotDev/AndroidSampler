package macbeth.androidsampler.Pictures;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.FileNotFoundException;
import java.io.IOException;

import macbeth.androidsampler.R;

public class Pictures extends AppCompatActivity {

    ImageView ivPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
        ivPicture = findViewById(R.id.ivPicture);

    }

    // Send Intent to the Media Store and wait for a response showing
    // what was picked.
    public void pickPhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1); // The 1 is arbitrary
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Uri pictureLink = data.getData();
            try {
                Bitmap picture = MediaStore.Images.Media.getBitmap(getContentResolver(), pictureLink);
                ivPicture.setImageBitmap(picture);
            }
            catch (IOException ioe) {
                Log.d("Pictures","Unable to open picture: "+ioe);
            }


        }
    }
}
