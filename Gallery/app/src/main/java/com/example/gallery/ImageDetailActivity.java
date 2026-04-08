package com.example.gallery;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_image_detail);

        Uri uri = Uri.parse(getIntent().getStringExtra("uri"));
        DocumentFile file = DocumentFile.fromSingleUri(this, uri);

        ImageView img = findViewById(R.id.imageView);
        TextView txt = findViewById(R.id.txtDetails);
        Button del = findViewById(R.id.btnDelete);

        img.setImageURI(uri);

        String info = "Name: " + file.getName() +
                "\nSize: " + file.length()/1024 + " KB" +
                "\nDate: " + new SimpleDateFormat("dd/MM/yyyy")
                .format(new Date(file.lastModified()));

        txt.setText(info);

        del.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", (d, w) -> {
                        file.delete();
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}