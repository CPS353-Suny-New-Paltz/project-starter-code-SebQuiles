// src/.../network/impl/UserComputeAPIDriver.java
package network.impl;

import network.api.UserComputeAPI;
import network.api.UserJobRequest;
import project.annotations.NetworkAPIPrototype;

public class UserComputeAPIDriver {

	@NetworkAPIPrototype
	public void networkAPIPrototype(UserComputeAPI api) {
		// UserJobRequest is an interface — provide an instance via anonymous class:
		UserJobRequest req = new UserJobRequest() {
			@Override
			public String getInputSource() {
				return "numbers.txt";
			}

			@Override
			public String getOutputDestination() {
				return "output.txt";
			}

			@Override
			public Character getPairSeparator() {
				return null;
			} // defaults OK

			@Override
			public Character getKvSeparator() {
				return null;
			} // defaults OK
		};

		api.submitJob(req);
	}
}
