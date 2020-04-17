package myprojects.alzingrejm.com.songs4heaven_v2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class PastorsSongs extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        Log.d( "DEBUG_PastorsSongs:", "Entree onCreate" );
        tableau_Etapes.sauv("PastorsSongs: entree onCreate");
        setContentView( R.layout.pastors_songs );

        // pour l'image : photo du pasteur
        ImageView sword = (ImageView)findViewById( R.id.pastors_face );
        int imaqe = R.drawable.gerard_lamarche;
        String description = "Gérard Lamarche pasteur";
        sword.setImageResource( imaqe );
        sword.setContentDescription( description );

        Button button4 = (Button)findViewById(R.id.songs_of_pastors);
        button4.setEnabled(true);
    }

    public void his_songs(View view) {

        //Toast.makeText( getBaseContext(),
        //        "Les chants préférés de notre pasteur s'afficheront prochainement ici !", Toast.LENGTH_SHORT)
        //        .show();
        tableau_Etapes.sauv("PastorsSongs: entree his_songs");
        Intent intent = new Intent(this, PastorsSongsDetails.class);
        startActivity(intent);
    }
}
