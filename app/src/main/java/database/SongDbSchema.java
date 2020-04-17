package database;

public class SongDbSchema {
    public static final class SongTable {
        public static final String NAME = "songs";

        public static final class Cols {
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
            public static final String MISC = "misc";
        }

    }
}
