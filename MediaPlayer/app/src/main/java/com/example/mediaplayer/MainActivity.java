package com.example.mediaplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.MediaController;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    VideoView videoView;
    EditText videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        videoUrl = findViewById(R.id.videoUrl);

        MediaController controller = new MediaController(this);
        videoView.setMediaController(controller);
        controller.setAnchorView(videoView);
    }

    // 🎵 OPEN AUDIO (RAW FILE SELECT DIALOG)
    public void openAudio(View view) {

        String[] audioFiles = {"Song 1"};

        int[] audioResIds = {
                R.raw.sample
        };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Select Audio File");

        builder.setItems(audioFiles, (dialog, which) -> {

            if (mediaPlayer != null) {
                mediaPlayer.release();
            }

            mediaPlayer = MediaPlayer.create(this, audioResIds[which]);
            Toast.makeText(this, audioFiles[which] + " Selected", Toast.LENGTH_SHORT).show();
        });

        builder.show();
    }

    // 🎥 OPEN VIDEO FROM URL
    public void openVideo(View view) {
        String url = videoUrl.getText().toString().trim();

        if (url.isEmpty()) {
            Toast.makeText(this, "Enter URL first", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(mp -> {
            Toast.makeText(this, "Video Started", Toast.LENGTH_SHORT).show();
            videoView.start();
        });

        videoView.setOnErrorListener((mp, what, extra) -> {
            Toast.makeText(this, "Video Error", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    // ▶ PLAY
    public void playMedia(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }

        if (!videoView.isPlaying()) {
            videoView.start();
        }
    }

    // ⏸ PAUSE
    public void pauseMedia(View view) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }

        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }

    // ⏹ STOP
    public void stopMedia(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (videoView.isPlaying()) {
            videoView.stopPlayback();
        }
    }

    // 🔄 RESTART
    public void restartMedia(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        }

        videoView.seekTo(0);
        videoView.start();
    }
}