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

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

// Cette activité permet d'effectuer une recherche multi critères dans la base de chants,
//  après la saisie d'un ou plusieurs éléments de recherche.

public class MultiSearchActivityFragment extends Fragment {


    // pour la partie RecyclerView
    private static final String ARG_SONG_ID = "myprojects.alzingrejm.com.songs4heaven_v2.song_id_MSAF";

    private RecyclerView mSongRecyclerView;
    private MultiSearchActivityFragment.SongAdapter mAdapter;
    private Song mSong;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Log.d("DEBUG_MultiSeaActFrag", "Entree onCreate");
        tableau_Etapes.sauv("MultiSearchActivityFragment: entree onCreate");
        //int songId = (int) getArguments().getSerializable(ARG_SONG_ID);
        int songId = (int) getArguments().getInt(ARG_SONG_ID);
        Log.i("DEBUG_MultiSeaActFrag", "onCreate:songId:"+songId);
        mSong = SongLab.get(getActivity()).getSong(songId);
        Log.i("DEBUG_MultiSeaActFrag", "onCreate:mSong.getmId:"+mSong.getmId());



    }

    public static MultiSearchActivityFragment newInstance(int songId) {

        Log.d("DEBUG_MultiSeaActFrag", "Entree newInstance");
        tableau_Etapes.sauv("MultiSearchActivityFragment: entree newInstance");


        Bundle args = new Bundle();
        args.putSerializable(ARG_SONG_ID, songId);

        Log.d("DEBUG_MultiSeaActFrag", "newInstance:songId:"+songId);

        MultiSearchActivityFragment fragment = new MultiSearchActivityFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate( R.layout.multi_search_results, container, false );
        tableau_Etapes.sauv("MultiSearchActivityFragment: entree onCreateView");
        //View v=inflater.inflate( R.layout.activity_multi_search_fragment, container, false );
        mSongRecyclerView = (RecyclerView) v.findViewById( R.id.app_recycler_view );
        mSongRecyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

        Intent intent = getActivity().getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Song> object = (ArrayList<Song>) args.getSerializable("ARRAYLIST");

        // je charge le xml dans la bdd
        //Cursor c1;
        //c1 = LoadSongsTask();

        updateUI(object);

        return  v;
    }

    private void updateUI(ArrayList<Song> object){
        // lt set up the UI

        //songs=SingleLoadXml.getSongs(getContext());
        tableau_Etapes.sauv("MultiSearchActivityFragment: entree updateUI");


        Log.i("DEBUG_MultiSeaActFrag", "Après SongLab.get");
        //ArrayList<Song> songs = getSongs(c);
        // J'ai désormais dans "songs" un objet ArrayList<Song> de taille 7 qui contient bien tous les chants (vérifié par checkData)
        // Cependant (20190612), pour la recherche multi critère, je ne dois faire figurer dans songs que les chants qui répondent aux critères choisis.

        Log.i("DEBUG_MultiSeaActFrag", "Après songLab.getSongs");

        if (mAdapter == null) {
            Log.i("DEBUG_MultiSeaActFrag", "mAdapter is null");
            //Log.i("DEBUG_MultiSeaActFrag", "Après le songLab.getSongs:"+songs.size());

            // BNRG p.170
            //  First the RecyclerView asks how many objects are in the list by calling the adapter's getItemCount') method.

            //mAdapter = new MultiSearchActivityFragment.SongAdapter(songs);
            mAdapter = new MultiSearchActivityFragment.SongAdapter(object);
            mSongRecyclerView.setAdapter(mAdapter);
        } else {
            Log.i("DEBUG_MultiSeaActFrag", "mAdapter is not null");
            //Log.i("DEBUG_MultiSeaActFrag", "Après le songLab.getSongs:"+songs.size());
            //mAdapter.setSongs(songs);
            mAdapter.setSongs(object);
            mAdapter.notifyDataSetChanged();
        }


        // Load xml data in a non-ui thread
        //Log.i("DEBUG_S4HFrag","Avant le LoadSongsTask");
        //new LoadSongsTask().execute();
        Log.i("DEBUG_MultiSeaActFrag","Apres le mAdapter");

        //mAdapter = new SongAdapter( songs );
        //mSongRecyclerView.setAdapter(mAdapter);
    }



    // Gestion RecyclerView

    private class SongHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // membres

        private Song mSong;

        // membres pour remplir le RecyclerView
        private TextView mIdSongView;
        private TextView mTitleSongView;
        private TextView mChordsSongView;

        public SongHolder(LayoutInflater inflater, ViewGroup parent) {
            super( inflater.inflate( R.layout.list_item_song, parent, false ) );
            Log.d( "DEBUG_MultiSeaActFrag", "Entree SongHolder(LayoutInflater" );
            tableau_Etapes.sauv("MultiSearchActivityFragment: entree SongHolder");
            itemView.setOnClickListener( this );

            //mIdSongView = (TextView) itemView.findViewById( R.id.song_id );
            mTitleSongView = (TextView) itemView.findViewById( R.id.song_title );
            mChordsSongView = (TextView) itemView.findViewById( R.id.song_chords );

        }

        public void bind(Song song) {
            Log.d( "DEBUG_MultiSeaActFrag", "Entree bind" );
            tableau_Etapes.sauv("MultiSearchActivityFragment: entree bind");
            mSong = song;
            //mIdSongView.setText( Integer.toString( mSong.getmId() ) );
            mTitleSongView.setText( mSong.getmTitle() );
            //mWordsSongView.setText( mSong.getmWords() );
            mChordsSongView.setText( mSong.getmChords() );
            Log.d( "DEBUG_MultiSeaActFrag", "bind:getmId:"+mSong.getmId() );
        }

        @Override
        public void onClick(View view) {

            // OK ce 20190221 - mais on poursuis en p.205 du BNRG
            //Toast.makeText(getActivity(),
            //        mSong.getmTitle() + " clicked!", Toast.LENGTH_SHORT)
            //        .show();

            //Intent intent = new Intent(getActivity(), SongActivity.class);
            //Intent intent = SongActivity.newIntent( getActivity(), mSong.getmId() );

            Log.d( "DEBUG_MultiSeaActFrag", "Entree onClick" );
            tableau_Etapes.sauv("MultiSearchActivityFragment: entree onClick");
            Log.d( "DEBUG_MultiSeaActFrag", "getmId:" + mSong.getmId() );
            Log.d( "DEBUG_MultiSeaActFrag", "getmTitle:" + mSong.getmTitle() );
            //Intent intent = SongActivity.newIntent( getActivity(), mSong.getmId());
            // comme j'ai toujours 0, pour une raison inconnue, je fixe la valeur à 4 par ex.
            Intent intent = SongPagerActivity.newIntent( getActivity(), mSong.getmId() );
            //Intent intent = SongPagerActivity.newIntent( getActivity(), 4);
            startActivity( intent );
        }
    }

    class SongAdapter extends RecyclerView.Adapter<MultiSearchActivityFragment.SongHolder> {

        private List<Song> mSongs;

        private Context mContext;
        private List<Song> mLSong;
        private Cursor mCursor;

        //public SongAdapter(List<Song> songs) {
        // 20190219 - tuto youtube
        //  https://www.youtube.com/watch?v=_m-Ve-BAYe0&list=PLrnPJCHvNZuBMJmll0xy2L2McYInT3aiu&index=3&t=0s

        public SongAdapter(ArrayList<Song> ls) {
            Log.d("DEBUG_MultiSeaActFrag","Entree SongAdapter(ArrayList<Song>");
            tableau_Etapes.sauv("MultiSearchActivityFragment: entree SongAdapter");
            mLSong = ls;
        }

        @NonNull
        @Override
        public MultiSearchActivityFragment.SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from( getActivity() );
            Log.d("DEBUG_MultiSeaActFrag","Entree SongAdapter onCreateViewHolder");
            tableau_Etapes.sauv("MultiSearchActivityFragment: entree onCreateViewHolder");

            return new MultiSearchActivityFragment.SongHolder( layoutInflater, parent );
        }

        @Override
        public void onBindViewHolder(MultiSearchActivityFragment.SongHolder holder, int position) {
            Log.d("DEBUG_MultiSeaActFrag","Entree onBindViewHolder avec position:"+position);
            Song song = mLSong.get(position);
            holder.bind(song);
        }

        @Override
        public int getItemCount() {
            Log.d("DEBUG_MultiSeaActFrag","Entree getItemCount");
            tableau_Etapes.sauv("MultiSearchActivityFragment: entree getItemCount");
            return mLSong.size();
        }

        public void setSongs(List<Song> songs) {
            Log.d("DEBUG_MultiSeaActFrag","Entree setSongs(List<Song>");
            tableau_Etapes.sauv("MultiSearchActivityFragment: entree setSongs");
            mSongs = songs;
        }
    }

    public int song_search_click (){
        // recherche d'un élément quelconque
        // peut-on ici recevoir les références du compteur et du type d'objet concerné ?
        Log.d("DEBUG_MultiSeaActFrag","Entree song_search_click");
        tableau_Etapes.sauv("MultiSearchActivityFragment: entree song_search_click");
        return 0;
    }

}


