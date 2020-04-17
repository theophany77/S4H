package myprojects.alzingrejm.com.songs4heaven_v2;


// nouvelle création de l'appli sur Play Store ce 01102019 suite pb de certificat .

import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    SoundPool ourSounds;
    int soundExplosion;
    int soundSpring;

    Button playExplosion;
    Button playSpring;

    private BeatBox mBeatBox;

    private String mon_chant_mp3 = "vide";

    // mon "mouchard" de suic=vi des étapes
    public static  MemoDebug tableau_Etapes = new MemoDebug();

    List<Sound> mSounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DEBUG_WelcomeActivity","Entree onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tableau_Etapes.sauv("WelcomeActivity: entree onCreate");

        // pour l'image
        ImageView sword = (ImageView) findViewById(R.id.epee_du_roi);
        int imaqe = R.drawable.sword;
        String description = "This is the sword of the King";
        sword.setImageResource(imaqe);
        sword.setContentDescription(description);


        // 20190729
        //      je remplace tous les boutons par des images cliquables


        // désactivation des boutons pas encore codés
        //Button button1 = (Button)findViewById(R.id.welcome_generation_liste);
        //button1.setEnabled(false);
        //Button button2 = (Button)findViewById(R.id.welcome_enchainements);
        //button2.setEnabled(false);

        //Button button5 = (Button)findViewById(R.id.welcome_liens);
        //button5.setEnabled(false);

        //playExplosion = (Button) findViewById(R.id.btExplosion);
        //playSpring = (Button) findViewById(R.id.btSpring);
        //playExplosion.setOnClickListener(this);
        //playSpring.setOnClickListener(this);

        // 20190831 : je remplace l'appel dans chaque classe par un singleton :
        //      https://madcoda.com/2016/06/using-soundpool-in-android/
        //initializeSoundPool();
        //SoundManager.initialize(getApplicationContext());

        // on charge la liste des chants présents sous assets/mp3
        mBeatBox = new BeatBox(getApplicationContext());

        mSounds = mBeatBox.getSounds();

    }


    public void open_allsongs_list(View view) {
        Log.d("DEBUG_Welcome", "Entree open_allsongs_list");
        tableau_Etapes.sauv("WelcomeActivity: entree open_allsongs_list");
        Intent intent = new Intent(this, Songs4HeavenActivity.class);
        startActivity(intent);
    }

    public void open_multi_search(View view) {
        Log.d("DEBUG_Welcome", "Entree open_multi_search");
        Intent intent = new Intent(this, MultiSearchActivityLaunch.class);
        Log.d("DEBUG_Welcome", "Avant startActivity");
        startActivity(intent);
    }

    public void open_song_generator(View view) {
        Log.d("DEBUG_Welcome", "Entree open_song_generator");
        Intent intent = new Intent(this, Songs4HeavenActivity.class);
        startActivity(intent);
    }

    public void open_chained_songs(View view) {
        Log.d("DEBUG_Welcome", "Entree open_chained_songs");
        Intent intent = new Intent(this, Songs4HeavenActivity.class);
        startActivity(intent);
    }

    public void open_new_songs(View view) {
        Log.d("DEBUG_Welcome", "Entree open_new_songs");
        Intent intent = new Intent(this, Songs4HeavenActivity.class);
        startActivity(intent);
    }

    public void open_pastor_songs(View view) {
        Log.d("DEBUG_Welcome", "Entree open_pastor_songs");
        Intent intent = new Intent(this, PastorsSongs.class);
        startActivity(intent);
    }

    public void open_links(View view) {
        Log.d("DEBUG_Welcome", "Entree open_links");
        Intent intent = new Intent(this, Songs4HeavenActivity.class);
        startActivity(intent);
    }

    public void website(View view) {
        Log.d("DEBUG_Welcome", "Entree open_website");
        Intent intent = new Intent(this, Songs4HeavenActivity.class);
        startActivity(intent);
    }


    public void onClickSpring(View v) {

        Log.d("DEBUG_Welcome", "Entree onClickSpring");
        // singleton depuis le 20190831
        String mon_chant_mp3 = "spring";
        //SoundManager.initialize(this.getApplicationContext(), mon_chant_mp3);
        //SoundManager.getInstance().playClickSound();

        //mon_chant_mp3="explosion";
        //SoundManager.initialize(this.getApplicationContext(), mon_chant_mp3);
        //SoundManager.getInstance().playClickSound();


        // Boucle pour trouver l'id avant d'appeler BeatBox.play
        // A terminer
        // principe :
        //      boucler sur mSounds qui contient la liste des objets de type Sound
        //          chercher le titre qui corrrspond à mon_chant_mp3
        //           relever l'id
        //           appeler alors Beatbox.play avec l'id trouvé

        for (Sound str : mSounds) {
            Log.d("DEBUG_Welcome:", "onClick:" + str.getAssetPath() + "/" + str.getName() + "/" + str.getSoundId());
            if (str.getName() == mon_chant_mp3) {
                Log.d("DEBUG_Welcome:", "onClickString:trouvé");
                mBeatBox.play(str);
            }
            else {
                Log.d("DEBUG_Welcome:", "onClickString:absent");
                mBeatBox.play(str);
            }
        }
    }

    public void onClickExplosion(View v) {

        Log.d("DEBUG_Welcome", "Entree onClickExplosion");

        // singleton depuis le 20190831
        mon_chant_mp3 = "explosion";
        //SoundManager.initialize(this.getApplicationContext(), mon_chant_mp3);
        //SoundManager.getInstance().playClickSound();

        //mon_chant_mp3="explosion";
        //SoundManager.initialize(this.getApplicationContext(), mon_chant_mp3);
        //SoundManager.getInstance().playClickSound();


        // Boucle pour trouver l'id avant d'appeler BeatBox.play
        // A terminer
        // principe :
        //      boucler sur mSounds qui contient la liste des objets de type Sound
        //          chercher le titre qui corrrspond à mon_chant_mp3
        //           relever l'id
        //           appeler alors Beatbox.play avec l'id trouvé

        for (Sound str : mSounds) {
            Log.d("DEBUG_Welcome:", "onClickExplosion:" + str.getAssetPath() + "/" + str.getName() + "/" + str.getSoundId());
            if (str.getName() == mon_chant_mp3) {
                Log.d("DEBUG_Welcome:", "onClickExplosion:trouvé");
                mBeatBox.play(str);
            }
            else
            {
                Log.d("DEBUG_Welcome:", "onClickExplosion:absent");
                mBeatBox.play(str);
            }
        }
    }
}

            //mBeatBox.play(1);

/*        switch (v.getId()) {
            case R.id.btExplosion:
                ourSounds.play (soundExplosion, 0.9f, 0.9f, 1, 0, 1);
                break;
            case R.id.btSpring:
                ourSounds.play (soundSpring, 0.9f, 0.9f, 1, 0, 1);
                break;
        }*/
