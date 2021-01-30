package channelpopularity.myException;

/**
 * Exception thrown when the video is already present in the channel.
 */
public class VideoAlreadyExistsException extends IllegalArgumentException  {
    public VideoAlreadyExistsException(String message){
        super(message);
    }
}


