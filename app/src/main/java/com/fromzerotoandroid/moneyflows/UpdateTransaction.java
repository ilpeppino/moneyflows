package com.fromzerotoandroid.moneyflows;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;


public class UpdateTransaction extends AppCompatActivity {

    public static final String TAG = "UpdateTransaction";
    String position, IdTimestamp, cost, description, category, date;

    EditText editTextCost, editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_trx);
        Toolbar toolbar = (Toolbar) findViewById(R.id.details_trx_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get intent from History list when Update is selected in context menu
        Intent myIntent = getIntent();
        IdTimestamp = myIntent.getStringExtra("IdTimestamp");
        cost = myIntent.getStringExtra("Cost");
        description = myIntent.getStringExtra("Description");
        category = myIntent.getStringExtra("Category");
        date = myIntent.getStringExtra("Date");

        // Hook the edit texts in UpdateTransaction details_trx_activity
        editTextCost = (EditText) findViewById(R.id.details_trx_cost);
        editTextDescription = (EditText) findViewById(R.id.details_trx_description);
        TextView textViewCategory = (TextView) findViewById(R.id.details_trx_category);
        TextView textViewDate = (TextView) findViewById(R.id.details_trx_date);

        // Get index for category
        int ind = Arrays.asList(Helper.CATEGORY_NAMES).indexOf(category);


        // Set text from clicked item to be updated
        editTextCost.setText(cost, TextView.BufferType.EDITABLE);
        editTextCost.setSelectAllOnFocus(true);
        editTextDescription.setText(description, TextView.BufferType.EDITABLE);
        editTextDescription.setSelectAllOnFocus(true);
        textViewCategory.setText(category);
        textViewCategory.setBackgroundResource(Helper.CATEGORY_COLORS[ind]);
        textViewDate.setText(date);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    public void onUpdateTrxClick(View v) {


        Log.d(TAG, "Clicked update transaction");

        editTextCost = (EditText) findViewById(R.id.details_trx_cost);
        editTextDescription = (EditText) findViewById(R.id.details_trx_description);
        String newCost = editTextCost.getText().toString();
        String newDescription = editTextDescription.getText().toString();

        BackgroundTask backgroundTask = new BackgroundTask(this);
        // The execute method trigger the doInBackground method in the backgroundtask
        backgroundTask.execute(FeedReaderContract.Methods.UPDATE_ROW, IdTimestamp, newCost, newDescription, category, date);
        Intent returnIntent = new Intent();


        returnIntent.putExtra("result", RESULT_OK);

        returnIntent.putExtra("newCost", newCost);
        returnIntent.putExtra("newDescription", newDescription);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }
}
