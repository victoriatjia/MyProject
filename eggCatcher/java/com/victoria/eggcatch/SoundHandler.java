package com.victoria.eggcatch;

import android.media.AudioManager;
import android.media.SoundPool;

public class SoundHandler {

    private SoundPool soundPool;
    private int[] soundType = new int[7];
    private boolean soundsOn;
    private MainActivity main;

    public SoundHandler(MainActivity main, boolean soundsOn) {
        this.main = main;
        this.soundsOn = soundsOn;
    }

    public void initializeSoundFx() {
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        soundType[0] = soundPool.load(main, R.raw.score_neg, 1);
        soundType[1] = soundPool.load(main, R.raw.laying_hen_gold, 1);
        soundType[2] = soundPool.load(main, R.raw.egg_dropped, 1);
        soundType[3] = soundPool.load(main, R.raw.score, 1);
        soundType[4] = soundPool.load(main, R.raw.score_2, 1);
        soundType[5] = soundPool.load(main, R.raw.countdown_sec_go, 1);
        soundType[6] = soundPool.load(main, R.raw.poodrop, 1);
    }

    void playSoundEffect(int id, int vol) {
        if(soundsOn){
            if(id == 2){
                soundPool.play(soundType[id], vol, vol, 1, 0, (float) 0.7);
            }else{
                soundPool.play(soundType[id], vol, vol, 1, 0, 1);
            }
        }
    }
}



