package database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import myprojects.alzingrejm.com.songs4heaven_v2.Song;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class SongCursorWrapper extends CursorWrapper {

    public SongCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Song getSong() {
        //Log.d("DEBUG_SongCursorWrapper","entree public Song getSong");
        tableau_Etapes.sauv("SongCursorWrapper: entree getSong");
        int id = getInt(getColumnIndex(SongDbSchema.SongTable.Cols.ID));

        if ( id == 1  || id == 394 ) {
            Log.d("DEBUG_SongCursorWrapper", "getSong avec id:" + id);
        }
        String title = getString(getColumnIndex(SongDbSchema.SongTable.Cols.TITLE));
        //Log.d("DEBUG_SongCursWrap:","getSong():TITLE:"+title);
        String words_fr = getString(getColumnIndex(SongDbSchema.SongTable.Cols.WORDS_FR));
        String words_gb = getString(getColumnIndex(SongDbSchema.SongTable.Cols.WORDS_GB));
        String words_sp = getString(getColumnIndex(SongDbSchema.SongTable.Cols.WORDS_SP));
        //Log.d("DEBUG_SongCursWrap:","getSong():WORDS_SP:"+words_sp+":");
        String chords = getString(getColumnIndex(SongDbSchema.SongTable.Cols.CHORDS));
        //Log.d("DEBUG_SongCursWrap:","getSong():CHORDS:"+chords);
        String kind = getString(getColumnIndex(SongDbSchema.SongTable.Cols.KIND));
        String speed = getString(getColumnIndex(SongDbSchema.SongTable.Cols.SPEED));
        String author = getString(getColumnIndex(SongDbSchema.SongTable.Cols.AUTHOR));
        String mp3 = getString(getColumnIndex(SongDbSchema.SongTable.Cols.MP3));
        //Log.d("DEBUG_SongCursWrap:","getSong():MP3:"+mp3);

        // Souci ce 20190302 - je vais initialiser à vide
        //String misc = getString(getColumnIndex(SongDbSchema.SongTable.Cols.MISC));

        Song song = new Song(id);
        song.setmTitle(title);
        song.setmWordsFr(words_fr);
        song.setmWordsGb(words_gb);
        song.setmWordsSp(words_sp);
        song.setmChords(chords);
        song.setmKind(kind);
        song.setmSpeed(speed);
        song.setmAuthor(author);
        song.setmMp3(mp3);
        //song.setmMisc(misc);
        song.setmMisc("");

        if ( song.getmTitle().contains("A Celui qui est") ||   song.getmTitle().contains("Voici le jour")) {
            Log.d("DEBUG_SongCursorWrapper", "avant return song avec titre song : " + song.getmTitle());
        }

        return song;
    }
}
