package channelpopularity.util;

import channelpopularity.context.ContextI;
import channelpopularity.myException.EmptyInputFileException;
import channelpopularity.myException.InvalidInputException;
import channelpopularity.myException.VideoNotFoundException;
import channelpopularity.operation.Operation;

import java.io.IOException;
import java.nio.file.InvalidPathException;

/**
 * InputProcessor is an utility the processes the input and sends it to the relevant methods.
 *
 * @author Shashank Bengaluru Srinivasa
 */
public class InputProcessor implements FileReaderInterface {
    private static final String addVideoInputFormat = Operation.ADD_VIDEO + "::<video name>";
    private static final String removeVideoInputFormat = Operation.REMOVE_VIDEO + "::<video name>";
    private static final String updateVideoMetricsInputFormat = Operation.METRICS + "__<video name>::[VIEWS=<delta in #views>,LIKES=<delta in #likes>,DISLIKES=<delta in #dislikes>]";
    private static final String videoAdProcessingInputFormat = Operation.AD_REQUEST + "__<video name>::LEN=<length>";

    private final String inputFileName;
    private final ContextI contextI;
    private final Parser parser;
    private final Validator validator;
    private FileProcessor fileProcessor;

    /**
     * Constructor to intialise several fields.
     *
     * @param fileNameIn input filename
     * @param contextIn  instance of the context interface.
     */
    public InputProcessor(String fileNameIn, ContextI contextIn) {
        inputFileName = fileNameIn;
        contextI = contextIn;
        parser = new Parser();
        validator = new Validator();
    }

    /**
     * toString method that aides in debugging.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Processes the input";
    }

    /**
     * processes each line from the input.
     *
     * @throws IOException if there is trouble reading the file
     */
    @Override
    public void processInput() throws IOException {
        String videoName;
        try {
            fileProcessor = new FileProcessor(inputFileName);
        } catch (IOException | SecurityException | InvalidPathException exception) {
            exception.printStackTrace();
            System.exit(0);
        }
        String readLine = fileProcessor.poll();
        if (readLine == null) {
            throw new EmptyInputFileException("the input file is empty");
        }
        while (readLine != null) {
            if (!validator.isInputFormatValid(readLine)) {
                throw new InvalidInputException("the input format is invalid.\n" + "Input format should be as follows:" + "\n" +
                        addVideoInputFormat + "\n" + removeVideoInputFormat + "\n" + updateVideoMetricsInputFormat + "\n" + videoAdProcessingInputFormat +
                        "\nNOTE:\n1.VALUES OF LIKES/DISLIKES/VIEWS/ADVERTISEMENT LENGTH SHOULD BE INTEGERS.\n" +
                        "2.VIDEO NAME CANNOT START WITH A SPACE.");
            }
            try {
                if (readLine.startsWith(Operation.ADD_VIDEO.toString())) {
                    videoName = parser.parseInput(readLine, Operation.ADD_VIDEO + "::([^ ].*)");
                    contextI.addVideo(videoName);
                } else if (readLine.startsWith(Operation.REMOVE_VIDEO.toString())) {
                    videoName = parser.parseInput(readLine, Operation.REMOVE_VIDEO + "::([^ ].*)");
                    contextI.removeVideo(videoName);
                } else if (readLine.startsWith(Operation.METRICS.toString())) {
                    videoName = parser.parseInput(readLine, "(?<=" + Operation.METRICS + "__)([^ ].*)(?=::)");
                    int views = Integer.parseInt(parser.parseInput(readLine, "VIEWS=(-?[0-9]+)"));
                    if (views < 0)
                        throw new InvalidInputException("Error: the number of views for video cannot be negative");
                    int likes = Integer.parseInt(parser.parseInput(readLine, "LIKES=(-?[0-9]+)"));
                    int dislikes = Integer.parseInt(parser.parseInput(readLine, "DISLIKES=(-?[0-9]+)"));
                    contextI.updateVideoMetrics(videoName, likes, views, dislikes);
                } else if (readLine.startsWith(Operation.AD_REQUEST.toString())) {
                    videoName = parser.parseInput(readLine, "(?<=" + Operation.AD_REQUEST + "__)([^ ].*)(?=::)");
                    int adLength = Integer.parseInt(parser.parseInput(readLine, "LEN=(-?[0-9]+)"));
                    if (adLength < 0)
                        throw new InvalidInputException("Error: the length of an Ad cannot be negative");
                    contextI.processAdRequests(videoName, adLength);
                }
            } catch (VideoNotFoundException | InvalidInputException invalidInputException) {
                invalidInputException.printStackTrace();
                System.exit(0);
            } finally {
                readLine = fileProcessor.poll();
            }

        }
    }

    /**
     * closes the file reader descriptor
     *
     * @throws IOException if there is trouble closing the file.
     */
    @Override
    public void closeFileReader() throws IOException {
        fileProcessor.close();
    }

}
