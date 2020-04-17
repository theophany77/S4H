package myprojects.alzingrejm.com.songs4heaven_v2;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class MultiSearchActivity extends SingleFragmentActivity {

    private static  final  String  EXTRA_SONG_ID =
            "myprojects.alzingrejm.com.songs4heaven_v2.song_id_MSAF";

    @Override
    protected Fragment createFragment() {
        Log.d("DEBUG_MultiSeaAct","Entree createFragment");
        tableau_Etapes.sauv("MultiSearchActivity: entree createFragment");
        int songid = getIntent().getIntExtra( EXTRA_SONG_ID, 1 );
        Log.d("DEBUG_MultiSeaAct","createFragment:songid:"+songid);
        return MultiSearchActivityFragment.newInstance(songid);
    }

    public void song_search_click(View view) {
        Log.d("DEBUG_MultiSeaAct","Entree song_search_click");
        tableau_Etapes.sauv("MultiSearchActivity: entree song_search_click");

    }
}