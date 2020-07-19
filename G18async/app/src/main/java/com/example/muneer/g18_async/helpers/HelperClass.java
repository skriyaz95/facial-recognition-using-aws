package com.example.muneer.g18_async.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Muneer on 19-03-2017.
 */

public class HelperClass {
    public String getFolderName() {
        return "G18Async";
    }

    public String getRootFolderPath() {
        return "/storage/emulated/0";
    }

    public String getBucketName(String uploadType) {
        String bucketName = "fbuc1";
        switch(uploadType) {
            case "Enroll":
                bucketName = "g18-enrollment-bucket";
                break;
            case "Verify":
                bucketName = "g18-verification-bucket";
                break;
            case "Identify":
                bucketName = "g18-identification-bucket";
                break;
        }
        return bucketName;
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
