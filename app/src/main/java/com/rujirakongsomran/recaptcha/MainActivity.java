package com.rujirakongsomran.recaptcha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {

    // Initialize variable
    CheckBox checkBox;
    GoogleApiClient googleApiClient;

    // Put SiteKey as a String
    String siteKey = "6Ld8T6wZAAAAAOTMsUVKkaj0U4MYaTOessc7Dt3k";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign Variable
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        // Create Google Api Client
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(MainActivity.this)
                .build();

        googleApiClient.connect();

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set if condition when checkbox checked
                if (checkBox.isChecked()) {
                    SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleApiClient, siteKey)
                            .setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                                @Override
                                public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {
                                    Status status = recaptchaTokenResult.getStatus();
                                    if ((status != null) && status.isSuccess()) {
                                        // Display Successful Message
                                        Toast.makeText(getApplicationContext(),
                                                "Successfully Varified...",
                                                Toast.LENGTH_SHORT).show();
                                        // Change checkbox text color
                                        checkBox.setTextColor(Color.GREEN);
                                    }
                                }
                            });
                } else {
                    // Default Checkbox text color
                    checkBox.setTextColor(Color.BLACK);
                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}