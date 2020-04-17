package myprojects.alzingrejm.com.songs4heaven_v2;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class BeatBox {
    private static final String TAG = "DEBUG_BeatBox:";

    // dossier assets/mp3 où sont stock&s les sons
    private static final String SOUNDS_FOLDER = "mp3";
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssets;
    private List<Sound> mSounds;
    private SoundPool mSoundPool;

    public BeatBox(Context context) {
        mAssets = context.getAssets();

        // This old constructor is deprecated, but we need it for
        // compatibility.
        //noinspection deprecation
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    public void play(Sound sound) {
        tableau_Etapes.sauv("BeatBox : entree play");
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        Log.d(TAG, "play:"+sound.getAssetPath()+"/"+sound.getName()+"/"+sound.getSoundId());
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release() {
        mSoundPool.release();
    }

    public List<Sound> getSounds() {

        //tableau_Etapes.sauv("BeatBox : entree getSounds");
        return mSounds;
    }

    private void loadSounds() {

        //tableau_Etapes.sauv("BeatBox : entree loadSounds");

        String[] soundNames;
        try {
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.d(TAG, "Found " + soundNames.length + " sounds");
        } catch (IOException ioe) {
            Log.d(TAG, "Could not list assets", ioe);
            return;
        }

        mSounds = new ArrayList<Sound>();
        for (String filename : soundNames) {
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException ioe) {
                Log.d(TAG, "Could not load sound " + filename, ioe);
            }
        }
    }

    private void load(Sound sound) throws IOException {
        //tableau_Etapes.sauv("BeatBox : entree load");
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }
}

