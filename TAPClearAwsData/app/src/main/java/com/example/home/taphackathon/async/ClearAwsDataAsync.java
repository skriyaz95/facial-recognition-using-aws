package com.example.home.taphackathon.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;
import com.example.home.taphackathon.awslambda.verify.ClearAwsDataInput;
import com.example.home.taphackathon.awslambda.verify.ClearAwsDataOutput;
import com.example.home.taphackathon.awslambda.verify.ClearAwsDataService;
import com.example.home.taphackathon.callbacks.ClearAwsDataCallback;
import com.example.home.taphackathon.helpers.PropertiesHelper;

/**
 * Created by Muneer on 31-03-2017.
 */

public class ClearAwsDataAsync extends AsyncTask<ClearAwsDataInput, Void, ClearAwsDataOutput> {

    Context context;
    PropertiesHelper propertiesHelper;
    ProgressDialog progressDialog;
    ClearAwsDataCallback clearAwsDataCallback;

    public ClearAwsDataAsync(Context context, PropertiesHelper propertiesHelper, ProgressDialog progressDialog, ClearAwsDataCallback clearAwsDataCallback) {
        this.context = context;
        this.propertiesHelper = propertiesHelper;
        this.progressDialog = progressDialog;
        this.clearAwsDataCallback = clearAwsDataCallback;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setMessage("Clearing aws data...");
        progressDialog.show();
    }

    @Override
    protected ClearAwsDataOutput doInBackground(ClearAwsDataInput... params) {
        System.out.println("verify doInBackground");
        try {
            ClearAwsDataInput verifyInput = params[0];

            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    context, // Context
                    propertiesHelper.getIdentityPoolId(), // Identity Pool ID
                    Regions.US_EAST_1 // Region
            );

            LambdaInvokerFactory factory = new LambdaInvokerFactory(context, Regions.US_EAST_1, credentialsProvider);
            ClearAwsDataService clearAwsDataService = factory.build(ClearAwsDataService.class);


            ClearAwsDataOutput verifyOutput = clearAwsDataService.verify(verifyInput);
            return verifyOutput;
        } catch (LambdaFunctionException lambdaFunctionException) {
            lambdaFunctionException.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ClearAwsDataOutput clearAwsDataOutput) {
        progressDialog.dismiss();
        clearAwsDataCallback.clearDone(clearAwsDataOutput);
    }
}
