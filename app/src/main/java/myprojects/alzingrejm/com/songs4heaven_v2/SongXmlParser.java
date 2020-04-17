package myprojects.alzingrejm.com.songs4heaven_v2;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class SongXmlParser {

        // names of the XML tags
        private static final String SONGBOOK = "songbook";
        private static final String SONG = "song";
        private static final String ID = "id";
        private static final String TITLE = "title";
        private static final String WORDS_FR = "words_fr";
        private static final String WORDS_GB = "words_gb";
        private static final String WORDS_SP = "words_sp";
        private static final String CHORD = "chord";
        private static final String KIND = "kind";
        private static final String SPEED = "speed";
        private static final String AUTHOR = "author";
        private static final String MP3 = "mp3";
        private static final String MISC = "misc";

        // On crée un tableau d'objets de type Song (classe Song)
        private ArrayList<Song> songList = null;
        private Song currentSong = null;
        private boolean done = false;
        private String currentTag = null;

        public ArrayList<Song> parse(Context context) {
            Log.d("DEBUG_SongXmlParser","Entree parse");
            tableau_Etapes.sauv("SongXmlParser: entree parse");

            // le fichier xml n'est plus sous app/assets mais désormais sous app/res/xml
            // on y accède ainsi :

            XmlPullParser parser = context.getResources().getXml( R.xml.songs4heaven );

            try {

                int eventType = parser.getEventType();

                // Following logic modified from http://www.ibm.com/developerworks/library/x-android/
                // Also look at http://developer.android.com/training/basics/network-ops/xml.html

                while (eventType != XmlPullParser.END_DOCUMENT && !done) {

                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            songList = new ArrayList<Song>();
                            break;
                        case XmlPullParser.START_TAG:
                            currentTag = parser.getName();
                            if (currentTag.equalsIgnoreCase(SONG)) {
                                currentSong = new Song();
                            } else if (currentSong != null) {
                                if (currentTag.equalsIgnoreCase(ID)) {
                                    //Log.d("DEBUG_SongXmlParser:","switch:ID:"+currentSong.getmId());
                                    currentSong.setmId( Integer.parseInt(parser.nextText()));
                                    // currentEmployee.setId(parser.nextText());
                                } else if (currentTag.equalsIgnoreCase(TITLE)) {
                                    //Log.d("DEBUG_SongXmlParser:","switch:TITLE:"+currentSong.getmTitle());
                                    currentSong.setmTitle(parser.nextText());
                                } else if (currentTag.equalsIgnoreCase(WORDS_FR)) {
                                    //Log.d("DEBUG_SongXmlParser:","switch:WORDS_FR:"+currentSong.getmWordsFr());
                                    currentSong.setmWordsFr(parser.nextText());
                                } else if (currentTag.equalsIgnoreCase(WORDS_GB)) {
                                    //Log.d("DEBUG_SongXmlParser:","switch:WORDS_GB:"+currentSong.getmWordsGb());
                                    currentSong.setmWordsGb(parser.nextText());
                                } else if (currentTag.equalsIgnoreCase(WORDS_SP)) {
                                    //Log.d("DEBUG_SongXmlParser:","switch:WORDS_SP:"+currentSong.getmWordsSp());
                                    currentSong.setmWordsSp(parser.nextText());
                                } else if (currentTag.equalsIgnoreCase(CHORD)) {
                                    //Log.d("DEBUG_SongXmlParser:","switch:CHORDS:"+currentSong.getmChords());
                                    currentSong.setmChords(parser.nextText());
                                } else if (currentTag.equalsIgnoreCase(KIND)) {
                                    //Log.d("DEBUG_SongXmlParser:","switch:KIND:"+currentSong.getmKind());
                                    currentSong.setmKind(parser.nextText());
                                } else if (currentTag.equalsIgnoreCase(SPEED)) {
                                    //Log.d("DEBUG_SongXmlParser:","switch:SPEED:"+currentSong.getmSpeed());
                                    currentSong.setmSpeed(parser.nextText());
                                } else if (currentTag.equalsIgnoreCase(AUTHOR)) {
                                    //Log.d("DEBUG_SongXmlParser:","switch:AUTHOR:"+currentSong.getmAuthor());
                                    currentSong.setmAuthor(parser.nextText());
                                } else if (currentTag.equalsIgnoreCase(MP3)) {
                                    //Log.d("DEBUG_SongXmlParser:","switch:MP3:"+currentSong.getmMp3());
                                    currentSong.setmMp3(parser.nextText());
                                } else if (currentTag.equalsIgnoreCase(MISC)) {
                                    //Log.d("DEBUG_SongXmlParser:","switch:MISC:"+currentSong.getmMisc());
                                    currentSong.setmMisc(parser.nextText());
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            currentTag = parser.getName();
                            if (currentTag.equalsIgnoreCase(SONG) && currentSong != null) {
                                songList.add(currentSong);
                            } else if (currentTag.equalsIgnoreCase(SONGBOOK)) {
                                done = true;
                            }
                            break;
                    }
                    eventType = parser.next();
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("DEBUG_SongXmlParser","avant return");
            return songList;

        }

}
