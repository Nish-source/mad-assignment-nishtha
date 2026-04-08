package com.example.gallery;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE = 1;
    private static final int REQUEST_FOLDER = 2;

    private Bitmap capturedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button capture = findViewById(R.id.btnCapture);
        Button gallery = findViewById(R.id.btnGallery);

        capture.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE);
        });

        gallery.setOnClickListener(v ->
                startActivity(new Intent(this, FolderListActivity.class)));
        if (checkSelfPermission(android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 100);
        }
    }


    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);

        if (req == REQUEST_IMAGE && res == RESULT_OK) {

            if (data != null && data.getExtras() != null) {

                capturedBitmap = (Bitmap) data.getExtras().get("data");

                if (capturedBitmap != null) {
                    // Open folder picker AFTER capture
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                    startActivityForResult(intent, REQUEST_FOLDER);
                }

            } else {
                Toast.makeText(this, "Camera failed", Toast.LENGTH_SHORT).show();
            }
        }

        else if (req == REQUEST_FOLDER && res == RESULT_OK) {

            if (data != null) {
                Uri folderUri = data.getData();

                getContentResolver().takePersistableUriPermission(
                        folderUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION |
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                FolderListActivity.folderUris.add(folderUri);

                saveImage(folderUri);
            }
        }
    }

    private void saveImage(Uri folderUri) {
        try {
            DocumentFile folder = DocumentFile.fromTreeUri(this, folderUri);

            DocumentFile file = folder.createFile("image/jpeg",
                    "IMG_" + System.currentTimeMillis());

            OutputStream out = getContentResolver().openOutputStream(file.getUri());
            capturedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}