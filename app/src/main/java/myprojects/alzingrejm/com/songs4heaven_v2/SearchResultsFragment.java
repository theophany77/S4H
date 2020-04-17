package myprojects.alzingrejm.com.songs4heaven_v2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class SearchResultsFragment extends Fragment {

    private RecyclerView mSongRecyclerView;
    private SearchResultsFragment.SongAdapter mAdapter;

    // scope des méthodes

    public static SearchResultsFragment newInstance() {
        return new SearchResultsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d( "DEBUG_SearchResFrag:", "Entree" );
        tableau_Etapes.sauv("SearchResultsFragment: entree onCreateView");
        View v = inflater.inflate( R.layout.activity_search_results, container, false );
        mSongRecyclerView = (RecyclerView) v.findViewById( R.id.app_recycler_view );
        mSongRecyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );


        updateUI();

        return v;
    }

    private void updateUI(){

        Log.i("DEBUG_S4HActFrag", "Après SongLab.get");
        tableau_Etapes.sauv("SearchResultsFragment: entree updateUI");

        ArrayList<Song> ALsongs = new ArrayList<Song>();

        ALsongs = (ArrayList<Song>) getActivity().getIntent().getSerializableExtra(SongFragment.EXTRA_SONGS);

        ArrayList<Song> songs = ALsongs;
        // J'ai désormais dans "songs" un objet ArrayList<Song> de taille 7 qui contient bien tous les chants (vérifié par checkData)

        Log.i("DEBUG_S4HActFrag", "Après songLab.getSongs");
        Log.i("DEBUG_S4HActFrag", "getSongs:"+ALsongs.toString());


        mAdapter = new SearchResultsFragment.SongAdapter(songs);
        mSongRecyclerView.setAdapter(mAdapter);

        Log.i("DEBUG_S4HFrag","Apres le mAdapter");

    }

    private class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    //private class SongHolder extends RecyclerView.ViewHolder {

        // membres pour remplir le RecyclerView
        private TextView mTitleSongView;
        private TextView mChordsSongView;

        private Song mSong;

        public SongHolder(LayoutInflater inflater, ViewGroup parent) {
            super( inflater.inflate( R.layout.list_search_songs, parent, false ) );
            Log.d( "DEBUG_SearchResFrag:", "Entree SongHolder" );
            tableau_Etapes.sauv("SearchResultsFragment: entree SongHolder");

            itemView.setOnClickListener(this);

            mTitleSongView = (TextView) itemView.findViewById(R.id.song_title);
            mChordsSongView = (TextView) itemView.findViewById(R.id.song_chords);
        }


        public void bind(Song song) {
            Log.i( "DEBUG_SearchResFrag", "Entree bind:song"+song.getmId());
            tableau_Etapes.sauv("SearchResultsFragment: entree bind");
            mSong = song;
            Log.i( "DEBUG_SearchResFrag", "Entree bind:title:"+mSong.getmTitle());
            Log.i( "DEBUG_SearchResFrag", "Entree bind:chords:"+mSong.getmChords());
            mTitleSongView.setText( mSong.getmTitle() );
            //mWordsSongView.setText( mSong.getmWords() );
            mChordsSongView.setText( mSong.getmChords() );
        }


        @Override
        public void onClick(View view) {

            Log.i("DEBUG_SearchResFrag","Entree onClick");
            tableau_Etapes.sauv("SearchResultsFragment: entree onClick");

            // OK ce 20190305 - mais on poursuis en p.205 du BNRG
            //Toast.makeText(getActivity(),
            //        mSong.getmTitle() + " clicked!", Toast.LENGTH_SHORT)
            //        .show();

            Log.i("DEBUG_SearchResFrag","getmId:"+mSong.getmId());
            Intent intent = SongPagerActivity.newIntent( getActivity(), mSong.getmId() );
            startActivity( intent );
        }
    }


    private class SongAdapter extends RecyclerView.Adapter<SearchResultsFragment.SongHolder> {

        private List<Song> mSongs;

        private Context mContext;
        //private List<Song> mLSong;
        //private Cursor mCursor;

        public SongAdapter(ArrayList<Song> ls) {
            Log.i("DEBUG_SearchResFrag","Entree constructeur SongAdapter");
            tableau_Etapes.sauv("SearchResultsFragment: entree SongAdapter");

            mSongs = ls;

            Log.i("DEBUG_SearchResFrag","ls:"+ls.toString());
        }


        @Override
        public SearchResultsFragment.SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from( getActivity() );
            Log.i( "DEBUG_SearchResFrag", "Entree SongAdapter onCreateViewHolder" );
            tableau_Etapes.sauv("SearchResultsFragment: entree SongHolder");

            return new SearchResultsFragment.SongHolder( layoutInflater, parent );
        }

        @Override
        public void onBindViewHolder(SearchResultsFragment.SongHolder holder, int position) {
            Log.i( "DEBUG_SearchResFrag", "Entree onBindViewHolder avec position:"+position);
            tableau_Etapes.sauv("SearchResultsFragment: entree onBindViewHolder");
            Song song = mSongs.get( position );
            Log.i( "DEBUG_SearchResFrag", "song:"+song.getmTitle());
            Log.i( "DEBUG_SearchResFrag", "song:"+song.getmChords());
            holder.bind( song );
        }

        @Override
        public int getItemCount() {
            Log.i( "DEBUG_SearchResFrag", "Entree getItemCount avec size:"+mSongs.size());
            tableau_Etapes.sauv("SearchResultsFragment: entree getItemCount");
            return mSongs.size();
        }

    }
}