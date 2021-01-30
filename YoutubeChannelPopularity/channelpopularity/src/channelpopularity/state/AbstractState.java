package channelpopularity.state;

import channelpopularity.myException.InvalidInputException;
import channelpopularity.util.FileDisplayInterface;
import channelpopularity.util.StdoutDisplayInterface;
import channelpopularity.video.Video;

import java.text.DecimalFormat;
import java.util.Map;


/**
 * Abstract state holds the methods that are common to all the states.
 *
 * @author Shashank Bengaluru Srinivasa
 */
public abstract class AbstractState implements StateI {

    /**
     * toString method that aides in debugging.
     *
     * @return String
     */
    @Override
    public String toString(){
        return "implements the common code for all the states ";
    }

    /**
     * checks if video is present in the channel.
     *
     * @param videoNameIn name of the video.
     * @param videoDataBaseIn map that holds all the videos.
     * @return boolean
     */
    public Boolean isVideoPresentInChannel(String videoNameIn, Map<String,Video>videoDataBaseIn){
        return videoDataBaseIn.containsKey(videoNameIn);
    }

    /**
     * checks if the video is added successfully.
     *
     * @param videoNameIn name of the video.
     * @param videoDataBaseIn map that holds all the videos.
     * @return boolean
     */
    public Boolean isVideoAddedToChannel(String videoNameIn, Map<String, Video> videoDataBaseIn) {
        if (!isVideoPresentInChannel(videoNameIn,videoDataBaseIn)) {
            Video video = new Video();
            video.setVideoName(videoNameIn);
            video.setPopularityScore(0);
            videoDataBaseIn.put(videoNameIn, video);
            return true;
        } else
            return false;
    }

    /**
     * checks if the video is removed successfully.
     *
     * @param videoNameIn name of the video.
     * @param videoDataBaseIn map that holds all the videos.
     * @return boolean
     */
    public Boolean isVideoRemovedFromChannel(String videoNameIn, Map<String, Video> videoDataBaseIn) {
        if (isVideoPresentInChannel(videoNameIn,videoDataBaseIn)) {
            videoDataBaseIn.remove(videoNameIn);
            return true;
        } else
            return false;
    }

    /**
     * checks if the video metrics are updated successfully.
     *
     * @param videoNameIn name of the video.
     * @param videoDataBaseIn map that holds all the videos.
     * @param viewsIn views for the video.
     * @param likesIn likes for the video
     * @param dislikesIn dislikes for the video.
     * @return boolean
     */
    public Boolean isVideoMetricsUpdated(String videoNameIn, Map<String, Video> videoDataBaseIn, int viewsIn, int likesIn, int dislikesIn) {
        if (isVideoPresentInChannel(videoNameIn,videoDataBaseIn)) {
            if(viewsIn < (likesIn + dislikesIn))
                throw new InvalidInputException("invalid input: Numbers of views cannot be lesser than the sum of likes and dislikes");
            Video temp = videoDataBaseIn.get(videoNameIn);
            temp.setVideoName(videoNameIn);
            temp.setViews(viewsIn);
            if (likesIn < 0) {
                if ((Math.abs(likesIn) > temp.getLikes()))
                    throw new InvalidInputException("invalid input: Decrease in number of likes more than the present number of likes");
            }
            temp.setLikes(likesIn);
            if (dislikesIn < 0) {
                if ((Math.abs(dislikesIn) > temp.getDislikes()))
                    throw new InvalidInputException("invalid input: Decrease in number of dislikes more than the present number of dislikes");
            }
            temp.setDislikes(dislikesIn);
            temp.setPopularityScore(calculateVideoPopularityScore(temp.getPopularityScore(),viewsIn,likesIn,dislikesIn));
            return true;
        } else return false;
    }

    /**
     * checks if the Ad for the video is approved or not.
     *
     * @param stateNameIn name of the state.
     * @param lengthOfAdIn length of the Ad.
     * @param videoDataBaseIn map that holds all the videos.
     * @param videoNameIn name of the video.
     * @return boolean
     */
    public Boolean isAdApproved(StateName stateNameIn, int lengthOfAdIn,Map<String,Video>videoDataBaseIn,String videoNameIn) {
        Video video = videoDataBaseIn.get(videoNameIn);
        switch (stateNameIn) {
            case UNPOPULAR:
                if (lengthOfAdIn > 1 && lengthOfAdIn <= 10) {
                    video.setAdlength(lengthOfAdIn);
                    return true;
                }
                break;
            case MILDLY_POPULAR:
                if (lengthOfAdIn > 1 && lengthOfAdIn <= 20) {
                    video.setAdlength(lengthOfAdIn);
                    return true;
                }
                break;
            case HIGHLY_POPULAR:
                if (lengthOfAdIn > 1 && lengthOfAdIn <= 30) {
                    video.setAdlength(lengthOfAdIn);
                    return true;
                }
                break;
            case ULTRA_POPULAR:
                if (lengthOfAdIn > 1 && lengthOfAdIn <= 40) {
                    video.setAdlength(lengthOfAdIn);
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * calculates the popularity state of the channel.
     *
     * @param videoDataBase map that holds all the videos.
     * @return double
     */
    public double calculateChannelPopularityScore(Map<String, Video> videoDataBase) {
        DecimalFormat decimalFormat = new DecimalFormat("#.0#");
        double totalChannelPopularityScore = 0;
        for (String videoName : videoDataBase.keySet()) {
            Video video = videoDataBase.get(videoName);
            totalChannelPopularityScore += video.getPopularityScore();
        }
        if (totalChannelPopularityScore <= 0)
            return 0;
        return Double.parseDouble(decimalFormat.format(totalChannelPopularityScore / videoDataBase.size()));
    }

    /**
     * calculates the popularity score of a video.
     *
     * @param currentPopularityScore current popularity score of the video.
     * @param viewsIn views for the video.
     * @param likesIn likes for the video.
     * @param dislikesIn dislikes for the video.
     * @return integer
     */
    public int calculateVideoPopularityScore(int currentPopularityScore,int viewsIn,int likesIn,int dislikesIn){
        int popularityScore = viewsIn + (2*(likesIn - dislikesIn));
        popularityScore = currentPopularityScore + popularityScore;
        if(popularityScore < 0){
            popularityScore = 0;
        }
        return popularityScore;
    }

    /**
     * updates the channel state.
     *
     * @param popularityScoreIn popularity score of the channel.
     * @return StateName.
     */
    public StateName getUpdatedChannelPopularityState(double popularityScoreIn) {
        if (popularityScoreIn >= 0 && popularityScoreIn <= 1000) {
            return StateName.UNPOPULAR;
        } else if (popularityScoreIn > 1000 && popularityScoreIn <= 10000) {
            return StateName.MILDLY_POPULAR;
        } else if (popularityScoreIn > 10000 && popularityScoreIn <= 100000) {
            return StateName.HIGHLY_POPULAR;
        } else if (popularityScoreIn > 100000 && popularityScoreIn <= Integer.MAX_VALUE) {
            return StateName.ULTRA_POPULAR;
        }
        return null;
    }

    /**
     * method that persists each output.
     *
     * @param fileDisplayInterfaceIn instance of the interface to output to a file.
     * @param stdoutDisplayInterfaceIn instance of the interface to output to the console.
     * @param outputIn output generated.
     */
    public void storeOutput(FileDisplayInterface fileDisplayInterfaceIn, StdoutDisplayInterface stdoutDisplayInterfaceIn, String outputIn) {
        fileDisplayInterfaceIn.Store(outputIn);
        stdoutDisplayInterfaceIn.Store(outputIn);
    }

}
