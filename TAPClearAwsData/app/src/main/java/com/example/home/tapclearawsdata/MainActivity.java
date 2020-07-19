package com.example.home.tapclearawsdata;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home.taphackathon.async.ClearAwsDataAsync;
import com.example.home.taphackathon.awslambda.verify.ClearAwsDataInput;
import com.example.home.taphackathon.awslambda.verify.ClearAwsDataOutput;
import com.example.home.taphackathon.callbacks.ClearAwsDataCallback;
import com.example.home.taphackathon.helpers.HelperClass;
import com.example.home.taphackathon.helpers.PropertiesHelper;

public class MainActivity extends AppCompatActivity implements ClearAwsDataCallback {

    Button button;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        progressDialog = new ProgressDialog(MainActivity.this);

        final PropertiesHelper propertiesHelper = new PropertiesHelper(getBaseContext());
        final HelperClass helperClass = new HelperClass();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!helperClass.isNetworkAvailable(getApplicationContext())) {
                    showToast("Please connect to internet");
                    return;
                }

                ClearAwsDataInput clearAwsDataInput = new ClearAwsDataInput();
                clearAwsDataInput.setTemp("temp");

                ClearAwsDataAsync clearAwsDataAsync = new ClearAwsDataAsync(getApplicationContext(), propertiesHelper, progressDialog, MainActivity.this);
                clearAwsDataAsync.execute(clearAwsDataInput);
            }
        });
    }

    void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void clearDone(ClearAwsDataOutput clearAwsDataOutput) {
        showToast(clearAwsDataOutput.getStatus());
    }
}
