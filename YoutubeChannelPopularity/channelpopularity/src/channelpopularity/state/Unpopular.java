package channelpopularity.state;

import channelpopularity.context.ContextI;
import channelpopularity.myException.InvalidInputException;
import channelpopularity.myException.VideoAlreadyExistsException;
import channelpopularity.myException.VideoNotFoundException;
import channelpopularity.operation.Operation;
import channelpopularity.util.FileDisplayInterface;
import channelpopularity.util.StdoutDisplayInterface;
import channelpopularity.video.Video;

import java.util.Map;

/**
 * Unpopular state performs the operations in a channel and switches to other states when needed.
 * popularity score range for the state - 0 <= popularity score <= 1000.
 *
 * @author Shashank Bengaluru Srinivasa .
 */
public class Unpopular extends AbstractState {
    private static final StateName stateName = StateName.UNPOPULAR;
    private final FileDisplayInterface fileDisplayInterface;
    private final StdoutDisplayInterface stdoutDisplayInterface;
    private final ContextI contextI;
    private final Map<String, Video> videoDataBase;
    private String output;

    /**
     * Constructor to initialise several fields.
     * @param fileDisplayInterfaceIn instance of the interface to output to a file.
     * @param stdoutDisplayInterfaceIn instance of the interface to output to the console.
     * @param contextIn instance of the context interface.
     */
    public Unpopular(FileDisplayInterface fileDisplayInterfaceIn, StdoutDisplayInterface stdoutDisplayInterfaceIn, ContextI contextIn) {
        fileDisplayInterface = fileDisplayInterfaceIn;
        stdoutDisplayInterface = stdoutDisplayInterfaceIn;
        contextI = contextIn;
        videoDataBase = contextIn.getVideoDataBase();
    }

    /**
     * toString method that aides in debugging.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "implements Unpopular state of the channel";
    }

    @Override
    public void addVideoToChannel(String videoNameIn) {
        if (isVideoAddedToChannel(videoNameIn,videoDataBase)) {
            output = stateName + "__" + Operation.VIDEO_ADDED + "::" + videoNameIn;
            storeOutput(fileDisplayInterface,stdoutDisplayInterface,output);
            contextI.setChannelPopularityScore(calculateChannelPopularityScore(videoDataBase));
            contextI.setChannelState(getUpdatedChannelPopularityState(calculateChannelPopularityScore(videoDataBase)));
        } else {
            throw new VideoAlreadyExistsException("Error Adding the Video: "+ '\"' + videoNameIn +'\"' + " already exists.");
        }
    }

    /**
     * method to remove video from a channel
     *
     * @param videoNameIn name of the video.
     */
    @Override
    public void removeVideoFromChannel(String videoNameIn) {
        if (isVideoRemovedFromChannel(videoNameIn,videoDataBase)) {
            output = stateName + "__" + Operation.VIDEO_REMOVED + "::" + videoNameIn;
            storeOutput(fileDisplayInterface,stdoutDisplayInterface,output);
            contextI.setChannelPopularityScore(calculateChannelPopularityScore(videoDataBase));
            contextI.setChannelState(getUpdatedChannelPopularityState(calculateChannelPopularityScore(videoDataBase)));
        } else {
            throw new VideoNotFoundException("Error Removing the Video: "+ '\"' + videoNameIn +'\"' + " does not exist.");
        }
    }

    /**
     * method to update video metrics.
     *
     * @param videoNameIn name of the video.
     * @param likesIn likes for the video.
     * @param viewsIn views for the video.
     * @param dislikesIn dislikes for the video.
     */
    @Override
    public void updateVideoMetrics(String videoNameIn, int likesIn, int viewsIn, int dislikesIn) {
        try {
            if (isVideoMetricsUpdated(videoNameIn, videoDataBase, viewsIn, likesIn, dislikesIn)) {
                output = stateName + "__" + Operation.POPULARITY_SCORE_UPDATE + "::" + calculateChannelPopularityScore(videoDataBase);
                storeOutput(fileDisplayInterface, stdoutDisplayInterface, output);
                contextI.setChannelPopularityScore(calculateChannelPopularityScore(videoDataBase));
                contextI.setChannelState(getUpdatedChannelPopularityState(calculateChannelPopularityScore(videoDataBase)));
            }
            else {
                throw new VideoNotFoundException("Error Updating the Video: " + '\"' + videoNameIn + '\"' + " does not exist.");
            }
        }
        catch (InvalidInputException invalidInputException) {
            invalidInputException.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * method to process Ad requests for the video.
     * @param videoNameIn name of the video.
     * @param lengthIn length of the Ad to be processed.
     */
    @Override
    public void processAdRequests(String videoNameIn, int lengthIn) {
        if (!isVideoPresentInChannel(videoNameIn,videoDataBase) || lengthIn < 0) {
            throw new VideoNotFoundException("Error Processing Ad Request for the Video: "+ '\"' + videoNameIn +'\"' + " does not exist.");
        } else {
            if (isAdApproved(stateName, lengthIn,videoDataBase,videoNameIn)) {
                output = stateName + "__" + Operation.AD_REQUEST + "::APPROVED";
            } else {
                output = stateName + "__" + Operation.AD_REQUEST + "::REJECTED";
            }
            storeOutput(fileDisplayInterface,stdoutDisplayInterface,output);
            contextI.setChannelPopularityScore(calculateChannelPopularityScore(videoDataBase));
            contextI.setChannelState(getUpdatedChannelPopularityState(calculateChannelPopularityScore(videoDataBase)));
        }
    }

}
