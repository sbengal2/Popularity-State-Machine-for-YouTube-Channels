package channelpopularity.driver;

import channelpopularity.context.ChannelContext;
import channelpopularity.context.ContextI;
import channelpopularity.state.factory.SimpleStateFactory;
import channelpopularity.state.factory.SimpleStateFactoryI;
import channelpopularity.util.*;

/**
 * Driver serves as the entry and exit point for the program.
 *
 * @author Shashank Bengaluru Srinivasa
 */
public class Driver {
    private static final int REQUIRED_NUMBER_OF_CMDLINE_ARGS = 2;

    /**
     * toString method that aides in debugging.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Entry and Exit point for the program";
    }


    /**
     * main method for the program
     *
     * @param args arguments from the commandLine
     * @throws Exception if the number of command line arguments are invalid/ if the input is invalid/ if the file is empty / if there is trouble opening or closing file.
     */
    public static void main(String[] args) throws Exception {

        /*
         * As the build.xml specifies the arguments as input,output or metrics, in case the
         * argument value is not given java takes the default value specified in
         * build.xml. To avoid that, below condition is used
         */

        StdoutDisplayInterface stdoutDisplayInterface = null;
        FileDisplayInterface fileDisplayInterface = null;
        FileReaderInterface fileReaderInterface = null;
        try {
            if ((args.length != REQUIRED_NUMBER_OF_CMDLINE_ARGS) || (args[0].equals("${input}")) || (args[1].equals("${output}"))) {
                throw new IllegalArgumentException("Error: Incorrect number of arguments. Program accepts " + REQUIRED_NUMBER_OF_CMDLINE_ARGS + " arguments.");
            }
            if (args[0].isEmpty() || args[1].isEmpty()) {
                throw new IllegalArgumentException("Missing filenames in the arguments");
            }
            fileDisplayInterface = new Results(args[1]);
            stdoutDisplayInterface = new Results(args[1]);
            SimpleStateFactoryI simpleStateFactory = new SimpleStateFactory(fileDisplayInterface, stdoutDisplayInterface);
            ContextI channelContext = new ChannelContext(simpleStateFactory);
            fileReaderInterface = new InputProcessor(args[0], channelContext);
            fileReaderInterface.processInput();
        } catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
            System.exit(0);
        } finally {
            if (stdoutDisplayInterface != null)
                stdoutDisplayInterface.writeToStdOut();
            if (fileDisplayInterface != null)
                fileDisplayInterface.writeToFile();
            if (fileReaderInterface != null)
                fileReaderInterface.closeFileReader();
        }

    }
}
