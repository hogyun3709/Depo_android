package com.example.hogyunkim.depo_android.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.hogyunkim.depo_android.MainActivity;
import com.example.hogyunkim.depo_android.PaymentActivity;
import com.example.hogyunkim.depo_android.ProfileActivity;
import com.example.hogyunkim.depo_android.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by hogyunkim on 21/11/17.
 */

public class BottomNavigationViewHelper  {
    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_dashboard:
                        Intent intent1 = new Intent(context, MainActivity.class);
                        context.startActivity(intent1);
                        break;

                    case R.id.navigation_payment:
                        Intent intent2 = new Intent(context, PaymentActivity.class);
                        context.startActivity(intent2);
                        break;

                    case R.id.navigation_profile:
                        Intent intent3 = new Intent(context, ProfileActivity.class);
                        context.startActivity(intent3);
                        break;

                }
                return false;
            }
        });
    }
}
