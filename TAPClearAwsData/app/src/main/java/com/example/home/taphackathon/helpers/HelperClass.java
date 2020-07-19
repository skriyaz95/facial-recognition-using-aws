package com.example.home.taphackathon.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Muneer on 19-03-2017.
 */

public class HelperClass {
    public String getFolderName() {
        return "CapturePhoto";
    }

    public String getRootFolderPath() {
        return "/storage/emulated/0";
    }

    public String getBucketName(String uploadType) {
        String bucketName = "fbuc1";
        switch(uploadType) {
            case "Enroll":
                bucketName = "tap-hackathon-enrollment-bucket";
                break;
            case "Verify":
                bucketName = "tap-hackathon-verification-bucket";
                break;
            case "Identify":
                bucketName = "tap-hackathon-identification-bucket";
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
