package com.example.bilhaghedeon.finalprojectghedeonkhaliq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Implemented by Samina Khaliq
 */


public class MainActivity extends Activity {


    // to get status codes back from intents
    private static final int ADD_ITEM_ACTIVITY = 1;
    private static final int CHECK_INVENTORY_ACTIVITY = 2;
    private static final int DAILY_REPORT_ACTIVITY = 3;

    // Button views
    Button btnAdd, btnCheckInventory, btnDailyReport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind buttons from UI to code
        btnAdd = findViewById(R.id.btn_add);
        btnCheckInventory = findViewById(R.id.btn_check_inventory);
        btnDailyReport = findViewById(R.id.btn_daily_report);


        // set on click listeners for buttons

        // launch activity to add an item
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddActivity();
            }
        });

        // launch activity to check user's inventory
        btnCheckInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCheckInventoryActivity();
            }
        });

        // launch activity to display report of quantities of all items
        btnDailyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDailyReportActivity();
            }
        });


    }

    // launching intents for activities

    private void launchAddActivity() {
        Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
        startActivityForResult(intent, ADD_ITEM_ACTIVITY);
    }

    private void launchCheckInventoryActivity() {
        Intent intent = new Intent(getApplicationContext(), CheckInventoryActivity.class);
        startActivityForResult(intent, CHECK_INVENTORY_ACTIVITY);
    }

    private void launchDailyReportActivity() {
        Intent intent = new Intent(getApplicationContext(), DailyReportActivity.class);
        startActivityForResult(intent, DAILY_REPORT_ACTIVITY);
    }

}
