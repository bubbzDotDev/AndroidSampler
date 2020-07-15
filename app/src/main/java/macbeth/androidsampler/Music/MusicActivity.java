package macbeth.androidsampler.Music;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import macbeth.androidsampler.R;

public class MusicActivity extends AppCompatActivity {

    private Button playButton;
    private Button pauseButton;
    private Button stopButton;
    private MediaPlayer mediaPlayer;
    private EditText etStreamUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        setTitle("Music");
        playButton = findViewById(R.id.play);
        pauseButton = findViewById(R.id.pause);
        stopButton = findViewById(R.id.stop);
        etStreamUrl = findViewById(R.id.et_stream_url);
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);

        mediaPlayer = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public void playC(View view) {
        mediaPlayer = new MediaPlayer();
        MediaPlayer midiFileMediaPlayer = MediaPlayer.create(this, R.raw.note_middle_c);
        midiFileMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
        midiFileMediaPlayer.start();
    }

    public void playStream(View view) {
        if (!stopButton.isEnabled()) { // If stop is disabled then I'm not in a pause state and
                                       // I want to start to playing a new song
            // Load stream
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(etStreamUrl.getText().toString());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
                return;
            }
            // Setup listeners when loading music
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    playButton.setEnabled(false);
                    pauseButton.setEnabled(true);
                    stopButton.setEnabled(true);
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) { // End music when stream is done
                    playButton.setEnabled(true);
                    pauseButton.setEnabled(false);
                    stopButton.setEnabled(false);
                    mediaPlayer.stop();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    playButton.setEnabled(true);
                    pauseButton.setEnabled(false);
                    stopButton.setEnabled(false);
                    mediaPlayer.stop();
                    return true;
                }
            });
            playButton.setEnabled(false);
            stopButton.setEnabled(true);
            pauseButton.setEnabled(false);
            mediaPlayer.prepareAsync();  // Start buffering from streaming site
                                        // Will start playing when onPrepared gets called
        }
        else {   // Restarting previous music while paused
            playButton.setEnabled(false);
            stopButton.setEnabled(true);
            pauseButton.setEnabled(true);
            mediaPlayer.start();
        }
    }

    public void pauseStream(View view) {
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(true);
        mediaPlayer.pause();
    }

    public void stopStream(View view) {
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        mediaPlayer.stop();
        mediaPlayer.release();
    }



}
