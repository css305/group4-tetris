package frontend;

public class Song {

    private final String myFilePath;
    private final String myImageFilePath;
    private final String myName;

    public Song(final String theFilePath,
                final String theImageFilePath, final String theName) {

        this.myFilePath = theFilePath;
        this.myImageFilePath = theImageFilePath;
        this.myName = theName;

    }

    public String getMyFilePath() {
        return myFilePath;
    }

    public String getMyImageFilePath() {
        return myImageFilePath;
    }

}
