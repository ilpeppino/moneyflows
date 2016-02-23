package com.fromzerotoandroid.moneyflows;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HistoryList extends AppCompatActivity {

    public static final String QUERY_ALL = "select * from " + FeedReaderContract.CostEntry.TABLE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();

        // result will contain the result of the query. It must be defined as ListArray
        List<ListViewItem> listViewItems = new ArrayList<ListViewItem>();



        // Here the database is queried and returns the values from db in result
        // Best practice is to use try-catch
        try {
            DbOperations dbOperations = new DbOperations(this);
            SQLiteDatabase db = dbOperations.getWritableDatabase();

            Cursor c = db.rawQuery(QUERY_ALL, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String cost = c.getString(c.getColumnIndex(FeedReaderContract.CostEntry.COLUMN_NAME_COST));
                        String category = c.getString(c.getColumnIndex(FeedReaderContract.CostEntry.COLUMN_NAME_CATEGORY));
                        String desc = c.getString(c.getColumnIndex(FeedReaderContract.CostEntry.COLUMN_NAME_DESCRIPTION));
                        String date = c.getString(c.getColumnIndex(FeedReaderContract.CostEntry.COLUMN_NAME_DATE));

                        ListViewItem lvItem = new ListViewItem();
                        lvItem.cost = cost;
                        lvItem.category = category;
                        lvItem.date = date;
                        listViewItems.add(lvItem);

                    } while (c.moveToNext());

                }
            }
        } catch (Exception e) {
            Log.e("History", "Error during database processing");
        }

        setContentView(R.layout.activity_history_list);
        ListView listview = (ListView) findViewById(R.id.listView);
//       listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, result));
        listview.setAdapter(new CustomAdapter(this, listViewItems));





//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    class ListViewItem {

        public String cost, date, category;

    }

}
