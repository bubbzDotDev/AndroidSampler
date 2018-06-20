package macbeth.androidsampler.Music;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import macbeth.androidsampler.R;

public class MusicActivity extends AppCompatActivity {

    private Button playButton;
    private Button pauseButton;
    private Button stopButton;
    private MediaPlayer mediaPlayer;
    private MediaPlayer midiFileMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        setTitle("Music");
        playButton = findViewById(R.id.play);
        pauseButton = findViewById(R.id.pause);
        stopButton = findViewById(R.id.stop);
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        midiFileMediaPlayer = MediaPlayer.create(this, R.raw.note_middle_c);
        initStream();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        midiFileMediaPlayer.release();
    }

    public void playC(View view) {
        midiFileMediaPlayer.start();
    }

    private void initStream() {
        mediaPlayer = new MediaPlayer();
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
            public void onCompletion(MediaPlayer mediaPlayer) {
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
        try {
            mediaPlayer.setDataSource("https://ia802600.us.archive.org/13/items/Mozart_201502/Mozart.mp3");
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void playStream(View view) {
        if (!stopButton.isEnabled()) { // Determines if not in paused state
            playButton.setEnabled(false);
            stopButton.setEnabled(true);
            pauseButton.setEnabled(false);
            mediaPlayer.prepareAsync();
        }
        else {
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
    }



}
