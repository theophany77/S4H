package myprojects.alzingrejm.com.songs4heaven_v2;

import android.support.v4.app.Fragment;
import android.util.Log;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public  class Songs4HeavenActivity extends SingleFragmentActivity {

    private static final String EXTRA_SONG_ID = "myprojects.alzingrejm.com.songs4heaven_v2.song_id_S4HAF";

    @Override
    protected Fragment createFragment() {
        Log.d("DEBUG_S4HActivity","Entree createFragment");
        tableau_Etapes.sauv("S4HActivity: entree createFragment");
        int songid = getIntent().getIntExtra( EXTRA_SONG_ID, 1 );
        Log.d("DEBUG_S4HActivity","createFragment:songid:"+songid);
        return Songs4HeavenActivityFragment.newInstance(songid);
    }
}
