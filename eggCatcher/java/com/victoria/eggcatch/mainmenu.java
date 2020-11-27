package com.victoria.eggcatch;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class mainmenu extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{
    ImageView playbtn;
    MediaPlayer mpe, mpe2;
    Uri uri, uri2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        Animation swayingAnim = AnimationUtils.loadAnimation(this, R.anim.sway_main);
        playbtn = findViewById(R.id.btn_play);
        playbtn.bringToFront();
        playbtn.startAnimation(swayingAnim);

        uri= Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.countdown_sec_go);
        mpe= new MediaPlayer();
        mpe.setOnPreparedListener(this);
        mpe.setOnErrorListener(this);
        mpe.setOnCompletionListener(this);

        uri2= Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bunny);
        mpe2= new MediaPlayer();
        mpe2.setOnPreparedListener(this);
        mpe2.setOnErrorListener(this);
        mpe2.setOnCompletionListener(this);

        try{
            mpe.setDataSource(this, uri);
            mpe2.setDataSource(this, uri2);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        mpe.prepareAsync();
        mpe2.prepareAsync();
    }
	
	@Override
    public void onCompletion(MediaPlayer mp) {}

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mpe2.start();
        mpe2.setLooping(true);
    }

    @SuppressLint("NewApi")
    public void nextPage(View v){
        mpe2.stop();
        mpe.start();
        Intent it = new Intent(mainmenu.this, MainActivity.class);
        Bundle translateBundle = ActivityOptions.makeCustomAnimation(
                mainmenu.this, R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
        startActivity(it, translateBundle);
    }
	
	public void exitgame(View v) {
        //finish();
        System.exit(0);
    }

    
}




