package myprojects.alzingrejm.com.songs4heaven_v2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import database.SongDatabase;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class PastorSongsFragment extends Fragment {


    private RecyclerView mSongRecyclerView;
    private PastorSongsFragment.SongAdapter mAdapter;

    // scope des méthodes

    public static PastorSongsFragment newInstance() {
        return new PastorSongsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d( "DEBUG_PastorSoFrag:", "Entree onCreateView" );
        tableau_Etapes.sauv("PastorsSongsFragment: entree onCreateView");
        View v = inflater.inflate( R.layout.fragment_pastor_songs, container, false );
        mSongRecyclerView = (RecyclerView) v.findViewById( R.id.app_recycler_view );
        mSongRecyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

        // je charge le xml dans la bdd
        Cursor c1;
        c1 = LoadSongsTask();
        updateUI(c1);

        return v;
    }

    public void onResume() {

        tableau_Etapes.sauv("PastorsSongsFragment: entree onResume");

        super.onResume();
        Cursor c1;
        c1 = LoadSongsTask();
        updateUI( c1 );
    }

    private void updateUI(Cursor c) {

        Log.i( "DEBUG_PastorSoFrg:", "Entrée updateUI" );
        tableau_Etapes.sauv("PastorsSongsFragment: entree updataUI");

        Log.i("DEBUG_PastorSoFrag:", "Après SongLab.get");
        ArrayList<Song> songs = getSongs(c);

        Log.i("DEBUG_PastorSoFrag:", "Après songLab.getSongs");

        if (mAdapter == null) {
            Log.i("DEBUG_PastorSoFrag:", "mAdapter is null");
            Log.i("DEBUG_PastorSoFrag:", "Après le songLab.getSongs:"+songs.size());
            tableau_Etapes.sauv("PastorsSongsFragment: mAdapter is null");

            // BNRG p.170
            //  First the RecyclerView asks how many objects are in the list by calling the adapter's getItemCount') method.

            mAdapter = new SongAdapter(songs);
            mSongRecyclerView.setAdapter(mAdapter);
        } else {
            Log.i("DEBUG_PastorSoFrag:", "mAdapter is not null");
            Log.i("DEBUG_PastorSoFrag:", "Après le songLab.getSongs:"+songs.size());
            tableau_Etapes.sauv("PastorsSongsFragment: mAdapter is not null");
            mAdapter.setSongs(songs);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class SongHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // membres

        private Song mSong;

        // membres pour remplir le RecyclerView
        private TextView mIdSongView;
        private TextView mTitleSongView;
        private TextView mChordsSongView;

        public SongHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_song, parent, false));
            Log.i("DEBUG_PastorSoFrag:","Entree SongHolder(LayoutInflater");
            tableau_Etapes.sauv("PastorsSongsFragment: entree SongHolder");
            itemView.setOnClickListener(this);

            //mIdSongView = (TextView) itemView.findViewById(R.id.song_id);
            mTitleSongView = (TextView) itemView.findViewById(R.id.song_title);
            mChordsSongView = (TextView) itemView.findViewById(R.id.song_chords);

        }

        public void bind(Song song) {
            Log.i("DEBUG_PastorSoFrag:","Entree bind");
            tableau_Etapes.sauv("PastorsSongsFragment: entree bind");
            mSong = song;
            //mIdSongView.setText(Integer.toString( mSong.getmId()));
            mTitleSongView.setText( mSong.getmTitle() );
            //mWordsSongView.setText( mSong.getmWords() );
            mChordsSongView.setText( mSong.getmChords() );
        }


        @Override
        public void onClick(View view) {

            tableau_Etapes.sauv("PastorsSongsFragment: entree onClick");

            // OK ce 20190221 - mais on poursuis en p.205 du BNRG
            //Toast.makeText(getActivity(),
            //        mSong.getmTitle() + " clicked!", Toast.LENGTH_SHORT)
            //        .show();

            //Intent intent = new Intent(getActivity(), SongActivity.class);
            //Intent intent = SongActivity.newIntent( getActivity(), mSong.getmId() );

            Log.i("DEBUG_PastorSoFrag:","Entree onClick");
            Log.i("DEBUG_PastorSoFrag:","getmId:"+mSong.getmId());
            Log.i("DEBUG_PastorSoFrag:","getmTitle:"+mSong.getmTitle());
            //Intent intent = SongActivity.newIntent( getActivity(), mSong.getmId());
            Intent intent = SongPagerActivity.newIntent( getActivity(), mSong.getmId() );
            startActivity( intent );
        }
    }

    class SongAdapter extends RecyclerView.Adapter<PastorSongsFragment.SongHolder> {

        private List<Song> mSongs;

        private Context mContext;
        private List<Song> mLSong;
        private Cursor mCursor;

        //public SongAdapter(List<Song> songs) {
        // 20190219 - tuto youtube
        //  https://www.youtube.com/watch?v=_m-Ve-BAYe0&list=PLrnPJCHvNZuBMJmll0xy2L2McYInT3aiu&index=3&t=0s

        public SongAdapter(ArrayList<Song> ls) {
            Log.i("DEBUG_PastorSoFrag:","Entree SongAdapter(ArrayList<Song>");
            tableau_Etapes.sauv("PastorsSongsFragment: entree SongAdapter");
            mLSong = ls;
        }

        @NonNull
        @Override
        public PastorSongsFragment.SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from( getActivity() );
            Log.i("DEBUG_PastorSoFrag:","Entree SongAdapter onCreateViewHolder");
            tableau_Etapes.sauv("PastorsSongsFragment: entree SonhHolder");

            return new SongHolder( layoutInflater, parent );
        }

        @Override
        public void onBindViewHolder(PastorSongsFragment.SongHolder holder, int position) {
            Log.i("DEBUG_PastorSoFrag:","Entree onBindViewHolder avec position:"+position);
            tableau_Etapes.sauv("PastorsSongsFragment: entree onBindViewHolder");
            Song song = mLSong.get(position);
            holder.bind(song);
        }

        @Override
        public int getItemCount() {
            Log.i("DEBUG_PastorSoFrag:","Entree getItemCount");
            tableau_Etapes.sauv("PastorsSongsFragment: entree getItemCount");
            return mLSong.size();
        }

        public void setSongs(List<Song> songs) {
            Log.i("DEBUG_PastorSoFrag:","Entree setSongs(List<Song>");
            tableau_Etapes.sauv("PastorsSongsFragment: entree setSongs");
            mSongs = songs;
        }
    }


    private Cursor LoadSongsTask() {

        Log.i("DEBUG_PastorSoFrag:","Entree doInBackground");
        tableau_Etapes.sauv("PastorsSongsFragment: entree LoadSongsTask");

        // query the database and return a cursor of employees.
        // PB avec le Context :     SongDatabase songDatabase = new SongDatabase(getApplicationContext());
        SongDatabase songDatabase = new SongDatabase(getContext());

        Log.i("DEBUG_PastorSoFrag:","doInBackground: avant cursor");

        Cursor cursor = songDatabase.getAllSongsCursor();

        Log.i("DEBUG_PastorSoFrag:","doInBackground: après cursor");

        return cursor;
    }

    public ArrayList<Song> getSongs(Cursor c){

        tableau_Etapes.sauv("PastorsSongsFragment: entree getSongs");
        // JMA - ce 20190220
        //  je dois lire le curseur et retourner une liste de Songs afin de permettre
        //  la gestion du RecycleView

        ArrayList<Song> songList = new ArrayList<Song>();

        if (c.moveToFirst()){
            do {
                // Passing values
                Integer column1 = c.getInt(0);
                String column2 = c.getString(1);
                String column3 = c.getString(2);
                String column4 = c.getString(3);
                String column5 = c.getString(4);
                String column6 = c.getString(5);
                String column7 = c.getString(6);
                String column8 = c.getString(7);
                String column9 = c.getString(8);
                String column10 = c.getString(9);
                String column11= c.getString(10);

                // Do something Here with values
                Song s = new Song();
                s.setmId( column1 );
                s.setmTitle( column2 );
                s.setmWordsFr( column3 );
                s.setmWordsGb( column4 );
                s.setmWordsSp( column5 );
                s.setmChords( column6 );
                s.setmKind( column7 );
                s.setmSpeed( column8 );
                s.setmAuthor( column9 );
                s.setmMp3( column10 );
                s.setmMisc( column11 );

                if (s.getmMisc().contains( "GL:BEST" )) {
                    songList.add( s );
                }

            } while(c.moveToNext());
        }
        //c.close();

        return songList;

    }
}