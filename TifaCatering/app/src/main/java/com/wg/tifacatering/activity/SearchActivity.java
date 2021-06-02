package com.wg.tifacatering.activity;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.wg.tifacatering.R;

public class SearchActivity extends AppCompatActivity {

    Toolbar toolbarSearch;
    MaterialSearchView materialSearchView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbarSearch = findViewById(R.id.toolbar_search);
//        materialSearchView = findViewById(R.id.search_bar);
        searchView = findViewById(R.id.search_bar);
        searchView.requestFocus();
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();


        setSupportActionBar(toolbarSearch);
        setTitle("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search, menu);
//        MenuItem item = menu.findItem(R.id.search);
//        materialSearchView.setMenuItem(item);
        searchView.setIconifiedByDefault(false);
        return super.onCreateOptionsMenu(menu);
    }

}
