package myprojects.alzingrejm.com.songs4heaven_v2;

import android.support.v4.app.Fragment;
import android.util.Log;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public  class SearchResults extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Log.d( "DEBUG_SearchRes:", "Entree" );
        tableau_Etapes.sauv("SearchResults entree createFragment");
        return SearchResultsFragment.newInstance();
    }
}
