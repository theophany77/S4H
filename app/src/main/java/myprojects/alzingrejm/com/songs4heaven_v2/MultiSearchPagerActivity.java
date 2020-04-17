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

public class MultiSearchPagerActivity extends AppCompatActivity {


    private static final String EXTRA_SONG_ID =
            "myprojects.alzingrejm.com.songs4heaven_v2.song_id_MSPA";

    private ViewPager mViewPager;
    private List<Song> mSongs;

    public static Intent newIntent(Context packageContext, int SongId) {
        Intent intent = new Intent(packageContext, SongPagerActivity.class);
        Log.d("DEBUG_MultiSeaPagerAct:","newIntent:songId:"+SongId);
        tableau_Etapes.sauv("MultiSearchPagerActivity: entree newIntent");
        intent.putExtra(EXTRA_SONG_ID, SongId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_pager);

        tableau_Etapes.sauv("MultiSearchPagerActivity: entree onCreate");

        //int songId = (int) getIntent().getSerializableExtra( EXTRA_SONG_ID );
        int songId = (int) getIntent().getIntExtra( EXTRA_SONG_ID , -1);
        //Bundle extras = getActivity().getIntent().getExtras();
        //int songId = (int) getArguments().getSerializable(ARG_SONG_ID);

        Log.d("DEBUG_MultiSeaPagerAct:","onCreate:songId:"+songId);

        mViewPager = (ViewPager) findViewById(R.id.song_view_pager);

        mSongs = SongLab.get(this).getSongs();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Log.d("DEBUG_MultiSeaPagerAct:","getItem:position:"+position);
                Song song = mSongs.get(position);
                return SongFragment.newInstance(song.getmId());
            }

            @Override
            public int getCount() {
                Log.d("DEBUG_MultiSeaPagerAct:","onCreate:getCount:"+mSongs.size());
                tableau_Etapes.sauv("MultiSearchPagerActivity: entree getCount");
                return mSongs.size();
            }
        });

        for (int i = 0; i < mSongs.size(); i++) {

            if (mSongs.get(i).getmId() == songId) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
