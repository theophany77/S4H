package myprojects.alzingrejm.com.songs4heaven_v2;

// cette classe est  destinée à mémoriser mes étapes de traitement à la fois pour le primo et la relance.
// depuis plusieurs mois, un clic sur le premier chant de ma liste n'affiche rien en primo mais affiche bien le contenu attendu en relance.
// cette classe esr destinée à mémoriser de manière plus facile que dans le Logcat les différentes étapes dans lesquelles mon
// programme passe afin, ensuite, de faire la différence entre primo et relance.

import android.util.Log;

import java.util.ArrayList;

public class MemoDebug {
    private static ArrayList<String> tabEtapes = new ArrayList<String>();

    public void  sauv(String etape) {
        //Log.d("DEBUG_MemoDebug", "entree public static sauv");

        if (tabEtapes == null) {
            tabEtapes.add("MemoDebug : entree sauv pour inititialisation");
        } else {
            tabEtapes.add(etape);
        }
    }

    public void vidage() {
        Log.d("DEBUG_MemoDebug", "entree public static print");
        tabEtapes.add("MemoDebug : entree vidage");
        // vidage du tableau

        int taille = tabEtapes.size();
        for (int i = 0; i < taille; i++) {
            Log.d("MEMODEBUG:",(tabEtapes.get(i)));
        }
    }
}