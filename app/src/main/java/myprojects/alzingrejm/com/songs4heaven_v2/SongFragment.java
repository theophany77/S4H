package myprojects.alzingrejm.com.songs4heaven_v2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class SongFragment extends Fragment {

    private static final String ARG_SONG_ID = "myprojects.alzingrejm.com.songs4heaven_v2.song_id";

    public static final String EXTRA_SONGS = "myprojects.alzingrejm.com.songs4heaven_v2.search_found_songs";

    private Song mSong;
    private TextView mTitleSong;
    private TextView mWordsSong;
    private TextView mWordsGbSong;
    private TextView mWordsSpSong;
    private TextView mChordsSong;
    private TextView mKindSong;
    private TextView mSpeedSong;
    private TextView mAuthorSong;
    private ImageButton mMp3Song;
    private TextView mMiscSong;

    private boolean bChords = true;
    private boolean bStockage = false;
    private boolean bColoriage = false;
    private int vPosDep = 0;
    private int vPosFin = 0;
    private int vNbChords =0;

    private StringBuilder mWordsWithoutChords;
    // pour permettre la mise en évidence des accords
    private SpannableStringBuilder mWordsWithChords;

    SoundPool ourSounds;
    int soundMp3;

    String ext="mp3";
    String filename="";

    // tableau des chants présents sous assets/mp3
    List<Sound> mSounds;

    private BeatBox mBeatBox;
    boolean mSongInMp3=false;

    // pour récupérer la chaîne de recherche qui vient de S4H_Act_Frag
    public static final String SEARCH_TEXT = "myprojects.alzingrejm.com.songs4heaven_v2.search_text";

    private String search_text="";

    ForegroundColorSpan fcsWhite = new ForegroundColorSpan(Color.WHITE);
    BackgroundColorSpan bcsBlack = new BackgroundColorSpan(Color.BLACK);

    // pour le cas classique
    public static SongFragment newInstance(int songId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SONG_ID, songId);

        Log.d("DEBUG_SongFrag","Entree newInstance");

        SongFragment fragment = new SongFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("DEBUG_SongFrag","Entree onCreate");
        tableau_Etapes.sauv("SongFragment: entree onCreate");

        super.onCreate( savedInstanceState );
        Bundle extras = getActivity().getIntent().getExtras();
        int songId = (int) getArguments().getSerializable(ARG_SONG_ID);
        search_text = (String) getArguments().getSerializable(SEARCH_TEXT);
        Log.d("DEBUG_SongFrag","onCreate:search:"+search_text);
        // new 20200305
        Log.d("DEBUG_SongFrag","avec songId:"+songId);
        mSong = SongLab.get( getActivity() ).getSong( songId );

        // gestion du son - 20190826
        //initializeSoundPool();

        // 20190831
        //      singleton désormais via SoundManager
        //SoundManager.initialize(this.getContext());

        mBeatBox = new BeatBox(getContext());

        mSounds = mBeatBox.getSounds();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_song, container, false);

        Log.d("DEBUG_SongFrag","onCreateView:search:"+search_text);
        tableau_Etapes.sauv("SongFragment: entree onCreateView");

        mTitleSong = (TextView) v.findViewById(R.id.song_title);
        Log.d("DEBUG_SongFrag","onCreateView:mSong:"+mSong);
        mTitleSong.setText(mSong.getmTitle());

        // on initialise, pour éviter de le faire à chaque clic, les chaînes sans et avec accords.
        Log.d("DEBUG_SongFrag:","avant les init hide et showChords" );
        mWordsWithoutChords=hideChords();
        mWordsWithChords=showChords();
        Log.d("DEBUG_SongFrag:","après les init hide et showChords" );

        // un clic sur le titre affiche les accords
        final Button button = v.findViewById(R.id.song_title);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // y a t-il des accords ?
                if (vNbChords == 0 ) {
                    Toast.makeText(getActivity(),
                            "Inutile d'afficher les accords, ce chant n'en comporte pas pour l'instant !", Toast.LENGTH_SHORT)
                            .show();

                }
                else {
                    if (bChords == false) {

                        // je supprime les accords {[A-Z]}
                        // je n'y arrive pas avec un ReplaceAll (regexp)

                        Log.d( "DEBUG_SongFrag:", "Chords:onClick:avec" + mWordsWithoutChords );

                        mWordsSong.setText( mWordsWithoutChords );

                        // au prochain clic, j'enlèverai les accords.
                        bChords = true;
                    } else {

                        // je veux les accords
                        mWordsSong.setText( mWordsWithChords );

                        //mWordsSong.setText(mSong.getmWordsFr());
                        Log.d( "DEBUG_SongFrag:", "Chords:onClick:else:avec" + mWordsWithChords );

                        // au prochain clic, je supprimerai les accords

                        bChords = false;

                    }
                }

            }

        });

        // un clic sur le "Song share" partage le chant
        final Button button_share = v.findViewById(R.id.song_share);
        button_share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // on appelle le Chooser
                onButtonChooserClicked(v);

            }


        });

        // un clic sur le "Song share" partage le chant
        final Button button_play = v.findViewById(R.id.song_play);
        button_play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // on appelle le Chooser
               // onButtonChooserClicked(v);

                //Toast.makeText(getActivity(),
                //        "Je passe dans le onClick du button_play !", Toast.LENGTH_SHORT)
                //         .show();

                // on appelle le Player
                onButtonPlayClicked(v);

            }


        });


        // 20190307 - au clic sur "Chords display" j'enlève les notions d'accords (format Chordpro, infos entre {} )
        //  Ex :
        //              Car {C}Dieu est un {G}Dieu puissant,Il {D}règne de son {Em}saint lieu,

        mWordsSong = (TextView) v.findViewById(R.id.song_words);
        // affichage de base sans les accords
        //Log.d("DEBUG_SongFrag:","avant init mWordsSong avec :"+ mWordsWithoutChords);
        mWordsSong.setText(mWordsWithoutChords);

        mWordsGbSong = (TextView) v.findViewById(R.id.song_words_gb);
        mWordsGbSong.setText(mSong.getmWordsGb());

        mWordsSpSong = (TextView) v.findViewById(R.id.song_words_sp);
        mWordsSpSong.setText(mSong.getmWordsSp());

        mChordsSong = (TextView) v.findViewById(R.id.song_chords);
        mChordsSong.setText(mSong.getmChords());

        mAuthorSong = (TextView) v.findViewById(R.id.song_author);
        mAuthorSong.setText(mSong.getmAuthor());

        //mMp3Song = (ImageButton) v.findViewById(R.id.song_mp3);
        //mMp3Song.setText(mSong.getmMp3());

        mMiscSong = (TextView) v.findViewById(R.id.song_misc);
        mMiscSong.setText(mSong.getmMisc());

        return v;
    }

    public static Intent newIntent(Context packageContext, ArrayList als)
    {
        //
        Log.d("DEBUG_SongFrag:","Entree newIntent");
        tableau_Etapes.sauv("SongFragment: entree newIntent");

        Intent intent  = new Intent(packageContext, SearchResults.class);
        intent.putStringArrayListExtra( EXTRA_SONGS, als );
        return intent;
    }

    public void onButtonChooserClicked(View view){
        Log.d( "DEBUG_SongFrag:", "Chooser - titre en cours:" + mTitleSong );
        tableau_Etapes.sauv("SongFragment: entree onButtonChooserClicked");

        Intent intent = new Intent( Intent.ACTION_SEND );
        intent.setType( "text/plain" );
        intent.putExtra(Intent.EXTRA_TEXT, mWordsSong.getText());
        Intent chosenIntent = Intent.createChooser( intent, "Envoyer les paroles ..." );
        startActivity( chosenIntent );
    }

    public void onButtonPlayClicked(View view){

        Log.d( "DEBUG_SongFrag:", "onButtonPlayClicked:entrée");
        tableau_Etapes.sauv("SongFragment: entree onButtonPlayClicked");


            //String search_text="";
            //String raw_mp3="R.raw"+"";
            //soundMp3 = ourSounds.load(this.getContext(), R.raw.je_m_envolerai, 1);

            // remplacement par un singleton ce 20190831
            //soundMp3 = ourSounds.load(this.getContext(), R.raw.spring, 1);
            //ourSounds.play (soundMp3, 0.9f, 0.9f, 1, 0, 1);

            // singleton depuis le 20190831
            //String mon_chant_mp3="spring";
            //SoundManager.initialize(this.getContext(), mon_chant_mp3);
            //SoundManager.getInstance().playClickSound();

        Log.d("DEBUG_SongFrag:", "onClick:mSong:<"+mSong.getmMp3()+">");
        for (Sound str : mSounds) {
            Log.d("DEBUG_SongFrag:", "onClick:str:<" + str.getName()+">");

            if (str.getName().equalsIgnoreCase(mSong.getmMp3())) {
                Log.d("DEBUG_SongFrag:", "onClickString:trouvé");
                mSongInMp3=true;
            }
            else {
                Log.d("DEBUG_SongFrag:", "onClickString:absent");
                mSongInMp3=false;
            }
        }

        // fin boucle
        if (mSongInMp3) {
            //mBeatBox.play(mSong);
            Toast.makeText(getActivity(),
                    "Le chant <"+mSong.getmMp3()+" > possède bien un extrait mp3. ",Toast.LENGTH_SHORT)
                    .show();
        }
        else {
            Toast.makeText(getActivity(),
                    "Le chant <"+mSong.getmMp3()+" > ne possède pas pour l'instant d'extrait mp3. ",Toast.LENGTH_SHORT)
                    .show();
        }


        Log.d( "DEBUG_SongFrag:", "onButtonPlayClicked:sortie");

    }

    public StringBuilder hideChords() {
        tableau_Etapes.sauv("SongFragment: entree hideChords");
        Log.d( "DEBUG_SongFrag:", "hideChords:mSong.getmWordsFr().length():"+mSong.getmWordsFr().length());
        Log.d( "DEBUG_SongFrag:", "hideChords:mSong.getmWordsFr()"+mSong.getmWordsFr());

        bStockage = true;
        StringBuilder stack = new StringBuilder();

        for (int i = 0; i < mSong.getmWordsFr().length(); i++) {
            char current = mSong.getmWordsFr().charAt( i );

            if (current == '{' ) {
                // détection d'un crochet ouvrant : début d'accord. On ne stocke plus rien.
                bStockage = false;
                Log.d( "DEBUG_SongFrag:", "hideChords:detection { en pos:"+i);
                Log.d( "DEBUG_SongFrag:", "hideChords:stack:"+stack);
            }
            if (current == '}' ) {
                // détection d'un crochet fermant : fin d'accord. On stocke à nouveau.
                bStockage = true;
                Log.d( "DEBUG_SongFrag:", "hideChords:detection } en pos:"+i);
                Log.d( "DEBUG_SongFrag:", "hideChords:stack:"+stack);
            }

            if ( bStockage) {
                // on stock si bStockage est vrai et que l'on ne lit pas de crocher fermant
                if ( current != '}') {
                    stack.append( current );
                }
            }
        }

        Log.d( "DEBUG_SongFrag:", "hideChords:stack avant return:"+stack);

        return stack;
    }

    public SpannableStringBuilder showChords() {
        tableau_Etapes.sauv("SongFragment: entree showChords");
        Log.d( "DEBUG_SongFrag:", "showChords:mSong.getmWordsFr().length():"+mSong.getmWordsFr().length());
        Log.d( "DEBUG_SongFrag:", "showChords:mSong.getmWordsFr()"+mSong.getmWordsFr());

        SpannableStringBuilder stack = new SpannableStringBuilder ();
        bColoriage = false;
        vNbChords=0;

        for (int i = 0; i < mSong.getmWordsFr().length(); i++) {
            char current = mSong.getmWordsFr().charAt( i );

            if (current == '{' ) {
                // détection d'un crochet ouvrant : début d'accord. On colorie jusqu'au crocher fermant.
                vPosDep = i;
                stack.append( current );
                Log.d( "DEBUG_SongFrag:", "colorChords:Coloriagestack:detection { en pos:"+i);
                Log.d( "DEBUG_SongFrag:", "colorChords:stack:"+stack);
            }
            if (current == '}' ) {
                // détection d'un crochet fermant : fin d'accord. On stocke à nouveau.
                bColoriage = true;
                vPosFin = i;
                stack.append( current );
                Log.d( "DEBUG_SongFrag:", "colorChords:Coloriagestack:detection } en pos:"+i);
                Log.d( "DEBUG_SongFrag:", "colorChords:stack:"+stack);

            }

            if ( bColoriage)
            {
                // on stock si bStockage est vrai et que l'on ne lit pas de crocher fermant
                Log.d( "DEBUG_SongFrag:", "colorChords:Coloriage:stack:"+stack);
                Log.d( "DEBUG_SongFrag:", "colorChords:Coloriage:vPosDeb:"+vPosDep);
                Log.d( "DEBUG_SongFrag:", "colorChords:Coloriage:vPosFin:"+vPosFin);
                Log.d( "DEBUG_SongFrag:", "colorChords:Coloriage:i:"+i);
                Log.d( "DEBUG_SongFrag:", "colorChords:Coloriage:stack.length():"+stack.length());

                Log.d( "DEBUG_SongFrag:", "colorChords:Coloriage avec vPosDep:"+vPosDep+" / vPosFin:"+vPosFin+" / i:"+i);
                stack.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), vPosDep, vPosFin+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                stack.setSpan(fcsWhite, vPosDep, vPosFin+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                stack.setSpan(bcsBlack, vPosDep, vPosFin+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                bColoriage = false;

                vNbChords = vNbChords + 1;
            }
            else {
                if ( current != '{' ){
                    stack.append( current );
                }
            }
        }

        Log.d( "DEBUG_SongFrag:", "showChords:stack avant return:"+stack);

        return stack;
    }

    private void initializeSoundPool() {

        Log.d( "DEBUG_SongFrag:", "initializeSoundPool:entrée");
        tableau_Etapes.sauv("SongFragment: entree initializeSoundPool");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d( "DEBUG_SongFrag:", "initializeSoundPool:SDK_INT >= LOLLIPOP");
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            ourSounds = new SoundPool.Builder()
                    .setMaxStreams(2)
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else {
            Log.d( "DEBUG_SongFrag:", "initializeSoundPool:SDK_INT < LOLLIPOP");
            ourSounds = new SoundPool(2, AudioManager.STREAM_MUSIC, 1);

        }

        Log.d( "DEBUG_SongFrag:", "initializeSoundPool:sortie");
    }

    public void onClick(View v) {

               // ourSounds.play (soundMp3, 0.9f, 0.9f, 1, 0, 1);

        // singleton depuis le 20190831


    }



}
