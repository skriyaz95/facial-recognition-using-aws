package com.example.muneer.g18_async.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;
import com.example.muneer.g18_async.awslambda.identify.G18IdentifyLambdaInput;
import com.example.muneer.g18_async.awslambda.identify.G18IdentifyLambdaOutput;
import com.example.muneer.g18_async.awslambda.identify.IdentifyService;
import com.example.muneer.g18_async.callbacks.IdentifyCallback;
import com.example.muneer.g18_async.helpers.PropertiesHelper;

/**
 * Created by Muneer on 31-03-2017.
 */

public class IdentifyAsync extends AsyncTask<G18IdentifyLambdaInput, Void, G18IdentifyLambdaOutput> {

    Context context;
    PropertiesHelper propertiesHelper;
    ProgressDialog progressDialog;
    IdentifyCallback identifyCallback;
    int currImageNo, noOfImages;

    public IdentifyAsync(Context context, PropertiesHelper propertiesHelper, ProgressDialog progressDialog, IdentifyCallback identifyCallback, int currImageNo, int noOfImages) {
        this.context = context;
        this.propertiesHelper = propertiesHelper;
        this.progressDialog = progressDialog;
        this.identifyCallback = identifyCallback;
        this.currImageNo = currImageNo;
        this.noOfImages = noOfImages;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setMessage("Identifying image " +  currImageNo + " / " + noOfImages);
        progressDialog.show();
    }

    @Override
    protected G18IdentifyLambdaOutput doInBackground(G18IdentifyLambdaInput... params) {
        System.out.println("identify doInBackground");
        try {
            G18IdentifyLambdaInput g18IdentifyLambdaInput = params[0];

            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    context, // Context
                    propertiesHelper.getIdentityPoolId(), // Identity Pool ID
                    Regions.US_EAST_1 // Region
            );

            LambdaInvokerFactory factory = new LambdaInvokerFactory(context, Regions.US_EAST_1, credentialsProvider);
            IdentifyService identifyService = factory.build(IdentifyService.class);


            G18IdentifyLambdaOutput identifyOutput = identifyService.identify(g18IdentifyLambdaInput);
            return identifyOutput;
        } catch (LambdaFunctionException lambdaFunctionException) {
            lambdaFunctionException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        G18IdentifyLambdaOutput g18IdentifyLambdaOutput = new G18IdentifyLambdaOutput();
        g18IdentifyLambdaOutput.setIdentificationStatus("Unable to get response from aws Lambda function");
        return g18IdentifyLambdaOutput;
    }

    @Override
    protected void onPostExecute(G18IdentifyLambdaOutput identifyOutput) {
        progressDialog.dismiss();
        identifyCallback.identificationDone(identifyOutput);
    }
}
