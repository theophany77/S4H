package myprojects.alzingrejm.com.songs4heaven_v2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import database.SongDatabase;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class Songs4HeavenActivityFragment extends Fragment {

    // scope des membre

    private static final String ARG_SONG_ID = "myprojects.alzingrejm.com.songs4heaven_v2.song_id_S4HAF";
    //public static final String SEARCH_TEXT = "myprojects.alzingrejm.com.songs4heaven_v2.search_text";

    private RecyclerView mSongRecyclerView;
    private SongAdapter mAdapter;
    private Song mSong;
    //public List<Song> songs = null;


    // scope des méthodes

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DEBUG_S4HActFrag", "Entree onCreate");
        tableau_Etapes.sauv("S4HActivityFragment: entree onCreate");
        //int songId = (int) getArguments().getSerializable(ARG_SONG_ID);
        int songId = (int) getArguments().getInt(ARG_SONG_ID);
        Log.d("DEBUG_S4HActFrag", "onCreate:songId:"+songId);
        // crée mDatabase. puis appelle querySongs
        mSong = SongLab.get(getActivity()).getSong(songId);
        // null object reference ce 20190826 sur le mSong.getmId()
        //Log.d("DEBUG_S4HActFrag", "onCreate:mSong.getmId:"+mSong.getmId());

    }

    public static Songs4HeavenActivityFragment newInstance(int songId) {

        Log.d("DEBUG_S4HActFrag", "Entree newInstance");
        tableau_Etapes.sauv("S4HActivityFragment: entree newInstance");

        Bundle args = new Bundle();
        //args.putSerializable(ARG_SONG_ID, songId);
        args.putInt( ARG_SONG_ID, songId );

        Log.d("DEBUG_S4HActFrag", "newInstance:songId:"+songId);

        Songs4HeavenActivityFragment fragment = new Songs4HeavenActivityFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("DEBUG_S4HActFrag", "Entree onCreateView");
        tableau_Etapes.sauv("S4HActivityFragment: entree onCreateView");

        View v = inflater.inflate( R.layout.fragment_songs4_heaven, container, false );
        mSongRecyclerView = (RecyclerView) v.findViewById( R.id.app_recycler_view );
        mSongRecyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

        // je charge le xml dans la bdd
        Cursor c1;
        c1 = LoadSongsTask();

        // 20190304 - recherche
        setHasOptionsMenu( true );

        //String[] dataColumns = { SongDatabase.COLUMN_TITLE, SongDatabase.COLUMN_CHORDS };
        //int[] viewIDs = { R.id.song_title, R.id.song_chords};

        // pb avec le Context :     SimpleCursorAdapter records = new SimpleCursorAdapter(getBaseContext(), R.layout.list_item_song, cursor, dataColumns, viewIDs, 0);
        //SimpleCursorAdapter records = new SimpleCursorAdapter(getContext(), R.layout.list_item_song, c1, dataColumns, viewIDs, 0);

        //setListAdapter(records);

        Log.d("DEBUG_S4HActFrag", "getColumnCount:"+c1.getColumnCount());

        updateUI(c1);

        return v;
    }

    //@Override
    // pour recharger les résultats en cas de retour arrière (BNRG 3rd - p.212/213)
    //  Ne change rien au fait que la recherche muli crière ne marche plus en retour arrière, malgré la présence
    //      des valeurs saisies.

    // 20200425 : inutile car données en R/O
/*    public void onResume() {

        Log.d("DEBUG_S4HActFrag","Entrée onResume");

        super.onResume();
        Cursor c1;
        c1 = LoadSongsTask();
        updateUI( c1 );
    }*/

    private void updateUI(Cursor c){
        // lt set up the UI

        //songs=SingleLoadXml.getSongs(getContext());
        Log.d("DEBUG_S4HActFrag","Entrée updateUI");


        Log.d("DEBUG_S4HActFrag", "Après SongLab.get");
        ArrayList<Song> songs = getSongs(c);
        // J'ai désormais dans "songs" un objet ArrayList<Song> qui contient bien tous les chants (vérifié par checkData)

        Log.d("DEBUG_S4HActFrag", "Après songLab.getSongs");

        if (mAdapter == null) {
            Log.d("DEBUG_S4HActFrag", "mAdapter is null");
            tableau_Etapes.sauv("S4HActivityFragment: mAdapter is null");
            Log.d("DEBUG_S4HActFrag", "Après le songLab.getSongs:"+songs.size());

            // BNRG p.170
            //  First the RecyclerView asks how many objects are in the list by calling the adapter's getItemCount') method.

            mAdapter = new SongAdapter(songs);
            // au cas où ce 20200206 - j'ajoute ce setSongs puisque mon clic primo ne retourne rien
            // inutile, ne change rien. Se focaliser sur le contexte avant le clic.
            //mAdapter.setSongs(songs);


            mSongRecyclerView.setAdapter(mAdapter);
        } else {
            Log.d("DEBUG_S4HActFrag", "mAdapter is not null");
            tableau_Etapes.sauv("S4HActivityFragment: mAdapter is not null");
            Log.d("DEBUG_S4HActFrag", "Après le songLab.getSongs:"+songs.size());
            mAdapter.setSongs(songs);
            mAdapter.notifyDataSetChanged();
        }


        // Load xml data in a non-ui thread
        //Log.d("DEBUG_S4HActFrag","Avant le LoadSongsTask");
        //new LoadSongsTask().execute();
        Log.d("DEBUG_S4HActFrag","Apres le mAdapter");

        //mAdapter = new SongAdapter( songs );
        //mSongRecyclerView.setAdapter(mAdapter);
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
            Log.d("DEBUG_S4HActFrag","Entree SongHolder(LayoutInflater)");
            tableau_Etapes.sauv("S4HActivityFragment: entree SongHolder");
            itemView.setOnClickListener(this);

            //mIdSongView = (TextView) itemView.findViewById(R.id.song_id);
            mTitleSongView = (TextView) itemView.findViewById(R.id.song_title);
            mChordsSongView = (TextView) itemView.findViewById(R.id.song_chords);

        }

        public void bind(Song song) {
            Log.d("DEBUG_S4HActFrag","Entree bind");
            tableau_Etapes.sauv("S4HActivityFragment: entree bind");
            mSong = song;
            //mIdSongView.setText(Integer.toString( mSong.getmId()));
            mTitleSongView.setText( mSong.getmTitle() );
            //mWordsSongView.setText( mSong.getmWords() );
            mChordsSongView.setText( mSong.getmChords() );
            Log.d( "DEBUG_S4HActFrag", "bind:getmId:"+mSong.getmId() );
        }


        @Override
        public void onClick(View view) {

            // OK ce 20190221 - mais on poursuis en p.205 du BNRG
            //Toast.makeText(getActivity(),
            //        mSong.getmTitle() + " clicked!", Toast.LENGTH_SHORT)
            //        .show();

            //Intent intent = new Intent(getActivity(), SongActivity.class);
            //Intent intent = SongActivity.newIntent( getActivity(), mSong.getmId() );

            Log.d("DEBUG_S4HActFrag","Entree onClick");
            tableau_Etapes.sauv("S4HActivityFragment: entree onClick");
            Log.d("DEBUG_S4HActFrag","getmId:"+mSong.getmId());
            Log.d("DEBUG_S4HActFrag","getmTitle:"+mSong.getmTitle());
            //Intent intent = SongActivity.newIntent( getActivity(), mSong.getmId());
            Intent intent = SongPagerActivity.newIntent( getActivity(), mSong.getmId() );
            startActivity( intent );
        }
    }

    class SongAdapter extends RecyclerView.Adapter<SongHolder> {

        private List<Song> mSongs;

        private Context mContext;
        private List<Song> mLSong;
        private Cursor mCursor;

        //public SongAdapter(List<Song> songs) {
        // 20190219 - tuto youtube
        //  https://www.youtube.com/watch?v=_m-Ve-BAYe0&list=PLrnPJCHvNZuBMJmll0xy2L2McYInT3aiu&index=3&t=0s

        public SongAdapter(ArrayList<Song> ls) {
            Log.d("DEBUG_S4HActFrag","Entree SongAdapter(ArrayList<Song>");
            tableau_Etapes.sauv("S4HActivityFragment: entree SongAdapter");
            mLSong = ls;
            Log.d("DEBUG_S4HActFrag","SongAdapter avec mLSong ayant "+mLSong.size()+ " elements");

        }

        @NonNull
        @Override
        public SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from( getActivity() );
            Log.d("DEBUG_S4HActFrag","Entree SongHolder onCreateViewHolder");
            tableau_Etapes.sauv("S4HActivityFragment: entree onCreateViewHolder");

            return new SongHolder( layoutInflater, parent );
        }

        @Override
        public void onBindViewHolder(SongHolder holder, int position) {
            Log.d("DEBUG_S4HActFrag","Entree onBindViewHolder avec position:"+position);
            tableau_Etapes.sauv("S4HActivityFragment: entree onBindViewHolder");
            Song song = mLSong.get(position);
            holder.bind(song);
        }

        @Override
        public int getItemCount() {
            Log.d("DEBUG_S4HActFrag","Entree getItemCount avec "+mLSong.size()+ " valeurs");
            tableau_Etapes.sauv("S4HActivityFragment: entree getItemCount");

            // 20200222 je remets le mLSongs
            return mLSong.size();
            // essai car tjs KO en primo. J'implémente la solution BNRG qui s'appuie sur "mSongs" en getItemCount ET en setSongs
            //return mSongs.size();
        }

        public void setSongs(List<Song> songs) {
            Log.d("DEBUG_S4HActFrag","Entree setSongs(List<Song> avec "+ songs.size() + " valeurs");
            tableau_Etapes.sauv("S4HActivityFragment: entree setSongs");
            mSongs = songs;
        }
    }

    // Chargement

    private Cursor LoadSongsTask() {

        Log.d("DEBUG_S4HActFrag","Entree LoadSongsTask");
        tableau_Etapes.sauv("S4HActivityFragment: entree LoadSongsTask");

        // query the database and return a cursor of employees.
        // PB avec le Context :     SongDatabase songDatabase = new SongDatabase(getApplicationContext());
        SongDatabase songDatabase = new SongDatabase(getContext());

        Log.d("DEBUG_S4HActFrag","LoadSongsTask: avant cursor");

        Cursor cursor = songDatabase.getAllSongsCursor();

        Log.d("DEBUG_S4HActFrag","LoadSongsTask: après cursor");

        return cursor;
        }

        public ArrayList<Song> getSongs(Cursor c){
            // JMA - ce 20190220
            //  je dois lire le curseur et retourner une liste de Songs afin de permettre
            //  la gestion du RecycleView

            tableau_Etapes.sauv("S4HActivityFragment: entree getSongs");

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

                    // check contents
/*                    Log.d("DEBUG_S4HActFrag:","getSongs:column1:"+column1);
                    Log.d("DEBUG_S4HActFrag","getSongs:column2:"+column2);
                    Log.d("DEBUG_S4HActFrag","getSongs:column3:"+column3);
                    Log.d("DEBUG_S4HActFrag","getSongs:column4:"+column4);
                    Log.d("DEBUG_S4HActFrag","getSongs:column5:"+column5);
                    Log.d("DEBUG_S4HActFrag","getSongs:column6:"+column6);
                    Log.d("DEBUG_S4HActFrag","getSongs:column7:"+column7);
                    Log.d("DEBUG_S4HActFrag","getSongs:column8:"+column8);
                    Log.d("DEBUG_S4HActFrag","getSongs:column9:"+column9);
                    Log.d("DEBUG_S4HActFrag","getSongs:column10:"+column10);
                    Log.d("DEBUG_S4HActFrag","getSongs:column11:"+column11);*/

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

/*                    Log.d("DEBUG_S4HActFrag:","getSongs:getmId:"+s.getmId());
                    Log.d("DEBUG_S4HActFrag:","getSongs:getmTitle:"+s.getmTitle());
                    Log.d("DEBUG_S4HActFrag:","getSongs:getmWords:"+s.getmWordsFr());
                    Log.d("DEBUG_S4HActFrag:","getSongs:getmWords:"+s.getmWordsGb());
                    Log.d("DEBUG_S4HActFrag:","getSongs:getmWords:"+s.getmWordsSp());
                    Log.d("DEBUG_S4HActFrag:","getSongs:getmChords:"+s.getmChords());
                    Log.d("DEBUG_S4HActFrag:","getSongs:getmChords:"+s.getmKind());
                    Log.d("DEBUG_S4HActFrag:","getSongs:getmChords:"+s.getmSpeed());
                    Log.d("DEBUG_S4HActFrag:","getSongs:getmAuthor:"+s.getmAuthor());
                    Log.d("DEBUG_S4HActFrag:","getSongs:getmChords:"+s.getmMp3());
                    Log.d("DEBUG_S4HActFrag:","getSongs:getmChords:"+s.getmMisc());*/

                    songList.add(s);

                } while(c.moveToNext());
            }
            //c.close();

            Log.d("DEBUG_S4HActFrag","getSongs avec songList ayant: "+songList.size()+" elements");

            return songList;

        }


    @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        super.onCreateOptionsMenu( menu, menuInflater );
        menuInflater.inflate( R.menu.search_menu, menu );

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("DEBUG_S4HActFrag:", "onQueryTextSubmit: " + s);
                tableau_Etapes.sauv("S4HActivityFragment: entree onQueryTextSubmit");
                seek4Songs(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("DEBUG_S4HActFrag:", "onQueryTextChange: " + s);
                tableau_Etapes.sauv("S4HActivityFragment: entree onQueryTextChange");
                return false;
            }
        });
    }

    private void seek4Songs(String search) {
        // appeler ici la recherche détaillée
        Log.d("DEBUG_S4HActFrag:", "seek4Songs");
        tableau_Etapes.sauv("S4HActivityFragment: entree seek4Songs");

        SongDatabase songDatabase = new SongDatabase(getContext());

        Log.d("DEBUG_S4HActFrag","seek4Songs: avant cursor:avec search:"+search);
        Cursor c = songDatabase.getAllSongsCursor();

        ArrayList<Song> songList = new ArrayList<Song>();

        if (c.moveToFirst()){
            do {
                // Passing values
                Integer column1 = c.getInt(0);

                // recherche dans le titre
                String column2 = c.getString(1);
                // recherche dans les paroles françaises
                String column3 = c.getString(2);
                // recherche dans les paroles anglaises
                String column4 = c.getString(3);

                if (column2.contains(search) || column3.contains(search) || column4.contains(search)) {
                    // trouvé !!!
                    Log.d( "DEBUG_S4HActFrag", "seek4Songs:titre:" + column2 );

                    String column5 = c.getString( 4 );
                    String column6 = c.getString( 5 );
                    String column7 = c.getString( 6 );
                    String column8 = c.getString( 7 );
                    String column9 = c.getString( 8 );
                    String column10 = c.getString( 9 );
                    String column11 = c.getString( 10 );

                    // Je stocke le chant complet au cas où.
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

                    songList.add( s );
                }

            } while(c.moveToNext());
        }
        c.close();

        // Affichage sur une activité spécialisée
        Intent intent = SongFragment.newIntent( getContext(), songList);
        //intent.putExtra(SEARCH_TEXT, search);
        startActivity( intent );


    }


}

