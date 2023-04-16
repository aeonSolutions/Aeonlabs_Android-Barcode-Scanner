package aeonlabs.barcodescanner.common.libraries.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;

import aeonlabs.barcodescanner.common.libraries.R;
import aeonlabs.common.libraries.PlugIns.BarCodeScanner.CodeScanner;
import aeonlabs.common.libraries.PlugIns.BarCodeScanner.CodeScannerView;
import aeonlabs.common.libraries.PlugIns.BarCodeScanner.DecodeCallback;
import aeonlabs.common.libraries.activities.ActivityBase;
import aeonlabs.common.libraries.activities.ActivityBaseObservable;

public class BarCodeScanActivity extends ActivityBase implements ActivityBaseObservable {

    private CodeScanner mCodeScanner;
    private ActivityBase activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_common_barcode_scanner);

        activity=this;

        CodeScannerView scannerView = findViewById(aeonlabs.common.libraries.R.id.scanner_view);
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (result != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] args= new String[2];
                        args[0]="BarCodeScanActivity";
                        args[1]=result.getText();
                        activity.notifyObserversActivity(args);
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    /******************************************************************************/
    @Override
    public void notifyObserversActivity(String... args) {

    }
}
