package channelpopularity.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Results is an utility that is used to store and write the output into the file and onto the console.
 *
 * @author Shashank Bengaluru Srinivasa.
 */
public class Results implements FileDisplayInterface, StdoutDisplayInterface {
    private final String outputFileName;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private final StringBuilder stringBuilder;

    /**
     * constructor
     * initialises the output file name.
     **/
    public Results(String outputFileNameIn){
        outputFileName = outputFileNameIn;
        stringBuilder = new StringBuilder();
    }

    /**
     * toString method that aides in debugging.
     *
     * @return String
     */
    @Override
    public String toString(){
        return "Puts out the results to the console and the output file";
    }

    /**
     * method that stores the output.
     *
     * @param outputIn - output from the states.
     */
    @Override
    public void Store(String outputIn){
        stringBuilder.append(outputIn);
        stringBuilder.append(System.lineSeparator());
    }

    /**
     * writes to the output file
     */
    @Override
    public void writeToFile() throws IOException {
        try {
            if (!Files.exists(Paths.get(outputFileName))) {
                File file = new File(outputFileName);
            }
                fileWriter = new FileWriter(outputFileName);

            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        finally {
            bufferedWriter.close();
            fileWriter.close();
        }

    }

    /**
     * writes to the console.
     */
    @Override
    public void writeToStdOut() {
        System.out.print(stringBuilder.toString());
    }

}



