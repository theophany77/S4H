package myprojects.alzingrejm.com.songs4heaven_v2;

// importé de
//      https://madcoda.com/2016/06/using-soundpool-in-android/

/// But : constituer un singleton pour la lecture des sons
//          Dans chaque classe d'appel, on aura :
//              1) Dans le onCreate :
//                      SoundManager.initialize(context);
//              2) Dans le onClick pour jouer le son :
//                      SoundManager.getInstance().playClickSound();


import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;
import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class SoundManager {

    private static SoundManager singleton;
    SoundPool soundPool;

    int CLICK_SOUND;

    private static final String SOUND_FOLDER="mp3";
    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>();

    public SoundManager() {
        tableau_Etapes.sauv("SoundManager: entree constructeur");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            soundPool = (new SoundPool.Builder()).setMaxStreams(1).build();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
        }

        //SoundManager.context = getApplicationContext();

        //Context context=
        //loadSound();
    }

    public void release() {
        soundPool.release();
    }

    private void loadSound(Context context) {

        tableau_Etapes.sauv("SoundManager: entree loadSound");

        mAssets = context.getAssets();

        String[] soundNames;
        try {
            soundNames = mAssets.list(SOUND_FOLDER);
            Log.i(TAG, "Found " + soundNames.length + " sounds");
        } catch (IOException ioe) {
            Log.e(TAG, "Could not list assets", ioe);
            return;
        }

        mSounds = new ArrayList<Sound>();
        for (String filename : soundNames) {
            try {
                String assetPath = SOUND_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException ioe) {
                Log.e(TAG, "Could not load sound " + filename, ioe);
            }
        }
    }

                //CLICK_SOUND = soundPool.load(context, R.raw.spring, 1);
                // load other sound if you like


            //CLICK_SOUND = context.getResources().getIdentifier("explosion", "raw",
            //            context.getPackageName());

           //     Log.d("DEBUG_SoundManager:", "loadSound avec CLICK_SOUND:" + CLICK_SOUND);


    private void load(Sound sound) throws IOException {
        tableau_Etapes.sauv("SoundManager: entree load");
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = soundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }


    public void playClickSound(){

        Log.d("DEBUG_SoundManager:","playClickSound avec CLICK_SOUND:"+CLICK_SOUND);
        tableau_Etapes.sauv("SoundManager: entree playClickSound");
        soundPool.play(CLICK_SOUND, 1.0F, 1.0F, 0, 0, 1.0F);


    }


    public static void initialize(Context context){
        SoundManager soundManager = getInstance();
        soundManager.loadSound(context);
    }

    public static synchronized SoundManager getInstance() {
        tableau_Etapes.sauv("SoundManager: entree synchronized");
        if (singleton == null) {
            //Object context=new Context();
            singleton = new SoundManager();
        }
        return singleton;
    }

 }

       // 20190831
        //      comme la méthode classique attend un chant en dur (raw), je vais désormais appelr la méthode
        //      qui attend un chemin.
/*

        int sound_id = context.getResources().getIdentifier(mon_chant_mp3, "raw",
                context.getPackageName());
        Log.d( "DEBUG_SoundManager:", "loadSound avec sound_id:"+sound_id);
        //CLICK_SOUND= soundPool.load(context, sound_id, 1);
        CLICK_SOUND_2 = soundPool.load(context, R.raw.spring, 1);
        Log.d( "DEBUG_SoundManager:", "loadSound avec CLICK_SOUND:"+CLICK_SOUND);


    public void playClickSound(){

        loaded=false;

        Log.d("DEBUG_SoundManager:","playClickSound avec CLICK_SOUND:"+CLICK_SOUND);
        if(loaded){
        Log.d("DEBUG_SoundManager:","playClickSound loaded");
        //soundPool.play(CLICK_SOUND, 1.0F, 1.0F, 0, 0, 1.0F);
        soundPool.play(CLICK_SOUND,0.9f,0.9f,1,0,1);
        }
        else{
        Log.d("DEBUG_SoundManager:","playClickSound not loaded");
        }

        Log.d("DEBUG_SoundManager:","playClickSound avec CLICK_SOUND_2:"+CLICK_SOUND_2);
        soundPool.play(CLICK_SOUND_2,1.0F,1.0F,0,0,1.0F);

        }*/
