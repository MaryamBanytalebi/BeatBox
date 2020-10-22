package com.example.beatbox.repository;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import com.example.beatbox.model.Sound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeatBoxRepository {

    public static final String TAG = "BeatBox";
    public static final int MAX_STREAMS = 5;
    private static BeatBoxRepository sInstance;
    private Context mContext;
    private SoundPool mSoundPool;
    private List<Sound> mSounds = new ArrayList<>();

    private static String ASSET_FOLDER = "sample_sound";

    public static BeatBoxRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new BeatBoxRepository(context);
        return sInstance;
    }

    private BeatBoxRepository(Context context) {
        mContext = context.getApplicationContext();
        mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC,0);

        loadSounds(getSounds());
    }

    public List<Sound> getSounds(){
        return mSounds;
    }

    public void play(Sound sound){

        if (sound == null || sound.getsoundId() == null)
            return;
        int playState = mSoundPool.play(
                sound.getsoundId(),
                1.0f,
                1.0f,
                0,
                0,
                1.0f);

        if (playState == 0)
            Log.e(TAG,"this sound has not been played"+ sound.getName());

    }

    public void loadSounds(List<Sound> sounds){
       /* for (Sound sound : sounds) {
            AssetManager assetManager = mContext.getAssets();
            try {
                AssetFileDescriptor afd = assetManager.openFd(sound.getAssetPath());
                int soundId = mSoundPool.load(afd,0);
                sound.setsoundId(soundId);

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG,e.getMessage(),e);
            }
        }*/

        AssetManager assetManager = mContext.getAssets();
        try {
            String[] fileNames = assetManager.list(ASSET_FOLDER);
            for (String fileName : fileNames) {
                String assetPath = ASSET_FOLDER + File.separator + fileName;
                Sound sound = new Sound(assetPath);

                loadInSoundPool(assetManager, sound);

                mSounds.add(sound);
            }

        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }

    private void loadInSoundPool(AssetManager assetManager, Sound sound) throws IOException {
        AssetFileDescriptor afd = assetManager.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setsoundId(soundId);
    }

    public void releaseSoundPool(){
        mSoundPool.release();
    }
}
