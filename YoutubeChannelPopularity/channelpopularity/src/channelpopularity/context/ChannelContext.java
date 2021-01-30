package channelpopularity.context;

import channelpopularity.myException.InvalidInputException;
import channelpopularity.myException.VideoNotFoundException;
import channelpopularity.state.StateI;
import channelpopularity.state.StateName;
import channelpopularity.state.factory.SimpleStateFactoryI;
import channelpopularity.video.Video;

import java.util.HashMap;
import java.util.Map;

/**
 * Channel Context helps in switching between the states of the channel.
 *
 * @author Shashank Bengaluru Srinivasa
 */

public class ChannelContext implements ContextI {

    /**
     * popularity score of the channel
     */
    private double channelPopularityScore;

    /**
     * current state of the channel
     */
    private StateI currentChannelState;

    private final Map<StateName, StateI> availableStates;
    private final Map<String, Video> videoBase;

    /**
     * Constructor used to get state instances of each state from the simple state factory, and store in a map.
     * @param simpleStateFactoryIIn creates the state instances.
     */
    public ChannelContext(SimpleStateFactoryI simpleStateFactoryIIn) {
        videoBase = new HashMap<>();
        availableStates = new HashMap<>();
        StateI unpopular = simpleStateFactoryIIn.create(StateName.UNPOPULAR, this);
        availableStates.put(StateName.UNPOPULAR, unpopular);
        StateI mildlyPopular = simpleStateFactoryIIn.create(StateName.MILDLY_POPULAR, this);
        availableStates.put(StateName.MILDLY_POPULAR, mildlyPopular);
        StateI highlyPopular = simpleStateFactoryIIn.create(StateName.HIGHLY_POPULAR, this);
        availableStates.put(StateName.HIGHLY_POPULAR, highlyPopular);
        StateI ultrapopular = simpleStateFactoryIIn.create(StateName.ULTRA_POPULAR, this);
        availableStates.put(StateName.ULTRA_POPULAR, ultrapopular);
        currentChannelState = unpopular;
    }

    /**
     *toString method that aides in debugging.
     *
     * @return String
     */
    @Override
    public String toString(){
        return "context class used to set or change states";
    }

    /**
     *adds the video to channel by calling the relevant method in the current state.
     *
     * @param videoNameIn name of the video.
     */
    @Override
    public void addVideo(String videoNameIn) throws VideoNotFoundException {
        currentChannelState.addVideoToChannel(videoNameIn);
    }

    /**
     *removes the video from the channel by calling the relevant method in the current state.
     *
     *  @param videoNameIn name of the video.
     */
    @Override
    public void removeVideo(String videoNameIn) throws VideoNotFoundException {
        currentChannelState.removeVideoFromChannel(videoNameIn);
    }

    /**
     *updates the video attributes by calling the relevant method in the current state.
     *
     * @param videoNameIn name of the video.
     * @param likesIn number of likes for the video.
     * @param viewsIn number of views for the video.
     * @param dislikesIn number of dislikes for the video.
     */
    @Override
    public void updateVideoMetrics(String videoNameIn, int likesIn, int viewsIn, int dislikesIn) throws VideoNotFoundException, InvalidInputException {
        currentChannelState.updateVideoMetrics(videoNameIn, likesIn, viewsIn, dislikesIn);
    }

    /**
     *processes Ad request for a video by calling the relevant method in the current state.
     *
     * @param videoNameIn name of the video.
     * @param lengthIn Ad length that needed to be processed
     */
    @Override
    public void processAdRequests(String videoNameIn, int lengthIn) throws VideoNotFoundException {
        currentChannelState.processAdRequests(videoNameIn, lengthIn);
    }

    /**
     *switches the state of the channel to the input state.
     *
     * @param stateNameIn state to be switched to.
     *
     */
    @Override
    public void setChannelState(StateName stateNameIn) {
        if (availableStates.containsKey(stateNameIn)) {
            currentChannelState = availableStates.get(stateNameIn);
        }
    }

    /**
     *getter to get the map that stores all the videos.
     *
     * @return Map<String,Video></String,Video>
     */
    @Override
    public Map<String, Video> getVideoDataBase() {
        return videoBase;
    }

    /**
     *setter to set the popularity score of the channel.
     *
     * @param channelPopularityScoreIn - updated popularity score of the channel.
     */
    @Override
    public void setChannelPopularityScore(double channelPopularityScoreIn) {
        channelPopularityScore = channelPopularityScoreIn;
    }

    /**
     *getter to get the popularity state of the channel.
     *
     * @return double
     */
    @Override
    public double getChannelPopularityScore() {
        return channelPopularityScore;
    }
}
