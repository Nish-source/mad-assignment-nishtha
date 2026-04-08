package com.example.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FolderListActivity extends AppCompatActivity {

    public static ArrayList<Uri> folderUris = new ArrayList<>();

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_folder_list);

        ListView listView = findViewById(R.id.listView);

        ArrayList<String> names = new ArrayList<>();
        for (Uri uri : folderUris) {
            names.add(uri.getLastPathSegment());
        }

        listView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names));

        listView.setOnItemClickListener((p, v, pos, id) -> {
            Intent i = new Intent(this, GalleryActivity.class);
            i.putExtra("folder", folderUris.get(pos).toString());
            startActivity(i);
        });
    }
}