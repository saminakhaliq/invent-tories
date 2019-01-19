package com.example.bilhaghedeon.finalprojectghedeonkhaliq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/*
*
* Barcode / Qr code scanner
* Using the ZXing Android Library
*
* */

public class ScanCodeActivity extends Activity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    public void handleResult(Result result) {

        // return the barcode / Qr code to the main activity
        Intent intent = new Intent();
        //MainActivity.code = result.getText();
        intent.putExtra("code", result.getText());
        setResult(RESULT_OK, intent);
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();

    }
}
