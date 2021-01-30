package channelpopularity.state.factory;

import channelpopularity.context.ContextI;
import channelpopularity.state.HighlyPopular;
import channelpopularity.state.MildlyPopular;
import channelpopularity.state.StateI;
import channelpopularity.state.StateName;
import channelpopularity.state.UltraPopular;
import channelpopularity.state.Unpopular;
import channelpopularity.util.FileDisplayInterface;
import channelpopularity.util.StdoutDisplayInterface;

/**
 * Simple state factory is used to create instance for each state.
 *
 * @author Shashank Bengaluru Srinivasa
 */
public class SimpleStateFactory implements SimpleStateFactoryI {
    private final FileDisplayInterface fileDisplayInterface;
    private final StdoutDisplayInterface stdoutDisplayInterface;

    /**
     * constructor used to initialise the fields fileDisplayInterface and stdoutDisplayInterface.
     * @param fileDisplayInterfaceIn instance of the interface to output to a file.
     * @param stdoutDisplayInterfaceIn instance of the interface to output to the console.
     */
    public SimpleStateFactory(FileDisplayInterface fileDisplayInterfaceIn, StdoutDisplayInterface stdoutDisplayInterfaceIn){
        fileDisplayInterface = fileDisplayInterfaceIn;
        stdoutDisplayInterface = stdoutDisplayInterfaceIn;
    }

    /**
     * toString method that aides in debugging.
     *
     * @return String
     */
    @Override
    public String toString(){
        return "creates the instance of each state.";
    }

    /**
     * takes a state as input, creates an instance for the same and returns it.
     *
     * @param stateNameIn name of the state
     * @param contextI - instance of context interface.
     */
    @Override
    public StateI create(StateName stateNameIn,ContextI contextI) {
        if (stateNameIn == StateName.UNPOPULAR) {
            return new Unpopular(fileDisplayInterface, stdoutDisplayInterface,contextI);
        } else if (stateNameIn == StateName.MILDLY_POPULAR) {
            return new MildlyPopular(fileDisplayInterface,stdoutDisplayInterface,contextI);
        } else if (stateNameIn == StateName.HIGHLY_POPULAR) {
            return new HighlyPopular(fileDisplayInterface,stdoutDisplayInterface,contextI);
        } else if (stateNameIn == StateName.ULTRA_POPULAR) {
            return new UltraPopular(fileDisplayInterface, stdoutDisplayInterface,contextI);
        }
        return null;
    }
}
