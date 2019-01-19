package com.example.bilhaghedeon.finalprojectghedeonkhaliq;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bilhaghedeon.finalprojectghedeonkhaliq.dB.ItemDao;
import com.example.bilhaghedeon.finalprojectghedeonkhaliq.dB.ItemsRoomDatabase;
import com.example.bilhaghedeon.finalprojectghedeonkhaliq.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Implemented by Bilha Ghedeon and Samina Khaliq
 */
public class AddItemActivity extends Activity {

    // status code to send to the camera scanner activity
    private static final int SECOND_ACTIVITY = 2;

    Button btnAdd;
    ImageButton btnScan;

    // global variable for the qr or barcode that will be scanned
    public String code;

    // edit text that will hold all information about an item
    EditText edtDescription;
    EditText edtName;
    EditText edtBarcode;
    EditText edtPrice;
    EditText edtQuantity;


    //Implemented by Bilha Ghedeon
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // bind UI elements to code

        edtDescription = findViewById(R.id.edt_description);

        edtName = findViewById(R.id.edt_name);
        edtBarcode = findViewById(R.id.edt_barcode);
        edtPrice = findViewById(R.id.edt_price);
        edtQuantity = findViewById(R.id.edt_quantity);
        btnAdd = findViewById(R.id.btn_add);

        btnScan = findViewById(R.id.btn_scan);

        // To scan means launching scanner activity which will open up camera ZXing barcode scanner
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ScanCodeActivity.class);
                startActivityForResult(intent, SECOND_ACTIVITY);

            }
        });

        // add Item will validate input and add the item entered to the database
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addItem();


            }
        });
    }


     // Implemented by Samina Khaliq

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SECOND_ACTIVITY) {

            if (resultCode == RESULT_OK) {

                // when camera scans item, it will return the code
                // code will be put into global variable
                code = data.getStringExtra("code");
                // Toast.makeText(getApplicationContext(),code,Toast.LENGTH_SHORT).show();

                // make an a info task that will load information from QR json or barcode API
                AddItemActivity.InfoTask myTask = new AddItemActivity.InfoTask();

                // figure out whether it is a json or QR code
                if (code.matches("[0-9]+")) {

                    // this is the call for the barcode API
                    // the key allows for us to make 50 calls only
                    // the code is inserted inside the link
                    myTask.execute("https://api.barcodelookup.com/v2/products?barcode=" + code + "&formatted=y&key=iata7snie5lr72jzylc2ykgha78z0n");
                } else
                    myTask.execute(code);
            }

        }

    }

    // Implemented by Samina Khaliq

    private class InfoTask extends AsyncTask<String, Void, String> {

        // dialog box will show Please wait as networking tasks completes

        private ProgressDialog dialog = new ProgressDialog(AddItemActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            // will get the json string
            return loadInfo(strings[0]);
        }

        @Override
        protected void onPostExecute(String jsonString) {

            // please wait dialog will be dismissed
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            // this will parse the json string gotten from the Qr code url or Barcode api
            parseJson(jsonString);

        }
    }

    // parses json string, same json structure for both qr code and barcode

    /**
     * Implemented by Bilha Ghedeon
     */
    private void parseJson(String itemJson) {

        try {

            JSONObject obj = new JSONObject(itemJson);
            JSONArray products = obj.getJSONArray("products");
            JSONObject productsObj = products.getJSONObject(0);
            String description = productsObj.getString("description");
            String name = productsObj.getString("product_name");
            String quantity = productsObj.getString("barcode_number");

            edtDescription.setText(description);
            edtBarcode.setText(quantity);
            edtName.setText(name);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


    }




     // Implemented by Bilha Ghedeon
    // goes to url and retrieves the json string
    private String loadInfo(String jsonURL) {


        try {
            URL url = new URL(jsonURL);

            URLConnection urlConnection = url.openConnection();

            // cast the URLConnection into an HttpURLConnection
            HttpURLConnection httpURLConnection =
                    (HttpURLConnection) urlConnection;

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder builder = new StringBuilder();

                InputStream is = httpURLConnection.getInputStream();

                Scanner scanner = new Scanner(is);

                while (scanner.hasNextLine()) {
                    builder.append(scanner.nextLine());
                }

                Log.e("AddItemActivity", builder.toString());

                return builder.toString();
            } else {
                return "Didn't get HTTP_OK";
            }
        } catch (Exception e) {
            try {
                return e.toString();
            } catch (Exception ex) {
                return ex.toString();
            }
        }
    }

    //Implemented by Samina Khaliq
    // will validate user input, make new item, and insert inside the database

    public void addItem() {

        // create an item object
        Item newItem = new Item("", "", "", 0, 0.00);


        // get information from edit texts
        String name = edtName.getText().toString();
        String description = edtDescription.getText().toString();

        String barcode = edtBarcode.getText().toString();

        String enteredQuantity = edtQuantity.getText().toString();
        String enteredPrice = edtPrice.getText().toString();

        double price = 0.00;
        int quantity = 0;

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

        else if (name.trim().length() == 0) {
            edtName.setError("Enter name");
            edtName.requestFocus();
        } else if (description.trim().length() == 0) {
            edtBarcode.setError("Enter barcode");
            edtBarcode.requestFocus();

        } else if (enteredQuantity.trim().length() == 0) {
            edtQuantity.setError("Enter quantity");
            edtQuantity.requestFocus();

        }


        // checking to see if quantity and price values are too big for their types

        else if (enteredQuantity.length() >= 10) {
            try {


                quantity = Integer.parseInt(enteredQuantity);

            } catch (Exception e) {
                edtQuantity.setError("Quantity amount too high");
                edtQuantity.requestFocus();

            }
        } else if (enteredPrice.trim().length() == 0) {
            edtPrice.setError("Enter price");
            edtPrice.requestFocus();

        } else if (enteredPrice.length() >= 10) {
            try {


                price = Double.parseDouble(enteredPrice);

            } catch (Exception e) {
                edtQuantity.setError("Invalid price amount");
                edtQuantity.requestFocus();


            }

        } else {

            // make new item with verified input

            quantity = Integer.parseInt(enteredQuantity);
            price = Double.parseDouble(enteredPrice);
            newItem.setName(name);
            newItem.setDescription(description);
            newItem.setPrice(price);
            newItem.setQuantity(quantity);
            newItem.setBarcode(barcode);


            // add item to the database

            InsertTask myTask = new InsertTask();

            myTask.execute(newItem);

            // go back to main

            finish();
        }


    }

    //Implemented by Samina Khaliq

    // add item to database
    private class InsertTask extends AsyncTask<Item, Void, Long> {

        @Override
        protected Long doInBackground(Item... items) {


            ItemsRoomDatabase db =
                    ItemsRoomDatabase.getDatabase(getApplicationContext());

            ItemDao dao = db.itemDao();

            long id = dao.insert(items[0]);


            return id;


        }

        @Override
        protected void onPostExecute(Long aLong) {

            // toast will relay on to main activity
            Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_LONG).show();
        }
    }
}
