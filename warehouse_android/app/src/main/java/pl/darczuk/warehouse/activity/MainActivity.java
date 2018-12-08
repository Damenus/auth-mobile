package pl.darczuk.warehouse.activity;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import java.util.List;

import pl.darczuk.warehouse.R;
import pl.darczuk.warehouse.activity.model.Product;
import pl.darczuk.warehouse.activity.util.Properties;
import pl.darczuk.warehouse.activity.view.ProductViewModel;

public class MainActivity extends AppCompatActivity implements ProductFragment.OnListFragmentInteractionListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Properties properties;

    public Activity getActivity(){
        return this;
    }

    private String getToken() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("warehouse", Context.MODE_PRIVATE);
        String defaultValue = "";
        String token = sharedPref.getString("Token", defaultValue);
        return token;
    }


    @Override
    public void onListFragmentInteraction(Product product) {
        Intent activityIntent;
        activityIntent = new Intent(this, ProductDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Product", product);
        activityIntent.putExtras(bundle);
        activityIntent.putExtra("token", getToken());
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
                activityIntent.putExtra("token", getToken());
                startActivity(activityIntent);
            }
        });


    }

}
