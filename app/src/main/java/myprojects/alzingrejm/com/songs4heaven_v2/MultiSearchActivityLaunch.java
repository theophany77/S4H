package myprojects.alzingrejm.com.songs4heaven_v2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import database.SongDatabase;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;


public class MultiSearchActivityLaunch extends AppCompatActivity {

    // cinématique
    // chargement de tous les chants dans LoadSongsTask
    // filtrage par les conditions dans getSongs


    EditText mTitleSong = null;
    EditText mWordsSong = null;
    EditText mKindSong = null;
    EditText mSpeedSong = null;
    EditText mChordSong = null;
    EditText mAuthorSong = null;
    EditText mMiscSong = null;

    public String myFinalSearch = "";

    // pour la partie mapping
    public int myMap = 0;
    HashMap<String, Integer> hmap = new HashMap<String, Integer>();
    public int myMapInLoop = 0;

    public String mTitle = "";
    public String mWords = "";
    public String mKind = "";
    public String mSpeed = "";
    public String mChord = "";
    public String mAuthor = "";
    public String mMisc = "";

    // pour la détection ou pas des éléments distinctifs
    public int mTitleDetectNb = 0;
    public int mWordsDetectNb = 0;
    public int mKindDetectNb = 0;
    public int mSpeedDetectNb = 0;
    public int mChordDetectNb = 0;
    public int mAuthorDetectNb = 0;
    public int mMiscDetectNb = 0;
    boolean BooleancheckBoxState = false;
    boolean BooleancheckBoxStateNewSongs = false;
    boolean BooleancheckBoxStatePastor = false;

    boolean AddSongList = false;
    boolean WordsFrOk=false;
    boolean WordsGbOk=false;
    boolean WordsSpOk=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        Log.d( "DEBUG_MultiSeaActLaunch", "Entree onCreate" );
        tableau_Etapes.sauv("MultiSearchActivityLaunch: entree onCreate");
        setContentView( R.layout.activity_multi_search );

        //}


        //public View onCreateView(LayoutInflater inflater, ViewGroup container,
        //                       Bundle savedInstanceState) {

        //View v = inflater.inflate( R.layout.activity_multi_search, container, false );

        // je charge le xml dans la bdd
        Log.d( "DEBUG_MultiSeaActLaunch", "Entree onCreateView" );

        //View v2 = inflater.inflate( R.layout.activity_multi_search_fragment, container, false );


        //return v;

    }

    public void song_search_click(View v) {
        //Intent intent = new Intent( this, MultiSearchActivity.class );
        //startActivity(intent);
        Log.d( "DEBUG_MultiSeaActLaunch", "Entree song_search" );
        tableau_Etapes.sauv("MultiSearchActivityLaunch: entree song_search_click");

        mTitleSong = (EditText) findViewById( R.id.song_title );
        mWordsSong = (EditText) findViewById( R.id.song_words );
        mKindSong = (EditText) findViewById( R.id.song_kind );
        mSpeedSong = (EditText) findViewById( R.id.song_speed );
        mChordSong = (EditText) findViewById( R.id.song_chord );
        mAuthorSong = (EditText) findViewById( R.id.song_author );
        mMiscSong = (EditText) findViewById( R.id.song_misc );

        // Les zones de recherches
        //
        mTitle = mTitleSong.getText().toString();
        mWords = mWordsSong.getText().toString();
        mChord = mChordSong.getText().toString();
        mKind = mKindSong.getText().toString();
        mSpeed = mSpeedSong.getText().toString();
        mAuthor = mAuthorSong.getText().toString();
        mMisc = mMiscSong.getText().toString();

        //check box pour les accords standards, les nouveaux chants et les chants du pasteur
        final CheckBox CheckBoxsimpleCheckBox = (CheckBox) findViewById( R.id.chordCheckBox );
        final CheckBox CheckBoxsimpleCheckBoxNewSongs = (CheckBox) findViewById( R.id.chordCheckBoxNewSongs );
        final CheckBox CheckBoxsimpleCheckBoxPastor = (CheckBox) findViewById( R.id.chordCheckBoxPastor );
        BooleancheckBoxState = CheckBoxsimpleCheckBox.isChecked();
        BooleancheckBoxStateNewSongs = CheckBoxsimpleCheckBoxNewSongs.isChecked();
        BooleancheckBoxStatePastor = CheckBoxsimpleCheckBoxPastor.isChecked();
        Log.d( "DEBUG_MultiSeaActLaunch", "song_search avant LoadSongsTask" );

        Cursor c1;
        c1 = LoadSongsTask();

        // 20190612 : getSongs retourne un tableau de chants filtrés sur les critères saisis
        //  soit songList_4_fragment
        final ArrayList<Song> songs = getSongs( c1 );
        // pour les chants dont le poids correspond à la valeur calculée par confectionMAP
        ArrayList<Song> songList_weight_ok = new ArrayList<Song>();


        // il faut que je passe ce tableau au fragment pour remplir la RecyclerView
        // Comment ?
        //  Via un argument. A suivre ...

        Log.d( "DEBUG_MultiSeaActLaunch", "Après appel getSongs(c1)" );
        Log.d( "DEBUG_MultiSeaActLaunch", "nb of songs:" + songs.size() );

        // un clic sur le bouton effectue le calcul du poids des zones saisies (confectionMAP ci-après),
        //  poids qui sera comparé au poids de chaque chant inspecté et toute égalité stricte
        //  indiquera un match.

        Log.d( "DEBUG_MultiSeaActLaunch", "mTitle:" + mTitle );
        Log.d( "DEBUG_MultiSeaActLaunch", "mWords:" + mWords );
        Log.d( "DEBUG_MultiSeaActLaunch", "mChord:" + mChord );
        Log.d( "DEBUG_MultiSeaActLaunch", "mKind:" + mKind );
        Log.d( "DEBUG_MultiSeaActLaunch", "mSpeed:" + mSpeed );
        Log.d( "DEBUG_MultiSeaActLaunch", "mAuthor:" + mAuthor );
        Log.d( "DEBUG_MultiSeaActLaunch", "mMisc:" + mMisc );

        int mTitleDetectNb = 0;
        int mWordsDetectNb = 0;
        int mKindDetectNb = 0;
        int mSpeedDetectNb = 0;
        int mChordDetectNb = 0;
        int mAuthorDetectNb = 0;
        int mMiscDetectNb = 0;

        // confectionMAP
        // je vais construire une sorte de map qui va résumer les choix opérés à la recherche
        //      0 pour pas de saisie
        //      1 pour saisie
        //  Ex:
        //           1          1           0           0           1           1           0
        //              signifiera ainsi la présence des 4 éléments suivants :
        //          Title   Words                                Chords     Author
        //
        //  Puis, en fin de boucle de recherche, on comparera la map obtenue dans la boucle (selon le même principe),
        //  et la map initiale. En cas d'égalité, on sortira le ou les chants concernés.
        //
        // On pourra éventuellement indiquer une recherche incomplète (3 sur 4 ou 2 sur 4 par ex).

        // j'ai mes zones de saisie
        // mes data sont également stockées dans songs
        // je vais pouvoir procéder à la recherche

        // calcul de la valeur numérique des saisies selon les puissances de 2
        confectionMap();

        Log.d( "DEBUG_MultiSeaActLaunch", "avant boucle for sur songs : nb of songs :" + songs.size() );

        for (Song song : songs) {

            // init de la map
            myMapInLoop = 0;
            AddSongList = false;
            WordsFrOk=false;
            WordsGbOk=false;
            WordsSpOk=false;

            // 1) recherche dans un  titre
            if (mTitle.length() > 0) {
                System.out.println( "Un titre a été saisi : " + song.getmTitle() );
                if (song.getmTitle() != null) {
                    if (song.getmTitle().contains( mTitle )) {
                        myMapInLoop = myMapInLoop + 1;

                        Log.d( "DEBUG_MultiSeaActLaunch", "mTitle(" + mTitle + ") trouvé pour <" + song.getmTitle() + ">" );
                        myFinalSearch = myFinalSearch + "mTitle (" + mTitle + ") trouvé pour <" + song.getmTitle() + ">\n";
                    }
                }

            }

            // 2) recherche dans le texte
            //      a) ai-je une saisie de paroles en Fr, Gb ou Sp ?
            //      b) pour chaque type, ai-je égalité entre saisie clavier et texte en base ?
            //
            if (mWords.length() > 0) {
                if (song.getmWordsFr() != null) {
                    AddSongList = true;
                    if (song.getmWordsFr().contains( mWords )) {
                        // trouvé en FR
                        WordsFrOk = true;
                    }
                }
                if (song.getmWordsGb() != null) {
                    AddSongList = true;
                    if (song.getmWordsGb().contains( mWords )) {
                        // trouvé en FR
                        WordsGbOk = true;
                    }
                }
                if (song.getmWordsSp() != null) {
                    AddSongList = true;
                    if (song.getmWordsSp().contains( mWords )) {
                        // trouvé en FR
                        WordsSpOk = true;
                    }
                }
                if (AddSongList) {
                    // au moins un type saisi
                    if (WordsFrOk || WordsGbOk || WordsSpOk) {
                        // égalité avec soit du Fr soit du Gb soit du Sp
                        myMapInLoop = myMapInLoop + 2;
                        Log.d("DEBUG_MultiSeaActLaunch", "mWord(fr/gb/sp) (" + mWords + ") trouvé pour <" + song.getmTitle() + ">");
                        myFinalSearch = myFinalSearch + "mWord(fr/gb/sp) (" + mWords + ") trouvé pour <" + song.getmTitle() + ">\n";
                    }
                }
            }

            // 3) recherche dans la tonalité
            if (mChord.length() > 0) {
                if (song.getmChords() != null) {
                    if (song.getmChords().contains( mChord )) {
                        // égalité
                        myMapInLoop = myMapInLoop + 4;
                        Log.d( "DEBUG_MultiSeaActLaunch", "mChord (" + mChord + ") trouvé pour <" + song.getmTitle() + ">" );
                        myFinalSearch = myFinalSearch + "mChord (" + mChord + ") trouvé pour <" + song.getmTitle() + ">\n";
                    }
                }

            }

            // 4) recherche dans la sorte de chant
            if (mKind.length() > 0) {
                if (song.getmKind() != null) {
                    if (song.getmKind().contains( mKind )) {
                        // égalité
                        myMapInLoop = myMapInLoop + 8;
                        Log.d( "DEBUG_MultiSeaActLaunch", "mKind (" + mKind + ") trouvé pour <" + song.getmTitle() + ">" );
                        myFinalSearch = myFinalSearch + "mKind (" + mKind + ") trouvé pour <" + song.getmTitle() + ">\n";
                    }
                }

            }

            // 5) recherche dans la vitesse
            if (mSpeed.length() > 0) {
                if (song.getmSpeed() != null) {
                    if (song.getmSpeed().contains( mSpeed )) {
                        // égalité
                        myMapInLoop = myMapInLoop + 16;
                        Log.d( "DEBUG_MultiSeaActLaunch", "mSpeed (" + mSpeed + ") trouvé pour <" + song.getmTitle() + ">" );
                        myFinalSearch = myFinalSearch + "mKind (" + mSpeed + ") trouvé pour <" + song.getmTitle() + ">\n";
                    }
                }

            }

            // 6) recherche dans l'auteur
            if (mAuthor.length() > 0) {
                if (song.getmAuthor() != null) {
                    if (song.getmAuthor().contains( mAuthor )) {
                        // égalité
                        myMapInLoop = myMapInLoop + 32;
                        Log.d( "DEBUG_MultiSeaActLaunch", "mAuthor (" + mAuthor + ") trouvé pour <" + song.getmTitle() + ">" );
                        myFinalSearch = myFinalSearch + "mAuthor (" + mAuthor + ") trouvé pour <" + song.getmTitle() + ">\n";
                    }
                }

            }

            // 7) recherche dans les détails
            if (mMisc.length() > 0) {
                if (song.getmMisc() != null) {
                    if (song.getmMisc().contains( mMisc )) {
                        // égalité
                        myMapInLoop = myMapInLoop + 64;
                        Log.d( "DEBUG_MultiSeaActLaunch", "mMisc (" + mMisc + ") trouvé pour <" + song.getmTitle() + ">" );
                        myFinalSearch = myFinalSearch + "mMisc (" + mMisc + ") trouvé pour <" + song.getmTitle() + ">\n";
                    }
                }

            }

            Log.d( "DEBUG_MultiSeaActLaunch", "song_search:BooleancheckBoxState:chords:" + BooleancheckBoxState );
            if (BooleancheckBoxState) {
                Log.d( "DEBUG_MultiSeaActLaunch", "song_search:chords:je rentre dans le if" );
                if (song.getmWordsFr().contains( "{" ) || song.getmWordsFr().contains( "}" )) {
                    // égalité
                    myMapInLoop = myMapInLoop + 128;
                    Log.d( "DEBUG_MultiSeaActLaunch", "accords trouvés pour <" + song.getmTitle() + ">" );
                    myFinalSearch = myFinalSearch + "accords trouvés pour <" + song.getmTitle() + ">\n";
                }
            }

            Log.d( "DEBUG_MultiSeaActLaunch", "song_search:BooleancheckBoxState:new_songs:" + BooleancheckBoxStateNewSongs );
            if (BooleancheckBoxStateNewSongs) {
                Log.d( "DEBUG_MultiSeaActLaunch", "song_search:new_songs:je rentre dans le if" );
                if (song.getmMisc().contains( "NEW:SONG" )) {
                    // égalité
                    myMapInLoop = myMapInLoop + 256;
                    Log.d( "DEBUG_MultiSeaActLaunch", "NEW:SONG trouvé pour <" + song.getmTitle() + ">" );
                    myFinalSearch = myFinalSearch + "NEW:SONG trouvé pour <" + song.getmTitle() + ">\n";
                }
            }

            Log.d( "DEBUG_MultiSeaActLaunch", "song_search:BooleancheckBoxState:pasteur:" + BooleancheckBoxStatePastor );
            if (BooleancheckBoxStatePastor) {
                Log.d( "DEBUG_MultiSeaActLaunch", "song_search:pasteur:je rentre dans le if" );
                if (song.getmMisc().contains( "GL:BEST" )) {
                    // égalité
                    myMapInLoop = myMapInLoop + 512;
                    Log.d( "DEBUG_MultiSeaActLaunch", "GL:BEST trouvé pour <" + song.getmTitle() + ">" );
                    myFinalSearch = myFinalSearch + "GL:BEST trouvé pour <" + song.getmTitle() + ">\n";
                }
            }

            // on stocke le résultat du parcours en hashmap
            hmap.put( song.getmTitle(), myMapInLoop );

        }   // fin boucle for

        // resultat final
        Log.d( "DEBUG_MultiSeaActLaunch", "RESULTAT FINAL : " + myFinalSearch + "\n" );

        // comme je ne sais pas ajouter un objet de type chant si j'ai son titre,
        // je procède autrement ... par une énième boucle sur les chants ...
        final ArrayList<Song> songs_that_match = getSongs4RecyclerView( c1 );

        // on passe en arguments la liste des chants issue de getSongs.
        // ces chants sont déjà triés mais ne prennent pas en compte la notion de poids
        // 20190706 : on passe désormais la liste des chants issue de getSongs4RecyclerView

        Log.d( "DEBUG_MultiSeaActLaunch", "Dans song_search, avant appel MultiSearchActivity" );
        Intent intent = new Intent( this, MultiSearchActivity.class );

        Bundle args = new Bundle();
        //args.putSerializable("ARRAYLIST",(Serializable)songs);
        args.putSerializable( "ARRAYLIST", (Serializable) songs_that_match );
        intent.putExtra( "BUNDLE", args );

        Log.d( "DEBUG_MultiSeaActLaunch", "Avant start MultiSearchActivity" );
        tableau_Etapes.sauv("MultiSearchActivityLaunch: avant start MultiSearchActivity");

        startActivity( intent );
    }

    public void confectionMap() {
        Log.d( "DEBUG_MultiSeaActLaunch", "ENTREE confectionMAP" );
        tableau_Etapes.sauv("MultiSearchActivityLaunch: entree confectionMap");

        myMap = 0;

        Log.d( "DEBUG_MultiSeaActLaunch", "confectionMap:Song Title:(" + mTitle + ")\n" );
        Log.d( "DEBUG_MultiSeaActLaunch", "confectionMap:Song Words:(" + mWords + ")\n" );
        Log.d( "DEBUG_MultiSeaActLaunch", "confectionMap:Song Chord:(" + mChord + ")\n" );
        Log.d( "DEBUG_MultiSeaActLaunch", "confectionMap:Song Kind:(" + mKind + ")\n" );
        Log.d( "DEBUG_MultiSeaActLaunch", "confectionMap:Song Speed:(" + mSpeed + ")\n" );
        Log.d( "DEBUG_MultiSeaActLaunch", "confectionMap:Song Author:(" + mAuthor + ")\n" );
        Log.d( "DEBUG_MultiSeaActLaunch", "confectionMap:Song Misc:(" + mMisc + ")\n" );

        if (mTitle.length() > 0) {
            myMap = 1;
        } else {
            Log.d( "DEBUG_MultiSeaActLaunch", "confectionMap:mTitle saisi avec " + mTitle );
        }

        if (mWords.length() > 0) {
            myMap = myMap + 2;
        } else {
            Log.d( "DEBUG_MultiSeaActLaunch", "confectionMap:mWords saisi avec " + mWords );
        }

        if (mChord.length() > 0) {
            myMap = myMap + 4;
        } else {
            Log.d( "DEBUG_MultiSeaActLaunch", "confectionMap:mChord saisi avec " + mChord );
        }

        if (mKind.length() > 0) {
            myMap = myMap + 8;
        } else {
            Log.d( "DEBUG_MultiSeaActLaunch", "confectionMap:mKind saisi avec " + mKind );
        }

        if (mSpeed.length() > 0) {
            myMap = myMap + 16;
        } else {
            Log.d( "DEBUG_MultiSeaActLaunch", "confectionMap:mSpeed saisi avec " + mSpeed );
        }

        if (mAuthor.length() > 0) {
            myMap = myMap + 32;
        } else {
            Log.d( "DEBUG_MultiSeaActLaunch", "confectionMap:mAuthor saisi avec " + mAuthor );
        }

        if (mMisc.length() > 0) {
            myMap = myMap + 64;
        } else {
            Log.d( "DEBUG_MultiSeaActLaunch", "confectionMap:mMisc saisi avec " + mMisc );
        }

        Log.d( "DEBUG_MultiSeaActLaunch", "MAP INITIALE : " + myMap );

        // gestion des cases  cocher
        //  1) chants avec accords au format standard
        if (BooleancheckBoxState) {
            myMap = myMap + 128;
        }

        //  2) nouveaux chants
        if (BooleancheckBoxStateNewSongs) {
            myMap = myMap + 256;
        }

        //  3) chants du pasteur
        if (BooleancheckBoxStatePastor) {
            myMap = myMap + 512;
        }

        Log.d( "DEBUG_MultiSeaActLaunch", "confectionMAP:myMap valeur finale:" + myMap );
    }

    private Cursor LoadSongsTask() {

        Log.d( "DEBUG_MultiSeaActLaunch", "Entree LoadSongsTask" );
        tableau_Etapes.sauv("MultiSearchActivityLaunch: entree LoadSongsTask");

        // query the database and return a cursor of employees.
        // PB avec le Context :
        SongDatabase songDatabase = new SongDatabase( getBaseContext() );
        //////////SongDatabase songDatabase = new SongDatabase( getBaseContext() );

        Log.d( "DEBUG_MultiSeaActLaunch", "LoadSongsTask: avant cursor" );

        Cursor cursor = songDatabase.getAllSongsCursor();

        Log.d( "DEBUG_MultiSeaActLaunch", "LoadSongsTask: après cursor" );

        return cursor;
    }

    public ArrayList<Song> getSongs(Cursor c) {
        // JMA - ce 20190220
        //  je dois lire le curseur et retourner une liste de Songs afin de permettre
        //  la gestion du RecyclerView

        tableau_Etapes.sauv("MultiSearchActivityLaunch: entree getSongs");

        ArrayList<Song> songList = new ArrayList<Song>();
        ArrayList<Song> songList_4_fragment = new ArrayList<Song>();

        if (c.moveToFirst()) {
            do {

                // init de la map
                myMapInLoop = 0;

                // Passing values
                Integer column1 = c.getInt( 0 );
                String column2 = c.getString( 1 );
                String column3 = c.getString( 2 );
                String column4 = c.getString( 3 );
                String column5 = c.getString( 4 );
                String column6 = c.getString( 5 );
                String column7 = c.getString( 6 );
                String column8 = c.getString( 7 );
                String column9 = c.getString( 8 );
                String column10 = c.getString( 9 );
                String column11 = c.getString( 10 );

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
                if (mTitle.length() > 0) {
                    System.out.println( "getSongs : Un titre a été saisi : " + s.getmTitle() );
                    if (s.getmTitle() != null) {
                        if (s.getmTitle().contains( mTitle )) {
                            songList_4_fragment.add( s );
                            myMapInLoop = myMapInLoop + 1;
                        }
                    }

                }

                s.setmWordsFr( column3 );
                AddSongList = false;
                if (mWords.length() > 0) {
                    System.out.println( "getSongs : Des paroles ont été saisies : " + s.getmWordsFr() );
                    // C'est lourd mais je suis obligé de tester chaque zone sinon j'ai des Null Pointer Exception.
                    System.out.println( "getSongs : Fr :" + s.getmWordsFr().length() );

                    if (s.getmWordsFr() != null) {
                        if (s.getmWordsFr().contains( mWords )) {
                            System.out.println( "getSongs : WordsFr match pour :" + s.getmTitle() );
                            AddSongList = true;
                        }
                    }

                    if (s.getmWordsGb() != null) {
                        if (s.getmWordsGb().contains( mWords )) {
                            System.out.println( "getSongs : WordsGb match pour :" + s.getmTitle() );
                            AddSongList = true;
                        }
                    }

                    if (s.getmWordsSp() != null) {
                        if (s.getmWordsSp().contains( mWords )) {
                            System.out.println( "getSongs : WordsSp match pour :" + s.getmTitle() );
                            AddSongList = true;
                        }
                    }
                }

                // On ajoute le chant s'il a été détecté présent dans les paroles FR et/ou GB et/ou SP
                if (AddSongList) {
                    songList_4_fragment.add( s );
                    myMapInLoop = myMapInLoop + 2;
                }

                s.setmChords( column6 );
                if (mChord.length() > 0) {
                    System.out.println( "getSongs : Des accords ont été saisis : " + s.getmChords() );
                    if (s.getmChords() != null) {
                        if (s.getmChords().contains( mChord )) {
                            songList_4_fragment.add( s );
                            myMapInLoop = myMapInLoop + 4;
                        }
                    }

                }

                s.setmKind( column7 );
                if (mKind.length() > 0) {
                    System.out.println( "getSongs : Un genre de chant a été saisi : " + s.getmKind() );
                    if (s.getmKind() != null) {
                        if (s.getmKind().contains( mKind )) {
                            songList_4_fragment.add( s );
                            myMapInLoop = myMapInLoop + 8;
                        }
                    }

                }

                s.setmSpeed( column8 );
                if (mSpeed.length() > 0) {
                    System.out.println( "getSongs : Une vitesse a été saisie : " + s.getmSpeed() );
                    if (s.getmSpeed() != null) {
                        if (s.getmSpeed().contains( mSpeed )) {
                            songList_4_fragment.add( s );
                            myMapInLoop = myMapInLoop + 16;
                        }
                    }

                }

                s.setmAuthor( column9 );
                if (mAuthor.length() > 0) {
                    System.out.println( "getSongs : Un auteur a été saisi : " + s.getmAuthor() );
                    if (s.getmAuthor() != null) {
                        if (s.getmAuthor().contains( mAuthor )) {
                            songList_4_fragment.add( s );
                            myMapInLoop = myMapInLoop + 32;
                        }
                    }

                }

/*                s.setmMp3( column10 );
                if (m.length() > 0) {
                    System.out.println( "getSongs : Des paroles ont été saisies : " + s.getmWordsGb() );
                    if (s.getmWordsGb().contains( mWords )) {
                        songList_4_fragment.add( s );
                        myMapInLoop = myMapInLoop + 4;
                    }
                }*/

                s.setmMisc( column11 );
                if (mMisc.length() > 0) {
                    System.out.println( "getSongs : Des divers ont été saisis : " + s.getmMisc() );
                    if (s.getmMisc() != null) {
                        if (s.getmMisc().contains( mMisc )) {
                            songList_4_fragment.add( s );
                            myMapInLoop = myMapInLoop + 64;
                        }
                    }

                }

                // accords au format standard
                //Log.d( "DEBUG_MultiSeaActLaunch","getSongs:BooleancheckBoxState:chords:"+BooleancheckBoxState);
                if (BooleancheckBoxState) {
                    //Log.d( "DEBUG_MultiSeaActLaunch","getSongs:chords:je rentre dans le if");
                    if (s.getmWordsFr().contains( "{" ) || s.getmWordsFr().contains( "}" )) {
                        System.out.println( "getSongs : Des accords standards ont été saisis" );
                        //Log.d( "DEBUG_MultiSeaActLaunch", "getSongs:accords standards dans:"+s.getmTitle() );
                        songList_4_fragment.add( s );
                        myMapInLoop = myMapInLoop + 128;
                    }
                }

                // nouveaux chants
                //Log.d( "DEBUG_MultiSeaActLaunch","getSongs:BooleancheckBoxState:new_song:"+BooleancheckBoxStateNewSongs);
                if (BooleancheckBoxStateNewSongs) {
                    //Log.d( "DEBUG_MultiSeaActLaunch","getSongs:new_songs:je rentre dans le if");
                    if (s.getmMisc().contains( "NEW:SONG" )) {
                        System.out.println( "getSongs : NEW:SONG a été trouvé" );
                        //Log.d( "DEBUG_MultiSeaActLaunch", "getSongs:new_songs dans:"+s.getmTitle() );
                        songList_4_fragment.add( s );
                        myMapInLoop = myMapInLoop + 256;
                    }
                }

                // chants préférés du pasteur
                //Log.d( "DEBUG_MultiSeaActLaunch","getSongs:BooleancheckBoxState:pasteur:"+BooleancheckBoxStatePastor);
                if (BooleancheckBoxStatePastor) {
                    //Log.d( "DEBUG_MultiSeaActLaunch","getSongs:pasteur:je rentre dans le if");
                    if (s.getmMisc().contains( "GL:BEST" )) {
                        System.out.println( "getSongs : GL:BEST a été trouvé" );
                        //Log.d( "DEBUG_MultiSeaActLaunch", "getSongs:pasteur dans:"+s.getmTitle() );
                        songList_4_fragment.add( s );
                        myMapInLoop = myMapInLoop + 512;
                    }
                }

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
                Log.d( "DEBUG_MultiSeaActLaun:", "getSongs:getmTitle:" + s.getmTitle() );
                songList.add( s );      // utile ????

                // on stocke le résultat du parcours en hashmap
                hmap.put( s.getmTitle(), myMapInLoop );

            }
            while (c.moveToNext());
        }
        //c.close();

        //return songList;
        return songList_4_fragment;

    }

    public ArrayList<Song> getSongs4RecyclerView(Cursor c) {
        // JMA - ce 20190706
        //  je dois lire le curseur et retourner une liste de Songs qui concordent avec les titres
        //  de bon poids afin de permettre la gestion du RecyclerView
        // Ce code est très semblable au getSongs sauf qu'il compare avec les titres du hmap
        // Je le fais car je ne sais pas comment récupérer un objet entier à partir de son titre,
        // ce que j'aurais pu faire directement dans l'exploitation du hmap.
        //
        // Je vais donc lire le hmap et, pour chaque correspondance entre le poids initial et le poids mémorisé
        // aller chercher l'objet Song corrrespodant au titre stocké dans le hmap.

        ArrayList<Song> songList = new ArrayList<Song>();
        ArrayList<Song> songList_4_fragment = new ArrayList<Song>();


        // affichage de la boucle avec comparaison entre map en boucle et map initiale


        // début boucle des chants complets
        if (c.moveToFirst()) {
            do {
                // Seul le titre m'intéresse
                // Non !!! J'ai besoin du ID en fait car c'est la clé pour obtenir le bon numéro de chant. Sinon, j'étais toujours sur le chant 0 !!!
                Integer column1 = c.getInt( 0 );
                String column3 = c.getString( 1 );

                // check contents
                //Log.d("DEBUG_S4HActFrag","getSongs:column3:titre4RecyclerView:"+column3);

                // Do something Here with values
                Song s = new Song();

                s.setmId( column1 );
                s.setmTitle( column3 );

                if (s.getmTitle() != null) {

                    /* Display content using Iterator*/
                    Set set = hmap.entrySet();
                    Iterator iterator = set.iterator();
                    while (iterator.hasNext()) {
                        Map.Entry mentry = (Map.Entry) iterator.next();
                        if (myMap == (int) mentry.getValue()) {
                            Log.d( "DEBUG_MultiSeaActLaunch", "EGALITE PARFAITE pour key:(" + mentry.getKey() + ") / value:(" + mentry.getValue() + ") / init:" + myMap + "\n" );
                        }


                        if ((int) mentry.getValue() == myMap) {
                            if (s.getmTitle().contains( (CharSequence) mentry.getKey() )) {
                                songList_4_fragment.add( s );
                                Log.d( "DEBUG_S4HActFrag", "getSongs:column3:titre4RecyclerView:ajout:<" + s.getmTitle() + "> pour getKey: <" + mentry.getKey() + "> avec myMap: " + myMap + " / value:" + (int) mentry.getValue() );
                            }
                        }
                    } // fin boucle sur hmap
                }
            }
            while (c.moveToNext());
        }
        //c.close();
        return songList_4_fragment;
    }
}



