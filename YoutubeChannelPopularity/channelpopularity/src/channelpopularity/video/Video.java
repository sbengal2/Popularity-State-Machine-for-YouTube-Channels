package channelpopularity.video;

/**
 * Video class stores attributes for each video..
 *
 * @author Shashank Bengaluru Srinivasa.
 */

public class Video {
    private String videoName;
    private int views=0;
    private int likes=0;
    private int dislikes=0;
    private int adLength =0;
    private int popularityScore;

    /**
     * toString method that aides in debugging.
     *
     * @return String
     */
    @Override
    public String toString(){
        return "Holds attributes of a video.(could consider the instance of this class as an actual video).";
    }


    /**
     * method to set the name of a video.
     * @param videoNameIn name of the video.
     */
    public void setVideoName(String videoNameIn){
        videoName = videoNameIn;

    }

    /**
     * method to set the number of views for a video;
     * @param viewsIN number of views for a video.
     */
    public void setViews(int viewsIN){
        views += viewsIN;

    }

    /**
     * method to set the number of likes for a video.
     * @param likesIn number of likes for a video.
     */
    public void setLikes(int likesIn){
        likes += likesIn;

    }

    /**
     * method to set the number of dislikes for the video.
     * @param dislikesIn number of dislikes for the video.
     */
    public void setDislikes(int dislikesIn){
        dislikes += dislikesIn;

    }

    /**
     * method to get number of likes for a video.
     * @return integer
     */
    public int getLikes(){
        return likes;

    }

    /**
     * method to get the number of dislikes for a video.
     * @return integer
     */
    public int getDislikes(){
        return dislikes;

    }

    /**
     * method to set length of an Ad applied to a video.
     * @param adLengthIn length of the Ad
     */
    public void setAdlength(int adLengthIn){
        adLength = adLengthIn;
    }

    /**
     * method to set popularity score of a video.
     * @param popularityScoreIn popularity score of the video.
     */
    public void setPopularityScore(int popularityScoreIn){
        popularityScore = popularityScoreIn;

    }

    /**
     * method to get popularity score of a video.
     * @return integer
     */
    public int getPopularityScore(){
        return popularityScore;
    }

}
