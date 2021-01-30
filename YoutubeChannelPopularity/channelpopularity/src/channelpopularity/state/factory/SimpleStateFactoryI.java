package channelpopularity.state.factory;

import channelpopularity.context.ContextI;
import channelpopularity.state.StateI;
import channelpopularity.state.StateName;

public interface SimpleStateFactoryI {

    StateI create(StateName stateNameIn,ContextI contextI);
}
