package pl.darczuk.warehouse.activity;

import pl.darczuk.warehouse.activity.util.Util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class MainEmptyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent activityIntent;

        Context context = this;
        SharedPreferences token = context.getSharedPreferences("warehouse-token", Context.MODE_PRIVATE);

        // go straight to main if a token is stored
        if (Util.getToken() == null) {
            activityIntent = new Intent(this, MainActivity.class);
        } else {
            activityIntent = new Intent(this, LoginActivity.class);
        }

        startActivity(activityIntent);
        finish();
    }
}
