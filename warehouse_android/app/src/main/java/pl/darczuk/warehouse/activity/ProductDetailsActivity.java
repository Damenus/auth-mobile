package pl.darczuk.warehouse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.darczuk.warehouse.R;
import pl.darczuk.warehouse.activity.model.Product;

public class ProductDetailsActivity extends AppCompatActivity {

    Product product;
    EditText editTextNumber;
    TextView textView19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Deleted", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                HttpClient.deleteProduct(product);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        product = (Product) bundle.getSerializable("Product");

        TextView textView10 = findViewById(R.id.textView10);
        textView10.setText("ID");
        TextView textView11 = findViewById(R.id.textView11);
        textView11.setText(product.getId().toString());

        TextView textView12 = findViewById(R.id.textView12);
        textView12.setText("modelName");
        TextView textView13 = findViewById(R.id.textView13);
        textView13.setText(product.getModelName());

        TextView textView14 = findViewById(R.id.textView14);
        textView14.setText("manufacturerName");
        TextView textView15 = findViewById(R.id.textView15);
        textView15.setText(product.getManufacturerName());

        TextView textView16 = findViewById(R.id.textView16);
        textView16.setText("price");
        TextView textView17 = findViewById(R.id.textView17);
        textView17.setText(product.getPrice().toString());

        TextView textView18 = findViewById(R.id.textView18);
        textView18.setText("quantity");
        textView19 = findViewById(R.id.textView19);
        textView19.setText(String.valueOf(product.getQuantity()));

        editTextNumber = findViewById(R.id.editTextNumber);

        Button buttonDecrease = findViewById(R.id.buttonDecrease);
        Button buttonIncrease = findViewById(R.id.buttonIncrease);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonPlus = findViewById(R.id.buttonPlus);

        buttonMinus.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String old_val = editTextNumber.getText().toString();
                int int_val = Integer.parseInt(old_val);
                if (int_val > 0)
                    int_val = int_val - 1;
                editTextNumber.setText(String.valueOf(int_val));
            }
        });

        buttonPlus.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String old_val = editTextNumber.getText().toString();
                int int_val = Integer.parseInt(old_val);
                int_val = int_val + 1;
                editTextNumber.setText(String.valueOf(int_val));
            }
        });

        buttonIncrease.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String result = HttpClient.increaseProduct(product, editTextNumber.getText().toString());

                JSONObject productJson = null;
                try {
                    productJson = new JSONObject(result);
                    product = new Product(
                            Long.decode(productJson.getString("id")),
                            productJson.getString("modelName"),
                            productJson.getString("manufacturerName"),
                            productJson.getDouble("price"),
                            productJson.getInt("quantity")
                    );
                    textView19 = findViewById(R.id.textView19);
                    textView19.setText(String.valueOf(product.getQuantity()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editTextNumber.setText(String.valueOf(0));

            }
        });

        buttonDecrease.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String result =HttpClient.decreaseProduct(product, editTextNumber.getText().toString());

                JSONObject productJson = null;
                try {
                    productJson = new JSONObject(result);
                    product = new Product(
                            Long.decode(productJson.getString("id")),
                            productJson.getString("modelName"),
                            productJson.getString("manufacturerName"),
                            productJson.getDouble("price"),
                            productJson.getInt("quantity")
                    );
                    textView19 = findViewById(R.id.textView19);
                    textView19.setText(String.valueOf(product.getQuantity()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editTextNumber.setText(String.valueOf(0));

            }
        });

    }

}
