package com.example.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.documentfile.provider.DocumentFile;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity implements ImageAdapter.OnClick {

    ArrayList<DocumentFile> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_gallery);

        RecyclerView recycler = findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));

        Uri folderUri = Uri.parse(getIntent().getStringExtra("folder"));
        DocumentFile dir = DocumentFile.fromTreeUri(this, folderUri);

        for (DocumentFile file : dir.listFiles()) {
            if (file.getType() != null && file.getType().startsWith("image")) {
                images.add(file);
            }
        }

        recycler.setAdapter(new ImageAdapter(images, this));
    }

    @Override
    public void onClick(DocumentFile file) {
        Intent intent = new Intent(this, ImageDetailActivity.class);
        intent.putExtra("uri", file.getUri().toString());
        startActivity(intent);
    }
}