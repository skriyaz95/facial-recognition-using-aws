package com.example.home.taphackathon.awslambda.verify;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

public interface ClearAwsDataService {
	@LambdaFunction(functionName = "ClearAwsDataLambdaFunction")
	ClearAwsDataOutput verify(ClearAwsDataInput verifyInput);
}
