package pl.darczuk.warehouse.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.view.View;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import pl.darczuk.warehouse.R;
import pl.darczuk.warehouse.activity.dummy.DummyContent;
import pl.darczuk.warehouse.activity.model.Product;
import pl.darczuk.warehouse.activity.util.Util;

public class MainActivity extends AppCompatActivity implements ProductFragment.OnListFragmentInteractionListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onListFragmentInteraction(Product product) {
        Intent activityIntent;
        activityIntent = new Intent(this, ProductDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Product", product);
        activityIntent.putExtras(bundle);
        startActivity(activityIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityIntent;
                activityIntent = new Intent(getBaseContext(), EditProductActivity.class);
                startActivity(activityIntent);
            }
        });

    }

}
