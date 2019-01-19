package com.example.bilhaghedeon.finalprojectghedeonkhaliq;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.bilhaghedeon.finalprojectghedeonkhaliq.dB.ItemDao;
import com.example.bilhaghedeon.finalprojectghedeonkhaliq.dB.ItemsRoomDatabase;
import com.example.bilhaghedeon.finalprojectghedeonkhaliq.model.Item;

import java.util.List;

/**
 * Implemented by Samina Khaliq
 */


public class DailyReportActivity extends Activity {


    private RecyclerView recItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_inventory);

        // bind recycler view UI to code

        recItems = findViewById(R.id.rec_items);

        recItems.addItemDecoration(new DividerItemDecoration(recItems.getContext(),
                DividerItemDecoration.VERTICAL));

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recItems.setLayoutManager(manager);

        // load items into recycler view
        loadItems();

    }

    private void loadItems() {

        RetrieveTask myTask = new RetrieveTask();

        myTask.execute();


    }

    // gets items from database
    private class RetrieveTask extends AsyncTask<Void, Void, List<Item>> {

        @Override
        protected List<Item> doInBackground(Void... voids) {

            ItemsRoomDatabase db =
                    ItemsRoomDatabase.getDatabase(getApplicationContext());

            ItemDao dao = db.itemDao();

            List<Item> items = dao.getAllQuantities();

            return items;
        }

        @Override
        protected void onPostExecute(List<Item> employees) {

            DailyReportItemAdapter adapter = new DailyReportItemAdapter(employees);
            recItems.setAdapter(adapter);

        }
    }
}
