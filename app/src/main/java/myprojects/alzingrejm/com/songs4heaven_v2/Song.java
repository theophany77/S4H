package myprojects.alzingrejm.com.songs4heaven_v2;

import android.util.Log;

import java.io.Serializable;

import static myprojects.alzingrejm.com.songs4heaven_v2.WelcomeActivity.tableau_Etapes;

public class Song implements Serializable
{
    //  A partir du fichier xml

    private static final String TAG="DEBUG_Song";

    /*
        <SONG>
        <ID>2</ID>
        <TITLE>A jamais tu seras</TITLE>
        <WORDS_FR>A_jamais_tu_seras, l\'Agneau sur le trône. Je fléchis le genou, pour T\'adorer Toi seul</WORDS>
        <WORDS_GB>Forever you will be ...</WORDS>
        <WORDS_FSP></WORDS>
        <CHORD>D</CHORD>
        <KIND>D</KIND>
        <SPEED>D</SPEED>
        <AUTHOR>unknown</AUTHOR>
        <MP3>D</MP3>
        <MISC>D</MISC>
        </SONG>
    */

    // scope des membres

    private int mId;
    private String mTitle;
    private String mWordsFr;
    private String mWordsGb;
    private String mWordsSp;
    private String mChords;
    private String mKind;
    private String mSpeed;
    private String mAuthor;
    private String mMp3;
    private String mMisc;

    // constructeur que j'appelle dans SongXmlParser
    public Song() {

        tableau_Etapes.sauv("Song: entree constructeur");

    }

    // JMA - 20190220. Il faut bien un ID au moins ...
    public Song(int i){
        this.mId = i;
    }

    public Song(int mId, String mTitle, String mWordsFr, String mWordsGb,String mWordsSp,String mChords, String mKind,String mSpeed,String mAuthor, String mMp3, String mMisc) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mWordsFr = mWordsFr;
        this.mWordsGb = mWordsGb;
        this.mWordsSp = mWordsSp;
        this.mChords = mChords;
        this.mKind = mKind;
        this.mSpeed = mSpeed;
        this.mAuthor = mAuthor;
        this.mMp3 = mMp3;
        this.mMisc = mMisc;
    }

    public int getmId() {
        return mId;
    }


    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmWordsFr() {
        return mWordsFr;
    }

    public void setmWordsFr(String mWordsFr) {
        this.mWordsFr = mWordsFr;
    }

    public String getmWordsGb() {
        return mWordsGb;
    }

    public void setmWordsGb(String mWordsGb) {
        this.mWordsGb = mWordsGb;
    }

    public String getmWordsSp() {
        return mWordsSp;
    }

    public void setmWordsSp(String mWordsSp) {
        this.mWordsSp = mWordsSp;
    }

    public String getmChords() {
        return mChords;
    }

    public void setmChords(String mChords) {
        this.mChords = mChords;
    }

    public String getmKind() {
        return mKind;
    }

    public void setmKind(String mKind) {
        this.mKind = mKind;
    }

    public String getmSpeed() {
        return mSpeed;
    }

    public void setmSpeed(String mSpeed) {
        this.mSpeed = mSpeed;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmMp3() {
        return mMp3;
    }

    public void setmMp3(String mMp3) {
        this.mMp3 = mMp3;
    }

    public String getmMisc() {
        return mMisc;
    }

    public void setmMisc(String mMisc) {
        this.mMisc = mMisc;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public static final void main(String[] args){
        Song s1 = new Song(1, "Mon titre 1", "Les paroles de mon titre 1", "GB", "SP","Accords de mon titre 1","Kind", "Speed", "Auteur de mon titre 1","MP3", "Misc");
        Log.d(TAG, "Chant test :"+s1.getmId()+"\n"+s1.mTitle+"\n"+s1.mWordsFr+"\n/"+s1.mWordsGb+"\n"+s1.mWordsSp
                +s1.mChords+"\n"
                +s1.mKind+"\n"
                +s1.mSpeed+"\n"
                +s1.mAuthor+"\n"
                +s1.mMisc+"\n"
        );
    }

}



