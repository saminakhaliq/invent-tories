package com.example.bilhaghedeon.finalprojectghedeonkhaliq;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bilhaghedeon.finalprojectghedeonkhaliq.dB.ItemDao;
import com.example.bilhaghedeon.finalprojectghedeonkhaliq.dB.ItemsRoomDatabase;
import com.example.bilhaghedeon.finalprojectghedeonkhaliq.model.Item;

/**
 * Implemented by Bilha Ghedeon
 */

public class UpdateItemActivity extends Activity {

    Button btnUpdate, btnDelete;

    EditText edtDescription;
    EditText edtName;
    EditText edtBarcode;
    EditText edtPrice;
    EditText edtQuantity;

    TextView txtDisplay;

    Item newItem = new Item();

    String deleteItem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);


        // bind UI elements to code
        btnUpdate = findViewById(R.id.btn_update);
        edtDescription = findViewById(R.id.edt_description);
        ;
        edtName = findViewById(R.id.edt_name);
        edtBarcode = findViewById(R.id.edt_barcode);
        edtPrice = findViewById(R.id.edt_price);
        edtQuantity = findViewById(R.id.edt_quantity);
        txtDisplay = findViewById(R.id.txt_display);
        btnDelete = findViewById(R.id.btn_delete);


        // get information from intent and load into edit texts

        final String barcode, name, description, quantity, price;

        Intent intent = getIntent();
        description = intent.getStringExtra("description");
        name = intent.getStringExtra("name");
        barcode = intent.getStringExtra("barcode");
        quantity = intent.getStringExtra("quantity");
        price = String.valueOf(intent.getDoubleExtra("price", 0.00));

        // delete item requires a name, store name in case user wants to delete item
        deleteItem = name;

        edtDescription.setText(description);
        edtName.setText(name);
        edtBarcode.setText(barcode);
        edtQuantity.setText(quantity);
        edtPrice.setText(price);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // update the item and return to check inventory
                InsertTask myInsertTask = new InsertTask();
                myInsertTask.execute(addItem());
                setResult(RESULT_OK);
                finish();


            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // delete the item and return to check inventory
                MyDeleteTask myDeleteTask = new MyDeleteTask();
                myDeleteTask.execute(deleteItem);
                setResult(RESULT_OK);
                finish();


            }
        });


    }


    // delete item

    private class MyDeleteTask extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... values) {

            ItemsRoomDatabase db = ItemsRoomDatabase.getDatabase(getApplicationContext());
            ItemDao dao = db.itemDao();

            String name = values[0];
            dao.deleteItemByName(name);


            return null;
        }


    }

    // update item

    private class InsertTask extends AsyncTask<Item, Void, Long> {

        @Override
        protected void onPreExecute() {

            // to update item it will first delete the existing one
            MyDeleteTask myTask = new MyDeleteTask();
            myTask.execute(deleteItem);
        }

        @Override
        protected Long doInBackground(Item... items) {

            // insert updated item into database

            ItemsRoomDatabase db =
                    ItemsRoomDatabase.getDatabase(getApplicationContext());

            ItemDao dao = db.itemDao();

            long id = dao.insert(items[0]);

            return id;

        }

        @Override
        protected void onPostExecute(Long aLong) {

            // will go over into the check inventory activity
            Toast.makeText(getApplicationContext(), "Item updated", Toast.LENGTH_LONG).show();
        }
    }


    // since updating item deletes the old and replaces it with new item
    // this method will add the newly updated item
    // will validate user input, make new item, and insert inside the database


    public Item addItem() {


        // get information from edit texts

        String name = edtName.getText().toString();
        String description = edtDescription.getText().toString();

        String barcode = edtBarcode.getText().toString();

        String enteredQuantity = edtQuantity.getText().toString();
        String enteredPrice = edtPrice.getText().toString();


        // if all fields are empty
        if (name.trim().length() == 0 && description.trim().length() == 0
                && enteredQuantity.trim().length() == 0 &&
                enteredPrice.trim().length() == 0) {

            edtName.setError("Enter name");
            edtName.requestFocus();
            edtBarcode.setError("Enter barcode");
            edtDescription.setError("Enter description");
            edtQuantity.setError("Enter quantity");
            edtPrice.setError("Enter price");


        }

        // if individual fields are empty

        if (name.trim().length() == 0) {
            edtName.setError("Enter name");
            edtName.requestFocus();

        }

        if (description.trim().length() == 0) {
            edtBarcode.setError("Enter barcode");
            edtBarcode.requestFocus();

        }

        if (enteredQuantity.trim().length() == 0) {
            edtQuantity.setError("Enter quantity");
            edtQuantity.requestFocus();

        }

        if (enteredPrice.trim().length() == 0) {
            edtPrice.setError("Enter price");
            edtPrice.requestFocus();

        }


        // checking to see if quantity and price values are too big for their types

        try {

            int quantity = Integer.parseInt(enteredQuantity);
            newItem.setQuantity(quantity);

        } catch (Exception e) {
            edtQuantity.setError("Quantity amount too high");
            edtQuantity.requestFocus();

        }


        try {

            double price = Double.parseDouble(enteredPrice);
            newItem.setPrice(price);

        } catch (Exception e) {
            edtQuantity.setError("Invalid price amount");
            edtQuantity.requestFocus();

        }

        // sets new item to have updated information
        newItem.setName(name);
        newItem.setDescription(description);
        newItem.setBarcode(barcode);


        // returns item to be executed in insert task
        return newItem;

    }
}
