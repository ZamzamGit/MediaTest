package com.example.mediatest;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener {

    private MediaPlayer mediaPlayer;
    private Button playBtn;
    private Button stopBtn;
    private Button openBtn;
    private Button nextBtn;
    private TextView textView;
    private int REQUEST_CODE = 1001;
    private Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playBtn = findViewById(R.id.playBtn);
        stopBtn = findViewById(R.id.stopBtn);
        openBtn = findViewById(R.id.openBtn);
        nextBtn = findViewById(R.id.nextBtn);
        textView = findViewById(R.id.textView);
        playBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        openBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.playBtn)) {
            mediaPlayer = new MediaPlayer();
            try {
                if(uri == null) {
                    Toast.makeText(this, "No sound to play", Toast.LENGTH_SHORT).show();
                } else {
                    mediaPlayer.setDataSource(this, uri);
                    mediaPlayer.setOnPreparedListener(this);
                    mediaPlayer.prepareAsync();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(view == findViewById(R.id.stopBtn)) {
            mediaPlayer.stop();
        }

        if(view == findViewById(R.id.openBtn)) {
            open();
        }

        if(view == findViewById(R.id.nextBtn)) {
            Intent intent = new Intent(this, SoundActivity.class);
            startActivity(intent);
        }
    }

    public void open() {
        Intent intent_upload = new Intent();
        intent_upload.setType("audio/*");
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent_upload,REQUEST_CODE);
        /*
        Intent audioIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(audioIntent,REQUEST_CODE);
         */
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                uri = data.getData();
                String path = uri.getPath();
                String audioName = path.substring(path.lastIndexOf("/") + 1);
                textView.setText(audioName);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
}