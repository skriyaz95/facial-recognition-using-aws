package com.example.muneer.g18_async.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.example.muneer.g18_async.MainActivity;
import com.example.muneer.g18_async.MySharedPreferences;
import com.example.muneer.g18_async.awslambda.identify.G18IdentifyLambdaInput;
import com.example.muneer.g18_async.awslambda.identify.G18IdentifyLambdaOutput;
import com.example.muneer.g18_async.awslambda.identify.IdentifyService;
import com.example.muneer.g18_async.callbacks.IdentifyCallback;
import com.example.muneer.g18_async.helpers.PropertiesHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Muneer on 11-11-2017.
 */

public class UploadAsync extends AsyncTask<UploadInput, Void, UploadOutput> {
    String rootFolderPath;
    String folderName;
    PropertiesHelper propertiesHelper;
    Context context;
    MySharedPreferences mySharedPreferences;
    ProgressDialog progressDialog;
    MainActivity parent;

    public UploadAsync(Context context, String rootFolderPath, String folderName, PropertiesHelper propertiesHelper, MainActivity parent, MySharedPreferences mySharedPreferences, ProgressDialog progressDialog) {
        this.context = context;
        this.rootFolderPath = rootFolderPath;
        this.folderName = folderName;
        this.propertiesHelper = propertiesHelper;
        this.mySharedPreferences = mySharedPreferences;
        this.progressDialog = progressDialog;
        this.parent = parent;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(UploadOutput uploadOutput) {
        progressDialog.dismiss();
    }

    @Override
    protected UploadOutput doInBackground(UploadInput... uploadInputs) {
        File folder = new File(rootFolderPath + "/" + folderName);
        File[] files = folder.listFiles();
        String bucketName = propertiesHelper.getAsyncIdentificationBucketName();

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context, // Context
                propertiesHelper.getIdentityPoolId(), // Identity Pool ID
                Regions.US_EAST_1 // Region
        );

        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);

        final int noOfFiles = files.length;
        for(int i = 0; i < files.length; i++) {
            final int fileNo = i + 1;

            try {
                final File file = files[i];
                String s3Key = file.getName();

                TransferUtility transferUtility = new TransferUtility(s3, context);
                TransferObserver observer = transferUtility.upload(
                        bucketName,     /* The bucket to upload to */
                        s3Key,    /* The key for the uploaded object */
                        file        /* The file where the data to upload exists */
                );


                System.out.println("Uploading image " + (i+1) + " / " + files.length);
                parent.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.setMessage("Uploading image " + fileNo + " / " + noOfFiles);
                    }
                });


                do {
                    observer.refresh();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Uploading status: " + observer.getState());
                } while (!(observer.getState().equals(TransferState.COMPLETED) || observer.getState().toString().equals("COMPLETE")));

                System.out.println("Identifying image " + (i+1) + " / " + files.length);
                parent.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.setMessage("Identifying image " + fileNo + " / " + noOfFiles);
                    }
                });

                G18IdentifyLambdaInput g18IdentifyLambdaInput = getG18IdentifyLambdaInput(file.getName(), mySharedPreferences);
                LambdaInvokerFactory factory = new LambdaInvokerFactory(context, Regions.US_EAST_1, credentialsProvider);
                IdentifyService identifyService = factory.build(IdentifyService.class);
                //G18IdentifyLambdaOutput identifyOutput = null;


                try {
                    final G18IdentifyLambdaOutput identifyOutput = identifyService.identify(g18IdentifyLambdaInput);
                    parent.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, identifyOutput.getIdentificationStatus(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    System.out.println(identifyOutput.getIdentificationStatus());
                } catch (Exception e) {
                    e.printStackTrace();
                    final G18IdentifyLambdaOutput identifyOutput = new G18IdentifyLambdaOutput();
                    identifyOutput.setIdentificationStatus("Unable to get response from aws Lambda function");
                    parent.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, identifyOutput.getIdentificationStatus(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    System.out.println(identifyOutput.getIdentificationStatus());
                }

                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("done");
        return null;
    }

    private G18IdentifyLambdaInput getG18IdentifyLambdaInput(String stringFileName, MySharedPreferences mySharedPreferences) {
        String accesKeyId = propertiesHelper.getAwsAccesKeyId();
        String secretAccessKey = propertiesHelper.getAwsSecretAccessKey();

        String bucket = propertiesHelper.getAsyncIdentificationBucketName();
        String key = stringFileName;
        String collectionId = propertiesHelper.getFacesCollectionId();
        String region = propertiesHelper.getAwsRegion();

        String kiosk = mySharedPreferences.getKiosk();

        String jdbcDriver = propertiesHelper.getJdbcDriverClass();
        String databaseUrl = propertiesHelper.getDatabaseUrl();
        String databaseUsername = propertiesHelper.getDatabaseUsername();
        String databasePassword = propertiesHelper.getDatabsePassword();
        String databaseName = propertiesHelper.getDatabaseName();
        String g18DatatableName = propertiesHelper.getG18DataTableName();
        String g18UsersTableName = propertiesHelper.getG18UsersTableName();

        int maxFaces = Integer.parseInt(propertiesHelper.getMaxFaces());
        Float threshold = Float.parseFloat(propertiesHelper.getThreshold());

        G18IdentifyLambdaInput identifyInput = new G18IdentifyLambdaInput();
        identifyInput.setAccessKeyId(accesKeyId);
        identifyInput.setSecretAccessKey(secretAccessKey);
        identifyInput.setRegion(region);
        identifyInput.setIdentifyBucket(bucket);
        identifyInput.setKey(key);
        identifyInput.setCollectionId(collectionId);
        identifyInput.setMaxFaces(maxFaces);
        identifyInput.setThreshold(threshold);

        identifyInput.setJdbcDriver(jdbcDriver);
        identifyInput.setDatabaseUrl(databaseUrl);
        identifyInput.setDatabaseUsername(databaseUsername);
        identifyInput.setDatabasePassword(databasePassword);
        identifyInput.setDatabaseName(databaseName);
        identifyInput.setG18DatatableName(g18DatatableName);
        identifyInput.setG18UsersTableName(g18UsersTableName);

        identifyInput.setKiosk(kiosk);

        return identifyInput;
    }
}
