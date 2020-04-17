package myprojects.alzingrejm.com.songs4heaven_v2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import database.SongBaseHelper;
import database.SongCursorWrapper;
import database.SongDbSchema.SongTable;

import static database.SongDatabase.KIND;
import static database.SongDatabase.MISC;
import static database.SongDatabase.MP3;
import static database.SongDatabase.SPEED;
import static database.SongDbSchema.SongTable.Cols.AUTHOR;
import static database.SongDbSchema.SongTable.Cols.CHORDS;
import static database.SongDbSchema.SongTable.Cols.ID;
import static database.SongDbSchema.SongTable.Cols.TITLE;
import static database.SongDbSchema.SongTable.Cols.WORDS_FR;
import static database.SongDbSchema.SongTable.Cols.WORDS_GB;
import static database.SongDbSchema.SongTable.Cols.WORDS_SP;
import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class SongLab {
    private static SongLab sSongLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static SongLab get(Context context) {
        Log.d("DEBUG_SongLab","entree public static SongLab");
        tableau_Etapes.sauv("SongLab: entree Songlag get(Context)");
        if (sSongLab == null) {
            Log.d("DEBUG_SongLab","sSongLab == null");
            sSongLab = new SongLab(context);
        }
        else{
            Log.d("DEBUG_SongLab","sSongLab NOT null");
        }

        return sSongLab;
    }

    private SongLab(Context context) {
        Log.d("DEBUG_SongLab","entree private SongLab");
        tableau_Etapes.sauv("SongLab: entree SongLab(Context)");

        mContext = context.getApplicationContext();
        mDatabase = new SongBaseHelper(mContext)
                .getReadableDatabase();

    }

    public void addSong(Song c) {
        Log.d("DEBUG_SongLab","entree addSong");
        tableau_Etapes.sauv("SongLab: entree addSong");
        ContentValues values = getContentValues(c);
        mDatabase.insert(SongTable.NAME, null, values);
    }

    public List<Song> getSongs() {
        Log.d("DEBUG_SongLab", "entree getSongs()");
        tableau_Etapes.sauv("SongLab: entree getSongs");
        List<Song> songs = new ArrayList<>();

        SongCursorWrapper cursor = querySongs(null, null);

        try {
                        cursor.moveToFirst();

                        int i = 0;

                        try {
                            while (cursor.moveToNext()) {
                                i += 1;
                            }

                            // vidage infos de debug
                            tableau_Etapes.vidage();;

                            Log.d("DEBUG_SongLab", "getSongs avec table SONGS existante comportant (cursor + moveToNext)  :" + i + "ligne(s)");

                            // 20200123
                            //      primo : 0 ligne
                            //      relance : 387 lignes

                        } finally {
                            //cursor.close();
                        }

        } finally {
            //cursor.close();
        }


        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                songs.add(cursor.getSong());
                cursor.moveToNext();
            }

        } finally {
            cursor.close();
        }
        return songs;
    }

    public Song getSong(int id) {
        Log.d("DEBUG_SongLab","entree getSong(int id)");
        tableau_Etapes.sauv("SongLab: entree getSong");

            // ne retourne rien
/*        SongCursorWrapper cursor = querySongs(
                SongTable.Cols.ID + " = ?",
                new String[]{String.valueOf( id )}
        );*/

        // idem !!!!!!!!!!
        SongCursorWrapper cursor = querySongs(null, null);

        Log.d("DEBUG_SongLab","ID:"+id);

        Log.d("DEBUG_SongLab","getSong(int):cursor:"+cursor.getColumnCount());
        Log.d("DEBUG_SongLab","getSong(int):cursor:"+cursor.getCount());

        try {
            if (cursor.getCount() == 0) {
                Log.d("DEBUG_SongLab","getSong(int):dans le try avec == 0");
                return null;
            }
            Log.d("DEBUG_SongLab","getSong(int):dans le try avec <> 0");
            cursor.moveToFirst();
            return cursor.getSong();
        } finally {
            cursor.close();
        }
    }

    public void updateSong(Song song) {
        Log.d("DEBUG_SongLab","entree updateSong");
        tableau_Etapes.sauv("SongLab: entree updateSong");
        int id = song.getmId();
        ContentValues values = getContentValues(song);
        mDatabase.update(SongTable.NAME, values,
                SongTable.Cols.ID + " = ?",
                new String[]{ID.toString()});
    }

    private SongCursorWrapper querySongs(String whereClause, String[] whereArgs) {
        Log.d("DEBUG_SongLab","entree querySongs");
        tableau_Etapes.sauv("SongLab: entree SongCursorWrapper");
        Log.d("DEBUG_SongLab","querySongs avec mDatabase:"+mDatabase.toString());

        // ma table existe  bien ?
        int count;
        int i=0;

        String monSql="select count(*)  from sqlite_master where type='table'  and name='"+SongTable.NAME+"'";

        Log.d("DEBUG_SongLab", "querySongs avec contrôle dans sqlite_master pour monSql :" + monSql);

        Cursor cursor_v = mDatabase.rawQuery(monSql, null);

        try{
            if (cursor_v != null) {
                Log.d("DEBUG_SongLab", "querySongs avec contrôle dans sqlite_master pour cursor_v NOT NULL");

                while (cursor_v.moveToNext()) {
                    i += 1;
                }

                Log.d("DEBUG_SongLab", "querySongs avec contrôle dans sqlite_master pour cursor_v  :" + i + "ligne(s)");

            }
        } finally {
            cursor_v.close();
        }

        i=0;

        // nb de lignes de la table ?
        String strCount = "";
        Cursor cursor3 = mDatabase.rawQuery("select count(*)  from "+SongTable.NAME+"", null);

        try{
        if (cursor3 != null) {
            Log.d("DEBUG_SongLab", "querySongs dans cursor != null");
            cursor3.moveToFirst();
            //strCount = cursor3.getString(cursor3.getColumnIndex("COUNT(*)"));
            //count = cursor3.getColumnCount();

            //count = cursor3.getInt(0);

            count = cursor3.getCount();

            //count = cursor3.getInt(0);
            //count = Integer.valueOf(strCount).intValue();
            Log.d("DEBUG_SongLab", "querySongs avec table SONGS existante comportant (select count(*) ):" + count + "ligne(s)");

            while (cursor3.moveToNext()) {
                i += 1;
            }

            Log.d("DEBUG_SongLab", "querySongs avec table SONGS existante comportant (cursor3 + moveToNext)  :" + i + "ligne(s)");

            // ma table existe  bien ?


            // nb de lignes de la table ?
            Cursor cursor4 = mDatabase.rawQuery("select *  from "+SongTable.NAME+"", null);

            try{
                if (cursor4 != null) {
                    Log.d("DEBUG_SongLab", "querySongs dans cursor != null");
                    //cursor4.moveToFirst();
                    //strCount = cursor3.getString(cursor3.getColumnIndex("COUNT(*)"));
                    //count = cursor4.getColumnCount();

                    // CursorOutOfBoundsException
                   // count = cursor4.getInt(0);

                    ///count = cursor4.getCount();

                    //count = cursor3.getInt(0);
                    //count = Integer.valueOf(strCount).intValue();
                    //Log.d("DEBUG_SongLab", "querySongs avec table SONGS existante comportant (select * from ):" + count + "ligne(s)");

                    i = 0;

                    while (cursor4.moveToNext()) {
                        i += 1;
                    }

                    Log.d("DEBUG_SongLab", "querySongs avec table SONGS existante comportant (cursor4 + moveToNext)  :" + i + "ligne(s)");

                }
            } finally {
                cursor4.close();

                // 20200224
                // pour une raison qui m'échappe, en primo, j'ai toujours 0 ici (contre 387 en relance).. Je vais donc appeler l'insertion en table pour voir.,
                //  soitSongDatabase.seedData
                if ( i == 0) {
                    // pas possible car non public ...
                    //SQLiteDatabase db  = new SQLiteDatabase();
                    // Il me faut à tout prix comprendre pourquoi l'appel à seedData dans SongDatabase.onCreate ne charge rien en primo.

                }
            }

        }
        } finally {
            cursor3.close();
        }

        Cursor cursor = mDatabase.query(
                SongTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy1
                null, // having
                null  // orderBy
        );

        i = 0;

        while (cursor.moveToNext()) {
            i += 1;
        }

        Log.d("DEBUG_SongLab", "mDatabase.query avec " + i + " valeur(s)");

        cursor.moveToFirst();

        // 20200130
        //  je vais compter les enregistrements distintcs dans ma table
        Cursor res = mDatabase.rawQuery("select (count(distinct title)) as countRecords from " + SongTable.NAME, null);
        try{
            if (res != null) {
                res.moveToFirst();
                while (res.isAfterLast() == false) {
                    if ((res != null) && (res.getCount() > 0)) {
                        //array_list.add(res.getString(res.getColumnIndex("countRecords")));
                        Log.d("DEBUG_SongLab", "querySongs avec table SONGS : comptage éléments distincts getCount > 0 :" + res.getString(res.getColumnIndex("countRecords")) + "ligne(s)");

                        res.moveToNext();
                    }
                    else {
                        Log.d("DEBUG_SongLab", "querySongs avec table SONGS : comptage éléments distincts :" + res.getString(res.getColumnIndex("countRecords")) + "ligne(s)");
                    }
                }
            }
            else {
                Log.d("DEBUG_SongLab", "querySongs avec table SONGS : comptage éléments distincts : res est à NULL");
            }

        } finally {
            res.close();
        }

        return new SongCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Song song) {
        Log.d("DEBUG_SongLab","entree ContentValues");
        tableau_Etapes.sauv("SongLab: entree getContentValues");
        ContentValues values = new ContentValues();
        values.put(ID, song.getmId());
        values.put(TITLE, song.getmTitle());
        values.put(WORDS_FR, song.getmWordsFr());
        values.put(WORDS_GB, song.getmWordsGb());
        values.put(WORDS_SP, song.getmWordsSp());
        values.put(CHORDS, song.getmChords());
        values.put(KIND, song.getmKind());
        values.put(SPEED, song.getmSpeed());
        values.put(AUTHOR, song.getmAuthor());
        values.put(MP3, song.getmMp3());
        values.put(MISC, song.getmMisc());

        return values;
    }


}
