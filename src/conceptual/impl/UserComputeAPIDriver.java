// src/.../conceptual/impl/UserComputeAPIDriver.java
package conceptual.impl;

import project.annotations.NetworkAPIPrototype;
import network.api.*;

public class UserComputeAPIDriver {

    @NetworkAPIPrototype
    public void networkAPIPrototype(UserComputeAPI api) {
        // UserJobRequest is an interface — provide an instance via anonymous class:
        UserJobRequest req = new UserJobRequest() {
            @Override public String getInputSource()       { 
            	return "numbers.txt"; 
            	}
            @Override public String getOutputDestination() { 
            	return "output.txt"; 
            	}
            @Override public Character getPairSeparator()  { 
            	return null; 
            	} // defaults OK
            @Override public Character getKvSeparator()    { 
            	return null; 
            	} // defaults OK
        };

        api.submitJob(req);
    	}
}
