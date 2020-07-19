package com.example.muneer.g18_async.awslambda.identify;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

public interface IdentifyService {
	@LambdaFunction(functionName = "G18IdentifyLambdaFunction")
	G18IdentifyLambdaOutput identify(G18IdentifyLambdaInput identifyInput);
}
