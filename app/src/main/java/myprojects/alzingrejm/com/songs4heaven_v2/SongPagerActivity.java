package myprojects.alzingrejm.com.songs4heaven_v2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class SongPagerActivity extends AppCompatActivity {


        private static final String EXTRA_SONG_ID =
                "myprojects.alzingrejm.com.songs4heaven_v2.song_id_SPA";

        private ViewPager mViewPager;
        private List<Song> mSongs;

        public static Intent newIntent(Context packageContext, int songId) {
            // 20200303 - je mets songId à la place de SongId. Pas forcément utile ...
            Intent intent = new Intent(packageContext, SongPagerActivity.class);
            Log.d("DEBUG_SongPagerAct:","newIntent:songId:"+songId);
            tableau_Etapes.sauv("SongPagerActivity: entree newIntent");
            intent.putExtra(EXTRA_SONG_ID, songId);
            return intent;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_song_pager);

            tableau_Etapes.sauv("SongPagerActivity: entree onCreate");

            //int songId = (int) getIntent().getSerializableExtra( EXTRA_SONG_ID );
            // 20200302 - pourquoi cette valeur -1 ?
            //int songId = (int) getIntent().getIntExtra( EXTRA_SONG_ID , -1);

            // 20200303 - je teste comme dans BNRG
            int songId = (int) getIntent().getSerializableExtra(EXTRA_SONG_ID);
            //Bundle extras = getActivity().getIntent().getExtras();
            //int songId = (int) getArguments().getSerializable(ARG_SONG_ID);

            Log.d("DEBUG_SongPagerAct:","onCreate:songId:"+songId);

            mViewPager = (ViewPager) findViewById(R.id.song_view_pager);

            Log.d("DEBUG_SongPagerAct:","onCreate:this"+this);;

            // 20191228 : vide en primo et plein de relance ...
            // le get est le singleton de chargement des données xml dans la database
            mSongs = SongLab.get(this).getSongs();

            //mSongs = SongLab.

            FragmentManager fragmentManager = getSupportFragmentManager();
            mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

                @Override
                public Fragment getItem(int position) {
                    Log.d("DEBUG_SongPagerAct:","getItem:position:"+position);
                    tableau_Etapes.sauv("SongPagerActivity: entree getItem");
                    Song song = mSongs.get(position);
                    return SongFragment.newInstance(song.getmId());
                }

                @Override
                public int getCount() {
                    Log.d("DEBUG_SongPagerAct:","onCreate:getCount:"+mSongs.size());
                    tableau_Etapes.sauv("SongPagerActivity: entree getCount");
                    return mSongs.size();
                }
            });

            Log.d("DEBUG_SongPagerActivity", "Avant mViewPager.setCurrentItem avec :"+mSongs.size() + " et  songId = "+songId);

            for (int i = 0; i < mSongs.size(); i++) {

                if (mSongs.get(i).getmId() == songId) {
                    if ( songId == 72) {
                        Log.d("DEBUG_SongPagerActivity", "dans boucle pour détecter CurrentItem avec songId trouvé :" + songId);
                    }
                    mViewPager.setCurrentItem(i);
                    break;
                }
            }
        }
    }
