package com.example.muneer.g18_async.callbacks;


import com.example.muneer.g18_async.awslambda.identify.G18IdentifyLambdaOutput;

/**
 * Created by Muneer on 31-03-2017.
 */

public interface IdentifyCallback {
    public void identificationDone(G18IdentifyLambdaOutput identifyOutput);
}
