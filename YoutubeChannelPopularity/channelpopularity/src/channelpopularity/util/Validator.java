package channelpopularity.util;

import channelpopularity.operation.Operation;

/**
 * Validator scans the input for the required format.
 */
public class Validator {

    private static final String addVideoInputFormat = Operation.ADD_VIDEO + "::([^ ].*)";
    private static final String removeVideoInputFormat = Operation.REMOVE_VIDEO + "::([^ ].*)";
    private static final String metricsVideoInputFormat = Operation.METRICS + "__([^ ].*)::\\Q[\\EVIEWS\\Q=\\E(-?[0-9]+),LIKES\\Q=\\E(-?[0-9]+),DISLIKES\\Q=\\E(-?[0-9]+)\\Q]\\E";
    private static final String adRequestInputFormat = Operation.AD_REQUEST + "__([^ ].*)::LEN\\Q=\\E(-?[0-9]+)";

    /**
     * toString method that aides in debugging.
     *
     * @return String
     */
    @Override
    public String toString(){
        return "Validates the input for required format";
    }

    /**
     * checks if the input is in the required format.
     * @param inputIn input to be checked
     * @return boolean
     */
    public Boolean isInputFormatValid(String inputIn){
        if(inputIn.matches(addVideoInputFormat)){
            return true;
        }
        else if(inputIn.matches(removeVideoInputFormat)){
            return true;
        }
        else if(inputIn.matches(metricsVideoInputFormat)){
            return true;
        }
        else return inputIn.matches(adRequestInputFormat);
    }
}
