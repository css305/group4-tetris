package frontend;

/**
 * Simple Song Object holds song path, image path, and name.
 * @author Hariroop Singh.
 * @version 3/11/2023 Sprint 3.
 */
public class Song {
    /**
     * Holds Song File Path.
     */
    private final String myFilePath;
    /**
     * Holds Image File Path.
     */
    private final String myImageFilePath;
    /**
     * Holds Song Name.
     */
    private final String myName;

    /**
     * Basic Constructor takes in image path, song path, and song name.
     * @param theFilePath the song file path.
     * @param theImageFilePath the image path.
     * @param theName the name in String form.
     */
    public Song(final String theFilePath,
                final String theImageFilePath, final String theName) {
        //throw exception if passed parameters are null
        if (theFilePath == null || theImageFilePath == null || theName == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        //set values
        this.myFilePath = theFilePath;
        this.myImageFilePath = theImageFilePath;
        this.myName = theName;

    }

    /**
     * Simple Actuator method returns String representation of file path.
     * @return A String Representation of song file path.
     */
    public String getMyFilePath() {
        return myFilePath;
    }

    /**
     * Simple Actuator method returns String representation of image path.
     * @return A String Representation of image file path.
     */
    public String getMyImageFilePath() {
        return myImageFilePath;
    }
    /**
     * Simple Actuator method returns String representation of song name.
     * @return A String Representation of song name.
     */
    public String getMyName() {
        return myName;
    }

}
