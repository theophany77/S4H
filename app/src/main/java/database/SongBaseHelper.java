package database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import database.SongDbSchema.SongTable;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class SongBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "songBase.db";

    public SongBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DEBUG_SongBaseHelper","Entree onCreate");
        tableau_Etapes.sauv("SongBaseHelper: entree onCreate");


        db.execSQL("create table " + SongTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                SongTable.Cols.ID + ", " +
                SongTable.Cols.TITLE + ", " +
                SongTable.Cols.WORDS_FR + ", " +
                SongTable.Cols.WORDS_GB + ", " +
                SongTable.Cols.WORDS_SP + ", " +
                SongTable.Cols.CHORDS + ", " +
                SongTable.Cols.KIND + ", " +
                SongTable.Cols.SPEED + ", " +
                SongTable.Cols.AUTHOR + ", " +
                SongTable.Cols.MP3 + ", " +
                SongTable.Cols.MISC +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DEBUG_SongBaseHelper","Entree onUpgrade");

    }
}

