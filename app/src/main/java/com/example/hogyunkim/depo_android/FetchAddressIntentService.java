package com.example.hogyunkim.depo_android;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by hogyunkim on 19/11/17.
 */

public class FetchAddressIntentService extends IntentService {
    private static final int SUCCESS_RESULT = 0;
    private static final int FAILURE_RESULT = 1;
    private static final String PACKAGE_NAME = "com.example.hogyunkim.depo_android";
    private static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    private static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    private static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

    protected ResultReceiver mReceiver;

    public FetchAddressIntentService(){
        super("name");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String errorMessage = "";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        //get the location passed to this service through extras

        Location location = intent.getParcelableExtra(LOCATION_DATA_EXTRA);
        mReceiver = intent.getParcelableExtra(RECEIVER);

        List<Address> addressess = null;
        try {
            addressess = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            errorMessage = "Service not available";
            Log.e(TAG, errorMessage, e);
            e.printStackTrace();
        } catch (IllegalArgumentException illegalArguementException) {
            errorMessage = "Invalid latitude and longitude used";
            Log.e(TAG, errorMessage + ". " + location.getLatitude() + ", Longitude = " + location.getLongitude());
        }

        if (addressess == null || addressess.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "No address found";
                Log.e(TAG, errorMessage);
            }
            deliverResultsToReceiver(FAILURE_RESULT, errorMessage);
        } else {
            Address address = addressess.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));

            }
            Log.i(TAG, "Address Found");
            deliverResultsToReceiver(SUCCESS_RESULT, TextUtils.join(System.getProperty("line.separator"), addressFragments));
        }
    }

    private void deliverResultsToReceiver(int failureResult, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(RESULT_DATA_KEY, message);
        mReceiver.send(failureResult, bundle);
    }


}
