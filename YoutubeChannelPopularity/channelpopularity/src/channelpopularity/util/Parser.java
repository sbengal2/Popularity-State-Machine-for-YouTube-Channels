package channelpopularity.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser parses the input to the required format.
 *
 * @author Shashank Bengaluru Srinivasa
 */
public class Parser{


    @Override
    public String toString(){
        return "Parses the input to extract required values";
    }

    /**
     * parses the input and returns it.
     *
     * @param lineToParseIn input line to be parsed.
     * @param parsePatternIn pattern to parse the input.
     * @return String
     */
    public String parseInput(String lineToParseIn, String parsePatternIn) {
        String parsedInput = null;
        Pattern pattern = Pattern.compile(parsePatternIn);
        Matcher matcher = pattern.matcher(lineToParseIn);
        if (matcher.find()) {
            parsedInput = matcher.group(1);
        }
        return parsedInput;
    }

}
