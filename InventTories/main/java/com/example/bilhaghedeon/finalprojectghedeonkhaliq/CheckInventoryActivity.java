package com.example.bilhaghedeon.finalprojectghedeonkhaliq;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.bilhaghedeon.finalprojectghedeonkhaliq.dB.ItemDao;
import com.example.bilhaghedeon.finalprojectghedeonkhaliq.dB.ItemsRoomDatabase;
import com.example.bilhaghedeon.finalprojectghedeonkhaliq.model.Item;

import java.util.List;
/**
 * Implemented by Samina Khaliq
 */

public class CheckInventoryActivity extends Activity {

    // status code for update activity
    private static final int UPDATE_ACTIVITY = 5;

    private RecyclerView recItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_inventory);

        // onCreate binds recycler view to code

        recItems = findViewById(R.id.rec_items);

        recItems.addItemDecoration(new DividerItemDecoration(recItems.getContext(),
                DividerItemDecoration.VERTICAL));

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recItems.setLayoutManager(manager);

        // loads items into recycler view
        loadItems();

    }

    // populates recycler view
    private void loadItems() {

        RetrieveTask myTask = new RetrieveTask();

        myTask.execute();
    }


    // retrieve all items from database
    private class RetrieveTask extends AsyncTask<Void, Void, List<Item>> {

        @Override
        protected List<Item> doInBackground(Void... voids) {

            ItemsRoomDatabase db =
                    ItemsRoomDatabase.getDatabase(getApplicationContext());

            ItemDao dao = db.itemDao();

            List<Item> items = dao.getAllItems();

            return items;
        }


        @Override
        protected void onPostExecute(final List<Item> items) {


            CheckInventoryItemAdapter adapter = new CheckInventoryItemAdapter(items);
            recItems.setAdapter(adapter);


            // after recycler view is populated add item touch listener
            // boilerplate code found on stack overflow:

            recItems.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recItems, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {


                    // will launch the update activity with edit text fields filled with current item info

                    String itemQuantity = String.format("%d", items.get(position).getQuantity());


                    Intent intent = new Intent(getApplicationContext(), UpdateItemActivity.class);
                    intent.putExtra("name", items.get(position).getName());
                    intent.putExtra("barcode", items.get(position).getBarcode());
                    intent.putExtra("description", items.get(position).getDescription());
                    intent.putExtra("quantity", itemQuantity);
                    intent.putExtra("price", items.get(position).getPrice());
                    startActivityForResult(intent, UPDATE_ACTIVITY);


                }

                @Override
                public void onLongClick(View view, int position) {

                }

            }));


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // update the recycler view when coming back from updating the item
        if (requestCode == UPDATE_ACTIVITY) {
            if (resultCode == RESULT_OK) {

                loadItems();
            }
        }
    }

}
