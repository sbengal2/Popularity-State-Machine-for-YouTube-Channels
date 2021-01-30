package channelpopularity.myException;

/**
 * Exception thrown when the video is not present in the channel.
 */

public class VideoNotFoundException extends IllegalArgumentException {

        public VideoNotFoundException(String message){
            super(message);
        }
    }

