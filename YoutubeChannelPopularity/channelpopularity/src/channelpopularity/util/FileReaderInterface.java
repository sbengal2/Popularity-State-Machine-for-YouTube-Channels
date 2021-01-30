package channelpopularity.util;

import java.io.IOException;

public interface FileReaderInterface {

    void processInput() throws IOException;
    void closeFileReader() throws IOException;
}
