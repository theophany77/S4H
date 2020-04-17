package myprojects.alzingrejm.com.songs4heaven_v2;

/*
Cette classe est destinée à gérer un Singleton (design pattern).
Ainsi, je ne chargerai le fichier xml qu'une seule fois.
*/


import android.content.Context;
import android.util.Log;

import java.util.List;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class SingleLoadXml {
        private  static List<Song> mSong = null;

        // partie db ce 20190213
        //private Context mContext;
        //private SQLiteDatabase mDatabase;


        public static List<Song> getSongs(Context context) {
            Log.d("DEBUG_SingleLoadXml","Entree getSongs");
            tableau_Etapes.sauv("SingleLoadXml: entree getSongs");
            if ( mSong == null ) {
                Log.d("DEBUG_SingleLoadXml","mSong null : on va charger le xml.");
                tableau_Etapes.sauv("SingleLoadXml: on charge le xml");
                // je récupère les données XML
                SongXmlParser parser = new SongXmlParser();

                mSong = parser.parse(context);

            }
            else {
                Log.d("DEBUG_SingleLoadXml","mSong NOT NULL : le xml est déjà chargé.");
                tableau_Etapes.sauv("SingleLoadXml: xml déjà chargé");
            }

            // je vais trier le mSong sur le titre car il n y a pas toujours un ordre alphabetique


            return mSong;
        }

        //private SingleLoadXml(Context context){
        //    mContext = context.getApplicationContext();
            // query the database and return a cursor of employees.
            //SongDatabase songDatabase = new SongDatabase(mContext);

            //Cursor cursor = songDatabase.getAllSongsCursor();

            //mSong = new ArrayList<>(  );
        //}
}
