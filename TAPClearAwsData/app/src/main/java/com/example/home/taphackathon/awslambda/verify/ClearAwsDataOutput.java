package com.example.home.taphackathon.awslambda.verify;

public class ClearAwsDataOutput {
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ClearAwsDataOutput [status=" + status + "]";
	}
}

