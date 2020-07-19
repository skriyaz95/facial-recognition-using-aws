package com.example.muneer.g18_async.awslambda.identify;

import java.util.List;

public class G18IdentifyLambdaOutput {
	private String identificationStatus;

	private List<User> users;

	public String getIdentificationStatus() {
		return identificationStatus;
	}

	public void setIdentificationStatus(String identificationStatus) {
		this.identificationStatus = identificationStatus;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "G18IdentifyLambdaOutput [identificationStatus=" + identificationStatus + ", users=" + users + "]";
	}
}

