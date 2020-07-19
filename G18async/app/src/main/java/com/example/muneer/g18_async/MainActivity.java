package com.example.muneer.g18_async;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.muneer.g18_async.async.UploadAsync;
import com.example.muneer.g18_async.async.UploadInput;
import com.example.muneer.g18_async.awslambda.identify.G18IdentifyLambdaOutput;
import com.example.muneer.g18_async.awslambda.identify.User;
import com.example.muneer.g18_async.callbacks.IdentifyCallback;
import com.example.muneer.g18_async.helpers.EncryptDecrypt;
import com.example.muneer.g18_async.helpers.HelperClass;
import com.example.muneer.g18_async.helpers.PropertiesHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IdentifyCallback {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    ArrayAdapter<String> listViewKiosksArrayAdapter;

    String[] kiosksArray;
    ArrayList<String> kiosksArrayList;

    HelperClass helperClass;
    PropertiesHelper propertiesHelper;

    MySharedPreferences mySharedPreferences;

    String stringRootFolderPath, stringFolderName, stringFileName;

    private List<String> listPermissionsNeeded;
    private ProgressDialog progressDialog;
    private Button buttonIdentifyAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();

        identifyAllListener();
    }

    private void identifyAllListener() {
        buttonIdentifyAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File folder = new File(stringRootFolderPath + "/" + stringFolderName);
                File[] files = folder.listFiles();

                if(!folder.exists()) {
                    showToast("folder does not exist");
                    return;
                }

                if(files == null || files.length == 0) {
                    System.out.println("No images to identify");
                    showToast("No images to identify");
                    return;
                }

                if(!helperClass.isNetworkAvailable(getApplicationContext())) {
                    showToast("Please connect to internet");
                }

                new UploadAsync(getApplicationContext(), stringRootFolderPath, stringFolderName, propertiesHelper, MainActivity.this, mySharedPreferences, progressDialog).execute(new UploadInput());
            }
        });
    }

    private void initialise() {
        listPermissionsNeeded = new ArrayList<>();

        checkAndRequestPermissions();

        helperClass = new HelperClass();
        try {
            InputStream propertiesFileStream = this.getAssets().open("properties-file.properties");
            propertiesHelper = new PropertiesHelper(propertiesFileStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mySharedPreferences = new MySharedPreferences(this);

        stringRootFolderPath = helperClass.getRootFolderPath();
        stringFolderName = helperClass.getFolderName();

        createFolder(stringRootFolderPath, stringFolderName);

        initializeKiosksArrayList();

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        buttonIdentifyAll = findViewById(R.id.buttonIdentifyAll);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.overflow_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showListViewPopUp();
        return super.onOptionsItemSelected(item);
    }

    public void showListViewPopUp() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Select Kiosk");

        kiosksArray = new String[kiosksArrayList.size()];
        kiosksArray = kiosksArrayList.toArray(kiosksArray);
        listViewKiosksArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice, kiosksArray);

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), mySharedPreferences.getKiosk() + " selected", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setSingleChoiceItems(listViewKiosksArrayAdapter, kiosksArrayList.indexOf(mySharedPreferences.getKiosk()), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String kiosk = listViewKiosksArrayAdapter.getItem(which);
                mySharedPreferences.setKiosk(kiosk);
            }
        });

        alertDialog.show();
    }

    private void createFolder(String rootFolderPath, String folderName) {
        try {
            File folder = new File(rootFolderPath + "/" + folderName);

            if (!folder.exists()) {
                folder.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showToast(e.getMessage());
        }
    }

    void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void initializeKiosksArrayList() {
        kiosksArrayList = new ArrayList<>();
        kiosksArrayList.add("pre-register");
        kiosksArrayList.add("register");

        for(int i = 1; i <= 20; i++) {
            kiosksArrayList.add("kiosk-" + i);
        }
    }

    private  boolean checkAndRequestPermissions() {
        int storage = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    @Override
    public void identificationDone(G18IdentifyLambdaOutput identifyOutput) {
        try {
            String identificationStatus = identifyOutput.getIdentificationStatus();
            if(identificationStatus.contains("identified")) {
                User user = identifyOutput.getUsers().get(0);
                identificationStatus = EncryptDecrypt.decrypt(user.getFirstname()) + " " + EncryptDecrypt.decrypt(user.getLastname()) + " identified";
            }
            showToast(identificationStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
