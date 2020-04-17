package database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import database.SongDbSchema;
import myprojects.alzingrejm.com.songs4heaven_v2.Song;
import myprojects.alzingrejm.com.songs4heaven_v2.SongXmlParser;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class SongDatabase extends SQLiteOpenHelper {

        private Context context;
        ArrayList<Song> songList = new ArrayList<Song>();

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "songBase.db";
        public static final String TABLE_SONGS = "songs";
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String WORDS_FR = "words_fr";
        public static final String WORDS_GB = "words_gb";
        public static final String WORDS_SP = "words_sp";
        public static final String CHORDS = "chords";
        public static final String KIND = "kind";
        public static final String SPEED = "speed";
        public static final String AUTHOR = "author";
        public static final String MP3 = "mp3";
        public static final String MISC = "MISC";

    private SQLiteDatabase mDatabase;

    private static final String CREATE_TABLE_SONGS = "CREATE TABLE " + TABLE_SONGS + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " TEXT NOT NULL, "
                + WORDS_FR + " TEXT NOT NULL, "
                + WORDS_GB + " TEXT NOT NULL, "
                + WORDS_SP + " TEXT NOT NULL, "
                + CHORDS + " TEXT NOT NULL, "
                + KIND + " TEXT NOT NULL, "
                + SPEED + " TEXT NOT NULL, "
                + MP3 + " TEXT NOT NULL, "
                + AUTHOR + " TEXT NOT NULL, "
                + MISC + " TEXT NOT NULL);";

        public SongDatabase(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;


            // je supprime systématiquement la db
            context.deleteDatabase(DATABASE_NAME);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Called when the database is created for the first time.
            // This is where the creation of tables and the initial population of the tables happens.
            Log.i("DEBUG_SongDatabase","Avant DROP TABLE");
            tableau_Etapes.sauv("SongDatabase: entree onCreate");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
            db.execSQL(CREATE_TABLE_SONGS);
            Log.i("DEBUG_SongDatabase","Avant seedData");
            seedData(db);
            Log.i("DEBUG_SongDatabase","Après seedData");
            checkData( db );
            Log.i("DEBUG_SongDatabase","Après checkData");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            tableau_Etapes.sauv("SongDatabase: entree onUpgrade");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
            onCreate(db);
        }

        public Cursor getAllSongsCursor() {

            Log.i("DEBUG_SongDatabase","Entree getAllSongsCursor");
            tableau_Etapes.sauv("SongDatabase: entree getAllSongsCursor");

            SQLiteDatabase db = this.getReadableDatabase();

            Log.i("DEBUG_SongDatabase","getAllSongsCursor avec TITLE:("+TITLE+")");

            Cursor cursor = db.query(TABLE_SONGS, new String[] { ID, TITLE, WORDS_FR, WORDS_GB, WORDS_SP, CHORDS, KIND, SPEED, AUTHOR, MP3, MISC}, null, null, null, null, TITLE );

            if (cursor != null) {
                cursor.moveToFirst();
            }

            Log.d("DEBUG_SongDatabase","getAllSongsCursor:avant checkData");
            checkData( db );
            Log.d("DEBUG_SongDatabase","getAllSongsCursor:après checkData");
            //checkData2( cursor );
            //Log.d("DEBUG_SongDatabase","getAllSongsCursor:après checkData2");

            if (cursor != null) {
                cursor.moveToFirst();
            }

            // cursor.close();
            db.close();

            Log.d("DEBUG_SongDatabase","getAllSongsCursor:taille cursor:"+cursor.getCount());

            return cursor;
        }

        // ajout JMA ce 20190223 pour obtenir un chant seulement grâce à son ID
        public Cursor getOneSongCursor(int songId) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SONGS, new String[] { ID, TITLE, WORDS_FR, WORDS_GB, WORDS_SP, CHORDS, KIND, SPEED, AUTHOR, MP3, MISC}, null, null, null, null, TITLE );

        // on a toutes les lignes. On va maintenant filtrer sur le ID souhaité.
        if (cursor != null) {
            cursor.moveToFirst();
            do {
                // Passing values
                Integer column1 = cursor.getInt(0);
                String column2 = cursor.getString(1);
                String column3 = cursor.getString(2);
                String column4 = cursor.getString(3);
                String column5 = cursor.getString(4);
                String column6 = cursor.getString(5);
                String column7 = cursor.getString(6);
                String column8 = cursor.getString(7);
                String column9 = cursor.getString(8);
                String column10 = cursor.getString(9);
                String column11= cursor.getString(10);
                // Do something Here with values
/*                Log.d("DEBUG_SongDatabase","getOneSongCursor:column1:"+column1);
                Log.d("DEBUG_SongDatabase","getOneSongCursor:column2:"+column2);
                Log.d("DEBUG_SongDatabase","getOneSongCursor:column3:"+column3);
                Log.d("DEBUG_SongDatabase","getOneSongCursor:column4:"+column4);
                Log.d("DEBUG_SongDatabase","getOneSongCursor:column5:"+column5);
                Log.d("DEBUG_SongDatabase","getOneSongCursor:column6:"+column6);
                Log.d("DEBUG_SongDatabase","getOneSongCursor:column7:"+column7);
                Log.d("DEBUG_SongDatabase","getOneSongCursor:column8:"+column8);
                Log.d("DEBUG_SongDatabase","getOneSongCursor:column9:"+column9);
                Log.d("DEBUG_SongDatabase","getOneSongCursor:column10:"+column10);
                Log.d("DEBUG_SongDatabase","getOneSongCursor:column11"+column11);*/
            } while(cursor.moveToNext());
        }

        //checkData2( cursor );
        //Log.i("DEBUG_SongDatabase","getAllSongsCursor:après checkData2");

        if (cursor != null) {
            cursor.moveToFirst();
        }

        // cursor.close();
        db.close();

        return cursor;
        }

        private void seedData(SQLiteDatabase db) {

            // appel du singleton pour charger le fichier res/xml/songs4heaven.xml dans la database
            Log.i("DEBUG_SongDatabase","Entree seedData");
            tableau_Etapes.sauv("SongDatabase: entree seedData");
            SongXmlParser parser = new SongXmlParser();
            songList = parser.parse(context);

            Log.i("DEBUG_SongDatabase","seedData avec songList:"+songList.size());

            // insère chaque chant dans la bdd
            for (Song song : songList) {
/*
                Log.i("DEBUG_S4HDatabase","Dans seedData avec COLUMN_ID:"+ String.valueOf(song.getmId())); */
                if ( song.getmTitle().contains("A Celui qui est") ||   song.getmTitle().contains("Voici le jour")) {
                    Log.d("DEBUG_SongDatabase","checkData:column2:"+song.getmTitle());
                    /*Log.d("DEBUG_SongDatabase","checkData:column3:"+column3);*/
                }
               /*  Log.i("DEBUG_S4HDatabase","Dans seedData avec COLUMN_WORDS_FR:"+ String.valueOf(song.getmWordsFr()));
                Log.i("DEBUG_S4HDatabase","Dans seedData avec COLUMN_CHORDS:"+ String.valueOf(song.getmChords()));
                Log.i("DEBUG_S4HDatabase","Dans seedData avec COLUMN_AUTHOR:"+ String.valueOf(song.getmAuthor()));
*/
                if ( song.getmTitle().contains("A Celui qui est") ||   song.getmTitle().contains("Voici le jour")) {
                    Log.i("DEBUG_SongDatabase", "seedData avant insertion en table avec:" + ID + ":" + Integer.valueOf(song.getmId()) + TITLE + ":" + String.valueOf(song.getmTitle()));
                }
                db.execSQL("INSERT INTO songs ("
                        + ID + ", "
                        + TITLE + ", "
                        + WORDS_FR + ", "
                        + WORDS_GB + ", "
                        + WORDS_SP + ", "
                        + CHORDS + ", "
                        + KIND + ", "
                        + SPEED+ ", "
                        + AUTHOR+ ", "
                        + MP3+ ", "
                        + MISC+ ")"
                        + " values (\""
                        + Integer.valueOf(song.getmId())
                        + "\", \""
                        + String.valueOf(song.getmTitle())
                        + "\", \""
                        + String.valueOf(song.getmWordsFr())
                        + "\", \""
                        + String.valueOf(song.getmWordsGb())
                        + "\", \""
                        + String.valueOf(song.getmWordsSp())
                        + "\", \""
                        + String.valueOf(song.getmChords())
                        + "\", \""
                        + String.valueOf(song.getmKind())
                        + "\", \""
                        + String.valueOf(song.getmSpeed())
                        + "\", \""
                        + String.valueOf(song.getmAuthor())
                        + "\", \""
                        + String.valueOf(song.getmMp3())
                        + "\", \""
                        + String.valueOf(song.getmMisc())
                        + "\");");

                //db.close();
                // je vérifie
                int i=0;
                Cursor cursor4 = db.rawQuery("select *  from "+ SongDbSchema.SongTable.NAME+"", null);

                try{
                    if (cursor4 != null) {
                        //Log.d("DEBUG_SongLab", "seedData dans cursor != null");

                        i = 0;

                        while (cursor4.moveToNext()) {
                            i += 1;
                        }

                        if ( song.getmTitle().contains("A Celui qui est") ||   song.getmTitle().contains("Voici le jour")) {
                            Log.d("DEBUG_SongDatabase", "seedData avec table SONGS existante comportant (cursor4 + moveToNext)  :" + i + "ligne(s)");
                        }

                    }
                } finally {
                    if ( song.getmTitle().contains("A Celui qui est") ||   song.getmTitle().contains("Voici le jour")) {
                        Log.i("DEBUG_SongDatabase", "seedData - fin boucle insertion avec cursor4 NULL");
                    }
                    cursor4.close();
                }
            }

        }

        // JMA - 20190218
        public void checkData(SQLiteDatabase db){
            Log.i("DEBUG_SongDatabase","Entree checkData");
            tableau_Etapes.sauv("SongDatabase: entree checkData");
            Cursor c = db.rawQuery("SELECT * FROM  "+ SongDbSchema.SongTable.NAME, null);
            if (c.moveToFirst()){
                do {
                    // Passing values
                    Integer column1 = c.getInt(0);
                    String column2 = c.getString(1);
                    String column3 = c.getString(2);
                    // Do something Here with values
/*                    Log.d("DEBUG_SongDatabase","checkData:column1:"+column1); */
                    if ( column2.contains("A Celui qui est") ||   column2.contains("Voici le jour")) {
                        Log.d("DEBUG_SongDatabase","checkData:column2:"+column2);
                        /*Log.d("DEBUG_SongDatabase","checkData:column3:"+column3);*/
                    }

                } while(c.moveToNext());
            }
            //c.close();
        }

        // JMA - 20190218
        public void checkData2(Cursor c){
            tableau_Etapes.sauv("SongDatabase: entree checkData2");
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
/*                Log.d("DEBUG_SongDatabase","checkData2:column1:"+column1); */
                Log.d("DEBUG_SongDatabase","checkData2:column2:"+column2);
                /* Log.d("DEBUG_SongDatabase","checkData2:column3:"+column3);
                Log.d("DEBUG_SongDatabase","checkData2:column3:"+column4);
                Log.d("DEBUG_SongDatabase","checkData2:column3:"+column5);
                Log.d("DEBUG_SongDatabase","checkData2:column3:"+column6);
                Log.d("DEBUG_SongDatabase","checkData2:column3:"+column7);
                Log.d("DEBUG_SongDatabase","checkData2:column3:"+column8);
                Log.d("DEBUG_SongDatabase","checkData2:column3:"+column9);
                Log.d("DEBUG_SongDatabase","checkData2:column3:"+column10);
                Log.d("DEBUG_SongDatabase","checkData2:column3:"+column11);*/
            } while(c.moveToNext());
        }
        //c.close();
    }
}
