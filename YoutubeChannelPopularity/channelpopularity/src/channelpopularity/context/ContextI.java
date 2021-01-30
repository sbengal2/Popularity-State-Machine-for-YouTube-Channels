package channelpopularity.context;

import channelpopularity.state.StateName;
import channelpopularity.video.Video;

import java.util.Map;

public interface ContextI{

    void setChannelState(StateName stateNameIn);

    void addVideo(String videoObjectName);

    void removeVideo(String videoObjectName);

    void updateVideoMetrics(String videoObjectName, int likes, int views, int dislikes);

    void processAdRequests(String videoObjectName, int length);

    Map<String,Video> getVideoDataBase();

    void setChannelPopularityScore(double channelPopularityScoreIn);

    double getChannelPopularityScore();
}
