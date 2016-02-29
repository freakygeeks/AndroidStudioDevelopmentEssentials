package com.chrysalis.audioapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.pm.PackageManager;
import java.io.IOException;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.os.Environment;
import android.widget.Button;

public class AudioAppActivity extends AppCompatActivity {

    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;
    private static String audioFilePath;
    private static Button stopButton;
    private static Button playButton;
    private static Button recordButton;
    private Boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_app);

        recordButton = (Button)findViewById(R.id.recordButton);
        playButton = (Button)findViewById(R.id.playButton);
        stopButton = (Button)findViewById(R.id.stopButton);

        if (!hasMicrophone()) {
            stopButton.setEnabled(false);
            playButton.setEnabled(false);
            recordButton.setEnabled(false);
        }
        else {
            playButton.setEnabled(false);
            stopButton.setEnabled(false);
        }

        audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myaudio.3gp";

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    protected boolean hasMicrophone() {
        PackageManager pmanager = this.getPackageManager();
        return pmanager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }

    public void recordAudio (View view) throws IOException {
        isRecording = true;
        stopButton.setEnabled(true);
        playButton.setEnabled(false);
        recordButton.setEnabled(false);

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
    }

    public void stopAudio (View view) {
        stopButton.setEnabled(false);
        playButton.setEnabled(true);

        if (isRecording) {
            recordButton.setEnabled(false);
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
        }
        else {
            mediaPlayer.release();
            mediaPlayer = null;
            recordButton.setEnabled(true);
        }
    }

    public void playAudio (View view) throws IOException {
        playButton.setEnabled(false);
        recordButton.setEnabled(false);
        stopButton.setEnabled(true);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(audioFilePath);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_audio_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
