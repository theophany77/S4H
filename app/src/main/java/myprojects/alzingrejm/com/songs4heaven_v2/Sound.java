package myprojects.alzingrejm.com.songs4heaven_v2;

// je me base sur BNRG / Beatbox.
//  Cette classe va permettre d'associer un id à un chant, id nécessaire pour effectuer la lecture (play).

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundId;

    public Sound(String assetPath) {
        //tableau_Etapes.sauv("Sound: entree Sound(assetPath");
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];
        mName = filename.replace(".mp3", "");

    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName(){
        return mName;
    }

    public Integer getSoundId(){return mSoundId;    }

    public void setSoundId(Integer soundId){
        mSoundId= soundId;
    }
}
