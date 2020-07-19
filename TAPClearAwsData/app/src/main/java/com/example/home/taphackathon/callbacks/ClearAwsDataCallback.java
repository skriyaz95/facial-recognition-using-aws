package com.example.home.taphackathon.callbacks;

import com.example.home.taphackathon.awslambda.verify.ClearAwsDataOutput;

/**
 * Created by Muneer on 31-03-2017.
 */

public interface ClearAwsDataCallback {
    public void clearDone(ClearAwsDataOutput clearAwsDataOutput);
}
