package com.example.home.taphackathon.awslambda.verify;

public class ClearAwsDataInput {
	String temp;

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	@Override
	public String toString() {
		return "ClearAwsDataInput [temp=" + temp + "]";
	}
}