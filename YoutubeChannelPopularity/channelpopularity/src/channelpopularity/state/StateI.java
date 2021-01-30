package channelpopularity.state;

public interface StateI {

     void addVideoToChannel(String videoNameIn);
     void removeVideoFromChannel(String videoNameIn);
     void updateVideoMetrics(String videoNameIn, int likesIn, int viewsIn, int dislikesIn);
     void processAdRequests(String videoNameIn, int lengthIn);
     }
