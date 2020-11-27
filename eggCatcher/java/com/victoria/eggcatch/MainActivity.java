package com.victoria.eggcatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    float downX, downY;
    private SoundHandler soundHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        soundHandler = new SoundHandler(this, true);
        soundHandler.initializeSoundFx();
        CMySurface tsuf=new CMySurface(this, soundHandler);
        setContentView(tsuf);

    }
}
