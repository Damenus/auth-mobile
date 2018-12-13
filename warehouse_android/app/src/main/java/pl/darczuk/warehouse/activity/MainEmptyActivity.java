package pl.darczuk.warehouse.activity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import pl.darczuk.warehouse.R;
import pl.darczuk.warehouse.activity.util.Properties;
import pl.darczuk.warehouse.activity.util.Util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class MainEmptyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent activityIntent;

        Context context = this;

        SharedPreferences sharedPref = this.getApplication().getSharedPreferences("warehouse", Context.MODE_PRIVATE);
        String token = sharedPref.getString("Token", "");
        String uuidApp = sharedPref.getString("UUIDApp", "");

        if (uuidApp == "")
        {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("UUIDApp", UUID.randomUUID().toString());
            editor.commit();
        }

        // go straight to main if a token is stored
        if (token != "") {
            Boolean isValidToken = false;
            try {
                isValidToken = new CheckTokenTask(token).execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (isValidToken)
                activityIntent = new Intent(this, MainActivity.class);
            else
                activityIntent = new Intent(this, LoginActivity.class);
        } else {
            activityIntent = new Intent(this, LoginActivity.class);
        }

        startActivity(activityIntent);
        finish();
    }

    public class CheckTokenTask extends AsyncTask<Void, Void, Boolean> {

        String mToken;

        CheckTokenTask(String token) {
            mToken = token;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                URL myUrl = new URL(Properties.getInstance().WAREHOUSE_URL+ "/testToken");

                HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty ("Authorization", mToken);
                connection.setDoInput(true);
                connection.setDoOutput(true);


                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.flush();
                writer.close();
                os.close();

                connection.connect();

                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());

                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                String inputLine="";
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }

                reader.close();
                streamReader.close();


                if(stringBuilder.toString() == "OK") {
                    return true;
                } else {
                    return false;
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;

        }



    }

}
