package com.muhammadyaseenfatimamazharsarfarz.pdfreader;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PdfSelectListener {
    private ImageView imgPdf;
    private PdfAdapter pdfAdapter;
    private List<File> pdfList;
    private RecyclerView recyclerView;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgPdf = findViewById(R.id.imgPdf);

        runTimePermission();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


    }

    private void runTimePermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                displayPdf();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "Permission is Required", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    public ArrayList<File> findPdfFile(File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        if (files != null) {
            for (File singlefile : files) {
                if (singlefile.isDirectory() && !singlefile.isHidden()) {
                    arrayList.addAll(findPdfFile(singlefile));
                } else {
                    if (singlefile.getName().endsWith(".pdf")) {
                        arrayList.add(singlefile);
                    }
                }

            }
        }
        return arrayList;

    }

    private void displayPdf() {
        recyclerView = findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        pdfList = new ArrayList<>();
        pdfList.addAll(findPdfFile(Environment.getExternalStorageDirectory()));
        pdfAdapter = new PdfAdapter(this, pdfList, this :: onPdfFileSelected);
        recyclerView.setAdapter(pdfAdapter);


    }

    @Override
    public void onPdfFileSelected(File file) {
        startActivity(new Intent(MainActivity.this, DocumentsActivity.class)
                .putExtra("path", file.getAbsolutePath()));


    }
}